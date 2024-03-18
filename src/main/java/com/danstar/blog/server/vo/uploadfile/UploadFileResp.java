package com.danstar.blog.server.vo.uploadfile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "文件上传响应")
@Data
@Builder
public class UploadFileResp {

    @Schema(description = "文件URL")
    private String url;
}
