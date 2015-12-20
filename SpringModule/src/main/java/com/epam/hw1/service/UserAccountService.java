package com.epam.hw1.service;

/**
 * Service interface for <code>UserAccount</code> entity.
 *
 * @author Yevhen_Larikov
 */
public interface UserAccountService {
    /**
     * Puts passed amount of money to User's account.
     *
     * @param userId user id
     * @param amount amount of money to refill
     * @return boolean whether account was refilled
     */
    boolean refillAccount(long userId, double amount);

    /**
     * Withdraw passed amount of money from User's account.
     *
     * @param userId user id
     * @param amount amount of money to withdraw
     * @return boolean whether money was withdraw
     */
    boolean withdraw(long userId, double amount);
}
