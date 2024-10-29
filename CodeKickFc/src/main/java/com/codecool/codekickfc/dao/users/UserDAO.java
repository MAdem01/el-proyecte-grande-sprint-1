package com.codecool.codekickfc.dao.users;

import com.codecool.codekickfc.controller.users.NewUserDTO;

import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();
    User createUser(NewUserDTO newUser);
}
