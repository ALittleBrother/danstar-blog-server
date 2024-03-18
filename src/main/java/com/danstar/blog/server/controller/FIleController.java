package com.danstar.blog.server.controller;

import com.danstar.blog.server.infrastructure.constant.CommonConstant;
import com.danstar.blog.server.service.uploadfile.UploadFileService;
import com.danstar.blog.server.vo.common.ResponseEntity;
import com.danstar.blog.server.vo.uploadfile.UploadFileResp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/file")
@Tag(name = "FIleController", description = "文件相关接口")
public class FIleController {

    private UploadFileService uploadFileService;
    @Autowired
    public void setUploadFileService(UploadFileService uploadFileService) {
        this.uploadFileService = uploadFileService;
    }

    @PostMapping("/upload")
    @Operation(summary = "上传文件")
    public ResponseEntity<UploadFileResp> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.fail(400, "文件不能为空");
        }

        return ResponseEntity.ok(uploadFileService.upload(file));
    }

    @GetMapping("/download")
    @Operation(summary = "下载文件")
    public org.springframework.http.ResponseEntity<Resource> download(@RequestParam String fileName) {
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        Path saveDirPath = Paths.get(CommonConstant.FILE_ROOT_DIR, fileExtension);
        Path filePath = Paths.get(saveDirPath.toString(), fileName).normalize();
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return org.springframework.http.ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return org.springframework.http.ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (MalformedURLException e) {
            return org.springframework.http.ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
