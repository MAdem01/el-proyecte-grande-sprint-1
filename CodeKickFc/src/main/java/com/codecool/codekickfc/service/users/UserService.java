package com.codecool.codekickfc.service.users;

import com.codecool.codekickfc.controller.dto.users.NewUserDTO;
import com.codecool.codekickfc.controller.dto.users.UpdateUserDTO;
import com.codecool.codekickfc.controller.dto.users.UserDTO;
import com.codecool.codekickfc.dao.model.users.User;
import com.codecool.codekickfc.dao.users.UserDAO;
import com.codecool.codekickfc.dao.users.UserDAOJdbc;
import org.springframework.stereotype.Service;
import com.codecool.codekickfc.controller.users.UserController;

import java.util.ArrayList;
import java.util.List;

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
        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            UserDTO userDTO =
                    new UserDTO(
                            user.username(),
                            user.firstName(),
                            user.lastName(),
                            user.email(),
                            user.matchIds()
                    );
            userDTOs.add(userDTO);
        }

        return userDTOs;
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

    public UserDTO getUserById(int userId) {
        User user = userDAO.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return new UserDTO(
                user.username(),
                user.firstName(),
                user.lastName(),
                user.email(),
                user.matchIds()
        );
    }
}
