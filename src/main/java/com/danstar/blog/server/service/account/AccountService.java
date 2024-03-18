package com.danstar.blog.server.service.account;

import com.danstar.blog.server.entity.Account;
import com.danstar.blog.server.vo.account.*;
import org.springframework.data.domain.Page;

public interface AccountService {

    /**
     * 添加账户
     */
    void add(AccountAddReq req);

    /**
     * 获取账户
     */
    Account get(Long id);

    /**
     * 获取账户详情
     */
    AccountDetailResp getDetail(Long id);

    /**
     * 更新账户
     */
    void update(AccountUpdateReq req);

    /**
     * 删除账户
     */
    void delete(Long id);

    /**
     * 查询
     */
    Page<Account> query(AccountSearchReq req);

    /**
     * 查询列表
     */
    Page<AccountListResp> list(AccountSearchReq req);

    /**
     * 登录
     */
    AccountLoginResp login(AccountLoginReq req);

    /**
     * 登出
     */
    void logout();

    /**
     * 生成验证码
     */
    CaptchaResp generateCaptcha();

    /**
     * 验证验证码
     */
    boolean validateCaptcha(String key, String code);
}
