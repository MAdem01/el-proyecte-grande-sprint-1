package com.codecool.codekickfc.controller.dto.users;

import com.codecool.codekickfc.controller.dto.matches.MatchDTO;
import com.codecool.codekickfc.dao.model.users.User;

import java.util.List;
import java.util.stream.Collectors;

public record UserDTO(long id, String username, String firstName, String lastName,
                      String email, List<MatchDTO> matches) {
    public static UserDTO fromUser(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getMatches().stream().
                        map(MatchDTO::fromMatch).
                        collect(Collectors.toList())
        );
    }
}
