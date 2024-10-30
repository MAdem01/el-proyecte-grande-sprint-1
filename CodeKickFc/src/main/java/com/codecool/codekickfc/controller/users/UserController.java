package com.codecool.codekickfc.controller.users;

import com.codecool.codekickfc.controller.dto.users.NewUserDTO;
import com.codecool.codekickfc.controller.dto.users.UpdateUserDTO;
import com.codecool.codekickfc.controller.dto.users.UserDTO;
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

    /**
     * Create a new user from the provided information and saves into the database through the
     * service and repository layers.
     *
     * @param newUser The request body based on the client inputs
     * @return ID of the newly created user
     */
    @PostMapping
    public int createUser(@RequestBody NewUserDTO newUser) {
        return userService.createUser(newUser);
    }

    /**
     * Update an existing user from the provided information and saves the changes in the
     * database through the service and repository layers.
     *
     * @param updateUserDetails The request body based on the client inputs.
     * @param userId            ID of the user client wants to update.
     * @return ID of the updated user
     */
    @PatchMapping("/{userId}")
    public int updateUser(@PathVariable int userId, @RequestBody UpdateUserDTO updateUserDetails) {
        return userService.updateUser(updateUserDetails, userId);
    }

    @DeleteMapping("/{userId}")
    public int deleteUser(@PathVariable int userId) {
        return userService.deleteUser(userId);
    }
}
