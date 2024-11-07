package com.codecool.codekickfc.controller.users;

import com.codecool.codekickfc.controller.dto.users.NewUserDTO;
import com.codecool.codekickfc.controller.dto.users.UpdateUserDTO;
import com.codecool.codekickfc.controller.dto.users.UserDTO;
import com.codecool.codekickfc.controller.dto.users.UserMatchDTO;
import com.codecool.codekickfc.service.users.UserService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Create a new user from the provided information and saves into the database.
     *
     * @param newUser The request body based on the client inputs
     * @return ID of the newly created user
     */
    @PostMapping
    public ResponseEntity<Long> createUser(@RequestBody NewUserDTO newUser) {
        return ResponseEntity.ok(userService.createUser(newUser));
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
    public ResponseEntity<Long> updateUser(@PathVariable long userId,
                                           @RequestBody UpdateUserDTO updateUserDetails) {
        return ResponseEntity.ok(userService.updateUser(updateUserDetails, userId));
    }

    /**
     * Delete an existing user based on the provided ID and saves the changes in the
     * database.
     *
     * @param userId ID of the user client wants to delete.
     * @return ID of the deleted user
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Long> deleteUser(@PathVariable long userId) {
        return ResponseEntity.ok(userService.deleteUser(userId));
    }

    /**
     * Get an existing user from the database based on the provided ID.
     *
     * @param userId ID of the user client wants to get.
     * @return ID of the found user.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    /**
     * Add an existing match from the database based on the provided ID to an existing user.
     *
     * @param userId  ID of the user to whom the client wants to assign a match.
     * @param matchId ID of the match the client wants to sign up for.
     * @return {@link UserMatchDTO} Includes signed up userId and matchId.
     */
    @PatchMapping("/{userId}/matches/{matchId}")
    public ResponseEntity<UserMatchDTO> addUserToMatch(@PathVariable long userId, @PathVariable long matchId) {
        return ResponseEntity.ok(userService.addUserToMatch(userId, matchId));
    }
}
