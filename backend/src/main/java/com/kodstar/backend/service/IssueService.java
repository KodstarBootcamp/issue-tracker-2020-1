package com.kodstar.backend.service;

import com.kodstar.backend.model.dto.Issue;

import java.util.Collection;

public interface IssueService {

    Issue saveIssueEntity(Issue issue);
    Collection<Issue> getAllIssues();
}
