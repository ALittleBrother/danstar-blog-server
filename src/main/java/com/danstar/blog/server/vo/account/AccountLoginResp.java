package com.danstar.blog.server.vo.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "账户登录响应")
public class AccountLoginResp {

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "角色")
    private List<String> roles;

    @Schema(description = "token")
    private String accessToken;

    @Schema(description = "token过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime expires;

    @Schema(description = "refreshToken")
    private String refreshToken;

    @Schema(description = "token过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime refreshExpires;
}
