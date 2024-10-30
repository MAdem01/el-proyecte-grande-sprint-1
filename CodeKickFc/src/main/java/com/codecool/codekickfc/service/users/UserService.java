package com.codecool.codekickfc.service.users;

import com.codecool.codekickfc.controller.users.NewUserDTO;
import com.codecool.codekickfc.controller.users.UpdateUserDTO;
import com.codecool.codekickfc.controller.users.UserDTO;
import com.codecool.codekickfc.dao.users.User;
import com.codecool.codekickfc.dao.users.UserDAO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
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
                    new UserDTO(user.username(), user.firstName(), user.lastName(), user.email());
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

    public int updateUser(UpdateUserDTO updateUserDetails, int id) {
        User updatedUser = userDAO.updateUser(updateUserDetails, id);
        return updatedUser.id();
    }
}
