package com.codecool.codekickfc.dao.footballpitch;

import com.codecool.codekickfc.dao.model.FootballPitch;
import com.codecool.codekickfc.dao.model.database.DatabaseConnection;
import com.codecool.codekickfc.exceptions.DatabaseAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FootballPitchJdbc implements FootballPitchDAO {

    private DatabaseConnection databaseConnection;

    @Autowired
    public FootballPitchJdbc(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public List<FootballPitch> getAllFootballPitch() {
        List<FootballPitch> footballPitchList = new ArrayList<>();

        String sql = "SELECT * FROM football_field";

        try (Connection conn = databaseConnection.getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(sql);
        ) {
            while(resultSet.next()) {
                int id = resultSet.getInt("field_id");
                String fieldName = resultSet.getString("field_name");
                double price = resultSet.getDouble("rental_price");
                int maxPlayers = resultSet.getInt("max_players");

                String address = resultSet.getString("field_postcode") + ";" +
                        resultSet.getString("field_city") + ";" +
                        resultSet.getString("field_street") + ";" +
                        resultSet.getString("field_street_number");
                footballPitchList.add(new FootballPitch(id, fieldName, maxPlayers, price, address));
            }

        } catch (SQLException e) {
            throw new DatabaseAccessException("Encountered error fetching data from the database.", e);
        }
        return footballPitchList;
    };
}
