package com.epam.hw1.repository;

import com.epam.hw1.exception.UserNotFoundException;
import com.epam.hw1.model.UserBean;

/**
 * Repository for storing users.
 *
 * @author Yevhen_Larikov on 20.12.2015.
 */
public interface UserRepository {
    /**
     * Adds user to repository.
     * @param version the version
     * @param userBean the user
     */
    void addUser(long version, UserBean userBean);

    /**
     * Returns user bean by its username.
     * @param username the username
     * @return the user
     */
    UserBean getUser(String username);

    /**
     * Checks user existence and throws exception when user wasn't found.
     * @param username the username
     * @throws UserNotFoundException is thrown when user wasn't found.
     */
    void checkUserExistence(String username) throws UserNotFoundException;
}
