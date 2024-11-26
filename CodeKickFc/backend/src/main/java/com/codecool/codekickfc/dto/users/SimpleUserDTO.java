package com.codecool.codekickfc.dto.users;

import com.codecool.codekickfc.repository.model.User;

public record SimpleUserDTO(long id, String username) {
    public static SimpleUserDTO fromUser(User user) {
        return new SimpleUserDTO(user.getId(), user.getUsername());
    }
}
