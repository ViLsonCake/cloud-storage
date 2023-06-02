package com.project.CloudStorage.service;

import com.project.CloudStorage.appConst.MessageConst;
import com.project.CloudStorage.entity.UserEntity;
import com.project.CloudStorage.exception.UserAlreadyExistException;
import com.project.CloudStorage.exception.UserNotFoundException;
import com.project.CloudStorage.modal.UserModal;
import com.project.CloudStorage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity addUser(UserEntity user) throws UserAlreadyExistException {
        if (userRepository.findByUsername(user.getUsername()) != null)
            throw new UserAlreadyExistException(String.format(MessageConst.USER_ALREADY_EXIST_MESSAGE, user.getUsername()));

        return userRepository.save(user);
    }

    @Transactional
    public UserModal getUserByUsername(String username) throws UserNotFoundException {
        if (userRepository.findByUsername(username) == null)
            throw new UserNotFoundException(String.format(MessageConst.USER_NOT_FOUND_MESSAGE, username));

        return UserModal.toModal(userRepository.findByUsername(username));
    }

    @Transactional
    public List<UserModal> getUsers() {
        return userRepository.findAll().stream().map(UserModal::toModal).collect(Collectors.toList());
    }

    public UserEntity deleteUser(String username) throws UserNotFoundException {
        if (userRepository.findByUsername(username) == null)
            throw new UserNotFoundException(String.format(MessageConst.USER_NOT_FOUND_MESSAGE, username));

        UserEntity userEntity = userRepository.findByUsername(username);
        userRepository.delete(userEntity);

        return userEntity;
    }

    public boolean changePrime(String username) throws UserNotFoundException {
        if (userRepository.findByUsername(username) == null)
            throw new UserNotFoundException(String.format(MessageConst.USER_NOT_FOUND_MESSAGE, username));

        UserEntity userEntity = userRepository.findByUsername(username);
        userEntity.setPrime(!userEntity.isPrime());

        userRepository.save(userEntity);

        return userEntity.isPrime();
    }

    public int usersCount() {
        return userRepository.findAll().size();
    }
}
