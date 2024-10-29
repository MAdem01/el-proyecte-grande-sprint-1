package com.codecool.codekickfc.config;

import com.codecool.codekickfc.dao.DatabaseConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class Configuration {

    //TODO: Add your db username, password and url to the environment variables
    @Value("${codekickfc.database.url}")
    private String databaseUrl;
    @Value("${codekickfc.database.username}")
    private String databaseUsername;
    @Value("${codekickfc.database.password}")
    private String databasePassword;

    @Bean
    public DatabaseConnection getDatabaseConnection() {
        return new DatabaseConnection(databaseUrl, databaseUsername, databasePassword);
    }
}
