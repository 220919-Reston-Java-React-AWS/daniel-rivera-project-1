package com.revature.menu;

import com.revature.Main;
import com.revature.service.AuthService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginMenu {
    public void display(){
        // menu title card
        System.out.println("-".repeat(45));
        System.out.println("Welcome to the Account Login");
        System.out.println("-".repeat(45));

        // Option Prompt
        System.out.println("PLEASE ENTER THE NUMBER OF OPTION CHOICE");

        // List of program functions
        System.out.println("1 - Account Login");
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
            case "1":   // go to Log-in Account Prompt
                loginAccountPrompt();
                break;
            case "2":   // return to the Main Menu of application
                //exit message
                System.out.println("Returning to Main Menu");
                System.out.println("-".repeat(45) + "\n");
                Main.mainMenuObject.display();  // Using MainMenu mainMenuObject from src.Main
                break;
        }
    }

    // process method for logging in User Account
    public void loginAccountPrompt(){
        String username, password;
        // ask for a username to be used to log in
        System.out.println("Enter the username to login:");
        username = Main.sc.nextLine();
        // ask for a password to be used to log in
        System.out.println("Enter the password to login");
        password = Main.sc.nextLine();

        // check that user account with username exists & password match
        // if yes, enter the reimbursement ticket menu with account; else, reject
        // give invalid messages for username not found or incorrect password
        try {
            Main.authService.login(username, password);
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

        display();
    }
}