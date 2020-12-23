package com.kodstar.backend.model.entity;

import com.kodstar.backend.model.enums.Label;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "issue")
public class IssueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 250)
    private String title;

    @Column(length = 1500)
    private String description;


    @ElementCollection(targetClass = Label.class, fetch = FetchType.EAGER)
    @JoinTable(name = "issue_labels", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "label", nullable = false)
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
