package com.revature;

import com.revature.controller.AuthController;
import com.revature.controller.ReimbursementTicketController;
import io.javalin.Javalin;

public class MainApp {
    public static void main(String[] args) {
        Javalin app = Javalin.create();

        // Instantiate the controllers for HTTP requests from client
        AuthController authController = new AuthController();   // handles login, register, logout
        authController.mapEndpoints(app);

        ReimbursementTicketController ticketController = new ReimbursementTicketController(); // anything with database
        ticketController.mapEndpoints(app);

        // start java-based server
        app.start(8080);
    }
}
