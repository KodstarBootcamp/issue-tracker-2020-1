package com.kodstar.backend.repository;

import com.kodstar.backend.model.entity.IssueHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface IssueHistoryRepository extends JpaRepository<IssueHistoryEntity, Long>{

  Collection<IssueHistoryEntity> findByIssueId(Long issueId);
}
