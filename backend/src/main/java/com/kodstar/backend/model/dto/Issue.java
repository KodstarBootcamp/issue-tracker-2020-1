package com.kodstar.backend.model.dto;

import com.kodstar.backend.model.enums.Label;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class Issue {

    private Long id;
    private String tittle;
    private String description;
    private Set<Label> labels;

}
