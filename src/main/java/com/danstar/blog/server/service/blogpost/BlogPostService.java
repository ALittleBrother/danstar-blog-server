package com.danstar.blog.server.service.blogpost;

import com.danstar.blog.server.entity.BlogPost;
import com.danstar.blog.server.vo.blogpost.*;
import org.springframework.data.domain.Page;

public interface BlogPostService {

    /**
     * 新增文章
     */
    void add(BlogPostAddReq req);

    /**
     * 删除文章
     */
    void delete(Long id);

    /**
     * 更新文章
     */
    void update(BlogPostUpdateReq req);

    /**
     * 获取文章
     */
    BlogPost get(Long id);

    /**
     * 获取文章详情
     */
    BlogPostDetailResp getDetail(Long id);

    /**
     * 获取文章列表
     */
    Page<BlogPost> query(BlogPostSearchReq req);

    /**
     * 获取文章列表
     */
    Page<BlogPostListResp> list(BlogPostSearchReq req);
}
