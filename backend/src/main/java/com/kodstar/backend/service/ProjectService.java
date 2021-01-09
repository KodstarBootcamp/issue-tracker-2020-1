package com.kodstar.backend.service;

import com.kodstar.backend.model.dto.Project;
import com.kodstar.backend.model.entity.ProjectEntity;
import com.kodstar.backend.utils.Converter;
import java.util.Collection;

public interface ProjectService extends Converter<Project, ProjectEntity> {

  Project saveProjectEntity(Project project);
  Collection<Project> getAllProjects();
  Project updateProjectEntity(Long id,Project project);
  Project findById(Long id);
  void deleteProject(Long id);
}
