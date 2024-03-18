package com.danstar.blog.server.infrastructure.util;

import java.util.UUID;

/**
 * 通用工具类
 */
public class CommonUtils {

    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
