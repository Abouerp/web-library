package com.abouerp.zsc.library.domain.user;

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

    BOOK_CATEGORY_CREATE("图书分类创建"),
    BOOK_CATEGORY_READ("图书分类查看"),
    BOOK_CATEGORY_UPDATE("图书分类更新"),
    BOOK_CATEGORY_DELETE("图书分类删除"),

    BOOK_CREATE("图书创建"),
    BOOK_READ("图书查看"),
    BOOK_UPDATE("图书更新"),
    BOOK_DELETE("图书删除"),

    BOOK_DETAIL_CREATE("图书详细创建"),
    BOOK_DETAIL_READ("图书详细查看"),
    BOOK_DETAIL_UPDATE("图书详细更新"),
    BOOK_DETAIL_DELETE("图书详细删除"),


    OPERATOR_LOGGER_READ("操作日志查看"),
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
