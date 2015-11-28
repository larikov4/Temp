package com.epam.hw1.dao;

import com.epam.hw1.model.UserAccount;

/**
 * @author Yevhen_Larikov
 */
public interface UserAccountDao {
    UserAccount getUserAccount(long userId);

    boolean refillAccount(long userId, double amount);

    boolean withdraw(long userId, double amount);
}
