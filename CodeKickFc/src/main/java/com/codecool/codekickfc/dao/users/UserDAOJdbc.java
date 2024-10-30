package com.codecool.codekickfc.dao.users;

import com.codecool.codekickfc.controller.dto.users.NewUserDTO;
import com.codecool.codekickfc.dao.model.database.DatabaseConnection;
import com.codecool.codekickfc.controller.dto.users.UpdateUserDTO;
import com.codecool.codekickfc.dao.model.users.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
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
                User user = new User(id, username, firstName, lastName, password, email);
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
                            newUser.email()
                    );
                } else {
                    throw new SQLException();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method updates a new User and save it to the database.
     * <br></br>
     * <b>Detailed explanation:</b>
     * <br></br>
     * It provides an UPDATE SQL query, then establish the connection with the database,
     * sets the SQL parameters and execute it. Next, it creates a User object
     * from the provided updated data.
     *
     * @param updateUserDetails Request body coming from the client with updated data.
     * @param userId            ID of the user client wants to update.
     * @return updated User object.
     * @throws RuntimeException In case connection fails or ID was not found.
     */
    @Override
    public User updateUser(UpdateUserDTO updateUserDetails, int userId) {
        String sql = "UPDATE \"user\" SET " +
                "username = ?, first_name = ?, last_name = ?, user_email = ?, user_password = ?" +
                " WHERE user_id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, updateUserDetails.username());
            preparedStatement.setString(2, updateUserDetails.firstName());
            preparedStatement.setString(3, updateUserDetails.lastName());
            preparedStatement.setString(4, updateUserDetails.email());
            preparedStatement.setString(5, updateUserDetails.password());
            preparedStatement.setInt(6, userId);

            int rowsChanged = preparedStatement.executeUpdate();
            if (rowsChanged == 0) {
                throw new SQLException("No user found");
            }
            return new User(
                    userId,
                    updateUserDetails.username(),
                    updateUserDetails.firstName(),
                    updateUserDetails.lastName(),
                    updateUserDetails.password(),
                    updateUserDetails.email()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteUser(int userId) {
        String sql = "DELETE FROM \"user\" WHERE user_id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setInt(1, userId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No user found");
            }
            return userId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
