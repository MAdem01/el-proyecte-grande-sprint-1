package com.codecool.codekickfc.service.matches;

import com.codecool.codekickfc.controller.dto.matches.MatchDTO;
import com.codecool.codekickfc.controller.dto.matches.MatchIdDTO;
import com.codecool.codekickfc.controller.dto.matches.NewMatchDTO;
import com.codecool.codekickfc.controller.dto.matches.UpdateMatchDTO;
import com.codecool.codekickfc.dao.footballpitch.FootballPitchRepository;
import com.codecool.codekickfc.dao.matches.MatchRepository;
import com.codecool.codekickfc.dao.model.matches.Match;
import com.codecool.codekickfc.dao.model.pitches.FootballPitch;
import com.codecool.codekickfc.dao.model.users.User;
import com.codecool.codekickfc.exceptions.DatabaseAccessException;
import com.codecool.codekickfc.exceptions.FootballPitchNotFoundException;
import com.codecool.codekickfc.exceptions.MatchNotFoundException;
import com.codecool.codekickfc.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final FootballPitchRepository footballPitchRepository;
    private static final Logger logger = LoggerFactory.getLogger(MatchService.class);
    private static final String[] ROMAN_SYMBOLS = {
            "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X",
            "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX",
            "XXI", "XXII", "XXIII"};


    @Autowired
    public MatchService(MatchRepository matchRepository,
                        FootballPitchRepository footballPitchRepository) {
        this.matchRepository = matchRepository;
        this.footballPitchRepository = footballPitchRepository;
    }

    /**
     * Get all information from upcoming matches through the database, and then it filters the
     * unnecessary details by converting the objects into DTO-s.
     *
     * @return A list of match data transfer object that includes match's id, fee, max capacity,
     * football field, match date, rules, subscribed players.
     * @throws UserNotFoundException In case of no match in database.
     */
    public List<MatchDTO> getAllMatches(String area, int pageNumber) {
        Page<Match> matches;

        if (area == null || area.trim().isEmpty()) {
            matches = matchRepository.findAllByMatchDateAfterOrderByMatchDate(
                    LocalDateTime.now(), PageRequest.of(pageNumber, 5)
            );
        } else if (isNumeric(area.trim())) {
            matches = findMatchesByDistrict(area.trim(), pageNumber);
        } else if (isRomanNumeric(area.trim().toUpperCase())) {
            matches = matchRepository.
                    findAllByFootballFieldDistrictEqualsIgnoreCaseAndMatchDateAfterOrderByMatchDate(
                            area.trim(), LocalDateTime.now(), PageRequest.of(pageNumber, 5)
                    );
        } else {
            matches = matchRepository.
                    findAllByFootballFieldCityEqualsIgnoreCaseAndMatchDateAfterOrderByMatchDate(
                            area.trim(), LocalDateTime.now(), PageRequest.of(pageNumber, 5)
                    );
        }

        if (matches.isEmpty()) {
            throw new MatchNotFoundException();
        }

        return matches.stream().map(MatchDTO::fromMatch).collect(Collectors.toList());
    }

    /**
     * Creates a new {@link Match} model from the provided {@link NewMatchDTO} request body,
     * and then it filters the unnecessary details by extracting only the ID from the
     * saved data.
     *
     * @param newMatchDTO The request body based on the client inputs.
     * @return ID of the created Match model
     * @throws DatabaseAccessException        In case of connection failure.
     * @throws FootballPitchNotFoundException In case of football pitch doesn't exist.
     */
    public MatchIdDTO createMatch(NewMatchDTO newMatchDTO) {
        FootballPitch footballPitch = footballPitchRepository.
                findById(newMatchDTO.footballPitch().getId())
                .orElseThrow(FootballPitchNotFoundException::new);


        Match newMatch = new Match(
                newMatchDTO.maxPlayers(),
                newMatchDTO.matchFeePerPerson(),
                newMatchDTO.matchDate(),
                newMatchDTO.matchRules(),
                footballPitch
        );
        try {
            return new MatchIdDTO(matchRepository.save(newMatch).getId());
        } catch (DataAccessException e) {
            throw new DatabaseAccessException(e);
        }
    }

    /**
     * Get all information from the match found in the database by its ID, update its fields,
     * and then it filters the unnecessary details by extracting only the ID from the saved data.
     *
     * @param updateMatchDetails The request body based on the client inputs.
     * @param matchId            ID of the match client wants to update.
     * @return ID of the updated Match model.
     * @throws MatchNotFoundException         In case of match doesn't exist.
     * @throws FootballPitchNotFoundException In case of football pitch doesn't exist.
     * @throws DatabaseAccessException        In case of connection failure.
     */
    public MatchIdDTO updateMatch(UpdateMatchDTO updateMatchDetails, long matchId) {
        Match updatedMatch = matchRepository.findById(matchId).
                orElseThrow(MatchNotFoundException::new);
        FootballPitch footballPitch = footballPitchRepository.
                findById(updateMatchDetails.footballPitch().getId())
                .orElseThrow(FootballPitchNotFoundException::new);

        updatedMatch.setMaxPlayers(updateMatchDetails.maxPlayers());
        updatedMatch.setMatchFeePerPerson(updateMatchDetails.matchFeePerPerson());
        updatedMatch.setMatchDate(updateMatchDetails.matchDate());
        updatedMatch.setMatchRules(updateMatchDetails.matchRules());
        updatedMatch.setFootballField(footballPitch);

        try {
            return new MatchIdDTO(matchRepository.save(updatedMatch).getId());
        } catch (DataAccessException e) {
            throw new DatabaseAccessException(e);
        }
    }

    /**
     * Get all information from the match found in the database by its ID, deletes it and also
     * deletes itself from all associated users.
     *
     * @param matchId ID of the match client wants to delete.
     * @return ID of the deleted match.
     * @throws MatchNotFoundException  In case of match doesn't exist.
     * @throws DatabaseAccessException In case of connection failure.
     */
    @Transactional
    public MatchIdDTO deleteMatch(long matchId) {
        Match deletableMatch = matchRepository.findById(matchId).
                orElseThrow(MatchNotFoundException::new);

        for (User user : deletableMatch.getUsers()) {
            user.removeMatch(deletableMatch);
        }

        try {
            matchRepository.delete(deletableMatch);
            return new MatchIdDTO(deletableMatch.getId());
        } catch (DataAccessException e) {
            throw new DatabaseAccessException(e);
        }
    }

    /**
     * Get all information from the match found in the database by its ID, and then it filters
     * the unnecessary details by converting the object into DTO.
     *
     * @param matchId ID of the match client wants to get.
     * @return Match data transfer object that includes match's id, fee, max capacity,
     * football field, match date, rules, subscribed players.
     * @throws MatchNotFoundException In case of match doesn't exist.
     */
    public MatchDTO getMatchById(long matchId) {
        Match match = matchRepository.findById(matchId).orElseThrow(MatchNotFoundException::new);

        return MatchDTO.fromMatch(match);
    }

    private boolean isNumeric(String area) {
        try {
            Integer.parseInt(area.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isRomanNumeric(String area) {
        return Arrays.asList(ROMAN_SYMBOLS).contains(area);
    }

    private String convertNumberToRoman(int number) {
        return ROMAN_SYMBOLS[number - 1];
    }

    private Page<Match> findMatchesByDistrict(String area, int pageNumber) {
        Page<Match> matches;
        try {
            String district = convertNumberToRoman(Integer.parseInt(area));
            matches = matchRepository.
                    findAllByFootballFieldDistrictEqualsIgnoreCaseAndMatchDateAfterOrderByMatchDate(
                            district, LocalDateTime.now(), PageRequest.of(pageNumber, 5)
                    );
            return matches;
        } catch (Exception e) {
            throw new MatchNotFoundException();
        }
    }
}
