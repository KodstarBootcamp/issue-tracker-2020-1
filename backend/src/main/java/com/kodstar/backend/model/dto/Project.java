package com.kodstar.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Project {

  private Long id;
  private String name;
  private String description;
  private String state;

  public Project(String name){this.name = name;}
}
