package com.revature.repository;

import com.revature.model.AppUserAccount;

import java.sql.*;

public class AppUsersRepository {

    // Account Registration (CREATE from CRUD)
    public void createUserAccount(AppUserAccount user) throws SQLException {
        // try-with-resources
        // is used to auto close the Connection object when the block is finished
        try (Connection connectionObject = ConnectionFactory.createConnection()){
            // SQL command to create a new record in the users table in the database
            String sql = "INSERT INTO users (username, password,first_name, last_name, role_id) values (?, ?, ?, ? ,?);";

            // create Prepared Statement object using a predefined template (? are used as placeholder for values)
            PreparedStatement pstmt = connectionObject.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, user.getUsername()); // the register account username
            pstmt.setString(2, user.getPassword()); // the register account password
            pstmt.setString(3, user.getFirstName()); // the register account first name
            pstmt.setString(4, user.getLastName()); // the register account last name
            pstmt.setInt(5, 1); // 1 means Employee; Hardcoded as the default role when creating new user

            // we should have just made 1 new record in database
            int numberOfRecordsAdded = pstmt.executeUpdate(); // applies to INSERT, UPDATE, and DELETE

        }
    }

    // Account Login (READ from CRUD)
    public AppUserAccount getUserByUsernameAndPassword(String username, String password) throws SQLException{
        // try-with-resources
        // is used to auto close the Connection object when the block is finished
        try (Connection connectionObject = ConnectionFactory.createConnection()){
            // SQL command to create a new record in the users table in the database
            String sql = "SELECT * FROM users as u WHERE u.username = ? and u.password = ?;";

            // create Prepared Statement object using a predefined template (? are used as placeholder for values)
            PreparedStatement pstmt = connectionObject.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, username); // the login account username
            pstmt.setString(2, password); // the login account password

//            System.out.println(pstmt.toString());

            ResultSet rs = pstmt.executeQuery(); // represents a temporary table that contains all data we have queried for

            //rs.next(); - return a boolean indicating whether there is a record or not for the next row AND iterates to it
            if  (rs.next()){
                int id = rs.getInt("id");
                String un = rs.getString("username");
                String pw = rs.getString("password");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int roleId = rs.getInt("role_id");

                return new AppUserAccount(id, un, pw, firstName, lastName, roleId);
            }
            else {
                return null;
            }
        }
    }

    // Check if username already exists in Users Database
    public boolean checkUsernameExists(String username) throws SQLException {
        // try-with-resources
        // is used to auto close the Connection object when the block is finished
        try (Connection connectionObject = ConnectionFactory.createConnection()) {
            // SQL command to create a new record in the users table in the database
            String sql = "SELECT * FROM users as u WHERE u.username = ?;";

            // create Prepared Statement object using a predefined template (? are used as placeholder for values)
            PreparedStatement pstmt = connectionObject.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, username); // the maybe new account username

//            System.out.println(pstmt.toString());

            ResultSet rs = pstmt.executeQuery(); // represents a temporary table that contains all data we have queried for

            //rs.next(); - return a boolean indicating whether there is a record or not for the next row AND iterates to it
            if (rs.next()) {
                return true;
            }
            else {
                return false;
            }

        }
    }


}
