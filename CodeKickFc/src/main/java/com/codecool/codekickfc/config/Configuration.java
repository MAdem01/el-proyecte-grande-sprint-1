package com.codecool.codekickfc.config;

import com.codecool.codekickfc.dao.model.database.DatabaseConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootConfiguration
public class Configuration {

    //TODO: Add your db username, password and url to the environment variables
    @Value("${spring.datasource.url}")
    private String databaseUrl;
    @Value("${spring.datasource.username}")
    private String databaseUsername;
    @Value("${spring.datasource.password}")
    private String databasePassword;

    @Bean
    public DatabaseConnection getDatabaseConnection() {
        return new DatabaseConnection(databaseUrl, databaseUsername, databasePassword);
    }
}
