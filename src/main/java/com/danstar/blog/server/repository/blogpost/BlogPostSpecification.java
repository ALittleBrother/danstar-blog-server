package com.danstar.blog.server.repository.blogpost;

import com.danstar.blog.server.entity.BlogPost;
import org.springframework.data.jpa.domain.Specification;

public class BlogPostSpecification {

    public static Specification<BlogPost> titleLike(String title) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }
}
