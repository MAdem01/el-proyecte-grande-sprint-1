package com.codecool.codekickfc.service.users;

import com.codecool.codekickfc.controller.dto.users.NewUserDTO;
import com.codecool.codekickfc.controller.dto.users.UpdateUserDTO;
import com.codecool.codekickfc.controller.dto.users.UserDTO;
import com.codecool.codekickfc.controller.dto.users.UserMatchDTO;
import com.codecool.codekickfc.controller.users.UserController;
import com.codecool.codekickfc.dao.model.users.User;
import com.codecool.codekickfc.dao.model.users.UserMatch;
import com.codecool.codekickfc.dao.users.UserDAO;
import com.codecool.codekickfc.dao.users.UserDAOJdbc;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserDAO userDAO;
    private final UserDAOJdbc userDAOJdbc;

    public UserService(UserDAO userDAO, UserDAOJdbc userDAOJdbc) {
        this.userDAO = userDAO;
        this.userDAOJdbc = userDAOJdbc;
    }

    /**
     * Establish a connection between controller and the repository layer. First returns all information
     * from users through the database, and then it filters the unnecessary details by converting the
     * objects into DTO-s.
     *
     * @return a list of user data transfer object that includes user's full name, username and email.
     */
    public List<UserDTO> getAllUsers() {
        List<User> users = userDAO.getAllUsers();

        return users.stream().
                map(user -> new UserDTO(
                        user.id(),
                        user.username(),
                        user.firstName(),
                        user.lastName(),
                        user.email(),
                        user.matchIds()
                )).collect(Collectors.toList());
    }


    /**
     * Establish a connection between controller and the repository layer. First returns all information
     * from the created user through the database, and then it filters the unnecessary details by
     * extracting the ID as a response.
     *
     * @param newUserDTO The request body based on the client inputs
     * @return ID of the created User model
     */
    public int createUser(NewUserDTO newUserDTO) {
        User createdUser = userDAO.createUser(newUserDTO);
        return createdUser.id();
    }

    /**
     * Establish a connection between controller and the repository layer. First returns all information
     * from the updated user through the database, and then it filters the unnecessary details by
     * extracting the ID as a response.
     *
     * @param updateUserDetails The request body based on the client inputs
     * @param userId            ID of the user client wants to update.
     * @return ID of the updated User model
     */
    public int updateUser(UpdateUserDTO updateUserDetails, int userId) {
        User updatedUser = userDAO.updateUser(updateUserDetails, userId);
        return updatedUser.id();
    }

    /**
     * Establish a connection between controller and the repository layer by calling
     * {@link UserDAOJdbc deleteUser(int userId)} method from the repository layer and returns
     * its result to the {@link UserController controller} layer.
     *
     * @param userId ID of the user client wants to delete.
     * @return ID of the deleted user
     */
    public int deleteUser(int userId) {
        return userDAO.deleteUser(userId);
    }

    /**
     * Establish a connection between controller and the repository layer by calling
     * {@link UserDAOJdbc getUserById(int userId)} method from the repository layer that returns
     * a User object which is then being converted to a DTO and returns it to
     * the {@link UserController controller} layer. If User's value is null,
     * {@link RuntimeException} is being thrown.
     *
     * @param userId ID of the user client wants to get.
     * @return ID of the found user.
     */
    public UserDTO getUserById(int userId) {
        User user = userDAO.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return new UserDTO(
                user.id(),
                user.username(),
                user.firstName(),
                user.lastName(),
                user.email(),
                user.matchIds()
        );
    }

    /**
     * Establish a connection between controller and the repository layer by calling
     * {@link UserDAOJdbc addUserToMatch(int userId, int matchId)} method from the
     * repository layer that returns a {@link UserMatch} object which is then being converted
     * to a {@link UserMatchDTO DTO} and returns it to the {@link UserController controller}
     * layer.
     *
     * @param userId  ID of the user to whom the client wants to assign a match.
     * @param matchId ID of the match the client wants to sign up for.
     * @return {@link UserMatchDTO} Includes signed up userId and matchId.
     */
    public UserMatchDTO addUserToMatch(int userId, int matchId) {
        UserMatch userMatch = userDAO.addUserToMatch(userId, matchId);
        return new UserMatchDTO(userMatch.userId(), userMatch.matchId());
    }
}
