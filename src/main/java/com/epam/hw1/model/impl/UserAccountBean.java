package com.epam.hw1.model.impl;

import com.epam.hw1.model.UserAccount;

/**
 * @author Yevhen_Larikov
 */
public class UserAccountBean implements UserAccount{
    private long id;
    private long userId;
    private double balance;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void setBalance(double amount) {
        this.balance = amount;
    }
}
