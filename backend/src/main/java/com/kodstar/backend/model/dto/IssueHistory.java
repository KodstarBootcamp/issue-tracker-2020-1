package com.kodstar.backend.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(Include.NON_NULL)
public class IssueHistory {

  private Long issueId;
  private String subject;
  private String action;
  private String field;
  @JsonRawValue
  private String oldValue;
  @JsonRawValue
  private String newValue;
  private String title;
  private String description;
  private String assignees;
  @JsonRawValue
  private String labels;
  private String project;
  private String category;
  private String state;
  private LocalDateTime createdAt;

}
