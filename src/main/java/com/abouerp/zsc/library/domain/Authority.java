package com.abouerp.zsc.library.domain;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.EnumMap;

/**
 * @author Abouerp
 */
public enum Authority {

    ROLE_CREATE("角色创建"),
    ROLE_READ("角色查看"),
    ROLE_UPDATE("角色更新"),
    ROLE_DELETE("角色删除"),

    USER_CREATE("用户创建"),
    USER_READ("用户查看"),
    USER_UPDATE("用户更新"),
    USER_DELETE("用户删除"),

    AUTHORITY_READ("权限查看");

    public static final EnumMap<Authority, String> mappings = new EnumMap<>(Authority.class);

    static {
        for (Authority authority : values()) {
            mappings.put(authority, authority.getDescription());
        }
    }

    private final String description;

    Authority(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public SimpleGrantedAuthority springAuthority() {
        return new SimpleGrantedAuthority(this.name());
    }
}
