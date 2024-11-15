package com.codecool.codekickfc.service.pitches;

import com.codecool.codekickfc.controller.dto.pitches.FootballPitchDTO;
import com.codecool.codekickfc.controller.dto.pitches.FootballPitchIdDTO;
import com.codecool.codekickfc.controller.dto.pitches.NewFootballPitchDTO;
import com.codecool.codekickfc.controller.dto.pitches.UpdateFootballPitchDTO;
import com.codecool.codekickfc.dao.footballpitch.FootballPitchRepository;
import com.codecool.codekickfc.dao.model.pitches.FootballPitch;
import com.codecool.codekickfc.exceptions.DatabaseAccessException;
import com.codecool.codekickfc.exceptions.FootballPitchNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FootballPitchService {

    private final FootballPitchRepository footballPitchRepository;

    @Autowired
    public FootballPitchService(FootballPitchRepository footballPitchRepository) {
        this.footballPitchRepository = footballPitchRepository;
    }

    /**
     * Get all information from pitches through the database, and then it filters the
     * unnecessary details by converting the objects into DTO-s.
     *
     * @return A list of pitch data transfer object that includes pitch's id, name, description,
     * type, address.
     * @throws FootballPitchNotFoundException In case of no pitch in database.
     */
    public List<FootballPitchDTO> getAllFootballPitches() {
        List<FootballPitch> pitches = footballPitchRepository.findAll();

        if (pitches.isEmpty()) {
            throw new FootballPitchNotFoundException();
        }

        return pitches.stream().
                map(FootballPitchDTO::fromFootballPitch).
                collect(Collectors.toList());
    }

    /**
     * Creates a new {@link FootballPitch} model from the provided {@link NewFootballPitchDTO}
     * request body, and then it filters the unnecessary details by extracting only the ID
     * from the saved data.
     *
     * @param newFootballPitchDTO The request body based on the client inputs.
     * @return ID of the created FootballPitch model
     * @throws DatabaseAccessException In case of connection failure.
     */
    public FootballPitchIdDTO createFootballPitch(NewFootballPitchDTO newFootballPitchDTO) {
        FootballPitch newFootballPitch = new FootballPitch(
                newFootballPitchDTO.pitchName(),
                newFootballPitchDTO.description(),
                newFootballPitchDTO.pitchType(),
                newFootballPitchDTO.city(),
                newFootballPitchDTO.district(),
                newFootballPitchDTO.postcode(),
                newFootballPitchDTO.streetName(),
                newFootballPitchDTO.streetNumber(),
                newFootballPitchDTO.imgUrl(),
                newFootballPitchDTO.longitude(),
                newFootballPitchDTO.latitude()
        );
        try {
            return new FootballPitchIdDTO(footballPitchRepository.save(newFootballPitch).getId());
        } catch (DataAccessException e) {
            throw new DatabaseAccessException(e);
        }
    }

    /**
     * Get all information from the footballPitch found in the database by its ID,
     * update its fields,and then it filters the unnecessary details by extracting only
     * the ID from the saved data.
     *
     * @param updateFootballPitchDetails The request body based on the client inputs.
     * @param footballPitchId            ID of the footballPitch client wants to update.
     * @return ID of the updated FootballPitch model.
     * @throws FootballPitchNotFoundException In case of footballPitch doesn't exist.
     * @throws DatabaseAccessException        In case of connection failure.
     */
    public FootballPitchIdDTO updateFootballPitch(
            UpdateFootballPitchDTO updateFootballPitchDetails,
            long footballPitchId
    ) {
        FootballPitch updatedFootballPitch = footballPitchRepository.findById(footballPitchId).
                orElseThrow(FootballPitchNotFoundException::new);

        updatedFootballPitch.setPitchName(updateFootballPitchDetails.pitchName());
        updatedFootballPitch.setPitchDescription(updateFootballPitchDetails.description());
        updatedFootballPitch.setPitchType(updateFootballPitchDetails.pitchType());
        updatedFootballPitch.setCity(updateFootballPitchDetails.city());
        updatedFootballPitch.setDistrict(updateFootballPitchDetails.district());
        updatedFootballPitch.setPostcode(updateFootballPitchDetails.postcode());
        updatedFootballPitch.setStreetName(updateFootballPitchDetails.streetName());
        updatedFootballPitch.setStreetNumber(updateFootballPitchDetails.streetNumber());
        updatedFootballPitch.setImgUrl(updateFootballPitchDetails.imgUrl());
        updatedFootballPitch.setLongitude(updatedFootballPitch.getLongitude());
        updatedFootballPitch.setLatitude(updatedFootballPitch.getLatitude());

        try {
            return new FootballPitchIdDTO(footballPitchRepository.save(updatedFootballPitch).getId());
        } catch (DataAccessException e) {
            throw new DatabaseAccessException(e);
        }
    }

    /**
     * Get all information from the footballPitch found in the database by its ID,
     * and then it filters the unnecessary details by converting the object into DTO.
     *
     * @param footballPitchId ID of the footballPitch client wants to get.
     * @return FootballPitch data transfer object that includes footballPitch's id, name,
     * type, address.
     * @throws FootballPitchNotFoundException In case of footballPitch doesn't exist.
     */
    public FootballPitchDTO getFootballPitchById(long footballPitchId) {
        FootballPitch footballPitch = footballPitchRepository.findById(footballPitchId).
                orElseThrow(FootballPitchNotFoundException::new);

        return FootballPitchDTO.fromFootballPitch(footballPitch);
    }
}
