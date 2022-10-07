Repo_Name: daniel-rivera-project-1

Project: Revature Training Project 1

## Employee Reimbursement System 

### Minimum Viable Product (MVP) must be complete by October 10, 2022

Due to project time period changes, you should complete the required features to present on Oct 10th 
for the back-end application.

Use Postman, DBeaver, and your Java back-end server application to show the following requirements

Features to be implemented
----------------
Some extra implementations specifically made in this version of the application are marked with NOTE. Otherwise,
it's a required feature to be implemented to meet the MVP requirements.


- [X] Can register a new account with username and password
	
  - A newly created user account should have its role as **Employee on default** 

- [X] Can use a username and password to log in

  - **NOTE**: In this implementation of the application, we have a "no spaces in username or password" restriction. Not a part of original requirements.

- [X] Will notify the user if the username is unavailable

  - When a new user account is registered/created, you should notify that the username has been taken if it exists in the database

- [X] Can submit new reimbursement tickets

  - A newly submitted/created reimbursement ticket should have its **ticket status** to be **PENDING on default**
  
- [X] Will make sure the reimbursement ticket author provides a description and amount during submission

  - Check to see that an **Employee** has provided a valid ***amount*** and a ***description*** for the reimbursement ticket

- [X] Pending tickets are in a queue/list that can be seen by Managers

  - TO reiterate, the **Manager** users should see a list of just **PENDING** tickets
  - **NOTE**: In this implementation of the project, we also gave **Managers** users to see a list of **ALL** tickets in the database

- [X] Tickets can be processed (approved or denied) by Managers

  - Be sure to implement that for any tickets that have been processed **CAN NOT BE CHANGED AGAIN** (It should be final)

- [X] Employees can see a list of their previous submissions

	- **NOTE**: In this implementation of the project, we also gave **Employee** users to see a list of **ALL or PENDING** tickets
