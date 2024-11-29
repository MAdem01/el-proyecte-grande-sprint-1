package com.codecool.codekickfc.controller;

import com.codecool.codekickfc.dto.users.*;
import com.codecool.codekickfc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Extracts available information from all existing users in the database.
     *
     * @return list of transformed user object that includes user's name, username, email and
     * matches.
     */
    @GetMapping("/all")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Create a new user from the provided information and saves into the database.
     *
     * @param newUser The request body based on the client inputs.
     * @return ID of the newly created user.
     */
    @PostMapping
    public long createUser(@RequestBody NewUserDTO newUser) {
        return userService.createUser(newUser);
    }

    /**
     * Update an existing user based on the provided information and saves the changes in the
     * database.
     *
     * @param updateUserDetails The request body based on the client inputs.
     * @param userId            ID of the user client wants to update.
     * @return ID of the updated user.
     */
    @PutMapping("/{userId}")
    public long updateUser(@PathVariable long userId,
                           @RequestBody UpdateUserDTO updateUserDetails) {
        return userService.updateUser(updateUserDetails, userId);
    }

    /**
     * Delete an existing user based on the provided ID and saves the changes in the
     * database.
     *
     * @param userId ID of the user client wants to delete.
     * @return ID of the deleted user
     */
    @DeleteMapping("/{userId}")
    public long deleteUser(@PathVariable long userId) {
        return userService.deleteUser(userId);
    }

    /**
     * Get an existing user from the database based on the provided ID.
     *
     * @param userId ID of the user client wants to get.
     * @return User data transfer object that includes user's full name, username,
     * email and matches.
     */
    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable long userId) {
        return userService.getUserById(userId);
    }

    /**
     * Add an existing match from the database based on the provided ID to an existing user and
     * vica-versa.
     *
     * @param userId  ID of the user to whom the client wants to assign a match.
     * @param matchId ID of the match the client wants to sign up for.
     * @return {@link UserMatchDTO} Includes signed up userId and matchId.
     */
    @PatchMapping("/{userId}/matches/{matchId}/add")
    public UserMatchDTO addUserToMatch(@PathVariable long userId,
                                       @PathVariable long matchId) {
        return userService.addUserToMatch(userId, matchId);
    }

    /**
     * Remove an existing match from the database based on the provided ID from an existing user
     * and vica-versa.
     *
     * @param userId  ID of the user from whom the client wants to delete a match.
     * @param matchId ID of the match the client wants cancel.
     * @return {@link UserMatchDTO} Includes removed userId and matchId.
     */
    @DeleteMapping("/{userId}/matches/{matchId}/remove")
    public UserMatchDTO removeUserFromMatch(@PathVariable long userId,
                                            @PathVariable long matchId) {
        return userService.removeUserFromMatch(userId, matchId);
    }

    @PostMapping("/admin/add")
    public long addAdmin(@RequestBody UsernameDTO user) {
        return userService.addAdminRoleFor(user);
    }
}
