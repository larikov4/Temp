package com.epam.hw1.service;

/**
 * @author Yevhen_Larikov
 */
public interface UserAccountService {
    boolean refillAccount(long userId, double amount);

    boolean withdraw(long userId, double amount);
}
