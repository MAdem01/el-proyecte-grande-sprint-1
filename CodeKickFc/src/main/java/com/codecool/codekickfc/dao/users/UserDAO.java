package com.codecool.codekickfc.dao.users;

import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();
    User createUser(User user);
}
