package com.project.CloudStorage.controller;

import com.project.CloudStorage.appConst.MessageConst;
import com.project.CloudStorage.exception.UserNotFoundException;
import com.project.CloudStorage.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
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

    @GetMapping("/{fileId}/info")
    public ResponseEntity<?> getFileInfo(@PathVariable("fileId") Long fileId) {
        try {
            return ResponseEntity.ok(fileService.getFileInfo(fileId));
        } catch (FileNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{fileId}/download")
    public ResponseEntity<?> downloadFile(@PathVariable("fileId") Long fileId) {
        try {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileService.getFileInfo(fileId).getOriginalFilename() + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(fileService.sendFile(fileId));
        } catch (FileNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{username}")
    public ResponseEntity<String> addFile(@PathVariable("username") String username,
                                          @RequestParam("files") List<MultipartFile> multipartFiles,
                                          @RequestParam(value = "groupName", defaultValue = "file") String groupName) {
        try {
            return ResponseEntity.ok(fileService.addFiles(username, multipartFiles, groupName));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(MessageConst.FILE_SAVE_ERROR);
        }
    }

    @PutMapping("/{fileId}")
    public ResponseEntity<String> changeFilename(@PathVariable("fileId") Long fileId,
                                                 @RequestParam("filename") String filename) {
        try {
            return ResponseEntity.ok(String.format(
                    MessageConst.CHANGE_FILENAME_MESSAGE, fileService.changeFilename(fileId, filename).getFilename()
            ));
        } catch (FileNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable("fileId") Long fileId) {
        try {
            return ResponseEntity.ok(String.format(
                    MessageConst.FILE_DELETE_MESSAGE, fileService.deleteFile(fileId).getOriginalFilename()
            ));
        } catch (FileNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
