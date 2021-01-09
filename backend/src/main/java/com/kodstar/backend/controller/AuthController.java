package com.kodstar.backend.controller;

import com.kodstar.backend.model.auth.ApplicationUser;
import com.kodstar.backend.model.dto.User;
import com.kodstar.backend.model.entity.UserEntity;
import com.kodstar.backend.service.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = {"*"})
//@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    //return type
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserEntity userEntity){

        return new ResponseEntity(authService.register(userEntity), HttpStatus.CREATED);
    }

    // Not completed
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody ApplicationUser user){

        return authService.login(user);
    }

    // Not completed
    @PostMapping("/logout")
    public ResponseEntity logout(){

        return null;
    }
}
