package com.guaitilsoft.models.constant;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN("Admin"),
    ROLE_SUPER_ADMIN("Super Admin"),
    ROLE_ASSOCIATED("Asociado");

    private final String message;

    Role(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthority() {
        return name();
    }
}
