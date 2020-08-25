package com.github.haseoo.courier.enums;

public enum ClientType {
    INDIVIDUAL("individual"),
    COMPANY("company");

    private String string;

    ClientType(String s) {
        string = s;
    }

    @Override
    public String toString() {
        return string;
    }
}
