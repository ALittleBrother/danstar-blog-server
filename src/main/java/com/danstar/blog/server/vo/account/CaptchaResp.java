package com.danstar.blog.server.vo.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "验证码响应")
public class CaptchaResp {

    @Schema(description = "验证码图片的base64编码")
    private String imageBase64;

    @Schema(description = "验证码的key")
    private String key;
}
