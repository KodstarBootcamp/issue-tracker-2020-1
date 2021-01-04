package com.kodstar.backend.model.entity;

import com.kodstar.backend.model.enums.State;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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

}
