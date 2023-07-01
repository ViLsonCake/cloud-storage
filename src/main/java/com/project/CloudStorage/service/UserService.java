package com.project.CloudStorage.service;

import com.project.CloudStorage.entity.UserEntity;
import com.project.CloudStorage.exception.EmailAlreadyExistException;
import com.project.CloudStorage.exception.UserAlreadyExistException;
import com.project.CloudStorage.exception.UserNotFoundException;
import com.project.CloudStorage.model.UserModel;

import java.util.List;

public interface UserService {
    UserEntity addUser(UserEntity user) throws UserAlreadyExistException, EmailAlreadyExistException;
    UserModel getUserByUsername(String username) throws UserNotFoundException;
    List<UserModel> getUsers(Integer page);
    UserEntity deleteUser(String username) throws UserNotFoundException;
    boolean changePrime(String username) throws UserNotFoundException;
    int usersCount();
}
