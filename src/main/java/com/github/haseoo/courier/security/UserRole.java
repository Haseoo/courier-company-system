package com.github.haseoo.courier.security;

public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    COURIER("ROLE_COURIER"),
    LOGISTICIAN("ROLE_LOGISTICIAN"),
    CLIENT("ROLE_CLIENT");

    private String string;

    UserRole(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}
