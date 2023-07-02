package com.project.CloudStorage.model;

import com.project.CloudStorage.entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class UserModel {
    @NonNull
    private String username;
    @NonNull
    private String email;
    @NonNull
    private Boolean prime;
    @NonNull
    private List<FileModel> files;

    public static UserModel toModal(UserEntity userEntity) {
        return new UserModel(
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.isPrime(),
                userEntity.getFiles().stream().map(FileModel::toModal).collect(Collectors.toList())
        );
    }
}
