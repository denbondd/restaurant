package com.denbondd.restaurant.db.entity;

public enum Status {
    NEW(1, "Waiting"), APPROVED(2, "Approved"),
    COOKING(3, "Cooking"), DELIVERING(4, "Delivering"),
    RECEIVED(5, "Received");

    private final long id;
    private final String value;

    Status(long id, String value) {
        this.value = value;
        this.id = id;
    }

    public static Status getStatusById(long id) {
        for (Status s : values()) {
            if (s.id == id) {
                return s;
            }
        }
        return null;
    }

    public long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
