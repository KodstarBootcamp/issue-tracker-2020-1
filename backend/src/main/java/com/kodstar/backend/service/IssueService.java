package com.kodstar.backend.service;

import com.kodstar.backend.model.dto.Issue;
import com.kodstar.backend.model.entity.IssueEntity;
import com.kodstar.backend.model.entity.LabelEntity;
import java.util.Collection;


public interface IssueService extends Converter<Issue, IssueEntity> {

    Issue saveIssueEntity(Issue issue);
    Collection<Issue> getAllIssues();
    Collection<String> getAllLabels();
    Issue updateIssueEntity(Long id,Issue issue);
    Issue findById(Long id);
    void deleteIssue(Long id);
}
