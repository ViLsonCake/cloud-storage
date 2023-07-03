package com.project.CloudStorage.service;

import com.project.CloudStorage.exception.UserNotFoundException;
import com.project.CloudStorage.model.FileModel;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FileService {
    String addFiles(String username, List<MultipartFile> multipartFiles, String groupName) throws UserNotFoundException, IOException;
    FileModel getFileInfo(Long fileId, String authHeader) throws FileNotFoundException;
    FileModel changeFilename(Long fileId, String filename, String authHeader) throws FileNotFoundException;
    FileModel deleteFile(Long fileId, String authHeader) throws FileNotFoundException;
    ByteArrayResource sendFile(Long fileId, String authHeader) throws FileNotFoundException;
    boolean isThisUserFile(Long fileId, String username);
}
