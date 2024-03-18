package com.danstar.blog.server.service.uploadfile;

import com.danstar.blog.server.entity.UploadFile;
import com.danstar.blog.server.infrastructure.constant.CommonConstant;
import com.danstar.blog.server.infrastructure.exception.BusinessException;
import com.danstar.blog.server.infrastructure.util.CommonUtils;
import com.danstar.blog.server.repository.uploadfile.UploadFileRepository;
import com.danstar.blog.server.vo.uploadfile.UploadFileResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadFileServiceImpl implements UploadFileService {

    private UploadFileRepository uploadFileRepository;
    @Autowired
    public void setUploadFileRepository(UploadFileRepository uploadFileRepository) {
        this.uploadFileRepository = uploadFileRepository;
    }

    @Override
    public UploadFileResp upload(MultipartFile file) {
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
            String newFilename = String.format("%s-%s.%s", System.currentTimeMillis(), CommonUtils.generateUUID(), fileExtension);
            Path filePath = saveDirPath.resolve(newFilename);
            Files.write(filePath, file.getBytes());

            UploadFile uploadFile = UploadFile.builder().originalFilename(originalFilename)
                    .newFilename(newFilename)
                    .fileExtension(fileExtension)
                    .fileSize(file.getSize())
                    .build();
            uploadFileRepository.save(uploadFile);

            return UploadFileResp.builder()
                    .url("/file/download?fileName=" + newFilename)
                    .build();
        } catch (IOException e) {
            throw new BusinessException("文件上传失败");
        }
    }
}
