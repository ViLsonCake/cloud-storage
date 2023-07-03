package com.project.CloudStorage.service.implementation;

import com.project.CloudStorage.constant.NumberConst;
import com.project.CloudStorage.config.SecurityConfig;
import com.project.CloudStorage.entity.UserEntity;
import com.project.CloudStorage.exception.EmailAlreadyExistException;
import com.project.CloudStorage.exception.UserAlreadyExistException;
import com.project.CloudStorage.exception.UserNotFoundException;
import com.project.CloudStorage.model.UserModel;
import com.project.CloudStorage.repository.UserRepository;
import com.project.CloudStorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static com.project.CloudStorage.constant.MessageConst.*;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity addUser(UserEntity user) throws UserAlreadyExistException, EmailAlreadyExistException {
        if (userRepository.findByUsername(user.getUsername()) != null)
            throw new UserAlreadyExistException(String.format(USER_ALREADY_EXIST_MESSAGE, user.getUsername()));
        if (userRepository.findByEmail(user.getEmail()) != null)
            throw new EmailAlreadyExistException(String.format(EMAIL_ALREADY_EXIST_MESSAGE, user.getEmail()));

        return userRepository.save(user);
    }

    @Transactional
    public UserModel getUserByUsername(String username) throws UserNotFoundException {
        if (userRepository.findByUsername(username) == null) throw new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username));

        return UserModel.toModal(userRepository.findByUsername(username));
    }

    @Transactional
    public List<UserModel> getUsers(Integer page) {
        Page<UserEntity> currentPageContent = userRepository.findAll(
                PageRequest.of(
                        page,
                        NumberConst.USERS_ON_PAGE_COUNT
        ));
        return currentPageContent.stream().toList().stream().map(UserModel::toModal).collect(Collectors.toList());
    }

    public UserEntity deleteUser(String username) throws UserNotFoundException {
        if (userRepository.findByUsername(username) == null) throw new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username));

        UserEntity userEntity = userRepository.findByUsername(username);
        userRepository.delete(userEntity);

        return userEntity;
    }

    public boolean changePrime(String username) throws UserNotFoundException {
        if (userRepository.findByUsername(username) == null) throw new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username));

        UserEntity userEntity = userRepository.findByUsername(username);
        userEntity.setPrime(!userEntity.isPrime());

        userRepository.save(userEntity);

        return userEntity.isPrime();
    }

    public int usersCount() {
        return userRepository.findAll().size();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);

        return User.builder()
        .username(user.getUsername())
        .passwordEncoder(SecurityConfig.passwordEncoder()::encode)
        .password(user.getPassword())
        .roles(String.valueOf(new HashSet<>()))
        .build();
    }
}
