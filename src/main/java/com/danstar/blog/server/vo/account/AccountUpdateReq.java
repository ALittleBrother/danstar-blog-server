package com.danstar.blog.server.vo.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "账户更新请求")
@Data
public class AccountUpdateReq extends AccountAddReq {

    @Schema(description = "账户ID")
    private Long id;
}
