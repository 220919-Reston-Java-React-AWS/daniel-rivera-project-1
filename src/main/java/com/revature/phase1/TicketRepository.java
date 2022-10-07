package com.revature.repository;

import com.revature.model.ReimbursementTicket;

import java.util.*;

public class TicketRepository {
    // temporary method to store information of tickets made
    // by using HashMap; will clear out when app restarts/end
    private Map<Integer, ReimbursementTicket> tickets = new HashMap<>();

    public void addTicket(ReimbursementTicket ticket){
        // let's give a new ticket a ticket number for the key
        int ticketNumber = tickets.size() + 1;
        tickets.put(ticketNumber, ticket);
    }

    // the Manager has look at all tickets issued
    public List<ReimbursementTicket> getAllTickets(){
        List<ReimbursementTicket> results = new ArrayList<>(); // List to hold results
        Set<Integer> keys = tickets.keySet();   // get all unique keys

        for (Integer k: keys) { // loop to add ReimbursementTicket objects
            results.add(tickets.get(k));
        }

        return results;
    }

    // an Employee can look at all tickets they have issued
    public List<ReimbursementTicket> getEmployeeTickets(String username){
        List<ReimbursementTicket> results = new ArrayList<>(); // List to hold results
        Set<Integer> keys = tickets.keySet(); // get all unique keys

        for (Integer k: keys) { // loop to add ReimbursementTicket objects
            // check if issuedBy in ticket the same as UserAccount's username
            if (tickets.get(k).getIssuedBy().equals(username))
                results.add(tickets.get(k)); // add if yes
        }

        return results;
    }



}
