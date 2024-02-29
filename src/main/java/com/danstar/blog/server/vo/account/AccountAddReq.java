package com.danstar.blog.server.vo.account;

import com.danstar.blog.server.infrastructure.validation.group.CreateOperation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "账户添加请求")
@Data
public class AccountAddReq {

    @NotBlank(message = "用户名不能为空", groups = CreateOperation.class)
    @Size(min = 4, max = 16, message = "用户名长度范围：2-16")
    @Schema(description = "用户名")
    private String username;

    @NotBlank(message = "密码不能为空", groups = CreateOperation.class)
    @Size(min = 8, max = 32, message = "用户名长度范围：8-32")
    @Schema(description = "密码")
    private String password;

    @NotBlank(message = "昵称不能为空")
    @Size(min = 4, max = 16, message = "昵称长度范围：2-16")
    @Schema(description = "昵称")
    private String nickname;
}
