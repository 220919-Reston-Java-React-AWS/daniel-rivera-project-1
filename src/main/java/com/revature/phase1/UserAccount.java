package com.revature.phase1;

public class UserAccount {
    // user login information
    private String username;
    private String password;

    // account job position - either Employee or Manager
    // Employee is default for Phase 1
    private String jobPosition;

    // future possible additions
    // private String firstName;
    // private String lastName;

    // constructor for User Account
    // used for registering a new account
    public UserAccount(String username, String password){
        // set initialized User object variable values
        this.username = username;
        this.password = password;
        this.jobPosition = "Employee";  // default case for now in Phase 1
    }

    // getter and setter methods
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) { this.username = username; }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJobPosition() {
        return jobPosition;
    }

}