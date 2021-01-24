package com.kodstar.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kodstar.backend.model.enums.*;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "issue_label", joinColumns = @JoinColumn(name = "issue_id"),
    inverseJoinColumns = @JoinColumn(name = "label_id"))
    private Set<LabelEntity> labels;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "issue_users", joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "users_id"))
    private Set<UserEntity> users;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private IssueCategory issueCategory;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private State issueState;

    @ManyToOne
    @JoinColumn(name = "project_id",nullable = false, updatable = false)
    @JsonBackReference
    private ProjectEntity projectEntity;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false,updatable = false)
    private UserEntity openedBy;

    @OneToMany(mappedBy = "issueEntity", orphanRemoval = true)
    @JsonManagedReference
    private Set<CommentEntity> commentEntities;

    public void addComment(CommentEntity commentEntity){

        if (commentEntities == null)
            this.commentEntities = new HashSet<>();

        this.commentEntities.add(commentEntity);
        commentEntity.setIssueEntity(this);
    }

    public void removeLabel(LabelEntity entity){
        this.labels.remove(entity);
    }

}
