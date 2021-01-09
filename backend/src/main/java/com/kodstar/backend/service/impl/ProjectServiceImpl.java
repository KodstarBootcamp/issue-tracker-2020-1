package com.kodstar.backend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodstar.backend.model.dto.Project;
import com.kodstar.backend.model.entity.*;
import com.kodstar.backend.model.enums.State;
import com.kodstar.backend.repository.*;
import com.kodstar.backend.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

  private final ProjectRepository projectRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private IssueRepository issueRepository;

  @Override
  public Project findById(Long id) {

    ProjectEntity projectEntity = projectRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Error: Project not found for this id " + id));

    return convertToDTO(projectEntity);
  }

  @Override
  public void deleteProject(Long id) {

    ProjectEntity projectEntity = projectRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Error: Project not found for this id " + id));

    Collection<IssueEntity> deleteBatchIssues = issueRepository.findByProjectEntity(projectEntity);

    if(!deleteBatchIssues.isEmpty())
      deleteBatchIssues
              .forEach(issue -> issue.setLabels(null));
      issueRepository.deleteInBatch(deleteBatchIssues);

    projectRepository.delete(projectEntity);
  }

  @Override
  public Collection<Project> getAllProjects() {

    return projectRepository.findAll()
            .stream()
            .map(project -> convertToDTO(project))
            .collect(Collectors.toList());
  }

  @Override
  public Project saveProjectEntity(Project project) {

    ProjectEntity projectEntity = convertToEntity(project);

    projectEntity = projectRepository.save(projectEntity);

        return convertToDTO(projectEntity);
  }

  @Override
  public Project updateProjectEntity(Long id, Project project) {

    if(projectRepository.findById(id) == null)
      throw new EntityNotFoundException("Error: Project not found for this id " + id);

    ProjectEntity projectEntityToUpdate = convertToEntity(project);
    projectEntityToUpdate.setId(id);
    projectEntityToUpdate.setModified(LocalDateTime.now());
    projectEntityToUpdate = projectRepository.save(projectEntityToUpdate);

    return convertToDTO(projectEntityToUpdate);
  }

  //Helper methods

  @Override
  public Project convertToDTO(ProjectEntity projectEntity) {

    Project project = objectMapper.convertValue(projectEntity,Project.class);
    project.setState(projectEntity.getProjectState().toString().toLowerCase());

    return project;
  }

  @Override
  public ProjectEntity convertToEntity(Project project) {

    ProjectEntity projectEntity = objectMapper.convertValue(project,ProjectEntity.class);
    projectEntity.setProjectState(State.fromString(project.getState()));

    return projectEntity;
  }
}
