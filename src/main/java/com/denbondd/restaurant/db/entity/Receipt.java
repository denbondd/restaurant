package com.denbondd.restaurant.db.entity;

public class Receipt {

    private long id;
    private long userId;
    private long statusId;
    private int total;
    private long managerId;

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getStatusId() {
        return statusId;
    }

    public int getTotal() {
        return total;
    }

    public long getManagerId() {
        return managerId;
    }

    public static class Builder {
        private final Receipt receipt = new Receipt();

        public Builder setId(long id) {
            receipt.id = id;
            return this;
        }

        public Builder setUserId(long userId) {
            receipt.userId = userId;
            return this;
        }

        public Builder setStatusId(long statusId) {
            receipt.statusId = statusId;
            return this;
        }

        public Builder setTotal(int total) {
            receipt.total = total;
            return this;
        }

        public Builder setManagerId(long managerId) {
            receipt.managerId = managerId;
            return this;
        }

        public Receipt getReceipt() {
            return receipt;
        }
    }
}
