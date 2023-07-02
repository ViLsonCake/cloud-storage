package com.project.CloudStorage.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "files")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;
    @Column(name = "filename")
    private String filename;
    @Column(name = "original_filename")
    private String originalFilename;
    @Column(name = "size")
    private Long size;
    @Column(name = "byte_array")
    @Lob
    private byte[] byteArray;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public FileEntity() {}

    public FileEntity(String filename, String originalFilename, Long size, byte[] byteArray, UserEntity user) {
        this.filename = filename;
        this.originalFilename = originalFilename;
        this.size = size;
        this.byteArray = byteArray;
        this.user = user;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
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

    public byte[] getByteArray() {
        return byteArray;
    }

    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
