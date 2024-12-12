package com.codecool.codekickfc.service;

import com.codecool.codekickfc.dto.matches.MatchDTO;
import com.codecool.codekickfc.exceptions.MatchNotFoundException;
import com.codecool.codekickfc.repository.FootballPitchRepository;
import com.codecool.codekickfc.repository.MatchRepository;
import com.codecool.codekickfc.repository.model.FootballPitch;
import com.codecool.codekickfc.repository.model.Match;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.profiles.active=test")
class MatchServiceTest {

    @Test
    void getAllMatches_whenRepositoryReturnsAllMatchesAsMatchDTO() {
        LocalDateTime startTime = LocalDateTime.now().plusDays(1);
        FootballPitch pitch = new FootballPitch(
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


        Match match = new Match(10, 10.1, startTime, "rules", pitch);

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

        MatchNotFoundException assertThrows = assertThrows(MatchNotFoundException.class, () -> matchService.getAllMatches("", 0));

        assertEquals("Match not found", assertThrows.getMessage());
    }

    @Test
    void getAllMatches_whenAreaIsProvidedAsNumber_AndReturnsMatchesWithFittingArea() {
        LocalDateTime startTime = LocalDateTime.now().plusDays(1);
        FootballPitch pitch = new FootballPitch(
                "",
                "",
                "",
                "",
                "12",
                "",
                "",
                "",
                "",
                0,
                0
        );

        Match match = new Match(10, 10.1, startTime, "rules", pitch);

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
        LocalDateTime startTime = LocalDateTime.now().plusDays(1);
        FootballPitch pitch = new FootballPitch(
                "",
                "",
                "",
                "",
                "XII",
                "",
                "",
                "",
                "",
                0,
                0
        );

        Match match = new Match(10, 10.1, startTime, "rules", pitch);

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
        LocalDateTime startTime = LocalDateTime.now().plusDays(1);
        FootballPitch pitch = new FootballPitch(
                "",
                "",
                "",
                "City",
                "XII",
                "",
                "",
                "",
                "",
                0,
                0
        );

        Match match = new Match(10, 10.1, startTime, "rules", pitch);
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



    @Test
    void createMatch() {
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