package com.project.CloudStorage.service;

import com.project.CloudStorage.appConst.MessageConst;
import com.project.CloudStorage.appConst.NumberConst;
import com.project.CloudStorage.entity.FileEntity;
import com.project.CloudStorage.entity.UserEntity;
import com.project.CloudStorage.exception.UserNotFoundException;
import com.project.CloudStorage.model.FileModel;
import com.project.CloudStorage.repository.FileRepository;
import com.project.CloudStorage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    public String addFiles(String username, List<MultipartFile> multipartFiles, String groupName) throws UserNotFoundException, IOException {
        // Find user
        if (userRepository.findByUsername(username) == null)
            throw new UserNotFoundException(String.format(MessageConst.USER_NOT_FOUND_MESSAGE, username));

        List<FileEntity> filesForSave = new ArrayList<>();

        UserEntity userEntity = userRepository.findByUsername(username);

        int rejectedFileSaveCount = 0;

        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile.getSize() > NumberConst.MAX_NON_PRIME_FILE_SIZE && !userEntity.isPrime()) {
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

    public FileModel getFileInfo(Long fileId) throws FileNotFoundException {
        if (!fileRepository.existsById(fileId))
            throw new FileNotFoundException(String.format(MessageConst.FILE_NOT_FOUND_MESSAGE, fileId));

        return FileModel.toModal(fileRepository.findById(fileId).get());
    }

    public FileModel changeFilename(Long fileId, String filename) throws FileNotFoundException {
        if (!fileRepository.existsById(fileId)) throw new FileNotFoundException(String.format(MessageConst.FILE_NOT_FOUND_MESSAGE, fileId));

        FileEntity fileEntity = fileRepository.findById(fileId).get();
        fileEntity.setFilename(filename);
        fileRepository.save(fileEntity);

        return FileModel.toModal(fileEntity);
    }

    public FileModel deleteFile(Long fileId) throws FileNotFoundException {
        if (!fileRepository.existsById(fileId))
            throw new FileNotFoundException(String.format(MessageConst.FILE_NOT_FOUND_MESSAGE, fileId));

        FileEntity fileEntity = fileRepository.findById(fileId).get();
        fileRepository.delete(fileEntity);

        return FileModel.toModal(fileEntity);
    }

    public ByteArrayResource sendFile(Long fileId) throws FileNotFoundException {
        if (!fileRepository.existsById(fileId))
            throw new FileNotFoundException(String.format(MessageConst.FILE_NOT_FOUND_MESSAGE, fileId));

        return new ByteArrayResource(fileRepository.findById(fileId).get().getByteArray());
    }

}
