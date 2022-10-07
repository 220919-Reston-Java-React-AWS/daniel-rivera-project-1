package com.revature.controller;

import com.revature.exception.InvalidAccountLoginException;
import com.revature.model.AppUserAccount;
import com.revature.service.AppAuthService;
import io.javalin.Javalin;

import javax.servlet.http.HttpSession;

public class AuthController {
    // the service layer
    private AppAuthService appAuthService = new AppAuthService();

    // AuthController - handles the login, logout, and register/create HTTP requests
    public void mapEndpoints(Javalin app){
        // register HTTP request - to create a new user account
        app.post("/register", (ctx) ->{
            // take the JSON in the request body and place it into a UserAccount object
            AppUserAccount registration = ctx.bodyAsClass(AppUserAccount.class);

            // Try creating the new Account
            try{
                appAuthService.register(registration);

                ctx.result("Account successfully created.");
                ctx.status(201);    // 2xx success - 201 Created
            } catch (Exception e){
                ctx.result(e.getMessage()); // print exception message
                ctx.status(500); // 5xx server errors - 500 Internal Server Error
            }
        });

        // login HTTP request - to enter an user account
        app.post("/login", (ctx) ->{
            // take the JSON in the request body and place it into the user Object
            AppUserAccount credentials = ctx.bodyStreamAsClass(AppUserAccount.class);

            // System.out.println(credentials);

            try{
                AppUserAccount user = appAuthService.login(credentials.getUsername(), credentials.getPassword());

                // set the user object into an HTTPSession object
                HttpSession session = ctx.req.getSession(); // get the HTTPSession (there is a cookie utilized by the client)
                // to identify the httpSession object associated with the client
                session.setAttribute("user", user);

                ctx.result("Welcome " + user.getFirstName() + " " + user.getLastName());
                ctx.status(200);
            }
            catch (InvalidAccountLoginException e){
                ctx.status(400);
                ctx.result(e.getMessage());
            }
        });

        // logout HTTP request - to exit logged-in user account
        app.post("/logout", (ctx) ->{
            // invalidate an active HTTPSession
            ctx.req.getSession().invalidate();
            ctx.result("Logged out account.");
            ctx.status(200);
        });
    }

}
