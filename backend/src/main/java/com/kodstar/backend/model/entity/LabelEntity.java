package com.kodstar.backend.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
@Table(name = "label")
public class LabelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 50)
    private  String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LabelEntity entity = (LabelEntity) o;
        return name.equals(entity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
