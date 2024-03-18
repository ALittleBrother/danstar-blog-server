package com.danstar.blog.server.service.uploadfile;

import com.danstar.blog.server.vo.uploadfile.UploadFileResp;
import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {

    UploadFileResp upload(MultipartFile file);
}
