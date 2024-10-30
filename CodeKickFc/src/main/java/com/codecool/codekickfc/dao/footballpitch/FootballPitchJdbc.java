package com.codecool.codekickfc.dao.footballpitch;

import com.codecool.codekickfc.controller.dto.pitches.FootballPitchDTO;
import com.codecool.codekickfc.controller.dto.pitches.NewFootballPitchDTO;
import com.codecool.codekickfc.controller.dto.pitches.UpdateFootballPitchDTO;
import com.codecool.codekickfc.dao.model.pitches.FootballPitch;
import com.codecool.codekickfc.dao.model.database.DatabaseConnection;
import com.codecool.codekickfc.exceptions.DatabaseAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.SQL;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FootballPitchJdbc implements FootballPitchDAO {

    private DatabaseConnection databaseConnection;

    @Autowired
    public FootballPitchJdbc(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    /**
     * Retrieves all football pitch entries from the database and converts each entry into a {@link FootballPitch} record.
     * This method queries the {@code football_field} table and constructs a list of {@link FootballPitch} objects
     * for the service layer to handle.
     *
     * @return a list of {@link FootballPitch} records, each representing an entry from the {@code football_field} table.
     *
     * @throws DatabaseAccessException if an error occurs while accessing the database, preventing data retrieval.
     */
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
                int postCode = resultSet.getInt("field_postcode");
                String city = resultSet.getString("field_city");
                String street = resultSet.getString("field_street");
                int streetNumber = resultSet.getInt("field_street_number");

                footballPitchList.add(new FootballPitch(id, fieldName, maxPlayers, price, postCode, city, street, streetNumber));
            }

        } catch (SQLException e) {
            throw new DatabaseAccessException("Encountered error fetching data from the database.", e);
        }
        return footballPitchList;
    };

    @Override
    public FootballPitch getFootballPitchById(Long fieldId) {
        String sql = "SELECT * FROM football_field WHERE field_id = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);) {

            statement.setLong(1, fieldId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("field_id");
                    String fieldName = resultSet.getString("field_name");
                    double price = resultSet.getDouble("rental_price");
                    int maxPlayers = resultSet.getInt("max_players");
                    int postCode = resultSet.getInt("field_postcode");
                    String city = resultSet.getString("field_city");
                    String street = resultSet.getString("field_street");
                    int streetNumber = resultSet.getInt("field_street_number");

                    return new FootballPitch(id, fieldName, maxPlayers, price, postCode, city, street, streetNumber);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DatabaseAccessException("Encountered error fetching data from the database.", e);}
    }

    @Override
    public long postNewFootballPitch(NewFootballPitchDTO footballPitchDTO) {
        String sql = "INSERT INTO football_field (" +
                "field_name, rental_price," +
                "max_players," +
                "field_postcode," +
                "field_city," +
                "field_street," +
                "field_street_number) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING field_id";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            setFootballPitchParameters(statement, footballPitchDTO); // Using the private method

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("field_id");
            }
            return 0;
        } catch (SQLException e) {
            throw new DatabaseAccessException("Encountered error inserting data into the database.", e);
        }
    }

    @Override
    public long updateFootballPitch(Long fieldId, NewFootballPitchDTO footballPitchDTO) {
        String sql = "UPDATE football_field SET " +
                "field_name = ?," +
                "rental_price = ?," +
                "max_players = ?," +
                "field_postcode = ?, " +
                "field_city = ?," +
                "field_street = ?," +
                "field_street_number = ? " +
                "WHERE field_id = ? RETURNING field_id";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            setFootballPitchParameters(statement, footballPitchDTO); // Reusing the private method
            statement.setLong(8, fieldId); // Set the fieldId as the last parameter

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("field_id");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new DatabaseAccessException("Encountered error updating data in the database.", e);
        }
    }

    @Override
    public boolean deleteFootballPitch(long id) {
        String sql = "DELETE FROM football_field WHERE field_id = ?";

        try(Connection conn = databaseConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setLong(1, id);
            int resultSet = statement.executeUpdate();
            return resultSet > 0;

        } catch (SQLException e) {
            throw new DatabaseAccessException("Encountered error deleting data from the database.", e);
        }
    }

    private void setFootballPitchParameters(PreparedStatement statement, NewFootballPitchDTO footballPitchDTO) throws SQLException {
        statement.setString(1, footballPitchDTO.name());
        statement.setDouble(2, footballPitchDTO.price());
        statement.setInt(3, footballPitchDTO.maxPlayers());
        statement.setInt(4, footballPitchDTO.postCode());
        statement.setString(5, footballPitchDTO.city());
        statement.setString(6, footballPitchDTO.street());
        statement.setInt(7, footballPitchDTO.street_number());
    }
}
