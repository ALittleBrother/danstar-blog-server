package com.danstar.blog.server.vo.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "文件上传响应")
@Data
public class FileUploadResp {

    private String url;
}
