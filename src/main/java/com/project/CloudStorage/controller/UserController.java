package com.project.CloudStorage.controller;

import com.project.CloudStorage.appConst.MessageConst;
import com.project.CloudStorage.entity.UserEntity;
import com.project.CloudStorage.exception.UserAlreadyExistException;
import com.project.CloudStorage.exception.UserNotFoundException;
import com.project.CloudStorage.modal.UserModal;
import com.project.CloudStorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable("username") String username) {
        try {
            return ResponseEntity.ok(userService.getUserByUsername(username));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> usersCount() {
        return ResponseEntity.ok(userService.usersCount());
    }

    @GetMapping
    public ResponseEntity<List<UserModal>> getUsers(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        return ResponseEntity.ok(userService.getUsers(page));
    }

    @PutMapping("/{username}")
    public ResponseEntity<String> changePrime(@PathVariable("username") String username) {
        try {
            return ResponseEntity.ok(String.format(MessageConst.PRIME_CHANGE_MESSAGE, userService.changePrime(username)));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody UserEntity user) {
        try {
            return ResponseEntity.ok(String.format(MessageConst.USER_SAVED_MESSAGE,
                    userService.addUser(user).getUsername()));
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable("username") String username) {
        try {
            return ResponseEntity.ok(String.format(MessageConst.USER_DELETE_MESSAGE, userService.deleteUser(username).getUsername()));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
