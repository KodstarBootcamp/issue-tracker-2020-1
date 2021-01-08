package com.kodstar.backend.controller;

import com.kodstar.backend.model.auth.ApplicationUser;
import com.kodstar.backend.model.dto.User;
import com.kodstar.backend.model.entity.UserEntity;
import com.kodstar.backend.service.AuthService;
import com.kodstar.backend.service.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = {"*"})
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserEntity userEntity){

        return new ResponseEntity(authService.register(userEntity), HttpStatus.CREATED);
    }

    // Not completed
    @PostMapping("/login")
    public String login(@Valid @RequestBody ApplicationUser user){

        return "login";
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody ApplicationUser applicationUser) {

        return null;
    }

    // Not completed
    @PostMapping("/logout")
    public ResponseEntity logout(){

        return null;
    }
}
