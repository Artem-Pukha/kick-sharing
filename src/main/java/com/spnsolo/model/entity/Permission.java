package com.spnsolo.model.entity;

public enum Permission {
    DEVELOPERS_READ("scooters:read"),
    DEVELOPER_WRITE("scooters:write");

    private final String permission;

     Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}

