package com.danstar.blog.server.controller;

import com.danstar.blog.server.infrastructure.constant.CommonConstant;
import com.danstar.blog.server.vo.common.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/file")
@Tag(name = "FIleController", description = "文件相关接口")
public class FIleController {

    @PostMapping("/upload")
    @Operation(summary = "上传文件")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.fail(400, "文件不能为空");
        }

        try {
            // 文件存储根文件夹，如果不存在则创建
            Path rootDirPath = Paths.get(CommonConstant.FILE_ROOT_DIR);
            if (!Files.exists(rootDirPath)) {
                Files.createDirectories(rootDirPath);
            }

            // 文件分类存储，根据文件后缀做区分，如果不存在则创建
            String originalFilename = file.getOriginalFilename();
            assert originalFilename != null;
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            Path saveDirPath = Paths.get(CommonConstant.FILE_ROOT_DIR, fileExtension);
            if (!Files.exists(saveDirPath)) {
                Files.createDirectories(saveDirPath);
            }

            // 保存文件
            Path filePath = saveDirPath.resolve(originalFilename);
            Files.write(filePath, file.getBytes());
            return ResponseEntity.ok("文件上传成功");
        } catch (IOException e) {
            return ResponseEntity.fail(500, "文件上传失败");
        }
    }

    @GetMapping("/download")
    @Operation(summary = "下载文件")
    public org.springframework.http.ResponseEntity<Resource> download(@RequestParam String fileName) {
        System.out.println(fileName);
        Path filePath = Paths.get(CommonConstant.FILE_ROOT_DIR + fileName).normalize();
        System.out.println(filePath);
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
