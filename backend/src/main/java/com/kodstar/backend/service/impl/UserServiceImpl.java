package com.kodstar.backend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodstar.backend.model.dto.User;
import com.kodstar.backend.model.entity.UserEntity;
import com.kodstar.backend.repository.UserRepository;
import com.kodstar.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

   @Override
    public User getUserById(Long id) {
        UserEntity userEntity = checkId(id);

        return convertToDTO(userEntity);
    }

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll().stream()
                .map(userEntity -> convertToDTO(userEntity))
                .collect(Collectors.toList());
    }

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
    @Override
    public UserEntity getLoginUser(){

      String username = SecurityContextHolder.getContext().getAuthentication().getName();

      UserEntity userEntity = userRepository.findByUsername(username)
              .orElseThrow(() -> new EntityNotFoundException("Error: User not found for this name " + username));

      return userEntity;
    }
}
