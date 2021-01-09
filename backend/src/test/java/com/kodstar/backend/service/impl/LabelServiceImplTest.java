package com.kodstar.backend.service.impl;

import com.kodstar.backend.model.dto.Label;
import com.kodstar.backend.model.entity.LabelEntity;
import com.kodstar.backend.repository.LabelRepository;
import com.kodstar.backend.service.LabelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import javax.persistence.EntityNotFoundException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class LabelServiceImplTest {

  @Autowired
  private LabelService labelService;

  @MockBean
  private LabelRepository labelRepository;

  private Label label;

  @BeforeEach
  void setUp() {
    this.label = new Label();
    label.setId(1L);
    label.setName("backend");
    label.setColor("fe642e");
  }

  @Test
  @DisplayName("Test saveLabelEntity Success")
  void saveLabelEntity() {

    // Setup our mock repository
    LabelEntity labelEntity = labelService.convertToEntity(label);
    when(labelRepository.save(labelEntity)).thenReturn(labelEntity);

    // Execute the service call
    Label savedLabel = labelService.saveLabelEntity(label);

    // Assert the response
    assertNotNull(savedLabel);
    assertEquals(labelEntity.getColor(),savedLabel.getColor());

  }

  @Test
  @DisplayName("Test findById Success")
  void findById() {

    // Setup our mock repository
    LabelEntity labelEntity = labelService.convertToEntity(label);
    when(labelRepository.findById(1L)).thenReturn(Optional.of(labelEntity));

    // Execute the service call
    Label returnedLabel = labelService.findById(1L);

    // Assert the response
    assertEquals(labelEntity.getColor(),returnedLabel.getColor());

  }

  @Test
  @DisplayName("Test findById_NotFound")
  void findByIdNotFound() {

    // Setup our mock repository
    when(labelRepository.findById(1L)).thenReturn(Optional.empty());

    // Assert the response
    assertThrows(EntityNotFoundException.class,()->labelService.findById(1L));

  }

  @Test
  @DisplayName("Test findByName Success")
  void findByName() {
    // Setup our mock repository
    LabelEntity labelEntity = labelService.convertToEntity(label);
    when(labelRepository.findByName("backend")).thenReturn(Optional.of(labelEntity));

    // Execute the service call
    Optional<LabelEntity> returnedLabelEntity = labelService.findByName("backend");

    // Assert the response
    assertTrue(returnedLabelEntity.isPresent());
  }

  @Test
  @DisplayName("Test getAllLabels Success")
  void getAllLabels() {

    // Setup our mock repository
    LabelEntity labelEntity = labelService.convertToEntity(label);
    List<LabelEntity> labels = new ArrayList<>();
    labels.add(labelEntity);
    when(labelRepository.findAll()).thenReturn(labels);

    // Execute the service call
    Collection<Label> returnedLabels  = labelService.getAllLabels();

    // Assert the response
    assertEquals(returnedLabels.size(),1);
  }

  @Test
  @DisplayName("Test updateLabelEntity Success")
  void updateLabelEntity() {

    // Setup our mock repository
    LabelEntity labelEntity = labelService.convertToEntity(label);

    when(labelRepository.findById(1L)).thenReturn(Optional.of(labelEntity));
    when(labelRepository.save(labelEntity)).thenReturn(labelEntity);

    // Execute the service call
    Label updatedLabel = labelService.updateLabelEntity(1L,label);

    // Assert the response
    assertNotNull(updatedLabel);
    assertEquals(labelEntity.getColor(),updatedLabel.getColor());
  }

  @Test
  @DisplayName("Test deleteLabel Success")
  void deleteLabel() {

    // Setup our mock repository
    LabelEntity labelEntity = labelService.convertToEntity(label);
    when(labelRepository.findById(1L)).thenReturn(Optional.of(labelEntity));

    // Execute the service call
    labelService.deleteLabel(1L);

    // Assert the response
    verify(labelRepository,times(1)).delete(labelEntity);

  }

  @Test
  @DisplayName("Test saveAll Success")
  void saveAll() {

    // Setup our mock repository
    LabelEntity labelEntity = labelService.convertToEntity(label);
    Set<LabelEntity> labelEntities = new HashSet<>();
    labelEntities.add(labelEntity);
    List<LabelEntity> labels = new ArrayList<>();
    labels.add(labelEntity);
    when(labelRepository.saveAll(labelEntities)).thenReturn(labels);

    // Execute the service call
    labelService.saveAll(labelEntities);

    // Assert the response
    assertEquals(labels.size(),1);
    verify(labelRepository,times(1)).saveAll(labelEntities);
  }

  @Test
  @DisplayName("Test findAll Success")
  void findAll() {

    // Setup our mock repository
    LabelEntity labelEntity = labelService.convertToEntity(label);
    List<LabelEntity> labels = new ArrayList<>();
    labels.add(labelEntity);
    labels.add(labelEntity);
    when(labelRepository.findAll()).thenReturn(labels);

    // Execute the service call
    Collection<LabelEntity> returnedLabels  = labelService.findAll();

    // Assert the response
    assertEquals(returnedLabels.size(),2);
  }

}