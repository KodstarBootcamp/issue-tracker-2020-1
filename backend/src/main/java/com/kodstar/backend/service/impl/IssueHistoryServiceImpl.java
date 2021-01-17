package com.kodstar.backend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodstar.backend.model.dto.*;
import com.kodstar.backend.model.entity.*;
import com.kodstar.backend.model.enums.*;
import com.kodstar.backend.repository.IssueHistoryRepository;
import com.kodstar.backend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class IssueHistoryServiceImpl implements IssueHistoryService {

  private final IssueHistoryRepository issueHistoryRepository;
  private final ObjectMapper objectMapper;

  @Autowired
  private UserService userService;


  @Override
  public void save(IssueEntity issueEntity) {

    IssueHistoryEntity issueHistoryEntity = IssueHistoryEntity.builder()
            .subject(userService.getLoginUser().getUsername())
            .issueId(issueEntity.getId())
            .action("add")
            .title(issueEntity.getTitle())
            .build();

    issueHistoryRepository.save(issueHistoryEntity);
  }

  @Override
  public void update(IssueEntity oldIssueEntity, Issue issue) {

    String username = userService.getLoginUser().getUsername();
    //Changed issue title
    if (!issue.getTitle().equals(oldIssueEntity.getTitle())) {
      issueHistoryRepository.save(getByTitleIssueHistoryEntity(username, oldIssueEntity, issue));
    }

    //Changed issue description
    if (!issue.getDescription().equals(oldIssueEntity.getDescription())) {
      issueHistoryRepository.save(getByDescriptionIssueHistoryEntity(username, oldIssueEntity, issue));
    }

    //Changed issue state
    if (!State.fromString(issue.getState()).equals(oldIssueEntity.getIssueState())) {
      issueHistoryRepository.save(getByStateIssueHistoryEntity(username, oldIssueEntity, issue));
      if(issue.getState().equalsIgnoreCase("closed"))
        issueHistoryRepository.save(getByCategoryIssueHistoryEntity(username, oldIssueEntity, issue)).setNewValue(asJsonString("FINISHED"));
    }

    //Changed issue category
    if (!IssueCategory.fromString(issue.getCategory()).equals(oldIssueEntity.getIssueCategory())) {
      issueHistoryRepository.save(getByCategoryIssueHistoryEntity(username, oldIssueEntity, issue));
    }

    //Changed issue labels
    if (!equalsSet(oldIssueEntity.getLabels(), issue.getLabels())) {
      issueHistoryRepository.save(getByLabelsIssueHistoryEntity(username, oldIssueEntity, issue));
    }
  }

  @Override
  public Collection<IssueHistory> getIssueHistories(Long issueId) {

     return issueHistoryRepository.findByIssueId(issueId).stream().map(issueHistoryEntity -> {
     IssueHistory issueHistory = objectMapper.convertValue(issueHistoryEntity, IssueHistory.class);
     issueHistory.setCreatedAt(issueHistoryEntity.getCreated());

      return issueHistory;
    }).collect(Collectors.toList());
  }

  @Override
  public void addedComment(Comment comment){
    String username = userService.getLoginUser().getUsername();
    IssueHistoryEntity issueHistoryEntity = IssueHistoryEntity.builder()
            .issueId(comment.getIssueId())
            .subject(username)
            .action("added")
            .field("comment")
            .newValue(asJsonString(comment.getContent()))
            .build();

    issueHistoryRepository.save(issueHistoryEntity);

  }

  @Override
  public void assignedUser(IssueEntity issueEntity,Set<UserEntity> oldAssignees) {
    String username = userService.getLoginUser().getUsername();
    IssueHistoryEntity issueHistoryEntity = getByAssigneesIssueHistoryEntity(username,issueEntity,oldAssignees);
    issueHistoryRepository.save(issueHistoryEntity);
  }

  //Helper methods

  private Set<User> getUserSet(Set<UserEntity> users) {
    if (users == null) return null;

    return users.stream().map(userEntity -> {
      User user = objectMapper.convertValue(userEntity, User.class);
      return user;
    }).collect(Collectors.toSet());

  }

  private boolean equalsSet(Set<?> set1, Set<?> set2) {

    if (set1 == null || set2 == null) return true;

    if (set1.size() != set2.size()) return false;

    return set1.containsAll(set2);
  }

  private String convertSetToString(Set<?> set1){

    if (set1 == null || set1.isEmpty()) return null;

    return asJsonString(set1);
  }

  private IssueHistoryEntity getByTitleIssueHistoryEntity(String username, IssueEntity oldIssueEntity, Issue issue) {
    return IssueHistoryEntity.builder()
            .issueId(oldIssueEntity.getId())
            .subject(username)
            .action("updated")
            .field("title")
            .oldValue(asJsonString(oldIssueEntity.getTitle()))
            .newValue(asJsonString(issue.getTitle()))
            .build();
  }

  private IssueHistoryEntity getByCategoryIssueHistoryEntity(String username, IssueEntity oldIssueEntity, Issue issue) {
    return IssueHistoryEntity.builder()
            .issueId(oldIssueEntity.getId())
            .subject(username)
            .action("moved")
            .field("category")
            .oldValue(asJsonString(oldIssueEntity.getIssueCategory().toString()))
            .newValue(asJsonString(issue.getCategory().toUpperCase()))
            .build();

  }

  private IssueHistoryEntity getByStateIssueHistoryEntity(String username, IssueEntity oldIssueEntity, Issue issue) {
    return IssueHistoryEntity.builder()
            .issueId(oldIssueEntity.getId())
            .subject(username)
            .action("updated")
            .field("state")
            .oldValue(asJsonString(oldIssueEntity.getIssueState().toString()))
            .newValue(asJsonString(issue.getState().toUpperCase()))
            .build();
  }

  private IssueHistoryEntity getByDescriptionIssueHistoryEntity(String username, IssueEntity oldIssueEntity, Issue issue) {
    return IssueHistoryEntity.builder()
            .issueId(oldIssueEntity.getId())
            .subject(username)
            .action("updated")
            .field("description")
            .oldValue(asJsonString(oldIssueEntity.getDescription()))
            .newValue(asJsonString(issue.getDescription()))
            .build();
  }

  private IssueHistoryEntity getByAssigneesIssueHistoryEntity(String username, IssueEntity issueEntity,Set<UserEntity> oldAssignees) {
    return IssueHistoryEntity.builder()
            .issueId(issueEntity.getId())
            .subject(username)
            .action("assigned")
            .field("assignees")
            .oldValue(convertSetToString(getUserSet(oldAssignees)))
            .newValue(convertSetToString(getUserSet(issueEntity.getUsers())))
            .build();
  }

  private IssueHistoryEntity getByLabelsIssueHistoryEntity(String username, IssueEntity oldIssueEntity, Issue issue) {
    return IssueHistoryEntity.builder()
            .issueId(oldIssueEntity.getId())
            .subject(username)
            .action("updated")
            .field("labels")
            .oldValue(convertSetToString(oldIssueEntity.getLabels()))
            .newValue(convertSetToString(issue.getLabels()))
            .build();
  }

  private String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
