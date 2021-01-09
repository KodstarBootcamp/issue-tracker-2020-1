package com.kodstar.backend.controller;

import com.kodstar.backend.model.dto.User;
import com.kodstar.backend.model.entity.UserEntity;
import com.kodstar.backend.service.impl.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = {"*"})
@Tag(name = "auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authServiceImpl;

    @PostMapping("/register")
    @Operation(summary = "User register")
    public ResponseEntity<User> register(@Valid @RequestBody UserEntity userEntity){

        return new ResponseEntity(authServiceImpl.register(userEntity), HttpStatus.CREATED);
    }

    // Not completed
    @PostMapping("/login")
    @Operation(summary = "User login")
    public ResponseEntity login(@Valid @RequestBody UserEntity user){

        return null;
    }

    // Not completed
    @PostMapping("/logout")
    @Operation(summary = "User logout")
    public ResponseEntity logout(){

        return null;
    }
}
