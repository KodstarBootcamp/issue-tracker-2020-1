package com.kodstar.backend.model.entity;

import com.kodstar.backend.model.enums.*;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name = "issue",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"title","project_id"})
})
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

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private IssueCategory issueCategory;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private State issueState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id",nullable = false)
    private ProjectEntity projectEntity;

    public void removeLabel(LabelEntity entity){
        this.labels.remove(entity);
    }

}
