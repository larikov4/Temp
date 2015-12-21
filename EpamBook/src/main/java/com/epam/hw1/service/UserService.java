package com.epam.hw1.service;

import com.epam.hw1.model.UserBean;

/**
 * Created by Yevhn on 20.12.2015.
 */
public interface UserService {
    void addUser(UserBean userBean);

    UserBean getUser(String username);
}
