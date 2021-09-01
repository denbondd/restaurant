package com.denbondd.restaurant.db.entity;

public class Category {

    private long id;
    private String name;
    private Long parentId;
    private String description;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getParentId() {
        return parentId;
    }

    public String getDescription() {
        return description;
    }

    public static class Builder {
        Category category = new Category();

        public Category getCategory() {
            return category;
        }

        public Builder setId(long id) {
            category.id = id;
            return this;
        }

        public Builder setName(String name) {
            category.name = name;
            return this;
        }

        public Builder setParentId(long parentId) {
            category.parentId = parentId;
            return this;
        }

        public Builder setDescription(String description) {
            category.description = description;
            return this;
        }
    }
}
