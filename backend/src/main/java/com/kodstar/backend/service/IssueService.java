package com.kodstar.backend.service;

import com.kodstar.backend.model.dto.BatchDeleteRequest;
import com.kodstar.backend.model.dto.Issue;
import com.kodstar.backend.model.entity.IssueEntity;
import com.kodstar.backend.model.entity.LabelEntity;
import org.springframework.data.domain.Sort;

import java.util.Collection;


public interface IssueService extends Converter<Issue, IssueEntity> {

    Issue saveIssueEntity(Issue issue);
    Collection<Issue> getAllIssues();
    Issue updateIssueEntity(Long id,Issue issue);
    Issue findById(Long id);
    void deleteIssue(Long id);

    //to use in label service
    Collection<IssueEntity> findAll();
    Collection<Issue> findAll(Sort sort);

    void deleteMultipleIssues(BatchDeleteRequest request);
    Collection<Issue> findByTitleContaining(String title, Sort sort);
    Collection<Issue> findByDescriptionContaining(String searchWord, Sort sort);
    Collection<Issue> findByLabels(LabelEntity labelEntity, Sort sort);
    Collection<Issue> findByProjectId(Long id);

}
