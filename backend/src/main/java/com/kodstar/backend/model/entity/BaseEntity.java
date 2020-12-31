package com.kodstar.backend.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

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
