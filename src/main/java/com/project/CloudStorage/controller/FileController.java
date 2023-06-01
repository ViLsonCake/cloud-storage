package com.project.CloudStorage.controller;

import com.project.CloudStorage.appConst.MessageConst;
import com.project.CloudStorage.exception.UserNotFoundException;
import com.project.CloudStorage.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/{username}")
    public ResponseEntity<String> addFile(@PathVariable("username") String username,
                                          @RequestParam("files") List<MultipartFile> multipartFiles,
                                          @RequestParam("groupName") String groupName) {
        try {
            return ResponseEntity.ok(fileService.addFiles(username, multipartFiles, groupName));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(MessageConst.FILE_SAVE_ERROR);
        }
    }

}
