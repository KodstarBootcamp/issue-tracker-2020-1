package com.kodstar.backend.service.impl;

import com.kodstar.backend.model.dto.BatchRequest;
import com.kodstar.backend.model.dto.Issue;
import com.kodstar.backend.model.dto.User;
import com.kodstar.backend.model.entity.IssueEntity;
import com.kodstar.backend.model.entity.LabelEntity;
import com.kodstar.backend.model.entity.ProjectEntity;
import com.kodstar.backend.model.entity.UserEntity;
import com.kodstar.backend.model.enums.IssueCategory;
import com.kodstar.backend.model.enums.State;
import com.kodstar.backend.repository.IssueRepository;
import com.kodstar.backend.repository.ProjectRepository;
import com.kodstar.backend.repository.UserRepository;
import com.kodstar.backend.service.IssueService;
import com.kodstar.backend.service.LabelService;
import com.kodstar.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {

  private final IssueRepository issueRepository;

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private LabelService labelService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  @Override
  public Issue findById(Long id) {

    IssueEntity issueEntity = issueRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Error: Issue not found for this id " + id));

    return convertToDTO(issueEntity);
  }

  @Override
  public void deleteIssue(Long id) {

    IssueEntity issueEntity = issueRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Error: Issue not found for this id " + id));

    issueEntity.setLabels(null);
    issueEntity.setUsers(null);
    issueRepository.delete(issueEntity);
  }

  @Override
  public Collection<IssueEntity> findAll() {
    return issueRepository.findAll();
  }

  @Override
  public void multipleIssues(BatchRequest request) {

    System.out.println(request.getMethod());

    if (!request.getMethod().equals("delete") && !request.getMethod().equals("close"))
      throw new IllegalArgumentException();

    // If we know the entities' ids, we can make direct fetching by findAllById.
    // It is simplest and more efficient.
    Collection<IssueEntity> batchIssues = issueRepository.findAllById(request.getIds());

    // We should get back an entity for each id
    // if sizes are not match, throw 404 not found
    if (batchIssues.size() != request.getIds().size())
      throw new EntityNotFoundException();

    if (request.getMethod().equals("delete")){
      batchIssues.forEach(issue -> {
                issue.setLabels(null);
                issue.setUsers(null);
              });

      issueRepository.deleteInBatch(batchIssues);
    }

    if (request.getMethod().equals("close")){
      batchIssues.forEach(issue -> {
        issue.setIssueState(State.CLOSED);
        issue.setIssueCategory(IssueCategory.FINISHED);
      });

      issueRepository.saveAll(batchIssues);
    }
  }

  @Override
  public Issue saveIssueEntity(Issue issue) {

    IssueEntity issueEntity = convertToEntity(issue);

    //check if label exist and then set id
    setIdFromExistingLabel(issueEntity);
    labelService.saveAll(issueEntity.getLabels());
    issueEntity = issueRepository.save(issueEntity);

    return convertToDTO(issueEntity);
  }

  @Override
  public Issue updateIssueEntity(Long id, Issue issue) {

    IssueEntity issueOldEntity = issueRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Error: Issue not found for this id " + id));

    IssueEntity issueEntityToUpdate = convertToEntity(issue);
    issueEntityToUpdate.setId(id);
    issueEntityToUpdate.setModified(LocalDateTime.now());
    issueEntityToUpdate.setOpenedBy(issueOldEntity.getOpenedBy());
    issueEntityToUpdate.setProjectEntity(issueOldEntity.getProjectEntity());

    if (issueEntityToUpdate.getIssueState().equals(State.CLOSED))
      issueEntityToUpdate.setIssueCategory(IssueCategory.FINISHED);

    setIdFromExistingLabel(issueEntityToUpdate);
    labelService.saveAll(issueEntityToUpdate.getLabels());

    Set<UserEntity> userEntities = issue.getUsers().stream().map(user -> userService.convertToEntity(user)).collect(Collectors.toSet());
    issueEntityToUpdate.setUsers(userEntities);

    issueEntityToUpdate = issueRepository.save(issueEntityToUpdate);

    return convertToDTO(issueEntityToUpdate);
  }

  @Override
  public Issue assignUsersToIssue(Long id, Set<Long> assignees) {
    // get IssueEntity by id.
    IssueEntity issueEntity = issueRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Error: Issue not found for this id " + id));

    // get new assignees from user table (UserEntity)
    Set<UserEntity> newAssignees = assignees.stream().map(userId -> {
      UserEntity userEntity = userRepository.findById(userId)
              .orElseThrow(() -> new EntityNotFoundException("Error: User not found for this id " + userId));
      return userEntity;
    }).collect(Collectors.toSet());

    // set assignees to the issueEntity
    issueEntity.setUsers(newAssignees);

    issueEntity = issueRepository.save(issueEntity);

    return convertToDTO(issueEntity);
  }

  @Override
  public Collection<Issue> findAllByUserId(Long userId) {

    UserEntity userEntity = userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException());

    return issueRepository.findByUsersContaining(userEntity)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toSet());
  }

  //Project related methods
  @Override
  public Collection<Issue> findByProjectId(Long projectId) {

    ProjectEntity projectEntity = getProject(projectId);

    return issueRepository.findByProjectEntity(projectEntity).stream()
            .map(issueEntity -> convertToDTO(issueEntity))
            .collect(Collectors.toList());
  }

  //Project related methods
  @Override
  public Map<String, Object> findByProjectId(Long projectId, int page, int size) {

    Sort sort = Sort.by(Sort.Order.desc("created"));
    Pageable pageable = PageRequest.of(page,size, sort);
    ProjectEntity projectEntity = getProject(projectId);
    Page<Issue> pageIssue = issueRepository.findByProjectEntity(projectEntity, pageable).map(this::convertToDTO);
    List<Issue> issues = pageIssue.getContent();
    Map<String, Object> response = new HashMap<>();

    response.put("issues", issues);
    response.put("currentPage", pageIssue.getNumber());
    response.put("totalItems", pageIssue.getTotalElements());
    response.put("totalPages", pageIssue.getTotalPages());

    return response;

  }

  @Override
  public Collection<Issue> findByProjectIdAndSort(Long projectId, Sort sort) {

    ProjectEntity projectEntity = getProject(projectId);

    return issueRepository.findByProjectEntity(projectEntity,sort).stream()
            .map(issueEntity -> convertToDTO(issueEntity))
            .collect(Collectors.toList());
  }

  @Override
  public Collection<Issue> findByProjectAndTitleContaining(Long projectId, String title, Sort sort) {

    ProjectEntity projectEntity = getProject(projectId);

    return issueRepository.findByProjectEntityAndTitleContaining(projectEntity, title, sort).stream()
            .map(issueEntity -> convertToDTO(issueEntity))
            .collect(Collectors.toList());
  }

  @Override
  public Collection<Issue> findByProjectAndLabels(Long projectId, LabelEntity labelEntity, Sort sort) {

    ProjectEntity projectEntity = getProject(projectId);

    return issueRepository.findByProjectEntityAndLabels(projectEntity, labelEntity, sort).stream()
            .map(issueEntity -> convertToDTO(issueEntity)).collect(Collectors.toList());
  }

  @Override
  public Collection<Issue> findByProjectAndDescriptionContaining(Long projectId, String searchWord, Sort sort) {

    ProjectEntity projectEntity = getProject(projectId);

    return issueRepository.findByProjectEntityAndDescriptionContaining(projectEntity, searchWord, sort).stream()
            .map(issueEntity -> convertToDTO(issueEntity)).collect(Collectors.toList());
  }

  //Helper methods
  @Override
  public Issue convertToDTO(IssueEntity issueEntity) {

    Issue issue = new Issue();

    ProjectEntity projectEntity = getProject(issueEntity.getProjectEntity().getId());
    UserEntity userEntity = userRepository.findById(issueEntity.getOpenedBy().getId()).get();

    issue.setId(issueEntity.getId());
    issue.setTitle(issueEntity.getTitle());
    issue.setDescription(issueEntity.getDescription());
    issue.setLabels(issueEntity.getLabels());
    issue.setCategory(issueEntity.getIssueCategory().toString().toLowerCase());
    issue.setState(issueEntity.getIssueState().toString().toLowerCase());
    issue.setProjectId(projectEntity.getId());
    issue.setOpenedBy(userEntity.getUsername());

    if (issueEntity.getUsers() != null) {
      Set<User> users = issueEntity.getUsers().stream()
              .map(user -> userService.convertToDTO(user))
              .collect(Collectors.toSet());
      issue.setUsers(users);
    }

    return issue;
  }

  @Override
  public IssueEntity convertToEntity(Issue issue) {

    //Convert explicitly, handling is easier for this case
    IssueEntity issueEntity = new IssueEntity();
    ProjectEntity projectEntity = getProject(issue.getProjectId());

    issueEntity.setDescription(issue.getDescription());
    issueEntity.setTitle(issue.getTitle());
    issueEntity.setId(issue.getId());
    issueEntity.setLabels(issue.getLabels());
    issueEntity.setIssueCategory(IssueCategory.fromString(issue.getCategory()));
    issueEntity.setIssueState(State.fromString(issue.getState()));
    issueEntity.setProjectEntity(projectEntity);
    issueEntity.setOpenedBy(getLoginUser());

    return issueEntity;
  }

  private void setIdFromExistingLabel(IssueEntity source) {

    Set<LabelEntity> labelEntities = labelService.findAll().stream()
            .collect(Collectors.toSet());

    for (LabelEntity label : labelEntities) {
      source.getLabels().stream()
              .filter(l -> l.equals(label))
              .findFirst()
              .ifPresent(entity -> entity.setId(label.getId()));
    }
  }

  private ProjectEntity getProject(Long projectId) {

    ProjectEntity projectEntity = projectRepository.findById(projectId)
            .orElseThrow(() -> new EntityNotFoundException("Error: Project not found for this id " + projectId));

    return projectEntity;
  }


  private UserEntity getLoginUser(){

    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    UserEntity userEntity = userRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("Error: User not found for this name " + username));

    return userEntity;

  }
}

