package com.revature.phase1;

import com.revature.phase1.menu.ReimbursementMenu;

import java.util.List;

public class AuthService {
    private UserRepository userRepository = Main.userRepository;
    private TicketRepository ticketRepository = Main.ticketRepository;

    // Business logic
    // input validation
    // Ex. check if username or password has spaces && username not taken; block it if yes
    public void register(UserAccount user){
        if (user.getUsername().contains(" ") || user.getPassword().contains(" ")){
            throw new IllegalArgumentException("spaces not allowed in username or password");
        }
        if (userRepository.checkUsername(user.getUsername())){
            throw new IllegalArgumentException("Username is already taken");
        }

        userRepository.addUser(user); // add the user

        //return true if successful
//        System.out.println("New User Account has been registered");
    }

    // input validation
    // Ex. check that a ticket has an amount and description
    public void issueTicket(ReimbursementTicket ticket){
        if (ticket.getTicketAmount() == 0){
            throw new IllegalArgumentException("Reimbursement amount is not set. (Must be greater than 0.00)");
        }
        if (ticket.getTicketDescription().equals(null)){
            throw new IllegalArgumentException("Reimbursement description is not set. (Must provide a description)");
        }

        ticketRepository.addTicket(ticket);



//        return true;

    }

    // input validation
    // Ex. check if username or password has spaces && username not taken; block it if yes
    public void login(String username, String password){
        if (username.contains(" ") || password.contains(" ")){
            throw new IllegalArgumentException("spaces not allowed in username or password");
        }
        if (!userRepository.checkUsername(username)){
            throw new IllegalArgumentException("Username is does not exists");
        }

        UserService userService = new UserService();
        UserAccount userAccount = userService.getUser(username);

        System.out.println(userAccount.getPassword());
        System.out.println(password);

        if(userAccount.getPassword().equals(password)){
            ReimbursementMenu reimbursementMenu = new ReimbursementMenu();
            reimbursementMenu.display(userAccount);
        } else {
            throw new IllegalArgumentException("Incorrect password");
        }

//        System.out.println("Login successful");
    }

    // get the reimbursement tickets made by the employee
    public void pastIssuedTickets(UserAccount loggedInUser){
        TicketService ticketService = new TicketService();
        List<ReimbursementTicket> employeeTickets = ticketService.getEmployeeTickets(loggedInUser);

        for (ReimbursementTicket ticket: employeeTickets) {
            System.out.println();
            System.out.println(ticket.toString());
            System.out.println();
        }

        ReimbursementMenu rm = new ReimbursementMenu();
        rm.display(loggedInUser);
    }
}
