package com.danstar.blog.server.vo.blogpost;

import com.danstar.blog.server.vo.common.BaseSearchReq;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "文章搜索请求")
public class BlogPostSearchReq extends BaseSearchReq {

    @Size(max = 255, message = "标题最大长度为255")
    @Schema(description = "标题")
    private String title;
}
