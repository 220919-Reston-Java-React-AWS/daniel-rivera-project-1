package com.revature.repository;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection createConnection() throws SQLException{
        // register the postgres driver with the DriverManager class from JDBC
        Driver postgresDriver = new Driver();
        DriverManager.registerDriver(postgresDriver);

        // define the data location (URI) and postgres database credentials
        // (get username & password for postgres from system environment variables)
        String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
        String username = System.getenv("PostgresUsername");
        String password = System.getenv("PostgresPassword");

        // create the connection to the database
        Connection connectionObject = DriverManager.getConnection(url, username, password);
        return connectionObject;
    }
}
