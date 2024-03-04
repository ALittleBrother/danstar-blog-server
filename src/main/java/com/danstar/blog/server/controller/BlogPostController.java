package com.danstar.blog.server.controller;

import com.danstar.blog.server.infrastructure.validation.group.SearchOperation;
import com.danstar.blog.server.service.blogpost.BlogPostService;
import com.danstar.blog.server.vo.blogpost.*;
import com.danstar.blog.server.vo.common.PageEntity;
import com.danstar.blog.server.vo.common.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog-post")
@Tag(name = "BlogPostController", description = "文章相关接口")
public class BlogPostController {

    private BlogPostService blogPostService;
    @Autowired
    public void setBlogPostService(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @PostMapping
    @Operation(summary = "添加文章")
    public ResponseEntity<Void> add(@Valid @RequestBody BlogPostAddReq req) {
        blogPostService.add(req);
        return ResponseEntity.ok();
    }

    @PutMapping
    @Operation(summary = "更新文章")
    public ResponseEntity<Void> update(@Valid @RequestBody BlogPostUpdateReq req) {
        blogPostService.update(req);
        return ResponseEntity.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除文章")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        blogPostService.delete(id);
        return ResponseEntity.ok();
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取文章详情")
    public ResponseEntity<BlogPostDetailResp> getDetail(@PathVariable Long id) {
        return ResponseEntity.ok(blogPostService.getDetail(id));
    }

    @PostMapping("/search")
    @Operation(summary = "查询文章列表")
    public ResponseEntity<PageEntity<BlogPostListResp>> queryList(
            @Validated(value = SearchOperation.class) @RequestBody BlogPostSearchReq req) {
        return ResponseEntity.ok(new PageEntity<>(blogPostService.list(req)));
    }
}