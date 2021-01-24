package com.kodstar.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kodstar.backend.model.enums.State;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity()
@Table(name = "project")
public class ProjectEntity extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(length = 250,unique = true)
  private String name;

  @Column(length = 1500)
  private String description;

  @Column(name = "state")
  @Enumerated(EnumType.STRING)
  private State projectState;

  @OneToMany(mappedBy = "projectEntity", orphanRemoval = true)
  @JsonManagedReference
  private Set<IssueEntity> issueEntities;


  public void addIssue(IssueEntity issueEntity){

    if (issueEntities == null)
      this.issueEntities = new HashSet<>();

    this.issueEntities.add(issueEntity);
    issueEntity.setProjectEntity(this);
  }


}
