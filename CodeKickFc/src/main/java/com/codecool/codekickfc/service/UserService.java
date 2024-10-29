package com.codecool.codekickfc.service;

import com.codecool.codekickfc.controller.UserDTO;
import com.codecool.codekickfc.dao.User;
import com.codecool.codekickfc.dao.UserDAO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

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
}
