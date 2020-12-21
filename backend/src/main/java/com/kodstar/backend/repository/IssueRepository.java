package com.kodstar.backend.repository;

import com.kodstar.backend.model.entity.IssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<IssueEntity,Long> {
}
