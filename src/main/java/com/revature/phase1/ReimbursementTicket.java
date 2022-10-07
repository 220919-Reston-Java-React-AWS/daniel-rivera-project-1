package com.revature.model;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ReimbursementTicket {
    // required ticket information
    private String issuedBy = null; // who issued the ticket
    private double ticketAmount = 0; // what is the amount for reimbursement
    private String ticketDescription = null; // description about the reimbursement
    private String ticketStatus = null; // what is the current status of the ticket
    // i.e. Pending when made by Employee, Denied/Approved after a Manager finalizes decision

    // possible future extra information to added
//     private ZonedDateTime ticketCreatedAt;   // when was the ticket created (w/ date & time)


    // constructor for Reimbursement Ticket
    // used for issuing a new reimbursement ticket
    public ReimbursementTicket(String issuedBy, Double ticketAmount, String ticketDescription){
        // set initialized Ticket object variable values
        this.issuedBy = issuedBy;
        this.ticketAmount = ticketAmount;
        this.ticketDescription = ticketDescription;
        this.ticketStatus = "Pending";  // the default status when created
//        this.ticketCreatedAt = ZonedDateTime.now(ZoneId.of("UTC/Greenwich")); // current UTC day & time
    }

    // getter and setter methods
    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public double getTicketAmount() { return ticketAmount; }

    public String getTicketDescription() { return ticketDescription; }

    // override toString to print out information of ticket
    @Override
    public String toString(){
        return "Issued by: " + this.issuedBy + " | Ticket Status: " + this.ticketStatus  + "\n" + "-".repeat(40)
                        + "\nAmount: " + this.ticketAmount + "\nDescription: " + this.ticketDescription;
    }
}