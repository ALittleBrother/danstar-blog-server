package com.danstar.blog.server.infrastructure.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class CaptchaConfig {

    @Bean
    public DefaultKaptcha captchaProducer() {
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        // 配置 Kaptcha
        Properties properties = new Properties();
        // 配置图片宽度
        properties.setProperty("kaptcha.image.width", "150");
        // 配置图片高度
        properties.setProperty("kaptcha.image.height", "32");
        // 配置字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "25");
        // 配置文本长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        // 关闭外层边框
        properties.setProperty("kaptcha.border", "no");
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }
}
