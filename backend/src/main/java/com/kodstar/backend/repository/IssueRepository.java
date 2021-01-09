package com.kodstar.backend.repository;

import com.kodstar.backend.model.entity.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;

public interface IssueRepository extends JpaRepository<IssueEntity,Long>{

    Collection<IssueEntity> findByProjectEntityAndTitleContaining(ProjectEntity projectEntity, String key, Sort sort);
    Collection<IssueEntity> findByProjectEntityAndDescriptionContaining(ProjectEntity projectEntity, String searchWord, Sort sort);
    Collection<IssueEntity> findByProjectEntityAndLabels(ProjectEntity projectEntity, LabelEntity labelEntity, Sort sort);
    Collection<IssueEntity> findByProjectEntity(ProjectEntity projectEntity,Sort sort);
    Collection<IssueEntity> findByProjectEntity(ProjectEntity projectEntity);

}
