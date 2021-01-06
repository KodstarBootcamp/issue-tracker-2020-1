package com.kodstar.backend.service;

import com.kodstar.backend.model.dto.User;
import com.kodstar.backend.model.entity.UserEntity;
import com.kodstar.backend.utils.Converter;


public interface UserService extends Converter<User, UserEntity> {

    User editUserEntity(Long id, User user);
    void deleteUserEntity(Long id);
    User getUserById(Long id);
}
