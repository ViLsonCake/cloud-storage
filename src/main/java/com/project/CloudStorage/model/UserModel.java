package com.project.CloudStorage.model;

import com.project.CloudStorage.entity.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

public class UserModel {
    private String username;
    private String email;
    private boolean prime;
    private List<FileModel> files;

    public UserModel() {}

    public UserModel(String username, String email, boolean prime, List<FileModel> files) {
        this.username = username;
        this.email = email;
        this.prime = prime;
        this.files = files;
    }

    public static UserModel toModel(UserEntity userEntity) {
        return new UserModel(userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.isPrime(),
                userEntity.getFiles().stream().map(FileModel::toModel).collect(Collectors.toList()));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPrime() {
        return prime;
    }

    public void setPrime(boolean prime) {
        this.prime = prime;
    }

    public List<FileModel> getFiles() {
        return files;
    }

    public void setFiles(List<FileModel> files) {
        this.files = files;
    }
}
