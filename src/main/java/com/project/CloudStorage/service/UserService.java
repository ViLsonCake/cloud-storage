package com.project.CloudStorage.service;

import com.project.CloudStorage.appConst.MessageConst;
import com.project.CloudStorage.entity.UserEntity;
import com.project.CloudStorage.exception.UserAlreadyExistException;
import com.project.CloudStorage.exception.UserNotFoundException;
import com.project.CloudStorage.modal.FileModal;
import com.project.CloudStorage.modal.UserModal;
import com.project.CloudStorage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public UserModal getUser(Long id) throws UserNotFoundException {
        if (!userRepository.existsById(id))
            throw new UserNotFoundException(String.format(MessageConst.USER_NOT_FOUND_MESSAGE, id));

        return UserModal.toModal(userRepository.findById(id).get());
    }

    public List<UserModal> getUsers() {
        return userRepository.findAll().stream().map(UserModal::toModal).collect(Collectors.toList());
    }
}
