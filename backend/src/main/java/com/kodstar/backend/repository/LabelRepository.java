package com.kodstar.backend.repository;

import com.kodstar.backend.model.entity.LabelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LabelRepository extends JpaRepository<LabelEntity,Long>{

    Optional<LabelEntity> findByName(String name);
}
