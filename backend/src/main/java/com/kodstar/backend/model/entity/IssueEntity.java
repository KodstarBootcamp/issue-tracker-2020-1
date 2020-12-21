package com.kodstar.backend.model.entity;


import com.kodstar.backend.model.enums.Label;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "issue")
public class IssueEntity {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 250)
    private String title;

    @Column(length = 1500)
    private String description;

    @Enumerated(EnumType.STRING)
    private Set<Label> labels;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime created;

    @Column(name = "modified_at")
    private LocalDateTime modified;

    @PrePersist
    void onCreate(){

        created=LocalDateTime.now();
        modified=LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate(){
        modified = LocalDateTime.now();
    }

}
