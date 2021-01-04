package com.kodstar.backend.service.impl;

import com.kodstar.backend.model.dto.Project;
import com.kodstar.backend.model.entity.ProjectEntity;
import com.kodstar.backend.model.enums.State;
import com.kodstar.backend.repository.ProjectRepository;
import com.kodstar.backend.service.ProjectService;
import lombok.RequiredArgsConstructor;
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

  @Override
  public Project convertToDTO(ProjectEntity projectEntity) {

    Project project = new Project();

    project.setId(projectEntity.getId());
    project.setName(projectEntity.getName());
    project.setDescription(projectEntity.getDescription());
    project.setState(projectEntity.getProjectState().toString().toLowerCase());

    return project;
  }

  @Override
  public ProjectEntity convertToEntity(Project project) {

    ProjectEntity projectEntity = new ProjectEntity();

    projectEntity.setId(project.getId());
    projectEntity.setName(project.getName());
    projectEntity.setDescription(project.getDescription());
    projectEntity.setProjectState(State.fromString(project.getState()));

    return projectEntity;
  }
}
