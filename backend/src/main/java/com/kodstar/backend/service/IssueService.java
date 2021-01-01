package com.kodstar.backend.service;

import com.kodstar.backend.model.dto.BatchDeleteRequest;
import com.kodstar.backend.model.dto.Issue;
import com.kodstar.backend.model.dto.Label;
import com.kodstar.backend.model.entity.IssueEntity;

import java.util.Collection;


public interface IssueService extends Converter<Issue, IssueEntity> {

    Issue saveIssueEntity(Issue issue);
    Collection<Issue> getAllIssues();
    Collection<Label> getAllLabels();
    Issue updateIssueEntity(Long id,Issue issue);
    Issue findById(Long id);
    void deleteIssue(Long id);

    void deleteMultipleIssues(BatchDeleteRequest request);
}
