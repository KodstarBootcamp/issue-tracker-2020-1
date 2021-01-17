package com.kodstar.backend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodstar.backend.model.dto.*;
import com.kodstar.backend.model.entity.*;
import com.kodstar.backend.model.enums.*;
import com.kodstar.backend.repository.IssueHistoryRepository;
import com.kodstar.backend.service.IssueHistoryService;
import lombok.RequiredArgsConstructor;
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


  @Override
  public void save(String username, IssueEntity issueEntity) {

    IssueHistoryEntity issueHistoryEntity = IssueHistoryEntity.builder()
            .subject(username)
            .issueId(issueEntity.getId())
            .action("add")
            .title(issueEntity.getTitle())
            .description(issueEntity.getDescription())
            .labels(convertSetToString(issueEntity.getLabels()))
            .assignees(convertSetToString(getUserSet(issueEntity)))
            .state(issueEntity.getIssueState().toString())
            .category(issueEntity.getIssueCategory().toString())
            .build();

    issueHistoryRepository.save(issueHistoryEntity);
  }

  @Override
  public void update(String username, IssueEntity oldIssueEntity, Issue issue) {

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
    }

    //Changed issue category
    if (!IssueCategory.fromString(issue.getCategory()).equals(oldIssueEntity.getIssueCategory())) {
      issueHistoryRepository.save(getByCategoryIssueHistoryEntity(username, oldIssueEntity, issue));
    }

    //Changed issue labels
    if (!equalsSet(oldIssueEntity.getLabels(), issue.getLabels())) {
      issueHistoryRepository.save(getByLabelsIssueHistoryEntity(username, oldIssueEntity, issue));
    }

    //Changed issue assignees
    if (!equalsSet(getUserSet(oldIssueEntity), issue.getUsers())) {
      issueHistoryRepository.save(getByAssigneesIssueHistoryEntity(username, oldIssueEntity, issue));
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

  //Helper methods
  private Set<User> getUserSet(IssueEntity issueEntity) {
    if (issueEntity.getUsers() == null) return null;

    Set<User> users = issueEntity.getUsers().stream().map(userEntity -> {
      User user = objectMapper.convertValue(userEntity, User.class);
      return user;
    }).collect(Collectors.toSet());
    return users;
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
            .action("updated")
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

  private IssueHistoryEntity getByAssigneesIssueHistoryEntity(String username, IssueEntity oldIssueEntity, Issue issue) {
    return IssueHistoryEntity.builder()
            .issueId(oldIssueEntity.getId())
            .subject(username)
            .action("updated")
            .field("assignees")
            .oldValue(convertSetToString(getUserSet(oldIssueEntity)))
            .newValue(convertSetToString(issue.getUsers()))
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
