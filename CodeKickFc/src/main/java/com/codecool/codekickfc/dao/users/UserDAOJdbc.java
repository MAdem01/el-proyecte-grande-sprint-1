package com.codecool.codekickfc.dao.users;

import com.codecool.codekickfc.controller.dto.users.NewUserDTO;
import com.codecool.codekickfc.controller.dto.users.UpdateUserDTO;
import com.codecool.codekickfc.dao.model.database.DatabaseConnection;
import com.codecool.codekickfc.dao.model.users.User;
import com.codecool.codekickfc.dao.model.users.UserMatch;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
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
     * the ResultSet has next row.
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
                List<Short> matchIds = new ArrayList<>();
                Array matchIdsResult = resultSet.getArray("match_id");
                if (matchIdsResult != null) {
                    Short[] matchIdsArray = (Short[]) matchIdsResult.getArray();
                    matchIds = Arrays.asList(matchIdsArray);
                }
                User user = new User
                        (id, username, firstName, lastName, password, email, matchIds);
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

    /**
     * This method deletes an existing User from the database.
     * <br></br>
     * <b>Detailed explanation:</b>
     * <br></br>
     * It provides a DELETE SQL query, then establish the connection with the database,
     * sets the SQL parameter and execute it.
     *
     * @param userId ID of the user client wants to delete.
     * @return int ID of the deleted user.
     * @throws RuntimeException In case connection fails or ID was not found.
     */
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

    /**
     * This method returns an existing User from the database.
     * <br></br>
     * <b>Detailed explanation:</b>
     * <br></br>
     * It provides a SELECT SQL query, then establish the connection with the
     * database and execute the query. Next, it creates a {@link User} object
     * from the result if the {@link ResultSet} has next row.
     *
     * @param userId The ID of the user client wants to get.
     * @return {@link User} found user, or null if not found
     * @throws RuntimeException In case connection fails or ID was not found.
     */
    @Override
    public User getUserById(int userId) {
        String sql = "SELECT * FROM \"user\" WHERE user_id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery();
            ) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("user_id");
                    String username = resultSet.getString("username");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String email = resultSet.getString("user_email");
                    String password = resultSet.getString("user_password");
                    List<Short> matchIds = new ArrayList<>();
                    Array matchIdsResult = resultSet.getArray("match_id");
                    if (matchIdsResult != null) {
                        Short[] matchIdsArray = (Short[]) matchIdsResult.getArray();
                        matchIds = Arrays.asList(matchIdsArray);
                    }
                    return new User(
                            id,
                            username,
                            firstName,
                            lastName,
                            password,
                            email,
                            matchIds);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * This method adds an existing Match to a User in the database.
     * <br></br>
     * <b>Detailed explanation:</b>
     * <br></br>
     * It provides three SQL query:
     * <ol>
     * <li>INSERT user and match ID into user_match table</li>
     * <li>UPDATE user table by adding match_id into match_id column</li>
     * <li>UPDATE match table by adding user_id into subscribed_players_id column</li>
     * </ol>
     * Then establish the connection with the database, sets the parameters and
     * execute the update. Next, it creates a {@link UserMatch} object
     * from the result if user or match exists.
     *
     * @param userId  ID of the user to whom the client wants to assign a match.
     * @param matchId ID of the match the client wants to sign up for.
     * @return {@link UserMatch} Includes signed up userId and matchId.
     * @throws RuntimeException In case connection fails or user or match not found in
     *                          database.
     */
    @Override
    public UserMatch addUserToMatch(int userId, int matchId) {
        String sqlUserMatch = "INSERT INTO \"user_match\" (user_id, match_id) VALUES (?,?)";
        String sqlUser = "UPDATE \"user\" SET match_id = match_id || ARRAY[?] WHERE user_id = ?";
        String sqlMatch = "UPDATE match SET subscribed_players_id = subscribed_players_id" +
                " || ARRAY[?] WHERE match_id = ?";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statementUserMatch = connection.prepareStatement(sqlUserMatch);
             PreparedStatement statementUser = connection.prepareStatement(sqlUser);
             PreparedStatement statementMatch = connection.prepareStatement(sqlMatch)
        ) {
            statementUser.setInt(1, matchId);
            statementUser.setInt(2, userId);
            int rowsChangedInUser = statementUser.executeUpdate();
            if (rowsChangedInUser == 0) {
                throw new SQLException("No User found");
            }

            statementMatch.setInt(1, userId);
            statementMatch.setInt(2, matchId);
            int rowsChangedInMatch = statementMatch.executeUpdate();
            if (rowsChangedInMatch == 0) {
                throw new SQLException("No Match found");
            }

            statementUserMatch.setInt(1, userId);
            statementUserMatch.setInt(2, matchId);
            statementUserMatch.executeUpdate();

            return new UserMatch(userId, matchId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

