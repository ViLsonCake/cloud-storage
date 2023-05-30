package com.project.CloudStorage.modal;

import com.project.CloudStorage.entity.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

public class UserModal {
    private String username;
    private String email;
    private boolean prime;
    private List<FileModal> files;

    public UserModal() {}

    public UserModal(String username, String email, boolean prime, List<FileModal> files) {
        this.username = username;
        this.email = email;
        this.prime = prime;
        this.files = files;
    }

    public static UserModal toModal(UserEntity userEntity) {
        return new UserModal(userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.isPrime(),
                userEntity.getFiles().stream().map(FileModal::toModal).collect(Collectors.toList()));
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

    public List<FileModal> getFiles() {
        return files;
    }

    public void setFiles(List<FileModal> files) {
        this.files = files;
    }
}
