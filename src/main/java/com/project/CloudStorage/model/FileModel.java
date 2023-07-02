package com.project.CloudStorage.model;

import com.project.CloudStorage.entity.FileEntity;
import com.project.CloudStorage.utils.FileUtils;

public class FileModel {
    private Long id;
    private String filename;
    private String originalFilename;
    private String convertSize;

    public FileModel() {}

    public FileModel(Long id, String filename, String originalFilename, String convertSize) {
        this.id = id;
        this.filename = filename;
        this.originalFilename = originalFilename;
        this.convertSize = convertSize;
    }

    public static FileModel toModal(FileEntity fileEntity) {
        return new FileModel(
                fileEntity.getFileId(),
                fileEntity.getFilename(),
                fileEntity.getOriginalFilename(),
                FileUtils.convertToReadableSize(fileEntity.getSize())
        );
    }

    public Long getId() {
        return id;
    }

    public FileModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public FileModel setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public FileModel setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
        return this;
    }

    public String getConvertSize() {
        return convertSize;
    }

    public FileModel setConvertSize(String convertSize) {
        this.convertSize = convertSize;
        return this;
    }
}
