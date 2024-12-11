package com.codecool.codekickfc.service;

import com.codecool.codekickfc.dto.matches.MatchDTO;
import com.codecool.codekickfc.dto.users.*;
import com.codecool.codekickfc.exceptions.AlreadySignedUpException;
import com.codecool.codekickfc.exceptions.DatabaseAccessException;
import com.codecool.codekickfc.exceptions.MatchNotFoundException;
import com.codecool.codekickfc.exceptions.UserNotFoundException;
import com.codecool.codekickfc.repository.MatchRepository;
import com.codecool.codekickfc.repository.RoleRepository;
import com.codecool.codekickfc.repository.UserRepository;
import com.codecool.codekickfc.repository.model.Match;
import com.codecool.codekickfc.repository.model.Role;
import com.codecool.codekickfc.repository.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final MatchRepository matchRepository;
    private final RoleRepository roleRepository;
    private static final long ROLE_ADMIN_ID = 2;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, MatchRepository matchRepository,
                       RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.matchRepository = matchRepository;
        this.roleRepository = roleRepository;

        this.passwordEncoder = passwordEncoder;
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
        Role userRole = roleRepository.findByType("ROLE_USER");
        User newUser = new User(
                newUserDTO.username(),
                newUserDTO.firstName(),
                newUserDTO.lastName(),
                passwordEncoder.encode(newUserDTO.password()),
                newUserDTO.email()
        );
        newUser.addRole(userRole);
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
        List<Match> upcomingMatches = matchRepository.findByUsersIdAndMatchDateAfter(
                user.getId(), LocalDateTime.now()
        );

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

    public long addAdminRoleFor(UsernameDTO user) {
        Role adminRole = roleRepository.getById(ROLE_ADMIN_ID);
        User userEntity = userRepository.findByUsername(user.username()).
                orElseThrow(UserNotFoundException::new);
        userEntity.addRole(adminRole);
        return userRepository.save(userEntity).getId();
    }
}
