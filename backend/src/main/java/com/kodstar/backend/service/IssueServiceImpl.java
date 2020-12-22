package com.kodstar.backend.service;

import com.kodstar.backend.model.dto.Issue;
import com.kodstar.backend.model.entity.IssueEntity;
import com.kodstar.backend.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;


@Service
@Transactional
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService{

    private final IssueRepository issueRepository;

    @Override
    public Issue saveIssueEntity(Issue issue) {
        return null;
    }

    @Override
    public Collection<Issue> getAllIssues() {
        return null;
    }
}
