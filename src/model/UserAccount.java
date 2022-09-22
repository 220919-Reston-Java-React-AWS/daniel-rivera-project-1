package src.model;

public class UserAccount {
    // user login information
    public String username;
    public String password;

    // account job position - either Employee or Manager
    // Employee is default for Phase 1
    public String jobPosition;

    // Employee number id variable?

    // constructor for User Account
    // used for registering a new account
    public UserAccount(String username, String password, String jobPosition){
        // set initialized User object variable values
        this.username = username;
        this.password = password;
        this.jobPosition = jobPosition;
    }
}
