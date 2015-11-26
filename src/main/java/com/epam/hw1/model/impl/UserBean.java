package com.epam.hw1.model.impl;

import com.epam.hw1.model.User;

import java.io.Serializable;
import java.util.Date;

/**
 * <code>User</code> implementation.
 *
 * @author Yevhen_Larikov
 */
public class UserBean implements User, Serializable {
    private Long id;
    private String name;
    private String email;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBean userBean = (UserBean) o;
        return id == userBean.id &&
                com.google.common.base.Objects.equal(name, userBean.name) &&
                com.google.common.base.Objects.equal(email, userBean.email);
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(id, name, email);
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
