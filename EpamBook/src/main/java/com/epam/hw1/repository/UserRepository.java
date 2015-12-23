package com.epam.hw1.repository;

import com.epam.hw1.exception.UserNotFoundException;
import com.epam.hw1.model.UserBean;

/**
 * Created by Yevhn on 20.12.2015.
 */
public interface UserRepository {
    void addUser(UserBean userBean);

    UserBean getUser(String username);

    boolean isUserExist(String username) throws UserNotFoundException;

    void checkUserExistence(String username) throws UserNotFoundException;
}
