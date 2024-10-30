package com.codecool.codekickfc.dao.users;

import com.codecool.codekickfc.controller.dto.users.NewUserDTO;
import com.codecool.codekickfc.controller.dto.users.UpdateUserDTO;
import com.codecool.codekickfc.dao.model.users.User;
import com.codecool.codekickfc.dao.model.users.UserMatch;

import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();

    User createUser(NewUserDTO newUser);

    User updateUser(UpdateUserDTO updateUserDetails, int userId);

    int deleteUser(int userId);

    User getUserById(int userId);

    UserMatch addUserToMatch(int userId, int matchId);
}
