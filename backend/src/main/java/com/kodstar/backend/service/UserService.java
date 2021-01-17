package com.kodstar.backend.service;

import com.kodstar.backend.model.dto.User;
import com.kodstar.backend.model.entity.UserEntity;
import com.kodstar.backend.utils.Converter;
import java.util.List;


public interface UserService extends Converter<User, UserEntity>{

    User getUserById(Long id);
    public List<User> getAllUsers();
    User editUserEntity(Long id, User user);
    void deleteUserEntity(Long id);
    UserEntity getLoginUser();
}
