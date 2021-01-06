package com.kodstar.backend.repository;

import com.kodstar.backend.model.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<ProjectEntity,Long> {
}
