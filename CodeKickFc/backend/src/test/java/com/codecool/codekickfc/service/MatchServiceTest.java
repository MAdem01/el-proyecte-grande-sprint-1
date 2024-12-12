package com.codecool.codekickfc.service;

import com.codecool.codekickfc.dto.matches.MatchDTO;
import com.codecool.codekickfc.dto.matches.MatchIdDTO;
import com.codecool.codekickfc.dto.matches.NewMatchDTO;
import com.codecool.codekickfc.exceptions.DatabaseAccessException;
import com.codecool.codekickfc.exceptions.MatchNotFoundException;
import com.codecool.codekickfc.repository.FootballPitchRepository;
import com.codecool.codekickfc.repository.MatchRepository;
import com.codecool.codekickfc.repository.model.FootballPitch;
import com.codecool.codekickfc.repository.model.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
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
        when(matchRepository.save(any(Match.class))).thenThrow(new DataAccessException("Failed to access Data") {});

        MatchService matchService = new MatchService(matchRepository, footballPitchRepository);

        assertThrows(DatabaseAccessException.class, () -> matchService.createMatch(newMatchDTO));

    }

    @Test
    void updateMatch() {
    }

    @Test
    void deleteMatch() {
    }

    @Test
    void getMatchById() {
    }
}