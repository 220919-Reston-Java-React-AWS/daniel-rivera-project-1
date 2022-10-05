package com.revature.service;

import com.revature.exception.*;
import com.revature.model.AppReimbursementTicket;
import com.revature.model.AppUserAccount;
import com.revature.repository.AppTicketRepository;

import java.sql.SQLException;
import java.util.List;

public class AppTicketService {
    private AppTicketRepository ticketRepository = new AppTicketRepository();

    // get all the tickets for Managers
    public List<AppReimbursementTicket> getAllTickets() throws SQLException{
        return ticketRepository.getAllTickets();
    }

    // get all the tickets that Employee made
    public List<AppReimbursementTicket> getAllTicketsForEmployee(int employeeId) throws SQLException{
         return ticketRepository.getAllTicketsForEmployee(employeeId);
    }

    // get all the PENDING tickets for Managers
    public List<AppReimbursementTicket> getAllTicketsWithStatus(int ticketStatus) throws SQLException{
        return ticketRepository.getAllTicketsWithStatus(ticketStatus);
    }

    // get all the PENDING tickets that Employee made
    public List<AppReimbursementTicket> getAllTicketsWithStatusForEmployee(int employeeId, int ticketStatus) throws SQLException{
        return ticketRepository.getAllTicketsWithStatusForEmployee(employeeId, ticketStatus);
    }

    // Business Logic - registering a new reimbursement ticket
    // input validation
    public void submitReimbursementTicket(AppReimbursementTicket ticketInquiry) throws SQLException, InvalidReimbursementTicketSubmissionException {
        // let's block if no reasonable amount is given
        if(ticketInquiry.getAmount() <= 0 ){
            throw new InvalidReimbursementTicketSubmissionException("A reimbursement ticket must have a value greater then 0.00");
        }
        // let's block if no description was made
        if(ticketInquiry.getDescription().equals("") | ticketInquiry.getDescription().equals(null)){
            throw new InvalidReimbursementTicketSubmissionException("A reimbursement ticket must have a description as for why");
        }
        // let's block if no employee id was given
        if(ticketInquiry.getEmployeeId() <= 0){
            throw new InvalidReimbursementTicketSubmissionException("A reimbursement ticket must have the employee's id number");
        }

        // try to create the new account
        ticketRepository.submitReimbursementTicket(ticketInquiry);
    }


    // Business Logic - updating/process a reimbursement ticket (MANAGERS ONLY)
    // input validation
    public void processReimbursementTicket(AppReimbursementTicket processTicket)
            throws SQLException, ReimbursementTicketNotFoundException, ProcessedReimbursementTicketException, InvalidReimbursementTicketStatusUpdateException {
        // Check if ticket exist
        if(!ticketRepository.checkReimbursementTicketExist(processTicket)){
            throw new ReimbursementTicketNotFoundException("The reimbursement ticket with id " + processTicket.getId() + " does not exist.");
        }

        // Check that ticket is already processed
        if(ticketRepository.checkReimbursementTicketAlreadyProcessed(processTicket)){
            throw new ProcessedReimbursementTicketException("The reimbursement ticket with id " + processTicket.getId() + " is already processed.");
        }

        System.out.println(processTicket.getTicketStatus());

        // Check if new ticket status is valid (Approved 2, Denied 3)
        if(processTicket.getTicketStatus() < 2 && processTicket.getTicketStatus() > 3){
            throw new InvalidReimbursementTicketStatusUpdateException("The reimbursement ticket update of its status is not 2 (APPROVED) or 3 (DENIED).");
        }

        // let's block if no manager id was given
        if(processTicket.getManagerId() <= 0){
            throw new InvalidReimbursementTicketStatusUpdateException("A reimbursement ticket must have the manager's id number");
        }

        // try to create the new account
        ticketRepository.processReimbursementTicket(processTicket);
    }

}
