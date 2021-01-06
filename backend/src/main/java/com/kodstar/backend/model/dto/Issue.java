package com.kodstar.backend.model.dto;

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
    private String category;
    private String state;
    private Long projectId;

    public Issue(String title) {
        this.title = title;
    }
}
