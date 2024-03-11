package com.danstar.blog.server.vo.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "账户登录请求")
public class AccountLoginReq {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 4, max = 16, message = "用户名长度范围：2-16")
    @Schema(description = "用户名")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 8, max = 32, message = "用户名长度范围：8-32")
    @Schema(description = "密码")
    private String password;

    @NotBlank(message = "验证码不能为空")
    @Size(min = 4, max = 4, message = "验证码长度不合规范")
    @Schema(description = "验证码")
    private String verifyCode;

    @Schema(description = "记住我")
    @NotNull(message = "记住我不能为空")
    private boolean rememberMe;
}
