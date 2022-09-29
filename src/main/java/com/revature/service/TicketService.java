package com.revature.service;

import com.revature.Main;
import com.revature.model.ReimbursementTicket;
import com.revature.model.UserAccount;
import com.revature.repository.TicketRepository;

import java.util.List;

public class TicketService {
    private TicketRepository ticketRepository = Main.ticketRepository;

    // get all issued tickets by every employee
    public List<ReimbursementTicket> getAllTickets(){
        return ticketRepository.getAllTickets();
    }

    // get all issued tickets by an employee
    public List<ReimbursementTicket> getEmployeeTickets(UserAccount user){
        return ticketRepository.getEmployeeTickets(user.getUsername());
    }

}
