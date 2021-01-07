package com.kodstar.backend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodstar.backend.model.dto.Label;
import com.kodstar.backend.model.entity.LabelEntity;
import com.kodstar.backend.repository.LabelRepository;
import com.kodstar.backend.service.IssueService;
import com.kodstar.backend.service.LabelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class LabelServiceImpl implements LabelService {

    @Autowired
    private IssueService issueService;

    private final LabelRepository labelRepository;
    private final ObjectMapper objectMapper;


    @Override
    public Label saveLabelEntity(Label label) {
        LabelEntity labelEntity = convertToEntity(label);

        labelEntity = labelRepository.save(labelEntity);

        return convertToDTO(labelEntity);
    }

    @Override
    public Label findById(Long id) {
        LabelEntity labelEntity = labelRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Error: Label not found for this id " + id));

        return convertToDTO(labelEntity);
    }

    @Override
    public Optional<LabelEntity> findByName(String name) {
        return labelRepository.findByName(name);
    }

    @Override
    public Collection<Label> getAllLabels() {
        return labelRepository.findAll()
                .stream()
                .map(label->convertToDTO(label))
                .collect(Collectors.toList());
    }

    @Override
    public Label updateLabelEntity(Long id, Label label) {
        if(labelRepository.findById(id) == null)
                throw new EntityNotFoundException("Error: Label not found for this id " + id);

        LabelEntity labelEntityToUpdate = convertToEntity(label);
        labelEntityToUpdate.setId(id);
        labelEntityToUpdate.setModified(LocalDateTime.now());

        labelEntityToUpdate = labelRepository.save(labelEntityToUpdate);

        return convertToDTO(labelEntityToUpdate);
    }

    @Override
    public void deleteLabel(Long id) {
        LabelEntity labelEntity = labelRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Error: Label not found for this id " + id));

        issueService.findAll().stream()
                .forEach(issue->issue.removeLabel(labelEntity));
        labelRepository.delete(labelEntity);
    }

    @Override
    public void saveAll(Set<LabelEntity> labels) {
        labelRepository.saveAll(labels);
    }

    @Override
    public Collection<LabelEntity> findAll() {
        return labelRepository.findAll();
    }

    @Override
    public Label convertToDTO(LabelEntity labelEntity) {
        return objectMapper.convertValue(labelEntity,Label.class);
    }

    @Override
    public LabelEntity convertToEntity(Label label) {
        return objectMapper.convertValue(label,LabelEntity.class);
    }
}
