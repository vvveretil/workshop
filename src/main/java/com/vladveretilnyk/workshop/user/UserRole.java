package com.vladveretilnyk.workshop.user;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ADMIN, MASTER, USER;

    @Override
    public String getAuthority() {
        return "ROLE_"+name();
    }
}
