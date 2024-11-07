package com.codecool.codekickfc.service.users;

import com.codecool.codekickfc.controller.dto.users.NewUserDTO;
import com.codecool.codekickfc.controller.dto.users.UpdateUserDTO;
import com.codecool.codekickfc.controller.dto.users.UserDTO;
import com.codecool.codekickfc.controller.dto.users.UserMatchDTO;
import com.codecool.codekickfc.controller.users.UserController;
import com.codecool.codekickfc.dao.model.users.User;
import com.codecool.codekickfc.dao.model.users.UserMatch;
import com.codecool.codekickfc.dao.users.UserDAO;
import com.codecool.codekickfc.dao.users.UserDAOJdbc;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final MatchRepository matchRepository;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Get all information from users through the database, and then it filters the
     * unnecessary details by converting the objects into DTO-s.
     *
     * @return A list of user data transfer object that includes user's full name, username,
     * email and matches.
     * @throws DatabaseAccessException In case of connection failure.
     */
    public List<UserDTO> getAllUsers() {
        List<User> users = userDAO.getAllUsers();

        return users.stream().
                map(user -> new UserDTO(
                        user.id(),
                        user.username(),
                        user.firstName(),
                        user.lastName(),
                        user.email(),
                        user.matchIds()
                )).collect(Collectors.toList());
    }


    /**
     * Creates a new {@link User} model from the provided {@link NewUserDTO} request body,
     * and then it filters the unnecessary details by extracting only the ID once from the
     * saved data.
     *
     * @param newUserDTO The request body based on the client inputs
     * @return ID of the created User model
     * @throws DataAccessException In case of connection failure.
     */
    public int createUser(NewUserDTO newUserDTO) {
        User createdUser = userDAO.createUser(newUserDTO);
        return createdUser.id();
    }

    /**
     * Get all information from the user found in the database by its ID, update its fields,
     * and then it filters the unnecessary details by extracting only the ID from the saved data.
     *
     * @param updateUserDetails The request body based on the client inputs.
     * @param userId            ID of the user client wants to update.
     * @return ID of the updated User model.
     * @throws UserNotFoundException   In case of user doesn't exist.
     * @throws DatabaseAccessException In case of connection failure.
     */
    public int updateUser(UpdateUserDTO updateUserDetails, int userId) {
        User updatedUser = userDAO.updateUser(updateUserDetails, userId);
        return updatedUser.id();
    }

    /**
     * Get all information from the user found in the database by its ID and deletes it.
     *
     * @param userId ID of the user client wants to delete.
     * @return ID of the deleted user
     * @throws UserNotFoundException   In case of user doesn't exist.
     * @throws DatabaseAccessException In case of connection failure.
     */
    public int deleteUser(int userId) {
        return userDAO.deleteUser(userId);
    }

    /**
     * Establish a connection between controller and the repository layer by calling
     * {@link UserDAOJdbc getUserById(int userId)} method from the repository layer that returns
     * a User object which is then being converted to a DTO and returns it to
     * the {@link UserController controller} layer. If User's value is null,
     * {@link RuntimeException} is being thrown.
     *
     * @param userId ID of the user client wants to get.
     * @return ID of the found user.
     */
    public UserDTO getUserById(int userId) {
        User user = userDAO.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
    }

    /**
     * Get all information from the user found in the database by its ID, and then it
     * filters the unnecessary details by converting the objects into DTO-s.
     *
     * @param userId ID of the user client wants to get.
     * @return User data transfer object that includes user's full name, username,
     * email and matches.
     * @throws UserNotFoundException In case of user doesn't exist.
     */
    public UserDTO getUserById(long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return new UserDTO(
                user.id(),
                user.username(),
                user.firstName(),
                user.lastName(),
                user.email(),
                user.matchIds()
        );
    }

    /**
     * Get all information from the user and match found in the database by their ID's, add
     * them to each other, saves the updates and extracting their ID's as a response.
     * Transactional annotation indicates that the updates should only happen together.
     *
     * @param userId  ID of the user to whom the client wants to assign a match.
     * @param matchId ID of the match the client wants to sign up for.
     * @return {@link UserMatchDTO} Includes signed up userId and matchId.
     * @throws UserNotFoundException In case of user doesn't exist.
     * @throws MatchNotFoundException In case of match doesn't exist.
     * @throws DatabaseAccessException In case of connection failure.
     */
    public UserMatchDTO addUserToMatch(int userId, int matchId) {
        UserMatch userMatch = userDAO.addUserToMatch(userId, matchId);
        return new UserMatchDTO(userMatch.userId(), userMatch.matchId());
    }
}
