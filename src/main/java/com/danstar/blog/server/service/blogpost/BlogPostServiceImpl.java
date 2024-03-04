package com.danstar.blog.server.service.blogpost;

import com.danstar.blog.server.convert.BlogPostMapper;
import com.danstar.blog.server.entity.BlogPost;
import com.danstar.blog.server.infrastructure.exception.ResourceNotFoundException;
import com.danstar.blog.server.repository.blogpost.BlogPostRepository;
import com.danstar.blog.server.repository.blogpost.BlogPostSpecification;
import com.danstar.blog.server.vo.blogpost.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogPostServiceImpl implements BlogPostService {

    private BlogPostRepository blogPostRepository;
    @Autowired
    public void setBlogPostRepository(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(BlogPostAddReq req) {
        BlogPost blogPost = BlogPostMapper.mapper.toEntity(req);
        blogPostRepository.save(blogPost);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        blogPostRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(BlogPostUpdateReq req) {
        BlogPost blogPost = this.get(req.getId());
        BlogPostMapper.mapper.toEntity(req, blogPost);
        blogPostRepository.save(blogPost);
    }

    @Override
    public BlogPost get(Long id) {
        return blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("文章不存在"));
    }

    @Override
    public BlogPostDetailResp getDetail(Long id) {
        BlogPost blogPost = this.get(id);
        return BlogPostMapper.mapper.toDetailResp(blogPost);
    }

    @Override
    public Page<BlogPost> query(BlogPostSearchReq req) {
        List<Specification<BlogPost>> specificationList = new ArrayList<>();

        String title = req.getTitle();
        if (StringUtils.hasText(title)) {
            specificationList.add((BlogPostSpecification.titleLike(title)));
        }

        Specification<BlogPost> specification = null;
        for (Specification<BlogPost> spec : specificationList) {
            if (specification == null) {
                specification = Specification.where(spec);
            } else {
                specification = specification.and(spec);
            }
        }

        Sort sort = req.getSort();
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize(), sort);
        Page<BlogPost> page;
        if (specification != null) {
            page = blogPostRepository.findAll(specification, pageable);
        } else {
            page = blogPostRepository.findAll(pageable);
        }

        return page;
    }

    @Override
    public Page<BlogPostListResp> list(BlogPostSearchReq req) {
        Page<BlogPost> page = this.query(req);
        return page.map(BlogPostMapper.mapper::toListResp);
    }
}
