package com.kodstar.backend.service.impl;

import com.kodstar.backend.model.dto.Issue;
import com.kodstar.backend.model.entity.LabelEntity;
import com.kodstar.backend.service.*;
import com.kodstar.backend.utils.SearchAndSortFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional
public class IssueSearchAndSortFilterService extends SearchAndSortFilter<Issue> {

  @Autowired
  private IssueService issueService;

  @Autowired
  private LabelService labelService;

  @Override
  public Collection<Issue> filterAndSort(Long projectId, String field, String key, String sort) {

    List<Sort.Order> orders = getOrders(sort);

    switch (field) {
      case "labels":
        Optional<LabelEntity> labelEntity = labelService.findByName(key);

        if (labelEntity.isPresent())
          return issueService.findByProjectAndLabels(projectId, labelEntity.get(), Sort.by(orders));

      case "title":
        return issueService.findByProjectAndTitleContaining(projectId, key, Sort.by(orders));

      case "description":
        return issueService.findByProjectAndDescriptionContaining(projectId, key, Sort.by(orders));

      default:
        return issueService.findByProjectIdAndSort(projectId, Sort.by(orders));
    }
  }
}
