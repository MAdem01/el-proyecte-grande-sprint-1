package com.codecool.codekickfc.service;

import com.codecool.codekickfc.dto.pitches.FootballPitchDTO;
import com.codecool.codekickfc.dto.pitches.FootballPitchIdDTO;
import com.codecool.codekickfc.dto.pitches.NewFootballPitchDTO;
import com.codecool.codekickfc.dto.pitches.UpdateFootballPitchDTO;
import com.codecool.codekickfc.exceptions.DatabaseAccessException;
import com.codecool.codekickfc.exceptions.FootballPitchNotFoundException;
import com.codecool.codekickfc.repository.FootballPitchRepository;
import com.codecool.codekickfc.repository.model.FootballPitch;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FootballPitchServiceTest {

    @Test
    void getAllFootballPitches_whenRepositoryReturns1Pitch_thenReturnListOfPitchDTO() {
        FootballPitchRepository footballPitchRepository = mock(FootballPitchRepository.class);

        FootballPitch pitch = new FootballPitch(
                "pitchName",
                "pitchDescription",
                "pitchType",
                "city",
                "district",
                "postcode",
                "streetName",
                "streetNumber",
                "imgUrl",
                1.5,
                1.2
        );

        when(footballPitchRepository.findAll()).thenReturn(List.of(pitch));

        FootballPitchService footballPitchService =
                new FootballPitchService(footballPitchRepository);

        List<FootballPitchDTO> pitchDTOList = footballPitchService.getAllFootballPitches();

        assertEquals(1, pitchDTOList.size());
        assertEquals("pitchName", pitchDTOList.get(0).name());
    }

    @Test
    void getAllFootballPitches_whenRepositoryReturns0Pitch_thenThrowsFootballPitchNotFoundException() {
        FootballPitchRepository footballPitchRepository = mock(FootballPitchRepository.class);

        when(footballPitchRepository.findAll()).thenReturn(Collections.emptyList());

        FootballPitchService footballPitchService =
                new FootballPitchService(footballPitchRepository);

        assertThrows(FootballPitchNotFoundException.class, footballPitchService::getAllFootballPitches);
    }

    @Test
    void createFootballPitch_whenRequestBodyValid_thenReturnFootballPitchIdDTO() {
        FootballPitchRepository footballPitchRepository = mock(FootballPitchRepository.class);

        NewFootballPitchDTO newPitch = new NewFootballPitchDTO(
                "pitchName",
                "pitchDescription",
                "pitchType",
                "city",
                "district",
                "postcode",
                "streetName",
                "streetNumber",
                "imgUrl",
                1.5,
                1.2
        );

        FootballPitch pitch = new FootballPitch(
                "pitchName",
                "pitchDescription",
                "pitchType",
                "city",
                "district",
                "postcode",
                "streetName",
                "streetNumber",
                "imgUrl",
                1.5,
                1.2
        );
        pitch.setId(1);

        when(footballPitchRepository.save(any(FootballPitch.class))).thenReturn(pitch);

        FootballPitchService footballPitchService =
                new FootballPitchService(footballPitchRepository);

        FootballPitchIdDTO result = footballPitchService.createFootballPitch(newPitch);

        assertEquals(pitch.getId(), result.id());
    }

    @Test
    void createFootballPitch_whenRequestBodyValid_thenThrowsDatabaseAccessException() {
        FootballPitchRepository footballPitchRepository = mock(FootballPitchRepository.class);

        NewFootballPitchDTO newPitch = new NewFootballPitchDTO(
                "pitchName",
                "pitchDescription",
                "pitchType",
                "city",
                "district",
                "postcode",
                "streetName",
                "streetNumber",
                "imgUrl",
                1.5,
                1.2
        );

        when(footballPitchRepository.save(any(FootballPitch.class)))
                .thenThrow(new DataAccessException("Database error") {
                });

        FootballPitchService footballPitchService =
                new FootballPitchService(footballPitchRepository);

        assertThrows(DatabaseAccessException.class, () ->
                footballPitchService.createFootballPitch(newPitch));
    }

    @Test
    void updateFootballPitch_whenRequestBodyAndIdValid_thenReturnFootballPitchIdDTO() {
        FootballPitchRepository footballPitchRepository = mock(FootballPitchRepository.class);

        UpdateFootballPitchDTO update = new UpdateFootballPitchDTO(
                "updatedPitchName",
                "pitchDescription",
                "pitchType",
                "city",
                "district",
                "postcode",
                "streetName",
                "streetNumber",
                "imgUrl",
                1.5,
                1.2
        );

        FootballPitch pitch = new FootballPitch(
                "pitchName",
                "pitchDescription",
                "pitchType",
                "city",
                "district",
                "postcode",
                "streetName",
                "streetNumber",
                "imgUrl",
                1.5,
                1.2
        );
        pitch.setId(1);

        when(footballPitchRepository.findById(1L)).thenReturn(Optional.of(pitch));
        when(footballPitchRepository.save(any(FootballPitch.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        FootballPitchService footballPitchService =
                new FootballPitchService(footballPitchRepository);

        FootballPitchIdDTO result =
                footballPitchService.updateFootballPitch(update, 1);

        assertEquals(pitch.getId(), result.id());
        assertEquals("updatedPitchName", pitch.getPitchName());
    }

    @Test
    void updateFootballPitch_whenRequestBodyValidButIdDont_thenThrowsFootballPitchNotFoundException() {
        FootballPitchRepository footballPitchRepository = mock(FootballPitchRepository.class);

        UpdateFootballPitchDTO update = new UpdateFootballPitchDTO(
                "updatedPitchName",
                "pitchDescription",
                "pitchType",
                "city",
                "district",
                "postcode",
                "streetName",
                "streetNumber",
                "imgUrl",
                1.5,
                1.2
        );

        when(footballPitchRepository.findById(1L)).thenReturn(Optional.empty());

        FootballPitchService footballPitchService =
                new FootballPitchService(footballPitchRepository);

        assertThrows(FootballPitchNotFoundException.class, () ->
                footballPitchService.updateFootballPitch(update, 1L));
    }

    @Test
    void updateFootballPitch_whenRequestBodyAndIdValid_thenThrowsDatabaseAccessException() {
        FootballPitchRepository footballPitchRepository = mock(FootballPitchRepository.class);

        UpdateFootballPitchDTO update = new UpdateFootballPitchDTO(
                "updatedPitchName",
                "pitchDescription",
                "pitchType",
                "city",
                "district",
                "postcode",
                "streetName",
                "streetNumber",
                "imgUrl",
                1.5,
                1.2
        );

        FootballPitch pitch = new FootballPitch(
                "pitchName",
                "pitchDescription",
                "pitchType",
                "city",
                "district",
                "postcode",
                "streetName",
                "streetNumber",
                "imgUrl",
                1.5,
                1.2
        );
        pitch.setId(1);

        FootballPitchService footballPitchService =
                new FootballPitchService(footballPitchRepository);

        when(footballPitchRepository.findById(1L)).thenReturn(Optional.of(pitch));
        when(footballPitchRepository.save(any(FootballPitch.class)))
                .thenThrow(new DataAccessException("Database error") {
                });

        assertThrows(DatabaseAccessException.class, () ->
                footballPitchService.updateFootballPitch(update, 1));
    }

    @Test
    void getFootballPitchById_whenRequestIdValid_thenReturnsFootballPitchDTO() {
        FootballPitchRepository footballPitchRepository = mock(FootballPitchRepository.class);

        FootballPitch pitch = new FootballPitch(
                "pitchName",
                "pitchDescription",
                "pitchType",
                "city",
                "district",
                "postcode",
                "streetName",
                "streetNumber",
                "imgUrl",
                1.5,
                1.2
        );
        pitch.setId(1);

        FootballPitchService footballPitchService =
                new FootballPitchService(footballPitchRepository);

        when(footballPitchRepository.findById(1L)).thenReturn(Optional.of(pitch));

        FootballPitchDTO result = footballPitchService.getFootballPitchById(1);

        assertEquals(pitch.getId(), result.id());
        assertEquals("pitchName", result.name());
    }

    @Test
    void getFootballPitchById_whenRequestIdInvalid_thenThrowsFootballPitchNotFoundException() {
        FootballPitchRepository footballPitchRepository = mock(FootballPitchRepository.class);

        FootballPitchService footballPitchService =
                new FootballPitchService(footballPitchRepository);

        when(footballPitchRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(FootballPitchNotFoundException.class, () ->
                footballPitchService.getFootballPitchById(1));
    }
}
