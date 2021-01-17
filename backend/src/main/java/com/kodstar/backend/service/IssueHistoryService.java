package com.kodstar.backend.service;

import com.kodstar.backend.model.dto.Issue;
import com.kodstar.backend.model.dto.IssueHistory;
import com.kodstar.backend.model.entity.IssueEntity;
import java.util.Collection;


public interface IssueHistoryService {

  void save(String username, IssueEntity issueEntity);
  void update(String username, IssueEntity issueEntity, Issue issue);
  Collection<IssueHistory> getIssueHistories(Long issueId);
}
