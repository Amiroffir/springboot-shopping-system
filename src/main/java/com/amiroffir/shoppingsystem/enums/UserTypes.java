package com.amiroffir.shoppingsystem.enums;

public enum UserTypes {
    Admin("Admin"),
    Customer("Customer");

    private String value;

    UserTypes(String type) {
        this.value = type;
    }

    public String getValue() {
        return value;
    }
}