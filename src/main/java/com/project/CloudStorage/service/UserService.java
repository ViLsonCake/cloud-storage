package com.project.CloudStorage.service;

import com.project.CloudStorage.appConst.MessageConst;
import com.project.CloudStorage.entity.UserEntity;
import com.project.CloudStorage.exception.UserAlreadyExistException;
import com.project.CloudStorage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity addUser(UserEntity user) throws UserAlreadyExistException {
        if (userRepository.findByUsername(user.getUsername()) != null)
            throw new UserAlreadyExistException(MessageConst.USER_ALREADY_EXIST_MESSAGE);

        return userRepository.save(user);
    }
}
