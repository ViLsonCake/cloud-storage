package com.project.CloudStorage.controller;

import com.project.CloudStorage.constant.MessageConst;
import com.project.CloudStorage.service.implementation.FileServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static com.project.CloudStorage.constant.MessageConst.*;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileServiceImpl fileService;

    @Autowired
    public FileController(FileServiceImpl fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/{fileId}/info")
    public ResponseEntity<?> getFileInfo(@PathVariable("fileId") Long fileId, @RequestHeader("Authorization") String authHeader) throws FileNotFoundException {
        return ResponseEntity.ok(fileService.getFileInfo(fileId, authHeader));
    }

    @GetMapping("/{fileId}/download")
    public ResponseEntity<?> downloadFile(@PathVariable("fileId") Long fileId, @RequestHeader("Authorization") String authHeader) throws FileNotFoundException {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileService.getFileInfo(fileId, authHeader).getOriginalFilename() + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(fileService.sendFile(fileId, authHeader));
    }

    @PostMapping
    public ResponseEntity<String> addFile(@RequestParam("files") List<MultipartFile> multipartFiles,
                                          @RequestParam(value = "groupName", defaultValue = "file") String groupName,
                                          @RequestHeader("Authorization") String authHeader) throws IOException {
        return ResponseEntity.ok(fileService.addFiles(multipartFiles, groupName, authHeader));
    }

    @PutMapping("/{fileId}")
    public ResponseEntity<String> changeFilename(@PathVariable("fileId") Long fileId,
                                                 @RequestParam("filename") String filename,
                                                 @RequestHeader("Authorization") String authHeader) throws FileNotFoundException {
            return ResponseEntity.ok(String.format(
                    CHANGE_FILENAME_MESSAGE,
                    fileService.changeFilename(fileId, filename, authHeader).getFilename()
            ));
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable("fileId") Long fileId,
                                             @RequestHeader("Authorization") String authHeader) throws FileNotFoundException {
            return ResponseEntity.ok(String.format(
                    FILE_DELETE_MESSAGE,
                    fileService.deleteFile(fileId, authHeader).getOriginalFilename()
            ));
    }

}
