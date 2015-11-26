package com.epam.hw1.dao;

/**
 * @author Yevhen_Larikov
 */
public interface UserAccountDao {
    boolean refillAccount(long userId, double amount);

    boolean withdraw(long userId, double amount);
}
