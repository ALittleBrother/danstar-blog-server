package com.danstar.blog.server.controller;

import com.danstar.blog.server.infrastructure.validation.group.CreateOperation;
import com.danstar.blog.server.service.account.AccountService;
import com.danstar.blog.server.vo.account.*;
import com.danstar.blog.server.vo.common.PageEntity;
import com.danstar.blog.server.vo.common.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@Tag(name = "AccountController", description = "账户相关接口")
public class AccountController {

    private AccountService accountService;
    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @Operation(summary = "添加账户")
    public ResponseEntity<Void> add(@Validated(value = CreateOperation.class) @RequestBody AccountAddReq req) {
        accountService.add(req);
        return ResponseEntity.ok();
    }

    @PutMapping
    @Operation(summary = "更新账户")
    public ResponseEntity<Void> update(@Valid@RequestBody AccountUpdateReq req) {
        accountService.update(req);
        return ResponseEntity.ok();
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取账户详情")
    public ResponseEntity<AccountDetailResp> getDetail(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getDetail(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除账户")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accountService.delete(id);
        return ResponseEntity.ok();
    }

    @PostMapping("/search")
    @Operation(summary = "查询账户列表")
    public ResponseEntity<PageEntity<AccountListResp>> queryList(@RequestBody AccountSearchReq req) {
        return ResponseEntity.ok(new PageEntity<>(accountService.list(req)));
    }

    @PostMapping("/login")
    @Operation(summary = "账户登录")
    public ResponseEntity<AccountLoginResp> login(@Valid@RequestBody AccountLoginReq req) {
        AccountLoginResp loginResp = accountService.login(req);
        return ResponseEntity.ok(loginResp);
    }

    @PostMapping("/logout")
    @Operation(summary = "账户登出")
    public ResponseEntity<Void> logout() {
        accountService.logout();
        return ResponseEntity.ok();
    }

    @GetMapping("/captcha")
    @Operation(summary = "获取验证码")
    public ResponseEntity<CaptchaResp> getCaptcha() {
        return ResponseEntity.ok(accountService.generateCaptcha());
    }
}
