package com.kodstar.backend.repository;

import com.kodstar.backend.model.entity.CommentEntity;
import com.kodstar.backend.model.entity.IssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CommentRepository extends JpaRepository<CommentEntity,Long> {

    Collection<CommentEntity> findAllByIssueEntity(IssueEntity issueEntity);
}
