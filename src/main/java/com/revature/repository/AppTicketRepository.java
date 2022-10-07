package com.revature.repository;

import com.revature.model.AppReimbursementTicket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppTicketRepository {

    // get all tickets from the database (Managers only)
    public List<AppReimbursementTicket> getAllTickets() throws SQLException {
        try(Connection connection = ConnectionFactory.createConnection()){
            List<AppReimbursementTicket> tickets = new ArrayList<>();

            // the SQL for all tickets
            String sql = "SELECT * FROM tickets ORDER BY id ASC;";

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // while rs pointer see a record, transfer that info and add it into the list
            while(rs.next()){
                int id = rs.getInt("id");
                double amount = rs.getDouble("amount");
                String description = rs.getString("description");
                int ticketStatus = rs.getInt("status_name");
                int employeeId = rs.getInt("employee_id");
                int managerId = rs.getInt("manager_id");

                // create new ticket object to store info & add to list
                AppReimbursementTicket reimbursementTicket = new AppReimbursementTicket(id, amount, description, ticketStatus, employeeId, managerId);
                tickets.add(reimbursementTicket);
            }

            return tickets;
        }
    }

    // get all tickets from the database that an Employee made
    public List<AppReimbursementTicket> getAllTicketsForEmployee(int employeeUserId) throws SQLException {
        try(Connection connection = ConnectionFactory.createConnection()){
            List<AppReimbursementTicket> tickets = new ArrayList<>();

            // the SQL for all tickets specifically by employee
            String sql = "SELECT * FROM tickets WHERE employee_id = ? ORDER BY id ASC;";

            // create Prepared Statement object using a predefined template (? are used as placeholder for values)
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, employeeUserId);

            // execute Statement to execute the SQL statement, use pstmt.toString() to get the completed SQL cmd
            ResultSet rs = pstmt.executeQuery();

            // while rs pointer see a record, transfer that info and add it into the list
            while(rs.next()){
                int id = rs.getInt("id");
                double amount = rs.getDouble("amount");
                String description = rs.getString("description");
                int ticketStatus = rs.getInt("status_name");
                int employeeId = rs.getInt("employee_id");
                int managerId = rs.getInt("manager_id");

                // create new ticket object to store info & add to list
                AppReimbursementTicket reimbursementTicket = new AppReimbursementTicket(id, amount, description, ticketStatus, employeeId, managerId);
                tickets.add(reimbursementTicket);
            }

            return tickets;
        }
    }

    // get all PENDING tickets from the database (Managers only)
    public List<AppReimbursementTicket> getAllTicketsWithStatus(int ticketStatus) throws SQLException {
        try(Connection connection = ConnectionFactory.createConnection()){
            List<AppReimbursementTicket> tickets = new ArrayList<>();

            // the SQL for all tickets
            String sql = "SELECT * FROM tickets WHERE status_name = ? ORDER BY id ASC;";

            // create Prepared Statement object using a predefined template (? are used as placeholder for values)
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, ticketStatus);

            // execute Statement to execute the SQL statement, use pstmt.toString() to get the completed SQL cmd
            ResultSet rs = pstmt.executeQuery();

            // while rs pointer see a record, transfer that info and add it into the list
            while(rs.next()){
                int id = rs.getInt("id");
                double amount = rs.getDouble("amount");
                String description = rs.getString("description");
                int ticketStatusId = rs.getInt("status_name");
                int employeeId = rs.getInt("employee_id");
                int managerId = rs.getInt("manager_id");

                // create new ticket object to store info & add to list
                AppReimbursementTicket reimbursementTicket = new AppReimbursementTicket(id, amount, description, ticketStatusId, employeeId, managerId);
                tickets.add(reimbursementTicket);
            }

            return tickets;
        }
    }

    // get all PENDING tickets from the database that an Employee made
    public List<AppReimbursementTicket> getAllTicketsWithStatusForEmployee(int employeeUserId, int ticketStatus) throws SQLException {
        try(Connection connection = ConnectionFactory.createConnection()){
            List<AppReimbursementTicket> tickets = new ArrayList<>();

            // the SQL for all tickets specifically by employee
            String sql = "SELECT * FROM tickets WHERE employee_id = ? AND status_name = ? ORDER BY id ASC;";

            // create Prepared Statement object using a predefined template (? are used as placeholder for values)
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, employeeUserId);
            pstmt.setInt(2, ticketStatus);

            // execute Statement to execute the SQL statement, use pstmt.toString() to get the completed SQL cmd
            ResultSet rs = pstmt.executeQuery();

            // while rs pointer see a record, transfer that info and add it into the list
            while(rs.next()){
                int id = rs.getInt("id");
                double amount = rs.getDouble("amount");
                String description = rs.getString("description");
                int ticketStatusId = rs.getInt("status_name");
                int employeeId = rs.getInt("employee_id");
                int managerId = rs.getInt("manager_id");

                // create new ticket object to store info & add to list
                AppReimbursementTicket reimbursementTicket = new AppReimbursementTicket(id, amount, description, ticketStatusId, employeeId, managerId);
                tickets.add(reimbursementTicket);
            }

            return tickets;
        }
    }

    // Reimbursement Ticket Submission (CREATE from CRUD)
    public void submitReimbursementTicket(AppReimbursementTicket ticketSubmission) throws SQLException{
        // try-with-resources
        // is used to auto close the Connection object when the block is finished
        try (Connection connectionObject = ConnectionFactory.createConnection()){
            // SQL command to create a new record in the users table in the database
            String sql = "INSERT INTO tickets (amount, description,status_name, employee_id) values (?, ?, ?, ?);";

            // create Prepared Statement object using a predefined template (? are used as placeholder for values)
            PreparedStatement pstmt = connectionObject.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setDouble(1, ticketSubmission.getAmount()); // the inquiry ticket amount
            pstmt.setString(2, ticketSubmission.getDescription()); // the inquiry ticket description
            pstmt.setInt(3, 1); // the PENDING status by default
            pstmt.setInt(4, ticketSubmission.getEmployeeId()); // the employee whose submitting the ticket

            // we should have just made 1 new record in database
            int numberOfRecordsAdded = pstmt.executeUpdate(); // applies to INSERT, UPDATE, and DELETE
        }
    }

    // check if reimbursement ticket exists
    public boolean checkReimbursementTicketExist(AppReimbursementTicket processTicket) throws SQLException{
        // try-with-resources
        // is used to auto close the Connection object when the block is finished
        try (Connection connectionObject = ConnectionFactory.createConnection()) {
            // SQL command to create a new record in the users table in the database
            String sql = "SELECT * FROM tickets as t WHERE t.id = ?;";

            // create Prepared Statement object using a predefined template (? are used as placeholder for values)
            PreparedStatement pstmt = connectionObject.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, processTicket.getId()); // the maybe new account username

            ResultSet rs = pstmt.executeQuery(); // represents a temporary table that contains all data we have queried for

            //rs.next(); - return a boolean indicating whether there is a record or not for the next row AND iterates to it
            if (rs.next()) {
                return true;
            }

            return false;

        }
    }

    // check if reimbursement ticket exists
    public boolean checkReimbursementTicketAlreadyProcessed(AppReimbursementTicket processTicket) throws SQLException{
        // try-with-resources
        // is used to auto close the Connection object when the block is finished
        try (Connection connectionObject = ConnectionFactory.createConnection()) {
            // SQL command to create a new record in the users table in the database
            String sql = "SELECT * FROM tickets as t WHERE t.id = ?;";

            // create Prepared Statement object using a predefined template (? are used as placeholder for values)
            PreparedStatement pstmt = connectionObject.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, processTicket.getId()); // the maybe new account username

            ResultSet rs = pstmt.executeQuery(); // represents a temporary table that contains all data we have queried for

            //rs.next(); - return a boolean indicating whether there is a record or not for the next row AND iterates to it
            while(rs.next()){
                int id = rs.getInt("id");
                int ticketStatusId = rs.getInt("status_name");
                int managerId = rs.getInt("manager_id");

                // if ticket does not have the status of 1 (PENDING)
                if(id == processTicket.getId() && ticketStatusId != 1 && managerId != 0){
                    return true;    // it's been processed
                }
            }

            // if the while loop end without issue
            return false; // ticket is not processed
        }
    }


    // Reimbursement Ticket Submission (CREATE from CRUD)
    public void processReimbursementTicket(AppReimbursementTicket processTicket) throws SQLException{
        // try-with-resources
        // is used to auto close the Connection object when the block is finished
        try (Connection connectionObject = ConnectionFactory.createConnection()){
            // SQL command to create a new record in the users table in the database
            String sql = "UPDATE tickets SET status_name = ?, manager_id = ? WHERE id = ?;";

            // create Prepared Statement object using a predefined template (? are used as placeholder for values)
            PreparedStatement pstmt = connectionObject.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, processTicket.getTicketStatus()); // the new status of ticket being processed
            pstmt.setInt(2, processTicket.getManagerId()); // the manager who did the process
            pstmt.setInt(3, processTicket.getId()); // the id of the ticket to process

            // we should have just made 1 new record in database
            int numberOfRecordsAdded = pstmt.executeUpdate(); // applies to INSERT, UPDATE, and DELETE

        }
    }

}
