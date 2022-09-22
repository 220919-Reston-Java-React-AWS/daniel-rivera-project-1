package src;

import java.util.Scanner;

public class Main {

    // main method to run program
    public static void main(String[] args) {

        // Creating Scanner object to get user input from console (aka System.in)
        Scanner userInput = new Scanner(System.in);

        // invoke the front menu of program
        menu(userInput);

    }

    // the front menu of the program
    public static void menu(Scanner input){
        // menu title card
        System.out.println("-".repeat(45));
        System.out.println("Welcome to the Employee Reimbursement System");
        System.out.println("-".repeat(45));

        // Option Prompt
        System.out.println("PLEASE ENTER THE NUMBER OF OPTION CHOICE");

        // List of program functions
        System.out.println("0 - Exit Application");
        System.out.println("1 - Login into System Account");
        System.out.println("2 - Register a New Account");

        boolean validChoice = false;

        do{
            // get user input
            System.out.print("\nInput Choice: ");
            String menuChoice = input.nextLine();

            // test the user's input for validity
            switch (menuChoice) {
                case "0":
                    // invoke method for exiting application
                    validChoice = true;
                    System.out.println("-".repeat(45));
                    System.out.println("Until next time. Good bye");
                    System.exit(0);
                    break;
                case "1":
                    validChoice = true;
                    loginAccount(input);
                    // invoke method to proceed for account login
                    break;
                case "2":
                    validChoice = true;
//                    System.out.println("Choice 2 has been picked.");
                    // invoke method to proceed for account registration
                    registerAccount(input);
                    break;
                default:
                    // ask the user to try again
                    System.out.println("Invalid Input. Please try again.");
                    break;
            }
        } while(!validChoice);  // loop until valid choice is made

    }

    // the account login menu of the program
    public static void loginAccount(Scanner input){
        // menu title card
        System.out.println("-".repeat(45));
        System.out.println("Welcome to the Account Login");
        System.out.println("-".repeat(45));

        // Option Prompt
        System.out.println("PLEASE ENTER THE NUMBER OF OPTION CHOICE");

        // List of program functions
        System.out.println("0 - Back to Menu");
        System.out.println("1 - Account Login");

        boolean validChoice = false;

        do{
            // get user input
            System.out.print("\nInput Choice: ");
            String menuChoice = input.nextLine();

            // test the user's input for validity
            switch (menuChoice) {
                case "0":
                    // invoke method for exiting application
                    validChoice = true;
                    System.out.println("Returning to main menu.");
                    menu(input);
                    break;
                case "1":
                    validChoice = true;
                    System.out.println("Choice 1 has been selected.");
                    // invoke method to proceed for account login
                    break;
                default:
                    // ask the user to try again
                    System.out.println("Invalid Input. Please try again.");
                    break;
            }
        } while(!validChoice);  // loop until valid choice is made
    }

    //
    public static void registerAccount(Scanner input){
        // menu title card
        System.out.println("-".repeat(45));
        System.out.println("Welcome to the Account Registration");
        System.out.println("-".repeat(45));

        // Option Prompt
        System.out.println("PLEASE ENTER THE NUMBER OF OPTION CHOICE");

        // List of program functions
        System.out.println("0 - Back to Menu");
        System.out.println("1 - Register a New Account");

        boolean validChoice = false;

        do{
            // get user input
            System.out.print("\nInput Choice: ");
            String menuChoice = input.nextLine();

            // test the user's input for validity
            switch (menuChoice) {
                case "0":
                    // invoke method for exiting application
                    validChoice = true;
                    System.out.println("Returning to main menu.");
                    menu(input);
                    break;
                case "1":
                    validChoice = true;
                    System.out.println("Choice 1 has been picked.");
                    // invoke method to proceed for account login
                    break;
                default:
                    // ask the user to try again
                    System.out.println("Invalid Input. Please try again.");
                    break;
            }
        } while(!validChoice);  // loop until valid choice is made
    }
}
