package com.revature.phase1.menu;

import com.revature.phase1.Main;
import com.revature.phase1.UserAccount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegistrationMenu {
    public void display(){
        // menu title card
        System.out.println("-".repeat(45));
        System.out.println("Welcome to the Account Registration");
        System.out.println("-".repeat(45));

        // Option Prompt
        System.out.println("PLEASE ENTER THE NUMBER OF OPTION CHOICE");

        // List of program functions
        System.out.println("1 - Register a New Account");
        System.out.println("2 - Return Back to Main Menu");

        // list of valid menuOptions to look for from input
        // convert to List<> to use methods to ease job
        String[] menuOptions = new String[]{"1", "2"};
        List<String> menuList = new ArrayList<>(Arrays.asList(menuOptions));

        // user input
        String menuChoice;

        while(true){
            // get user input
            System.out.print("\nInput Choice: ");
            menuChoice = Main.sc.nextLine();    // Using Scanner sc from src.Main

            // check if input is valid
            if (menuList.contains(menuChoice)) {
                break;
            } else {
                System.out.println("invalid choice. Please try again.");
            }
        }

        // Invoke decided menuChoice from User
        switch(menuChoice){
            case "1":   // go to Register Account Prompt
                registerAccountPrompt();
                break;
            case "2":   // return to the Main Menu of application
                //exit message
                System.out.println("Returning to Main Menu");
                System.out.println("-".repeat(45) + "\n");
                Main.mainMenuObject.display();  // Using MainMenu mainMenuObject from src.Main
                break;
        }
    }

    // process method for creating a new User Account
    public void registerAccountPrompt(){
        String username, password;
        // ask for a username to be used to log in
        System.out.println("Enter the username to register with:");
        username = Main.sc.nextLine();
        // ask for a password to be used to log in
        System.out.println("Enter the password to register with");
        password = Main.sc.nextLine();

        // create User Account
        UserAccount newUser = new UserAccount(username, password);

        // System.out.println(newUser.toString());

        // check that username or password have no spaces
        // check that username is not already registered
        // add (register) the new account
        try {
            Main.authService.register(newUser);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            display();
        }
        Main.mainMenuObject.display();
    }
}