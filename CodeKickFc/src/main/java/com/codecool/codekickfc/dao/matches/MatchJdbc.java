package com.codecool.codekickfc.dao.matches;

import com.codecool.codekickfc.dao.model.database.DatabaseConnection;
import com.codecool.codekickfc.dao.model.matches.Match;
import com.codecool.codekickfc.exceptions.DatabaseAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MatchJdbc implements MatchDAO{

    private DatabaseConnection databaseConnection;

    @Autowired
    public MatchJdbc(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public List<Match> getAllMatches() {
        List<Match> matches = new ArrayList<>();

        String sql = "SELECT * FROM matches";

        try (Connection connection = databaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("match_id");
                Array subscribed_players = resultSet.getArray("subscribed_players_id");
                double match_fee = resultSet.getDouble("match_fee_per_players");
                int field_id = resultSet.getInt("field_id");
                Timestamp match_date = resultSet.getTimestamp("match_date");

                matches.add(new Match(id, subscribed_players, match_fee, field_id, match_date.toLocalDateTime()));
            }

        } catch (SQLException e) {
        throw new DatabaseAccessException("Encountered error inserting data into the database.", e);
        }
        return matches;
    }
}
