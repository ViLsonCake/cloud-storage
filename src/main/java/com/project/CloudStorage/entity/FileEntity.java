package com.project.CloudStorage.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "files")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;
    @NonNull
    @Column(name = "filename")
    private String filename;
    @NonNull
    @Column(name = "original_filename")
    private String originalFilename;
    @NonNull
    @Column(name = "size")
    private Long size;
    @NonNull
    @Column(name = "byte_array")
    @Lob
    private Byte[] byteArray;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
