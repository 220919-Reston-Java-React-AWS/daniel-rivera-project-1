package com.revature.controller;

import com.revature.model.AppReimbursementTicket;
import com.revature.model.AppUserAccount;
import com.revature.service.AppTicketService;
import io.javalin.Javalin;

import javax.servlet.http.HttpSession;
import java.util.List;

public class ReimbursementTicketController {
    // service layer
    private AppTicketService appTicketService = new AppTicketService();

    // this controller should handle actions a user may make
    // see a list of tickets (all & pending), create a new ticket, process a ticket, and
    public void mapEndpoints(Javalin app){
        // tickets HTTP request - to create a new user account
        // for both Employee and Manager users
        app.get("/tickets", (ctx) ->{
            // get the login user
            HttpSession httpSession = ctx.req.getSession();

            //return user attribute
            AppUserAccount user = (AppUserAccount) httpSession.getAttribute("user");

            // check if logged in
            if(user != null) {
                // if the user is a Manager
                if (user.getRoleId() == 2) {
                    List<AppReimbursementTicket> ticketList = appTicketService.getAllTickets();

                    // return info to client
                    ctx.json(ticketList);
                    ctx.status(200);
                }
                // if the user is an Employee
                else if (user.getRoleId() == 1){
                    int employeeId = user.getId();

                    List<AppReimbursementTicket> ticketList = appTicketService.getAllTicketsForEmployee(employeeId);

                    // return info to client
                    ctx.json(ticketList);
                    ctx.status(200);
                }
                else {
                    ctx.result("You are logged in, but not a Employee or Manager");
                    ctx.status(401);
                }
            }
            // else return "error" status
            else {
                ctx.result("You are not logged in");
                ctx.status(401);
            }
        });

        // pending HTTP request - to enter a user account
        // for Manager users
        app.get("tickets/list-status={ticketStatus}", (ctx) ->{
            // get the login user
            HttpSession httpSession = ctx.req.getSession();

            //return user attribute
            AppUserAccount user = (AppUserAccount) httpSession.getAttribute("user");

            // get the ticket status to list from the URI
            int uriTicketStatus;
            switch (ctx.pathParam("ticketStatus")){
                case "pending":
                    uriTicketStatus = 1;
                    break;
                case "approved":
                    uriTicketStatus = 2;
                    break;
                case "denied":
                    uriTicketStatus = 3;
                    break;
                default:
                    uriTicketStatus = 0;
            }

            // check if logged in
            if(user != null) {
                // check that a valid ticket status is in the uri
                if(uriTicketStatus == 0){
                    ctx.result("You are logged in, but the URI is incorrect. Status should equal: pending, approved, or denied");
                    ctx.status(401);
                }
                else {
                    // if the user is a Manager
                    if (user.getRoleId() == 2) {
                        // 1 is PENDING, 2 is APPROVED. 3 is DENIED
                        List<AppReimbursementTicket> assignmentList = appTicketService.getAllTicketsWithStatus(uriTicketStatus);
                        ctx.json(assignmentList);

                    }
                    // if the user is a Employee
                    else if (user.getRoleId() == 1) {
                        int employeeId = user.getId();

                        // 1 is PENDING, 2 is APPROVED. 3 is DENIED
                        List<AppReimbursementTicket> assignments = appTicketService.getAllTicketsWithStatusForEmployee(employeeId, uriTicketStatus);
                        ctx.json(assignments);
                    } else {
                        ctx.result("You are logged in, but not a Employee or Manager");
                        ctx.status(401);
                    }
                }
            }
            // else return "error" status
            else {
                ctx.result("You are not logged in");
                ctx.status(401);
            }
        });

        // submit HTTP request - to exit logged-in user account
        // for Employee users
        app.post("tickets/submit", (ctx) ->{
            // get the login user
            HttpSession httpSession = ctx.req.getSession();

            //return user attribute from cookie
            AppUserAccount user = (AppUserAccount) httpSession.getAttribute("user");

            // check if logged in
            if(user != null) {
                // if the user is an Employee
                if (user.getRoleId() == 1){
                    // employee user is logged-in, so get their id
                    int employeeId =  user.getId();
                    // take the JSON in the request body and place it into a UserAccount object
                    AppReimbursementTicket inquiry = ctx.bodyAsClass(AppReimbursementTicket.class);
                    // add the employee's id to the info
                    inquiry.setEmployeeId(employeeId);

                    // Try creating the new ticket
                    try{
                        // invoke the service layer for submitting a ticket
                        appTicketService.submitReimbursementTicket(inquiry);

                        ctx.result("The new reimbursement ticket is made.");
                        ctx.status(201);    // 2xx success - 201 Created
                    } catch (Exception e){
                        ctx.result(e.getMessage()); // print exception message
                        ctx.status(500); // 5xx server errors - 500 Internal Server Error
                    }
                }
                // A manager or something else
                else {
                    ctx.result("You are logged in, but not a Employee");
                    ctx.status(401);
                }
            }
            // else return "error" status
            else {
                ctx.result("You are not logged in");
                ctx.status(401);
            }
        });

        // process HTTP request - to create a new user account
        // for Manager users
        app.patch("tickets/process", (ctx) ->{
            // get the login user
            HttpSession httpSession = ctx.req.getSession();

            //return user attribute from cookie
            AppUserAccount user = (AppUserAccount) httpSession.getAttribute("user");

            // check if logged in
            if(user != null) {
                // if the user is a Manager
                if (user.getRoleId() == 2){
                    // employee user is logged-in, so get their id
                    int managerId =  user.getId();
                    // take the JSON in the request body and place it into a UserAccount object
                    AppReimbursementTicket processTicket = ctx.bodyAsClass(AppReimbursementTicket.class);
                    // add the employee's id to the info
                    processTicket.setManagerId(managerId);


                    // Try creating the new ticket
                    try{
                        // invoke the service layer for processing a ticket (APPROVED 2, DENIED 3)
                        appTicketService.processReimbursementTicket(processTicket);

                        ctx.result("The reimbursement ticket is processed.");
                        ctx.status(200);    // 2xx success - 200 OK
                    } catch (Exception e){
                        ctx.result(e.getMessage()); // print exception message
                        ctx.status(500); // 5xx server errors - 500 Internal Server Error
                    }
                }
                // A employee or something else
                else {
                    ctx.result("You are logged in, but not a Manager");
                    ctx.status(401);
                }
            }
            // else return "error" status
            else {
                ctx.result("You are not logged in");
                ctx.status(401);
            }

        });


    }
}
