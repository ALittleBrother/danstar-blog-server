package com.danstar.blog.server.vo.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class PageEntity<T> {

    private PageInfo pageInfo;
    private List<T> content;

    // 构造函数
    public PageEntity(Page<T> page) {
        this.pageInfo = new PageInfo(page.getNumber(), page.getSize(), page.getTotalElements());
        this.content = page.getContent();
    }

    // PageInfo内部类
    @Getter
    @Setter
    public static class PageInfo {
        private int page;
        private int size;
        private long totalElements;

        public PageInfo(int page, int size, long totalElements) {
            this.page = page;
            this.size = size;
            this.totalElements = totalElements;
        }

    }
}

