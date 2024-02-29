package com.danstar.blog.server.vo.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "账户详情响应")
@Data
public class AccountDetailResp extends AccountListResp {
}
