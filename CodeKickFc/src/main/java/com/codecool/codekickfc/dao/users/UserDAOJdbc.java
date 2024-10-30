package com.codecool.codekickfc.dao.users;

import com.codecool.codekickfc.controller.users.NewUserDTO;
import com.codecool.codekickfc.dao.model.database.DatabaseConnection;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOJdbc implements UserDAO {
    private final DatabaseConnection databaseConnection;

    public UserDAOJdbc(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }


    /**
     * This method returns all existing users from the database.
     * <br></br>
     * <b>Detailed explanation:</b>
     * <br></br>
     * It provides a SELECT SQL query, then establish the connection with the database and execute
     * the query. Next, it creates User objects from the results and add them to an ArrayList while
     * the ResultSet has
     * next row.
     *
     * @return A list of User object
     * @throws RuntimeException In case connection fails
     */
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM \"user\"";
        try (Connection connection = databaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql);) {
            while (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                String username = resultSet.getString("username");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("user_email");
                String password = resultSet.getString("user_password");
                Array matchId = resultSet.getArray("match_id");
                User user = new User(id, username, firstName, lastName, password, email, matchId);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }


    /**
     * This method creates a new User and save it to the database.
     * <br></br>
     * <b>Detailed explanation:</b>
     * <br></br>
     * It provides an INSERT SQL query, then establish the connection with the database,
     * generates an ID, sets the SQL parameters and execute it. Next, it creates a User object
     * from the generated ID and the provided newUser data.
     *
     * @param newUser Request body coming from the client.
     * @return Newly created User object.
     * @throws RuntimeException In case connection fails or duplication detected
     */
    @Override
    public User createUser(NewUserDTO newUser) {
        String sql = "INSERT INTO \"user\"(username, first_name," +
                "last_name, user_password, user_email) VALUES (?,?,?,?,?)";
        try (Connection connection = databaseConnection.getConnection();) {
            PreparedStatement statement = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, newUser.username());
            statement.setString(2, newUser.firstName());
            statement.setString(3, newUser.lastName());
            statement.setString(4, newUser.password());
            statement.setString(5, newUser.email());

            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getInt(1),
                            newUser.username(),
                            newUser.firstName(),
                            newUser.lastName(),
                            newUser.password(),
                            newUser.email(),
                            null
                    );
                } else {
                    throw new SQLException();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
