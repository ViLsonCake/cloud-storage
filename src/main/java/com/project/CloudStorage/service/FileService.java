package com.project.CloudStorage.service;

import com.project.CloudStorage.appConst.MessageConst;
import com.project.CloudStorage.entity.FileEntity;
import com.project.CloudStorage.entity.UserEntity;
import com.project.CloudStorage.exception.UserNotFoundException;
import com.project.CloudStorage.repository.FileRepository;
import com.project.CloudStorage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    @Autowired
    public FileService(UserRepository userRepository, FileRepository fileRepository) {
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
    }

    @Transactional
    public String addFiles(String username, List<MultipartFile> multipartFiles, String groupName) throws UserNotFoundException, IOException {
        // Find user
        if (userRepository.findByUsername(username) == null)
            throw new UserNotFoundException(String.format(MessageConst.USER_NOT_FOUND_MESSAGE, username));

        List<FileEntity> filesForSave = new ArrayList<>();

        UserEntity userEntity = userRepository.findByUsername(username);

        int rejectedFileSaveCount = 0;

        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile.getSize() > 1024 * 1024 * 5 && !userEntity.isPrime()) {
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

        return String.format("%s files has been saved\n%s files rejected", filesForSave.size(), rejectedFileSaveCount);
    }

}
