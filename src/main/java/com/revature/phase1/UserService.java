package com.revature.phase1;

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
