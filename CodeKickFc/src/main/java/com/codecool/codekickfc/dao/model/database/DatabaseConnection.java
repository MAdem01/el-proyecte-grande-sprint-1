package com.codecool.codekickfc.dao.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private final String databaseUrl;
    private final String databaseUsername;
    private final String databasePassword;

    public DatabaseConnection(String databaseUrl, String databaseUsername, String databasePassword) {
        this.databaseUrl = databaseUrl;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
    }

    public Connection getConnection() throws SQLException {
        Properties props = new Properties();
        props.put("user", databaseUsername);
        props.put("password", databasePassword);
        return DriverManager.getConnection(databaseUrl, props);
    }
}

