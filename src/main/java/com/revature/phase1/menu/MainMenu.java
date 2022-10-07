package com.revature.phase1.menu;

import com.revature.phase1.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainMenu {
    public void display() {
        // menu title card
        System.out.println("-".repeat(45));
        System.out.println("Welcome to the Employee Reimbursement System");
        System.out.println("-".repeat(45));

        // Option Prompt
        System.out.println("PLEASE ENTER THE NUMBER OF OPTION CHOICE");

        // List of program functions
        System.out.println("1 - Login Account");
        System.out.println("2 - Register a New Account");
        System.out.println("3 - Exit Application");

        // list of valid menuOptions to look for from input
        // convert to List<> to use methods to ease job
        String[] menuOptions = new String[]{"1", "2", "3"};
        List<String> menuList = new ArrayList<>(Arrays.asList(menuOptions));

        // user input
        String menuChoice;

        // loop for valid user choice
        while(true){
            // get user input
            System.out.print("\nInput Choice: ");
            menuChoice = Main.sc.nextLine();

            // check if input is valid
            if (menuList.contains(menuChoice)) {
                break;
            } else {
                System.out.println("invalid choice. Please try again.");
            }
        }

        // Invoke decided menuChoice from User
        switch(menuChoice){
            case "1":   // go to Log-in Account
                System.out.println("Entering Account Login");
                System.out.println("-".repeat(45) + "\n");
                LoginMenu loginMenu = new LoginMenu();
                loginMenu.display();
                break;
            case "2":   // go to Register Account Menu
                System.out.println("Entering Account Registration");
                System.out.println("-".repeat(45) + "\n");
                RegistrationMenu registrationMenu = new RegistrationMenu();
                registrationMenu.display();
                break;
            case "3":   // end the Application
                //exit message
                System.out.println("Thank you for using the Employee Reimbursement System. Goodbye");
                System.out.println("-".repeat(45));
                System.exit(0); // ends program
                break;
        }

    }
}
