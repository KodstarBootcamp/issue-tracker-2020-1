package com.kodstar.backend.controller;

import com.kodstar.backend.model.dto.User;
import com.kodstar.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
//@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
@Tag(name = "user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "Find a user by id")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {

        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    @Operation(summary = "Find all users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();

        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.editUserEntity(id,user));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserEntity(id);
    }
}
