package com.kodstar.backend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodstar.backend.model.auth.LoginRequest;
import com.kodstar.backend.model.dto.User;
import com.kodstar.backend.model.entity.UserEntity;
import com.kodstar.backend.repository.UserRepository;
import com.kodstar.backend.response.JwtResponse;
import com.kodstar.backend.security.jwt.JwtUtils;
import com.kodstar.backend.security.userdetails.UserDetailsImpl;
import com.kodstar.backend.utils.Converter;
import com.kodstar.backend.utils.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthServiceImpl implements Converter<User, UserEntity> {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtils jwtUtils;

    public User register(UserEntity userEntity) {
        if(!PasswordValidator.isValid(userEntity.getPassword())){
            throw new IllegalArgumentException("Password is not valid");
        }
        userEntity.setPassword(encoder.encode(userEntity.getPassword()));
        userEntity = userRepository.save(userEntity);
        return convertToDTO(userEntity);
    }

    public ResponseEntity<?> login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok( new JwtResponse(jwt,
                                                   userDetails.getId(),
                                                   userDetails.getUsername(),
                                                   userDetails.getEmail()));
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
