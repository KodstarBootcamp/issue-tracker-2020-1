package com.kodstar.backend.model.entity;

import com.kodstar.backend.model.enums.IssueState;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name = "issue")
public class IssueEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 250)
    private String title;

    @Column(length = 1500)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "issue_label", joinColumns = @JoinColumn(name = "issue_id"),
    inverseJoinColumns = @JoinColumn(name = "label_id"))
    private Set<LabelEntity> labels;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private IssueState issueState;

}
