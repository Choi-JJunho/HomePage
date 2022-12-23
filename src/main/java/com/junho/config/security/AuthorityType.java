package com.junho.config.security;

import java.util.StringJoiner;

/**
 * 권한이 높은 순서대로 Enum을 정의한다.
 */
public enum AuthorityType {
    ADMIN(ROLES.ADMIN, "관리자"),
    MANAGER(ROLES.MANAGER, "매니저"),
    USER(ROLES.USER, "사용자");

    public static class ROLES {
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String MANAGER = "ROLE_MANAGER";
        public static final String USER = "ROLE_USER";
    }

    private final String authority;
    private final String description;

    AuthorityType(String authority, String description) {
        this.authority = authority;
        this.description = description;
    }

    public static AuthorityType of(String role) {
        for (AuthorityType value : values()) {
            if (value.authority.equals(role)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid AuthorityType");
    }

    //큰 권한 순서로 ' > ' 를 사용하여 입력해준다. 띄어쓰기도 중요하다.
    public static String getHierarchy() {
        StringJoiner joiner = new StringJoiner(" > ");
        for (AuthorityType value : values()) {
            joiner.add(value.authority);
        }
        return joiner.toString();
    }

    public String getDescription() {
        return this.description;
    }
}
