package com.kodstar.backend.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kodstar.backend.model.entity.LabelEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Issue {

    private Long id;
    private String title;
    private String description;
    private Set<LabelEntity> labels;
    private Set<User> users;
    private String category;
    private String state;
    private Long projectId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String openedBy;

    public Issue(String title) {
        this.title = title;
    }
}
