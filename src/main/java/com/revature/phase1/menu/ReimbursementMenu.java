package com.revature.phase1.menu;

import com.revature.phase1.Main;
import com.revature.phase1.ReimbursementTicket;
import com.revature.phase1.UserAccount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReimbursementMenu {
    private UserAccount loggedInUser;

    public void display(UserAccount user){
        loggedInUser = user;    // set user

        // menu title card
        System.out.println("-".repeat(45));
        System.out.println("Welcome to the Reimbursement System " +loggedInUser.getUsername());
        System.out.println("-".repeat(45));

        // Option Prompt
        System.out.println("PLEASE ENTER THE NUMBER OF OPTION CHOICE");

        // List of program functions
        System.out.println("1 - Issue a Ticket");
        System.out.println("2 - Check Submitted Tickets");
        System.out.println("3 - Logout Back to Main Menu");

        // list of valid menuOptions to look for from input
        // convert to List<> to use methods to ease job
        String[] menuOptions = new String[]{"1", "2", "3"};
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
            case "1":   // go to Issue a Ticket Prompt
                issueTicketPrompt();
                break;
            case "2":   // go to past tickets made by employee
                pastTicketPrompt();
                break;
            case "3":   // return to the Main Menu of application
                //exit message
                System.out.println("Logging out. Returning to Main Menu");
                System.out.println("-".repeat(45) + "\n");
                loggedInUser = null;    // clear UserAccount (garbage collect)
                Main.mainMenuObject.display();  // Using MainMenu mainMenuObject from src.Main
                break;
        }
    }

    // process method for creating a new ReimbursementTicket
    public void issueTicketPrompt(){
        double amount;
        String description;
        // ask for an amount to reimburse
        System.out.println("Enter the amount to reimburse:");
        amount = Main.sc.nextDouble();
        Main.sc.nextLine(); // remove the newline
        // ask for a description of the reimbursement
        System.out.println("Enter the description for the reimbursement:");
         description = Main.sc.nextLine();

        // need to pass in who made the ticket as well
        // create the Reimbursement Ticket
        ReimbursementTicket newTicket = new ReimbursementTicket(loggedInUser.getUsername(), amount, description);

//        System.out.println(newTicket.toString());

        // check that amount isn't 0.00
        // check that description isn't null
        // add the new ticket
        Main.authService.issueTicket(newTicket);
        display(loggedInUser);
    }

    // process method for getting pass tickets made by employee
    public void pastTicketPrompt(){
        Main.authService.pastIssuedTickets(loggedInUser);
    }
}
