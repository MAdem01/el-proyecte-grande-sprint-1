package com.codecool.codekickfc.service;

import com.codecool.codekickfc.dto.matches.MatchDTO;
import com.codecool.codekickfc.dto.matches.MatchIdDTO;
import com.codecool.codekickfc.dto.matches.NewMatchDTO;
import com.codecool.codekickfc.dto.matches.UpdateMatchDTO;
import com.codecool.codekickfc.exceptions.FootballPitchNotFoundException;
import com.codecool.codekickfc.exceptions.MatchNotFoundException;
import com.codecool.codekickfc.repository.FootballPitchRepository;
import com.codecool.codekickfc.repository.MatchRepository;
import com.codecool.codekickfc.repository.model.FootballPitch;
import com.codecool.codekickfc.repository.model.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.codecool.codekickfc.exceptions.DatabaseAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.profiles.active=test")
class MatchServiceTest {

    private LocalDateTime startTime;
    private FootballPitch pitch;
    private Match match;

    @BeforeEach
    void setUp() {
        startTime = LocalDateTime.now().plusDays(1);
        pitch = new FootballPitch(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                0,
                0
        );
        match = new Match(10, 10.1, startTime, "rules", pitch);
    }

    /// GET_ALL_MATCHES TESTS ///

    @Test
    void getAllMatches_whenRepositoryReturnsAllMatchesAsMatchDTO() {
        MatchRepository matchRepository = mock(MatchRepository.class);

        Page<Match> mockPage = new PageImpl<>(List.of(match));

        when(matchRepository.findAllByMatchDateAfterOrderByMatchDate(
                any(), any()))
                .thenReturn(mockPage);

        FootballPitchRepository footballPitchRepository = mock(FootballPitchRepository.class);

        MatchService matchService = new MatchService(matchRepository, footballPitchRepository);

        List<MatchDTO> matchDTOs = matchService.getAllMatches("", 0);

        assertEquals(1, matchDTOs.size(), "The result should contain exactly one match");
    }

    @Test
    void getAllMatches_whenRepositoryIsEmpty_AndThrowMatchNotFoundException() {
        MatchRepository matchRepository = mock(MatchRepository.class);

        when(matchRepository.findAllByMatchDateAfterOrderByMatchDate(any(), any()))
                .thenReturn(new PageImpl<>(List.of()));

        FootballPitchRepository footballPitchRepository = mock(FootballPitchRepository.class);

        MatchService matchService = new MatchService(matchRepository, footballPitchRepository);

        MatchNotFoundException assertThrows =
                assertThrows(MatchNotFoundException.class, () ->
                        matchService.getAllMatches("", 0));

        assertEquals("Match not found", assertThrows.getMessage());
    }

    @Test
    void getAllMatches_whenAreaIsProvidedAsNumber_AndReturnsMatchesWithFittingArea() {
        pitch.setDistrict("12");

        MatchRepository matchRepository = mock(MatchRepository.class);

        Page<Match> mockPage = new PageImpl<>(List.of(match));

        when(matchRepository
                .findAllByFootballFieldDistrictEqualsIgnoreCaseAndMatchDateAfterOrderByMatchDate(
                        any(), any(), any()))
                .thenReturn(mockPage);

        FootballPitchRepository footballPitchRepository = mock(FootballPitchRepository.class);
        MatchService matchService = new MatchService(matchRepository, footballPitchRepository);

        List<MatchDTO> matchDTOs = matchService.getAllMatches("12", 0);


        assertEquals(1, matchDTOs.size(), "The result should contain exactly one match");
    }

    @Test
    void getAllMatches_whenAreaIsProvidedAsRomanNumber_AndReturnsMatchesWithFittingArea () {
        pitch.setDistrict("XII");

        MatchRepository matchRepository = mock(MatchRepository.class);

        Page<Match> mockPage = new PageImpl<>(List.of(match));

        when(matchRepository
                .findAllByFootballFieldDistrictEqualsIgnoreCaseAndMatchDateAfterOrderByMatchDate(
                        any(), any(), any()))
                .thenReturn(mockPage);

        FootballPitchRepository footballPitchRepository = mock(FootballPitchRepository.class);
        MatchService matchService = new MatchService(matchRepository, footballPitchRepository);

        List<MatchDTO> matchDTOs = matchService.getAllMatches("XII", 0);


        assertEquals(1, matchDTOs.size(), "The result should contain exactly one match");
    }

    @Test
    void getAllMatches_whenCityNameIsProvided_AndReturnsMatchesWithFittingCity() {
        pitch.setDistrict("City");

        MatchRepository matchRepository = mock(MatchRepository.class);

        Page<Match> mockPage = new PageImpl<>(List.of(match));

        when(matchRepository
                .findAllByFootballFieldCityEqualsIgnoreCaseAndMatchDateAfterOrderByMatchDate(
                        any(), any(), any()))
                .thenReturn(mockPage);

        FootballPitchRepository footballPitchRepository = mock(FootballPitchRepository.class);
        MatchService matchService = new MatchService(matchRepository, footballPitchRepository);

        List<MatchDTO> matchDTOs = matchService.getAllMatches("City", 0);

        assertEquals(1, matchDTOs.size(), "The result should contain exactly one match");
    }

    ///  CREATE_MATCH TESTS ///

    @Test
    void createMatch_whenRequestDetailsAreCorrect() {
        NewMatchDTO newMatchDTO = new NewMatchDTO(10, 10, startTime, "rules", pitch);
        match.setId(1);

        MatchRepository matchRepository = mock(MatchRepository.class);
        FootballPitchRepository footballPitchRepository = mock(FootballPitchRepository.class);

        when(matchRepository.save(any(Match.class))).thenReturn(match);
        when(footballPitchRepository.findById(any())).thenReturn(Optional.ofNullable(pitch));

        MatchService matchService = new MatchService(matchRepository, footballPitchRepository);

        MatchIdDTO mockIdReturn = matchService.createMatch(newMatchDTO);

        assertEquals(match.getId(), mockIdReturn.id());
    }

    @Test
    void createMatch_whenRequestDetailsAreCorrect_AndThrowsDatabaseAccessException() {
        NewMatchDTO newMatchDTO = new NewMatchDTO(10, 10, startTime, "rules", pitch);

        MatchRepository matchRepository = mock(MatchRepository.class);
        FootballPitchRepository footballPitchRepository = mock(FootballPitchRepository.class);

        when(footballPitchRepository.findById(any())).thenReturn(Optional.ofNullable(pitch));
        when(matchRepository.save(any(Match.class))).thenThrow(DatabaseAccessException.class);

        MatchService matchService = new MatchService(matchRepository, footballPitchRepository);

        assertThrows(DatabaseAccessException.class, () -> matchService.createMatch(newMatchDTO));

    }

    @Test
    void createMatch_AndThrowFootballPitchNotFoundException() {
        NewMatchDTO newMatchDTO = new NewMatchDTO(10, 10, startTime, "rules", pitch);

        MatchRepository matchRepository = mock(MatchRepository.class);
        FootballPitchRepository footballPitchRepository = mock(FootballPitchRepository.class);

        when(footballPitchRepository.findById(any())).thenThrow(FootballPitchNotFoundException.class);

        MatchService matchService = new MatchService(matchRepository, footballPitchRepository);

        assertThrows(FootballPitchNotFoundException.class,
                () -> matchService.createMatch(newMatchDTO));

    }

    ///  UPDATE_MATCH TESTS ///

    @Test
    void updateMatch_whenIdAndUpdateDetailsAreCorrect_AndReturnsMatchId() {
        match.setId(1);

        UpdateMatchDTO updateMatchDTO = new UpdateMatchDTO(10, 10, startTime, "rules", pitch);

        MatchRepository matchRepository = mock(MatchRepository.class);
        FootballPitchRepository footballPitchRepository = mock(FootballPitchRepository.class);

        when(footballPitchRepository.findById(any())).thenReturn(Optional.ofNullable(pitch));
        when(matchRepository.findById(any())).thenReturn(Optional.ofNullable(match));
        when(matchRepository.save(any(Match.class))).thenReturn(match);

        MatchService matchService = new MatchService(matchRepository, footballPitchRepository);

        MatchIdDTO actualId = matchService.updateMatch(updateMatchDTO, match.getId());

        assertEquals(match.getId(), actualId.id(), "Ids should be the same");
    }

    @Test
    void updateMatch_whenIdIncorrectDetailsCorrect_andThrowsMatchNotFoundException() {
        UpdateMatchDTO updateMatchDTO = new UpdateMatchDTO(10, 10, startTime, "rules", pitch);
        MatchRepository matchRepository = mock(MatchRepository.class);
        FootballPitchRepository footballPitchRepository = mock(FootballPitchRepository.class);

        when(matchRepository.findById(any())).thenReturn(Optional.empty());

        MatchService matchService = new MatchService(matchRepository, footballPitchRepository);

        assertThrows(MatchNotFoundException.class, () ->
                matchService.updateMatch(updateMatchDTO, match.getId()));
    }

    @Test
    void updateMatch_whenPitchNotFound_andFootballPitchNotFoundException() {
        match.setId(1);
        UpdateMatchDTO updateMatchDTO = new UpdateMatchDTO(10, 10, startTime, "rules", pitch);

        MatchRepository matchRepository = mock(MatchRepository.class);
        FootballPitchRepository footballPitchRepository = mock(FootballPitchRepository.class);

        when(matchRepository.findById(any())).thenReturn(Optional.ofNullable(match));
        when(footballPitchRepository.findById(any()))
                .thenThrow(FootballPitchNotFoundException.class);

        MatchService matchService = new MatchService(matchRepository, footballPitchRepository);

        assertThrows(FootballPitchNotFoundException.class,
                () -> matchService.updateMatch(updateMatchDTO, match.getId()));
    }

    @Test
    void updateMatch_whenIdAndUpdateDetailsAreCorrect_AndThrowsDatabaseAccessException() {
        UpdateMatchDTO updateMatchDTO = new UpdateMatchDTO(10, 10, startTime, "rules", pitch);
        MatchRepository matchRepository = mock(MatchRepository.class);
        FootballPitchRepository footballPitchRepository = mock(FootballPitchRepository.class);

        when(matchRepository.findById(any()))
                .thenReturn(Optional.ofNullable(match));
        when(footballPitchRepository.findById(any()))
                .thenReturn(Optional.ofNullable(pitch));
        when(matchRepository.save(any(Match.class)))
                .thenThrow(DatabaseAccessException.class);

        MatchService matchService = new MatchService(matchRepository, footballPitchRepository);

        assertThrows(DatabaseAccessException.class,
                () -> matchService.updateMatch(updateMatchDTO, match.getId()));
    }

    @Test
    void deleteMatch_whenMatchIdValid_thenReturnsDeletedMatchId() {
        match.setId(1);

        MatchRepository matchRepository = mock(MatchRepository.class);
        FootballPitchRepository footballPitchRepository = mock(FootballPitchRepository.class);

        when(matchRepository.findById(any())).thenReturn(Optional.ofNullable(match));

        MatchService matchService = new MatchService(matchRepository, footballPitchRepository);

        assertEquals(match.getId(), matchService.deleteMatch(match.getId()).id(),
                "Ids should be the same");
    }

    @Test
    void deleteMatch_whenIdInvalid_thenThrowsMatchNotFoundException() {
        MatchRepository matchRepository = mock(MatchRepository.class);
        FootballPitchRepository footballPitchRepository = mock(FootballPitchRepository.class);

        when(matchRepository.findById(any())).thenThrow(MatchNotFoundException.class);

        MatchService matchService = new MatchService(matchRepository, footballPitchRepository);

        assertThrows(MatchNotFoundException.class,
                () -> matchService.deleteMatch(match.getId()));
    }

    @Test
    void deleteMatch_whenIdIsValid_thenThrowDatabaseAccessException() {
        match.setId(1);

        MatchRepository matchRepository = mock(MatchRepository.class);
        FootballPitchRepository footballPitchRepository = mock(FootballPitchRepository.class);

        when(matchRepository.findById(any())).thenThrow(DatabaseAccessException.class);

        MatchService matchService = new MatchService(matchRepository, footballPitchRepository);

        assertThrows(DatabaseAccessException.class,
                () -> matchService.deleteMatch(match.getId()));
    }

    @Test
    void getMatchById_whenIdIsValid_thenReturnsMatchDTO() {
        match.setId(1);

        MatchRepository matchRepository = mock(MatchRepository.class);
        FootballPitchRepository footballPitchRepository = mock(FootballPitchRepository.class);

        when(matchRepository.findById(any())).thenReturn(Optional.ofNullable(match));

        MatchService matchService = new MatchService(matchRepository, footballPitchRepository);

        assertEquals(match.getId(), matchService.getMatchById(match.getId()).match_id());
    }

    @Test
    void getMatchById_whenIdIsInvalid_thenThrowsMatchNotFoundException() {
        match.setId(1);

        MatchRepository matchRepository = mock(MatchRepository.class);
        FootballPitchRepository footballPitchRepository = mock(FootballPitchRepository.class);

        when(matchRepository.findById(any())).thenThrow(MatchNotFoundException.class);

        MatchService matchService = new MatchService(matchRepository, footballPitchRepository);

        assertThrows(MatchNotFoundException.class,
                () -> matchService.getMatchById(match.getId()));
    }

    @Test
    void getMatchById_whenIdIsValid_thenThrowDatabaseAccessException() {
        MatchRepository matchRepository = mock(MatchRepository.class);
        FootballPitchRepository footballPitchRepository = mock(FootballPitchRepository.class);

        when(matchRepository.findById(any())).thenThrow(DatabaseAccessException.class);

        MatchService matchService = new MatchService(matchRepository, footballPitchRepository);

        assertThrows(DatabaseAccessException.class,
                () -> matchService.getMatchById(match.getId()));
    }

    @Test void isNumeric_whenItIsNumeric_andReturnsTrue() {

    }
}