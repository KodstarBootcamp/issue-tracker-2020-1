package com.kodstar.backend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodstar.backend.model.dto.User;
import com.kodstar.backend.model.entity.UserEntity;
import com.kodstar.backend.repository.AuthRepository;
import com.kodstar.backend.service.AuthService;
import com.kodstar.backend.utils.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncryptor encoder;

    @Override
    public User register(UserEntity userEntity) {
        if(!PasswordValidator.isValid(userEntity.getPassword())){
            throw new IllegalArgumentException("Password is not valid");
        }
        userEntity.setPassword(encoder.encryptPassword(userEntity.getPassword()));
        userEntity = authRepository.save(userEntity);
        return convertToDTO(userEntity);
    }

    // Not completed
    @Override
    public User login(User user) {
        //
        return null;
    }

    // Not completed
    @Override
    public void logout(User user) {

    }

    @Override
    public User convertToDTO(UserEntity userEntity) {
        return objectMapper.convertValue(userEntity,User.class);
    }

    @Override
    public UserEntity convertToEntity(User user) {
        return objectMapper.convertValue(user, UserEntity.class);
    }
}
