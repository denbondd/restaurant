package com.denbondd.restaurant.db.entity;

import java.util.Date;

public class User {

    private long id;
    private String login;
    private long roleId;
    private Date createDate;

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public long getRoleId() {
        return roleId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", roleId=" + roleId +
                ", createDate=" + createDate +
                '}';
    }

    public static class Builder {
        User user = new User();

        public Builder setId(long id) {
            user.id = id;
            return this;
        }

        public Builder setLogin(String login) {
            user.login = login;
            return this;
        }

        public Builder setRoleId(long roleId) {
            user.roleId = roleId;
            return this;
        }

        public Builder setCreateDate(Date createDate) {
            user.createDate = createDate;
            return this;
        }

        public User getUser() {
            return user;
        }
    }
}
