package com.danstar.blog.server.service.account;

import com.danstar.blog.server.convert.AccountMapper;
import com.danstar.blog.server.entity.Account;
import com.danstar.blog.server.infrastructure.exception.ResourceNotFoundException;
import com.danstar.blog.server.repository.AccountRepository;
import com.danstar.blog.server.repository.AccountSpecification;
import com.danstar.blog.server.vo.account.*;
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
    public Page<AccountListResp> queryList(AccountSearchReq req) {
        Page<Account> accountPage = this.query(req);
        return accountPage.map(AccountMapper.mapper::toListResp);
    }
}
