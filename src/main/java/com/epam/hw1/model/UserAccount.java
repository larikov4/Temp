package com.epam.hw1.model;

/**
 * @author Yevhen_Larikov
 */
public interface UserAccount {
    /**
     * Amount of prepaid money.
     * @return money amount
     */
    double getBalance();
    void setBalance(double money);
}
