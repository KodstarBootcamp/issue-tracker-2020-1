package com.kodstar.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Issue {

    private Long id;
    private String title;
    private String description;
    private Set<String> labels;

    public Issue(String title) {
        this.title = title;
    }
}
