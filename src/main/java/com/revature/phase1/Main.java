package com.revature.phase1;

import com.revature.phase1.menu.MainMenu;

import java.util.Scanner;

public class Main {
    // instantiate global (static) Scanner object
    public static Scanner sc = new Scanner(System.in);
    // instantiate a MainMenu object
    public static MainMenu mainMenuObject = new MainMenu();
    // instantiate a UserRepository object
    public static UserRepository userRepository = new UserRepository();
    //instantiate a TicketRepository
    public static TicketRepository ticketRepository = new TicketRepository();
    public static AuthService authService = new AuthService();

    public static void main(String[] args) {
        // invoke display method of MainMenu (the front menu of app)
        mainMenuObject.display();
    }
}
