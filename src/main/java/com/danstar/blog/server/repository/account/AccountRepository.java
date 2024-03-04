package com.danstar.blog.server.repository.account;

import com.danstar.blog.server.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {

    boolean existsByUsername(String username);

    boolean existsByNickname(String nickname);

    Optional<Account> findByUsername(String username);
}
