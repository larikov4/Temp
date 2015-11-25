package com.epam.hw1.dao;

/**
 * @author Yevhen_Larikov
 */
public interface UserAccountDao {
    boolean refillAccount(int amount);

    double getBalance();

    boolean withdraw(int amount);
}
