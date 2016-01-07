package com.epam.hw1.service;

import com.epam.hw1.exception.UserNotFoundException;
import com.epam.hw1.model.UserBean;

/**
 * Service for manipulation user entities.
 *
 * @author Yevhen_Larikov on 20.12.2015.
 */
public interface UserService {
    /**
     * Adds user to repository.
     * @param userBean the user
     */
    void addUser(UserBean userBean);

    /**
     * Returns user bean by its username.
     * @param username the username
     * @return the user
     */
    UserBean getUser(String username) throws UserNotFoundException;
}
