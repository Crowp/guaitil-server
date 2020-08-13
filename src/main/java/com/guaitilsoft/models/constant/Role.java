package com.guaitilsoft.models.constant;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_ASSOCIATED;

    public String getAuthority() {
        return name();
    }
}
