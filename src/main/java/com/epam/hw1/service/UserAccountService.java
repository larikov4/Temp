package com.epam.hw1.service;

/**
 * @author Yevhen_Larikov
 */
public interface UserAccountService {
    boolean refillAccount(int amount);

    double getBalance();

    boolean withdraw(int amount);
}
