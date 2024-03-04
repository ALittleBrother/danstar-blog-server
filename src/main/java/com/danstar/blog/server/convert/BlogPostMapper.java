package com.danstar.blog.server.convert;

import com.danstar.blog.server.entity.BlogPost;
import com.danstar.blog.server.vo.blogpost.BlogPostAddReq;
import com.danstar.blog.server.vo.blogpost.BlogPostDetailResp;
import com.danstar.blog.server.vo.blogpost.BlogPostListResp;
import com.danstar.blog.server.vo.blogpost.BlogPostUpdateReq;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BlogPostMapper {

    BlogPostMapper mapper = Mappers.getMapper(BlogPostMapper.class);

    BlogPost toEntity(BlogPostAddReq req);

    void toEntity(BlogPostUpdateReq req, @MappingTarget BlogPost blogPost);

    BlogPostListResp toListResp(BlogPost blogPost);

    BlogPostDetailResp toDetailResp(BlogPost blogPost);
}
