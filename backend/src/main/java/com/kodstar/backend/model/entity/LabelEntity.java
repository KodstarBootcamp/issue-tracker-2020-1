package com.kodstar.backend.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
@Table(name = "label")
public class LabelEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 50)
    private  String name;

    @Column(length = 6)
    private String color;

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
