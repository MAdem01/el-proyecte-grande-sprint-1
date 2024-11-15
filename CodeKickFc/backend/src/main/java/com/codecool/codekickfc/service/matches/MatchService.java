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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final FootballPitchRepository footballPitchRepository;

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
    public List<MatchDTO> getAllMatches(LocalDateTime date, Double minPrice, Double maxPrice, String district, Pageable pageable) {
        Page<Match> matches;

        matches = findMatches(date, minPrice, maxPrice, district, pageable);

        if (matches.isEmpty()) {
            throw new MatchNotFoundException();
        }

        return matches.stream().map(MatchDTO::fromMatch).collect(Collectors.toList());
    }

    public Page<Match> findMatches(LocalDateTime matchDate, Double minPrice, Double maxPrice, String district, Pageable pageable) {
        Specification<Match> specification = Specification.where(null);

        if (matchDate != null) {
            specification = specification.and(matchDateAfter(matchDate));
        }

        if (minPrice != null && maxPrice != null) {
            specification = specification.and(matchFeeBetween(minPrice, maxPrice));
        }

        if (district != null && !district.trim().isEmpty()) {
            specification = specification.and(footballFieldDistrictEquals(district));
        }

        return matchRepository.findAll(specification, pageable);
    }

    private Specification<Match> matchDateAfter(LocalDateTime matchDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("matchDate"), matchDate);
    }

    private Specification<Match> matchFeeBetween(double minPrice, double maxPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("matchFeePerPerson"), minPrice, maxPrice);
    }

    private Specification<Match> footballFieldDistrictEquals(String district) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.lower(root.get("footballField").get("district")),
                district.toLowerCase());
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
}
