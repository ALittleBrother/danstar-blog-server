package com.danstar.blog.server.vo.common;

import lombok.Data;

/**
 * 统一响应体
 */
@Data
public class ResponseEntity<T> {
    private int code;
    private String message;
    private T data;

    public ResponseEntity(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 成功响应
    public static <T> ResponseEntity<T> ok() {
        return new ResponseEntity<>(200, "操作成功", null);
    }

    public static <T> ResponseEntity<T> ok(T data) {
        return new ResponseEntity<>(200, "操作成功", data);
    }

    // 失败响应
    public static <T> ResponseEntity<T> fail(int code, String message) {
        return new ResponseEntity<>(code, message, null);
    }

    public static <T> ResponseEntity<T> fail(int code, String message, T data) {
        return new ResponseEntity<>(code, message, data);
    }
}
