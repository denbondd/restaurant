package com.denbondd.restaurant.db.entity;

import java.util.Date;
import java.util.List;

public class Receipt {

    private long id;
    private long userId;
    private Status status;
    private int total;
    private long managerId;
    private Date createDate;
    private List<Dish> dishes;

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public Status getStatus() {
        return status;
    }

    public int getTotal() {
        return total;
    }

    public long getManagerId() {
        return managerId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
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
            receipt.status = Status.getStatusById(statusId);
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

        public Builder setCreateDate(Date createDate) {
            receipt.createDate = createDate;
            return this;
        }

        public Builder setDishes(List<Dish> dishes) {
            receipt.dishes = dishes;
            return this;
        }

        public Receipt getReceipt() {
            return receipt;
        }
    }

    public static class Dish {
        private long id;
        private String name;
        private int count;
        private int price;

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getCount() {
            return count;
        }

        public int getPrice() {
            return price;
        }

        public static class Builder {
            private final Dish dish = new Dish();

            public Builder setId(long id) {
                dish.id = id;
                return this;
            }

            public Builder setName(String name) {
                dish.name = name;
                return this;
            }

            public Builder setCount(int count) {
                dish.count = count;
                return this;
            }

            public Builder setPrice(int price) {
                dish.price = price;
                return this;
            }

            public Dish getDish() {
                return dish;
            }
        }
    }
}
