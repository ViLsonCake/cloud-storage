package com.project.CloudStorage.model;

import com.project.CloudStorage.entity.FileEntity;
import com.project.CloudStorage.utils.FileUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class FileModel {
    @NonNull
    private Long id;
    @NonNull
    private String filename;
    @NonNull
    private String originalFilename;
    @NonNull
    private String convertSize;

    public static FileModel toModal(FileEntity fileEntity) {
        return new FileModel(
                fileEntity.getFileId(),
                fileEntity.getFilename(),
                fileEntity.getOriginalFilename(),
                FileUtils.convertToReadableSize(fileEntity.getSize())
        );
    }
}
