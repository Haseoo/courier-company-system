package com.github.haseoo.courier.enums;

public enum EmployeeType {
    LOGISTICIAN("logistician"),
    COURIER("courier");

    private String string;
    EmployeeType(String s) {
        string = s;
    }

    @Override
    public String toString() {
        return string;
    }
}
