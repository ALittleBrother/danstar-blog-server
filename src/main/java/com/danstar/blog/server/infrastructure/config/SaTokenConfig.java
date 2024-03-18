package com.danstar.blog.server.infrastructure.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    private final String[] excludePathPatterns = {
            "/account/login",
            "/account/captcha",
            "/api-docs/**",
            "/swagger-ui/**",
            "/file/download/**",
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handle -> {
                    // 指定一条 match 规则
                    SaRouter
                            .match("/**")    // 拦截的 path 列表，可以写多个 */
                            .notMatch(excludePathPatterns)        // 排除掉的 path 列表，可以写多个
                            .check(r -> StpUtil.checkLogin());        // 要执行的校验动作，可以写完整的 lambda 表达式

                    // 根据路由划分模块，不同模块不同鉴权
//                    SaRouter.match("/account/**", r -> StpUtil.checkPermission(""));
                }))
                .addPathPatterns("/**");
    }
}

