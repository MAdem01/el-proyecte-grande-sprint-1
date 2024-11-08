package com.codecool.codekickfc.service.users;

import com.codecool.codekickfc.controller.dto.matches.MatchDTO;
import com.codecool.codekickfc.controller.dto.users.NewUserDTO;
import com.codecool.codekickfc.controller.dto.users.UpdateUserDTO;
import com.codecool.codekickfc.controller.dto.users.UserDTO;
import com.codecool.codekickfc.controller.dto.users.UserMatchDTO;
import com.codecool.codekickfc.dao.matches.MatchRepository;
import com.codecool.codekickfc.dao.model.matches.Match;
import com.codecool.codekickfc.dao.model.users.User;
import com.codecool.codekickfc.dao.users.UserRepository;
import com.codecool.codekickfc.exceptions.AlreadySignedUpException;
import com.codecool.codekickfc.exceptions.DatabaseAccessException;
import com.codecool.codekickfc.exceptions.MatchNotFoundException;
import com.codecool.codekickfc.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final MatchRepository matchRepository;

    @Autowired
    public UserService(UserRepository userRepository, MatchRepository matchRepository) {
        this.userRepository = userRepository;
        this.matchRepository = matchRepository;
    }

    /**
     * Get all information from users through the database, and then it filters the
     * unnecessary details by converting the objects into DTO-s.
     *
     * @return A list of user data transfer object that includes user's id, full name, username,
     * email and matches.
     * @throws UserNotFoundException In case of no user in database.
     */
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new UserNotFoundException();
        }

        return users.stream().map(UserDTO::fromUser).collect(Collectors.toList());
    }


    /**
     * Creates a new {@link User} model from the provided {@link NewUserDTO} request body,
     * and then it filters the unnecessary details by extracting only the ID from the
     * saved data.
     *
     * @param newUserDTO The request body based on the client inputs
     * @return ID of the created User model
     * @throws DatabaseAccessException In case of connection failure.
     */
    public long createUser(NewUserDTO newUserDTO) {
        User newUser = new User(
                newUserDTO.username(),
                newUserDTO.firstName(),
                newUserDTO.lastName(),
                newUserDTO.password(),
                newUserDTO.email()
        );
        try {
            return userRepository.save(newUser).getId();
        } catch (DataAccessException e) {
            throw new DatabaseAccessException(e);
        }
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
    public long updateUser(UpdateUserDTO updateUserDetails, long userId) {
        User updatedUser = userRepository.findById(userId).
                orElseThrow(UserNotFoundException::new);

        updatedUser.setFirstName(updateUserDetails.firstName());
        updatedUser.setLastName(updateUserDetails.lastName());
        updatedUser.setEmail(updateUserDetails.email());
        updatedUser.setUsername(updateUserDetails.username());

        try {
            return userRepository.save(updatedUser).getId();
        } catch (DataAccessException e) {
            throw new DatabaseAccessException(e);
        }
    }

    /**
     * Get all information from the user found in the database by its ID, deletes it and also
     * deletes itself from all associated matches.
     *
     * @param userId ID of the user client wants to delete.
     * @return ID of the deleted user
     * @throws UserNotFoundException   In case of user doesn't exist.
     * @throws DatabaseAccessException In case of connection failure.
     */
    @Transactional
    public long deleteUser(long userId) {
        User deletableUser = userRepository.findById(userId).
                orElseThrow(UserNotFoundException::new);

        for (Match match : deletableUser.getMatches()) {
            match.removeUser(deletableUser);
        }

        try {
            userRepository.delete(deletableUser);
            return deletableUser.getId();
        } catch (DataAccessException e) {
            throw new DatabaseAccessException(e);
        }
    }

    /**
     * Get all information from the user found in the database by its ID, get only the upcoming
     * games, and then it filters the unnecessary details by converting the object into DTO.
     *
     * @param userId ID of the user client wants to get.
     * @return User data transfer object that includes user's full name, username,
     * email and matches.
     * @throws UserNotFoundException In case of user doesn't exist.
     */
    public UserDTO getUserById(long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Match> upcomingMatches = userRepository.findUpcomingMatchesByUserId(user.getId());

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                upcomingMatches.stream().map(MatchDTO::fromMatch).toList()
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
     * @throws UserNotFoundException   In case of user doesn't exist.
     * @throws MatchNotFoundException  In case of match doesn't exist.
     * @throws DatabaseAccessException In case of connection failure.
     */
    @Transactional
    public UserMatchDTO addUserToMatch(long userId, long matchId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Match match = matchRepository.findById(matchId).orElseThrow(MatchNotFoundException::new);

        if (user.getMatches().contains(match)) {
            throw new AlreadySignedUpException(match.getId());
        }

        user.addMatch(match);

        try {
            userRepository.save(user);
            return new UserMatchDTO(user.getId(), match.getId());
        } catch (DataAccessException e) {
            throw new DatabaseAccessException(e);
        }
    }

    /**
     * Get all information from the user and match found in the database by their ID's, remove
     * them from each other, saves the updates and extracting their ID's as a response.
     * Transactional annotation indicates that the updates should only happen together.
     *
     * @param userId  ID of the user from whom the client wants to remove a match.
     * @param matchId ID of the match the client wants to cancel.
     * @return {@link UserMatchDTO} Includes removed userId and matchId.
     * @throws UserNotFoundException   In case of user doesn't exist.
     * @throws MatchNotFoundException  In case of match doesn't exist.
     * @throws DatabaseAccessException In case of connection failure.
     */
    @Transactional
    public UserMatchDTO removeUserFromMatch(long userId, long matchId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Match match = matchRepository.findById(matchId).orElseThrow(MatchNotFoundException::new);

        user.removeMatch(match);
        match.removeUser(user);

        try {
            userRepository.save(user);
            return new UserMatchDTO(user.getId(), match.getId());
        } catch (DataAccessException e) {
            throw new DatabaseAccessException(e);
        }
    }
}
