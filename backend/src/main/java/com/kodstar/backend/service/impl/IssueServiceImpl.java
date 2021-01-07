package com.kodstar.backend.service.impl;

import com.kodstar.backend.model.dto.BatchDeleteRequest;
import com.kodstar.backend.model.dto.Issue;
import com.kodstar.backend.model.dto.User;
import com.kodstar.backend.model.entity.IssueEntity;
import com.kodstar.backend.model.entity.LabelEntity;
import com.kodstar.backend.model.entity.ProjectEntity;
import com.kodstar.backend.model.entity.UserEntity;
import com.kodstar.backend.model.enums.IssueCategory;
import com.kodstar.backend.model.enums.State;
import com.kodstar.backend.repository.IssueRepository;
import com.kodstar.backend.repository.LabelRepository;
import com.kodstar.backend.repository.UserRepository;
import com.kodstar.backend.repository.ProjectRepository;
import com.kodstar.backend.service.IssueService;
import com.kodstar.backend.service.LabelService;
import com.kodstar.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
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
    private LabelRepository labelRepository;

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
    public Collection<Issue> findAll(Sort sort) {
        return issueRepository.findAll(sort).stream()
                .map(issueEntity -> convertToDTO(issueEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMultipleIssues(BatchDeleteRequest request) {

        if (!request.getMethod().equals("delete"))
            throw new IllegalArgumentException();

        // If we know the entities' ids, we can make direct fetching by findAllById.
        // It is simplest and more efficient.
        Collection<IssueEntity> deleteBatchIssues = issueRepository.findAllById(request.getIds());

        // We should get back an entity for each id
        // if sizes are not match, throw 404 not found
        if (deleteBatchIssues.size() != request.getIds().size())
            throw new EntityNotFoundException();

        deleteBatchIssues.stream()
                .forEach(issue -> {
                    issue.setLabels(null);
                    issue.setUsers(null);
                });

        issueRepository.deleteInBatch(deleteBatchIssues);
    }

    @Override
    public Collection<Issue> findByTitleContaining(String title, Sort sort) {
        return issueRepository.findByTitleContaining(title,sort).stream()
                .map(issueEntity -> convertToDTO(issueEntity))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Issue> findByLabels(LabelEntity labelEntity, Sort sort) {
        return issueRepository.findByLabels(labelEntity,sort).stream()
                .map(issueEntity -> convertToDTO(issueEntity)).collect(Collectors.toList());
    }

    @Override
    public Issue assignUsersToIssue(Long id, Set<User> assignees) {
        // get IssueEntity by id.
        IssueEntity issueEntity = issueRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Error: Issue not found for this id " + id));

        // get new assignees from user table (UserEntity)
        Set<UserEntity> newAssignees = assignees.stream().map(user -> {
            UserEntity userEntity = userRepository.findById(user.getId())
                    .orElseThrow(()->new EntityNotFoundException("Error: User not found for this id " + user.getId()));
            return userEntity;
        }).collect(Collectors.toSet());

        // set assignees to the issueEntity
        issueEntity.setUsers(newAssignees);

        issueEntity = issueRepository.save(issueEntity);

        return convertToDTO(issueEntity);
    }

    @Override
    public Collection<Issue> findByDescriptionContaining(String searchWord, Sort sort) {
        return issueRepository.findByDescriptionContaining(searchWord, sort).stream()
                .map(issueEntity -> convertToDTO(issueEntity)).collect(Collectors.toList());
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

        if(issueRepository.findById(id) == null)
            throw new EntityNotFoundException("Error: Issue not found for this id " + id);

        IssueEntity issueEntityToUpdate = convertToEntity(issue);
        issueEntityToUpdate.setId(id);
        issueEntityToUpdate.setModified(LocalDateTime.now());
        setIdFromExistingLabel(issueEntityToUpdate);
        labelService.saveAll(issueEntityToUpdate.getLabels());
        issueEntityToUpdate = issueRepository.save(issueEntityToUpdate);

        return convertToDTO(issueEntityToUpdate);
    }

    @Override
    public Collection<Issue> getAllIssues() {
        return issueRepository.findAll()
                .stream()
                .map(issue -> convertToDTO(issue))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Issue> findByProjectId(Long id){

        ProjectEntity projectEntity = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error: Project not found for this id " + id));

        return issueRepository.findByProjectEntity(projectEntity).stream()
                .map(issueEntity -> convertToDTO(issueEntity))
                .collect(Collectors.toList());
    }

    @Override
    public Issue convertToDTO(IssueEntity issueEntity) {

        Issue issue = new Issue();

        ProjectEntity projectEntity = projectRepository.findById(issueEntity.getProjectEntity().getId()).get();

        issue.setId(issueEntity.getId());
        issue.setTitle(issueEntity.getTitle());
        issue.setDescription(issueEntity.getDescription());
        issue.setLabels(issueEntity.getLabels());
        issue.setCategory(issueEntity.getIssueCategory().toString().toLowerCase());
        issue.setState(issueEntity.getIssueState().toString().toLowerCase());
        issue.setProjectId(projectEntity.getId());

        if(issueEntity.getUsers()!=null){
            Set<User> users = issueEntity.getUsers().stream()
                    .map(userEntity -> userService.convertToDTO(userEntity))
                    .collect(Collectors.toSet());
            issue.setUsers(users);
        }

        return issue;
    }

    @Override
    public IssueEntity convertToEntity(Issue issue) {

        //Convert explicitly, handling is easier for this case
        IssueEntity issueEntity = new IssueEntity();

        ProjectEntity projectEntity = projectRepository.findById(issue.getProjectId()).get();

        issueEntity.setDescription(issue.getDescription());
        issueEntity.setTitle(issue.getTitle());
        issueEntity.setId(issue.getId());
        issueEntity.setLabels(issue.getLabels());
        issueEntity.setIssueCategory(IssueCategory.fromString(issue.getCategory()));
        issueEntity.setIssueState(State.fromString(issue.getState()));
        issueEntity.setProjectEntity(projectEntity);

        return issueEntity;
    }

    private void setIdFromExistingLabel(IssueEntity source){

        Set<LabelEntity> labelEntities = labelService.findAll().stream()
                .collect(Collectors.toSet());

        for (LabelEntity label : labelEntities){
            source.getLabels().stream()
                    .filter(l -> l.equals(label))
                    .findFirst()
                    .ifPresent(entity -> entity.setId(label.getId()));
        }
    }
}

