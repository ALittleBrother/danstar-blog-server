package com.danstar.blog.server.vo.blogpost;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "文章新增请求")
public class BlogPostAddReq {

    @Size(max = 255, message = "文章标题最大长度为255")
    @NotBlank(message = "文章标题不能为空")
    @Schema(description = "文章标题")
    private String title;

    @Size(max = 65535, message = "文章内容最大长度为65535")
    @Schema(description = "文章内容")
    private String content;
}
