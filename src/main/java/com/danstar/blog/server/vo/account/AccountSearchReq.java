package com.danstar.blog.server.vo.account;

import com.danstar.blog.server.vo.common.BaseSearchReq;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "账户查询请求")
@Data
public class AccountSearchReq extends BaseSearchReq {

    @Schema(description = "昵称")
    @Size(min = 4, max = 16, message = "昵称长度范围：2-16")
    private String nickname;
}
