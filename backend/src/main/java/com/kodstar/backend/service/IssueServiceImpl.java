package com.kodstar.backend.service;

import com.kodstar.backend.model.dto.Issue;
import com.kodstar.backend.model.entity.IssueEntity;
import com.kodstar.backend.model.enums.Label;
import com.kodstar.backend.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    @Override
    public Issue saveIssueEntity(Issue issue) {

        IssueEntity issueEntity = convertToEntity(issue);

        issueEntity = issueRepository.save(issueEntity);

        return convertToDTO(issueEntity);
    }

    @Override
    public Issue updateIssueEntity(long id,Issue issue) {

        IssueEntity issueEntityToUpdate = issueRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error: Issue not found for this id " + id));

        issueEntityToUpdate.setTitle(issue.getTitle());
        issueEntityToUpdate.setDescription(issue.getDescription());
        issueEntityToUpdate.setModified(LocalDateTime.now());

        Set<Label> labels = issue.getLabels().stream()
                .map(label -> Label.fromString(label))
               .collect(Collectors.toSet());
        issueEntityToUpdate.setLabels(labels);
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
    public Issue convertToDTO(IssueEntity issueEntity) {
        Issue issue = modelMapper.map(issueEntity, Issue.class);
        return issue;
    }

    @Override
    public IssueEntity convertToEntity(Issue issue) {

    //Convert explicitly, handling is easier for this case
        Set<Label> labels = issue.getLabels().stream()
                .map(label -> Label.fromString(label))
                .collect(Collectors.toSet());

        IssueEntity issueEntity = modelMapper.typeMap(Issue.class, IssueEntity.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getId(),
                            IssueEntity::setId);
                    mapper.map(src -> src.getTitle(),
                            IssueEntity::setTitle);
                    mapper.map(src -> src.getDescription(),
                            IssueEntity::setDescription);
                }).map(issue);

        issueEntity.setLabels(labels);
        return issueEntity;
    }

}

