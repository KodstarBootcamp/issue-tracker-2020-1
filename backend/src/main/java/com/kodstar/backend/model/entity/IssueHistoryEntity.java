package com.kodstar.backend.model.entity;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "issue_history")
@Builder
public class IssueHistoryEntity extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String subject;
  private String action;

  @Column(name = "issue_id")
  private Long issueId;
  private String field;

  @Column(name = "old_value")
  private String oldValue;

  @Column(name = "new_value")
  private String newValue;
  private String title;

}
