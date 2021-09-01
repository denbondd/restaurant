package com.denbondd.restaurant.db.entity;

public class Dish {

    private long id;
    private String name;
    private long categoryId;
    private long price;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public long getPrice() {
        return price;
    }

    public static class Builder {
        Dish dish = new Dish();

        public Dish getDish() {
            return dish;
        }

        public Builder setId(long id) {
            dish.id = id;
            return this;
        }

        public Builder setName(String name) {
            dish.name = name;
            return this;
        }

        public Builder setCategoryId(long categoryId) {
            dish.categoryId = categoryId;
            return this;
        }

        public Builder setPrice(long price) {
            dish.price = price;
            return this;
        }
    }
}
