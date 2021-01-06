package com.kodstar.backend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodstar.backend.model.dto.User;
import com.kodstar.backend.model.entity.UserEntity;
import com.kodstar.backend.repository.UserRepository;
import com.kodstar.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    //changeUserPassword() will be implemented

    @Override
    public User editUserEntity(Long id,User user) {

        UserEntity userEntity = checkId(id);

        userEntity.setEmail(user.getEmail());
        userEntity.setUsername(user.getUsername());
        userEntity = userRepository.save(userEntity);

        return convertToDTO(userEntity);
    }

    @Override
    public void deleteUserEntity(Long id) {
        UserEntity userEntity = checkId(id);
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(Long id) {
        UserEntity userEntity = checkId(id);

      return convertToDTO(userEntity);
    }

    private UserEntity checkId(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Error: User not found for this id" + id));
        return  userEntity;
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
