package com.danstar.blog.server.vo.blogpost;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "文章更新请求")
public class BlogPostUpdateReq extends BlogPostAddReq {

    @NotNull(message = "文章ID不能为空")
    @Schema(description = "文章ID")
    private Long id;
}
