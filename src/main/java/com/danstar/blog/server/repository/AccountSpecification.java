package com.danstar.blog.server.repository;

import com.danstar.blog.server.entity.Account;
import org.springframework.data.jpa.domain.Specification;

public class AccountSpecification {

    public static Specification<Account> nickNameLike(String nickname) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("nickname"), "%" + nickname + "%");
    }
}
