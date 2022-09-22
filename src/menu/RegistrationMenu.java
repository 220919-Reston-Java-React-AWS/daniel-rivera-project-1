package src.menu;

import src.Main;

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

        String menuChoice; // user input

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
                break;
            case "2":   // return to the Main Menu of application
                //exit message
                System.out.println("Returning to Main Menu");
                System.out.println("-".repeat(45) + "\n");
                Main.mainMenuObject.display();  // Using MainMenu mainMenuObject from src.Main
                break;
        }
    }
}
