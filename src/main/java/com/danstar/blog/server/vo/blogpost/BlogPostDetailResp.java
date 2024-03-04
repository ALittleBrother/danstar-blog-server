package com.danstar.blog.server.vo.blogpost;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "文章详情响应")
public class BlogPostDetailResp extends BlogPostListResp {

    @Schema(description = "文章内容")
    private String content;

    @Schema(description = "文章作者")
    private String authorName;
}
