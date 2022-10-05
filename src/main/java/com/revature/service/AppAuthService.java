package com.revature.service;

import com.revature.exception.InvalidAccountLoginException;
import com.revature.exception.InvalidRegisterAccountException;
import com.revature.model.AppUserAccount;
import com.revature.repository.AppUsersRepository;

import java.sql.SQLException;

public class AppAuthService {
    private AppUsersRepository usersRepository = new AppUsersRepository();

    // Business Logic - registering a new User
    // input validation
    public void register(AppUserAccount user) throws SQLException, InvalidRegisterAccountException {
        // let's block spaces from being used in usernames and passwords
        if(user.getUsername().contains(" ") || user.getPassword().contains(" ")){
            throw new InvalidRegisterAccountException("Spaces are not allowed in username or password");
        }

        // Check if the username for the new account has been taken
        if(usersRepository.checkUsernameExists(user.getUsername())){
            throw new InvalidRegisterAccountException("Username (" + user.getUsername() + ") has already been taken.");
        }

        // try to create the new account
        usersRepository.createUserAccount(user);
    }

    // Business Logic - log into an existing user account
    // input validation
    public AppUserAccount login(String username, String password) throws SQLException, InvalidAccountLoginException {
        // could return a null valve or non-null
        // null if invalid credentials provided
        // non-null = valid credentials were provided

        AppUserAccount user = usersRepository.getUserByUsernameAndPassword(username, password);

        // there's two things that a method might do
        // 1 - successfully return value
        // 2 - throw an exception and not return anything
        if(user == null){
            throw new InvalidAccountLoginException("Invalid username and/or password");
        }

        // return user object to create HTTP cookie
        return user;
    }
}
