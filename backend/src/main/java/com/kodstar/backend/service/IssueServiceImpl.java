package com.kodstar.backend.service;

import com.kodstar.backend.model.dto.Issue;
import com.kodstar.backend.model.entity.IssueEntity;
import com.kodstar.backend.model.entity.LabelEntity;
import com.kodstar.backend.repository.IssueRepository;
import com.kodstar.backend.repository.LabelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {

    private final LabelRepository labelRepository;
    private final IssueRepository issueRepository;

    @Override
    public Issue findById(Long id) {

        IssueEntity issueEntity = issueRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error: Issue not found for this id " + id));

        return convertToDTO(issueEntity);
    }

    @Override
    public void deleteIssue(Long id) {

        IssueEntity issueEntity = issueRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Error: Issue not found for this id " + id));

        issueRepository.delete(issueEntity);
    }

    @Override
    public Issue saveIssueEntity(Issue issue) {

        IssueEntity issueEntity = convertToEntity(issue);

        setIdFromExistingLabel(issueEntity);

        labelRepository.saveAll(issueEntity.getLabels());
        issueEntity = issueRepository.save(issueEntity);

        return convertToDTO(issueEntity);
    }

    @Override
    public Issue updateIssueEntity(Long id, Issue issue) {

        if(issueRepository.findById(id).isEmpty())
            throw new EntityNotFoundException("Error: Issue not found for this id " + id);

        IssueEntity issueEntityToUpdate = convertToEntity(issue);
        issueEntityToUpdate.setId(id);
        issueEntityToUpdate.setModified(LocalDateTime.now());

        setIdFromExistingLabel(issueEntityToUpdate);

        labelRepository.saveAll(issueEntityToUpdate.getLabels());
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
    public Collection<String> getAllLabels() {
        return labelRepository.findAll()
                .stream()
                .map(LabelEntity::getName)
                .collect(Collectors.toSet());
    }


    @Override
    public Issue convertToDTO(IssueEntity issueEntity) {
        Set<String> labels  = issueEntity.getLabels()
                .stream()
                .map(LabelEntity::getName)
                .collect(Collectors.toSet());

        Issue issue = new Issue();

        issue.setId(issueEntity.getId());
        issue.setTitle(issueEntity.getTitle());
        issue.setDescription(issueEntity.getDescription());
        issue.setLabels(labels);

        return issue;
    }

    @Override
    public IssueEntity convertToEntity(Issue issue) {
        //Convert explicitly, handling is easier for this case
        Set<LabelEntity> labels = issue.getLabels()
                .stream()
                .map(label -> {
                    LabelEntity entity = new LabelEntity();
                    entity.setName(label.trim().toLowerCase());
                    return entity;
                }).collect(Collectors.toSet());

        IssueEntity issueEntity = new IssueEntity();

        issueEntity.setDescription(issue.getDescription());
        issueEntity.setTitle(issue.getTitle());
        issueEntity.setId(issue.getId());
        issueEntity.setLabels(labels);

        return issueEntity;
    }

    private void setIdFromExistingLabel(IssueEntity source){

        Set<LabelEntity> labelEntities = labelRepository.findAll().stream()
                .collect(Collectors.toSet());

        for (LabelEntity label : labelEntities){
            source.getLabels().stream()
                    .filter(l -> l.equals(label))
                    .findFirst()
                    .ifPresent(entity -> entity.setId(label.getId()));
        }
    }

}

