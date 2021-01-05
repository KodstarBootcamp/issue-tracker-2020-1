package com.kodstar.backend.repository;

import com.kodstar.backend.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<UserEntity,Long> {
}
