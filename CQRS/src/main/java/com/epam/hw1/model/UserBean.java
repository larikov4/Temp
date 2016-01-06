package com.epam.hw1.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Objects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents user entity.
 *
 * Created by Yevhen_Larikov on 20.12.2015.
 */
public class UserBean {
    private String name;
    private String username;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "UTC")
    private Date dateOfBirth;

    public UserBean() {
    }

    public UserBean(UserBean user) {
        name = user.name;
        username = user.username;
        dateOfBirth = user.dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBean userBean = (UserBean) o;
        return Objects.equal(name, userBean.name) &&
                Objects.equal(username, userBean.username) &&
                Objects.equal(dateOfBirth, userBean.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, username, dateOfBirth);
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
