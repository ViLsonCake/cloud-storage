package com.project.CloudStorage.service.implementation;

import com.project.CloudStorage.entity.FileEntity;
import com.project.CloudStorage.entity.UserEntity;
import com.project.CloudStorage.exception.NotThisUserFileException;
import com.project.CloudStorage.exception.UserNotFoundException;
import com.project.CloudStorage.model.FileModel;
import com.project.CloudStorage.repository.FileRepository;
import com.project.CloudStorage.repository.UserRepository;
import com.project.CloudStorage.service.FileService;
import com.project.CloudStorage.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.project.CloudStorage.constant.MessageConst.*;
import static com.project.CloudStorage.constant.NumberConst.MAX_NON_PRIME_FILE_SIZE;
import static com.project.CloudStorage.utils.FileUtils.getUsernameFromHeader;

@Service
public class FileServiceImpl implements FileService {

    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    @Autowired
    public FileServiceImpl(UserRepository userRepository, FileRepository fileRepository) {
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
    }

    @Transactional
    public String addFiles(List<MultipartFile> multipartFiles, String groupName, String authHeader) throws UserNotFoundException, IOException {
        final String username = FileUtils.getUsernameFromHeader(authHeader);
        if (userRepository.findByUsername(username) == null) throw new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username));

        List<FileEntity> filesForSave = new ArrayList<>();
        UserEntity userEntity = userRepository.findByUsername(username);
        int rejectedFileSaveCount = 0;

        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile.getSize() > MAX_NON_PRIME_FILE_SIZE && !userEntity.isPrime()) {
                rejectedFileSaveCount++;
                continue;
            }
            FileEntity fileEntity = new FileEntity(
                    groupName,
                    multipartFile.getOriginalFilename(),
                    multipartFile.getSize(),
                    multipartFile.getBytes(),
                    userEntity
            );
            filesForSave.add(fileEntity);
        }
        fileRepository.saveAll(filesForSave);

        return String.format(
                RESPONSE_SAVING_FILE_MESSAGE,
                filesForSave.size(),
                rejectedFileSaveCount
        );
    }

    public FileModel getFileInfo(Long fileId, String authHeader) {
        if (!isThisUserFile(fileId, getUsernameFromHeader(authHeader))) throw new NotThisUserFileException(String.format(NOT_THIS_USER_FILE_MESSAGE, fileId));

        return FileModel.toModal(fileRepository.findById(fileId).get());
    }

    public FileModel changeFilename(Long fileId, String filename, String authHeader) {
        if (!isThisUserFile(fileId, getUsernameFromHeader(authHeader))) throw new NotThisUserFileException(String.format(NOT_THIS_USER_FILE_MESSAGE, fileId));

        FileEntity fileEntity = fileRepository.findById(fileId).get();
        fileEntity.setFilename(filename);
        fileRepository.save(fileEntity);

        return FileModel.toModal(fileEntity);
    }

    public FileModel deleteFile(Long fileId, String authHeader) {
        if (!isThisUserFile(fileId, getUsernameFromHeader(authHeader))) throw new NotThisUserFileException(String.format(NOT_THIS_USER_FILE_MESSAGE, fileId));

        FileEntity fileEntity = fileRepository.findById(fileId).get();
        fileRepository.delete(fileEntity);

        return FileModel.toModal(fileEntity);
    }

    public ByteArrayResource sendFile(Long fileId, String authHeader) {
        if (!isThisUserFile(fileId, getUsernameFromHeader(authHeader))) throw new NotThisUserFileException(String.format(NOT_THIS_USER_FILE_MESSAGE, fileId));

        return new ByteArrayResource(fileRepository.findById(fileId).get().getByteArray());
    }

    public boolean isThisUserFile(Long fileId, String username) {
        final Long userId = userRepository.findByUsername(username).getUserId();
        final List<Long> userFilesId = fileRepository.findAllFileIdByUserId(userId);

        return userFilesId.contains(fileId);
    }
}
