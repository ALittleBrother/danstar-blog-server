package com.danstar.blog.server.infrastructure.constant;

/**
 * 角色枚举
 */
public enum RoleEnum {

    /**
     * 管理员
     */
    ADMIN,
    /**
     * 用户
     */
    USER;

    public static RoleEnum getRole(String role) {
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (roleEnum.name().equalsIgnoreCase(role)) {
                return roleEnum;
            }
        }
        return null;
    }
}
