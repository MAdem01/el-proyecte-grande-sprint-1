package com.codecool.codekickfc.service;

import com.codecool.codekickfc.dto.matches.MatchDTO;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.profiles.active=test")
class MatchServiceTest {

    @Test
    void getAllMatches_whenRepositoryReturnsAllMatches() {
        LocalDateTime startTime = LocalDateTime.now().plusDays(1);
        FootballPitch pitch = new FootballPitch(
                "pitchName",
                "pitchDesc",
                "type",
                "city",
                "district",
                "postcode",
                "streetName",
                "streetNumbers",
                "imgURL",
                10,
                10
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
        assertEquals(match.getMatchDate(), startTime, "The match date should match the expected start time");
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