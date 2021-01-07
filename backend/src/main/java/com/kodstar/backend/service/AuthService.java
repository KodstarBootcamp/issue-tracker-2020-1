package com.kodstar.backend.service;

import com.kodstar.backend.model.dto.User;
import com.kodstar.backend.model.entity.UserEntity;
import com.kodstar.backend.utils.Converter;

public interface AuthService extends Converter<User, UserEntity> {

    User register(UserEntity user);
    User login(User user);
    void logout(User user);

}
