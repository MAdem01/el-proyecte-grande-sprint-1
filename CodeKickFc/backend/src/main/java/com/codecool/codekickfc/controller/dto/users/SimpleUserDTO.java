package com.codecool.codekickfc.controller.dto.users;

import com.codecool.codekickfc.dao.model.users.User;

public record SimpleUserDTO(long id, String username) {
    public static SimpleUserDTO fromUser(User user) {
        return new SimpleUserDTO(user.getId(), user.getUsername());
    }
}
