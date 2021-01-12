package com.kodstar.backend.service;

import com.kodstar.backend.model.dto.*;
import com.kodstar.backend.model.entity.*;
import com.kodstar.backend.utils.Converter;
import org.springframework.data.domain.Sort;
import java.util.Collection;
import java.util.Set;


public interface IssueService extends Converter<Issue, IssueEntity> {

    Issue saveIssueEntity(Issue issue);
    Issue updateIssueEntity(Long id,Issue issue);
    Issue findById(Long id);
    void deleteIssue(Long id);

    //to use in label service
    Collection<IssueEntity> findAll();

    void deleteMultipleIssues(BatchDeleteRequest request);

    //to use search and sort
    Collection<Issue> findByProjectAndTitleContaining(Long projectId, String title, Sort sort);
    Collection<Issue> findByProjectAndDescriptionContaining(Long projectId, String searchWord, Sort sort);
    Collection<Issue> findByProjectAndLabels(Long projectId, LabelEntity labelEntity, Sort sort);
    Collection<Issue> findByProjectIdAndSort(Long id, Sort sort);
    Collection<Issue> findByProjectId(Long id);

    Issue assignUsersToIssue(Long id, Set<Long> assignees);
    Collection<Issue> findAllByUserId(Long userId);

}
