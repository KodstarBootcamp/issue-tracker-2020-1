package com.kodstar.backend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodstar.backend.model.auth.ApplicationUser;
import com.kodstar.backend.model.dto.User;
import com.kodstar.backend.model.entity.UserEntity;
import com.kodstar.backend.repository.UserRepository;
import com.kodstar.backend.service.AuthService;
import com.kodstar.backend.utils.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public User register(UserEntity userEntity) {
        if(!PasswordValidator.isValid(userEntity.getPassword())){
            throw new IllegalArgumentException("Password is not valid");
        }
        userEntity.setPassword(encoder.encode(userEntity.getPassword()));
        userEntity = userRepository.save(userEntity);
        return convertToDTO(userEntity);
    }

    // Not completed
    @Override
    public User login(ApplicationUser applicationUser) {

        UserEntity userEntity = userRepository.getUserEntityByUsername(applicationUser.getUsername())
                .orElseThrow(()->new UsernameNotFoundException(String.format("Error: %s not found") + applicationUser.getUsername()));

        if(encoder.encode(applicationUser.getPassword()).equals(userEntity.getPassword())){
            return convertToDTO(userEntity);
        }
/*
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(applicationUser.getUsername(), applicationUser.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));

 */

        //??
        throw new InvalidParameterException();
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
