package com.epam.hw1.repository;

import com.epam.hw1.model.UserBean;

/**
 * Created by Yevhn on 20.12.2015.
 */
public interface UserRepository {
    void addUser(UserBean userBean);

    UserBean getUser(String username);

    boolean exists(String username);
}
