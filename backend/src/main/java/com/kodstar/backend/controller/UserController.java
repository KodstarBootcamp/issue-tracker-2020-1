package com.kodstar.backend.controller;

import com.kodstar.backend.model.dto.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long userId) {
        //Not completed
        return null;
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long userid, @RequestBody User user) {
        //Not completed
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long userId) {
        //Not completed
    }


}
