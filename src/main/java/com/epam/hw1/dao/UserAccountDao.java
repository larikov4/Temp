package com.epam.hw1.dao;

import com.epam.hw1.model.UserAccount;

/**
 * Dao interface for <code>UserAccount</code> entity.
 *
 * @author Yevhen_Larikov
 */
public interface UserAccountDao {
    /**
     * Returns User's account by passed id.
     *
     * @param userId user id
     * @return User's account
     */
    UserAccount getUserAccount(long userId);

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
