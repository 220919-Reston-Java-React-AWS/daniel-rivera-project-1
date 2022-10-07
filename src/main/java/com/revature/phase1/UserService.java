package com.revature.service;

import com.revature.Main;
import com.revature.model.UserAccount;
import com.revature.repository.UserRepository;

import java.util.List;

public class UserService {
    private UserRepository userRepository = Main.userRepository;
    public List<UserAccount> getAllUsers(){
        return userRepository.getAllUsers();
    }
    public UserAccount getUser(String username){
        return userRepository.getUser(username);
    }

}
