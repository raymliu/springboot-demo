package com.hand.domain;

/**
 * Created by Ray Ma on 2018/6/7.
 */
public class UserAccount {
    private final int userId;
    private final String name;
    private final int accountType;
    private final boolean isFeatureXenabled;
    private final boolean isFeatureYenabled;
    private final boolean isFeatureZenabled;

    public UserAccount(int userId, String name, int accountType, boolean x, boolean y, boolean z) {
        this.userId = userId;
        this.name = name;
        this.accountType = accountType;
        this.isFeatureXenabled = x;
        this.isFeatureYenabled = y;
        this.isFeatureZenabled = z;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public int getAccountType() {
        return accountType;
    }

    public boolean isFeatureXenabled() {
        return isFeatureXenabled;
    }

    public boolean isFeatureYenabled() {
        return isFeatureYenabled;
    }

    public boolean isFeatureZenabled() {
        return isFeatureZenabled;
    }
}
