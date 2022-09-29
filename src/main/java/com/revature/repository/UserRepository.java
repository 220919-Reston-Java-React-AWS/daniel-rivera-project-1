package com.revature.repository;

import com.revature.model.UserAccount;

import java.util.*;

public class UserRepository {
    // temporary method to store information of users made
    // by using HashMap; will clear out when app restarts/end
    private Map<String, UserAccount> users = new HashMap<>();

    public void addUser(UserAccount user){
        // let's give the username of the account for the key
        users.put(user.getUsername(), user);
    }

    // get all the UserAccount objects
    public List<UserAccount> getAllUsers(){
        List<UserAccount> results = new ArrayList<>(); // List to hold results
        Set<String> keys = users.keySet();   // get all unique keys

        for (String k: keys) { // loop to add UserAccount objects
            results.add(users.get(k));
        }

        return results;
    }

    // see if a username exists ()
    public Boolean checkUsername(String username){
        Set<String> keys = users.keySet(); // get all unique keys

        return keys.contains(username);
    }

    public UserAccount getUser(String username){
        return users.get(username);
    }
}
