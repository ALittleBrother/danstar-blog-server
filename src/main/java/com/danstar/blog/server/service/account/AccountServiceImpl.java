package com.danstar.blog.server.service.account;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import com.danstar.blog.server.convert.AccountMapper;
import com.danstar.blog.server.entity.Account;
import com.danstar.blog.server.infrastructure.exception.BusinessException;
import com.danstar.blog.server.infrastructure.exception.ResourceNotFoundException;
import com.danstar.blog.server.repository.account.AccountRepository;
import com.danstar.blog.server.repository.account.AccountSpecification;
import com.danstar.blog.server.vo.account.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(AccountAddReq req) {
        if (accountRepository.existsByUsername(req.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }

        if (accountRepository.existsByNickname(req.getNickname())) {
            throw new RuntimeException("昵称已存在");
        }

        Account account = AccountMapper.mapper.toEntity(req);
        String hashPW = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt());
        account.setPassword(hashPW);
        accountRepository.save(account);
    }

    @Override
    public Account get(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("账户不存在"));
    }

    @Override
    public AccountDetailResp getDetail(Long id) {
        Account account = this.get(id);
        return AccountMapper.mapper.toDetailResp(account);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(AccountUpdateReq req) {
        Account account = this.get(req.getId());
        AccountMapper.mapper.toEntity(req, account);
        accountRepository.save(account);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Page<Account> query(AccountSearchReq req) {
        List<Specification<Account>> specificationList = new ArrayList<>();

        String nickname = req.getNickname();
        if (StringUtils.hasText(nickname)) {
            specificationList.add((AccountSpecification.nickNameLike(nickname)));
        }

        Specification<Account> specification = null;
        for (Specification<Account> spec : specificationList) {
            if (specification == null) {
                specification = Specification.where(spec);
            } else {
                specification = specification.and(spec);
            }
        }

        Sort sort = req.getSort();
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize(), sort);
        Page<Account> page;
        if (specification != null) {
            page = accountRepository.findAll(specification, pageable);
        } else {
            page = accountRepository.findAll(pageable);
        }

        return page;

    }

    @Override
    public Page<AccountListResp> list(AccountSearchReq req) {
        Page<Account> accountPage = this.query(req);
        return accountPage.map(AccountMapper.mapper::toListResp);
    }

    @Override
    public AccountLoginResp login(AccountLoginReq req) {
        if (!req.getVerifyCode().equals("3738")) {
            throw new BusinessException("验证码错误");
        }

        Account account = accountRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new BusinessException("用户名或密码错误"));

        if (!BCrypt.checkpw(req.getPassword(), account.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        AccountLoginResp loginResp = AccountMapper.mapper.toLoginResp(account);
        loginResp.setRoles(new ArrayList<>());
        loginResp.getRoles().add("admin");
        loginResp.setAccessToken("token");
        loginResp.setExpires(LocalDateTime.now().plusDays(1));
        loginResp.setRefreshToken("refreshToken");
        loginResp.setRefreshExpires(LocalDateTime.now().plusDays(7));

        StpUtil.login(account.getId(), req.isRememberMe());

        return loginResp;
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }
}
