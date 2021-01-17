package com.kodstar.backend.service;

import com.kodstar.backend.model.dto.*;
import com.kodstar.backend.model.entity.*;
import java.util.Collection;
import java.util.Set;


public interface IssueHistoryService {

  void save(IssueEntity issueEntity);
  void update(IssueEntity issueEntity, Issue issue);
  Collection<IssueHistory> getIssueHistories(Long issueId);
  void addedComment(Comment comment);
  void assignedUser(IssueEntity issueEntity, Set<UserEntity> assignees);
}
