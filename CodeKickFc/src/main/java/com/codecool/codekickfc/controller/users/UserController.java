package com.codecool.codekickfc.controller.users;

import com.codecool.codekickfc.service.users.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Extracts available information from all existing users in the database.
     *
     * @return list of transformed user object that includes user's name, username and email.
     */
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public int createUser(@RequestBody NewUserDTO newUser) {
        return userService.createUser(newUser);
    }
}
