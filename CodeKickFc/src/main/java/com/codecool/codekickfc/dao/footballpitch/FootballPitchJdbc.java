package com.codecool.codekickfc.dao.footballpitch;

import com.codecool.codekickfc.controller.dto.NewFootballPitchDTO;
import com.codecool.codekickfc.dao.model.FootballPitch;
import com.codecool.codekickfc.dao.model.database.DatabaseConnection;
import com.codecool.codekickfc.exceptions.DatabaseAccessException;
import org.springframework.beans.factory.annotation.Autowired;
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
    public long postNewFootballPitch(NewFootballPitchDTO footballPitchDTO) {
        String sql = "INSERT INTO football_field (" +
                "field_name," +
                "rental_price," +
                "max_players," +
                "field_postcode," +
                "field_city, field_street," +
                "field_street_number)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING field_id";

        try (Connection conn = databaseConnection.getConnection();
              PreparedStatement statement = conn.prepareStatement(sql)
        ){
            statement.setString(1, footballPitchDTO.name());
            statement.setDouble(2, footballPitchDTO.price());
            statement.setInt(3, footballPitchDTO.maxPlayers());
            statement.setInt(4, footballPitchDTO.postCode());
            statement.setString(5, footballPitchDTO.city());
            statement.setString(6, footballPitchDTO.street());
            statement.setInt(7, footballPitchDTO.street_number());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("field_id");
            }
            return 0;
        } catch (SQLException e) {
            throw new DatabaseAccessException("Encountered error inserting data into the database.", e);
        }
    };
}
