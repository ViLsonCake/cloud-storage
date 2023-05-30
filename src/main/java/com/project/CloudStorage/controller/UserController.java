package com.project.CloudStorage.controller;

import com.project.CloudStorage.entity.UserEntity;
import com.project.CloudStorage.exception.UserAlreadyExistException;
import com.project.CloudStorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Work user");
    }

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody UserEntity user) {
        try {
            return ResponseEntity.ok(String.format("User %s is successfully saved",
                    userService.addUser(user).getUsername()));
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
