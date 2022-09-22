package src.model;

public class ReimbursementTicket {

    // maybe use User instead?
    public String issuedBy; // who issued the ticket

    public double ticketAmount; // what is the amount for reimbursement
    public String ticketDescription; // description about the reimbursement

    // what is the current status of the ticket
    // i.e. Pending when made by Employee, Denied/Approved after a Manager finalizes decision
    public String ticketStatus;

    // constructor for Reimbursement Ticket
    // used for issuing a new reimbursement ticket
    public ReimbursementTicket(String issuedBy, Double ticketAmount, String ticketDescription){
        // set initialized Ticket object variable values
        this.issuedBy = issuedBy;
        this.ticketAmount = ticketAmount;
        this.ticketDescription = ticketDescription;
        this.ticketStatus = "Pending";  // default status when created
    }


}
