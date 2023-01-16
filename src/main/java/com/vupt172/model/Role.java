package com.vupt172.model;

public enum Role {
    SUPER_ADMIN(0),
    ADMIN(1),
    USER(2);
    private int value;
    private Role(int value){
        this.value=value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
