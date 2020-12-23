package com.kodstar.backend.service;

import com.kodstar.backend.model.dto.Issue;
import com.kodstar.backend.model.entity.IssueEntity;
import com.kodstar.backend.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;
    private final ModelMapper modelMapper;

    @Override
    public Issue saveIssueEntity(Issue issue) {

        IssueEntity issueEntity = convertToEntity(issue);

        issueEntity = issueRepository.save(issueEntity);

        return convertToDTO(issueEntity);
    }

    @Override
    public Collection<Issue> getAllIssues() {
        return issueRepository.findAll()
                .stream()
                .map(issue -> convertToDTO(issue))
                .collect(Collectors.toList());
    }

    @Override
    public Issue convertToDTO(IssueEntity issueEntity) {
        Issue issue = modelMapper.map(issueEntity, Issue.class);
        return issue;
    }

    @Override
    public IssueEntity convertToEntity(Issue issue) {

        IssueEntity issueEntity = modelMapper.map(issue, IssueEntity.class);
        return issueEntity;
    }
}

