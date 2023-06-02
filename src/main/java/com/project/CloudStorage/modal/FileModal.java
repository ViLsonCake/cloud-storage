package com.project.CloudStorage.modal;

import com.project.CloudStorage.entity.FileEntity;
import com.project.CloudStorage.utils.FileUtils;

public class FileModal {
    private Long id;
    private String filename;
    private String originalFilename;
    private String convertSize;

    public FileModal() {}

    public FileModal(Long id, String filename, String originalFilename, String convertSize) {
        this.id = id;
        this.filename = filename;
        this.originalFilename = originalFilename;
        this.convertSize = convertSize;
    }

    public static FileModal toModal(FileEntity fileEntity) {
        return new FileModal(
                fileEntity.getFileId(),
                fileEntity.getFilename(),
                fileEntity.getOriginalFilename(),
                FileUtils.convertToReadableSize(fileEntity.getSize())
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getConvertSize() {
        return convertSize;
    }

    public void setConvertSize(String convertSize) {
        this.convertSize = convertSize;
    }
}
