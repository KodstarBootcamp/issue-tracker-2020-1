package com.kodstar.backend.repository;

import com.kodstar.backend.model.entity.IssueEntity;
import com.kodstar.backend.model.entity.LabelEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface IssueRepository extends JpaRepository<IssueEntity,Long>{

    Collection<IssueEntity> findByTitleContaining(String key, Sort sort);
    Collection<IssueEntity> findByDescriptionContaining(String searchWord, Sort sort);
    Collection<IssueEntity> findByLabels(LabelEntity labelEntity, Sort sort);

}
