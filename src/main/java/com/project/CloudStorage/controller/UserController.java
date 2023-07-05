package com.project.CloudStorage.controller;

import com.project.CloudStorage.entity.UserEntity;
import com.project.CloudStorage.model.UserModel;
import com.project.CloudStorage.service.implementation.UserServiceImpl;
import com.project.CloudStorage.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.project.CloudStorage.constant.MessageConst.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserModel> getUserByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserModel> userProfile(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(userService.getUserByUsername(FileUtils.getUsernameFromHeader(authHeader)));
    }

    @GetMapping("/count")
    @Cacheable("usersCount")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Integer> usersCount() {
        return ResponseEntity.ok(userService.usersCount());
    }

    @GetMapping
    @Cacheable("users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserModel>> getUsers(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        return ResponseEntity.ok(userService.getUsers(page));
    }

    @PutMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changePrime(@PathVariable("username") String username) {
        return ResponseEntity.ok(String.format(
                PRIME_CHANGE_MESSAGE,
                userService.changePrime(username))
        );
    }

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody UserEntity user) {
        return ResponseEntity.ok(String.format(
                USER_SAVED_MESSAGE,
                userService.addUser(user).getUsername())
        );
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable("username") String username) {
        return ResponseEntity.ok(String.format(
                USER_DELETE_MESSAGE,
                userService.deleteUser(username).getUsername())
        );
    }
}
