//package com.codecool.codekickfc.service.pitches;
//
//import com.codecool.codekickfc.controller.dto.pitches.*;
//import com.codecool.codekickfc.dao.footballpitch.FootballPitchDAO;
//import com.codecool.codekickfc.dao.model.pitches.FootballPitch;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class FootballPitchService {
//
//    private final FootballPitchDAO footballPitchDAO;
//
//    @Autowired
//    public FootballPitchService(FootballPitchDAO footballPitchDAO) {
//        this.footballPitchDAO = footballPitchDAO;
//    }
//
//    /**
//     * Retrieves all football pitch entries from the repository layer, converts each entry to a {@link FootballPitchDTO},
//     * and provides the resulting list to the controller layer. This method serves as an intermediary between the controller
//     * and repository layers, facilitating the transformation of {@link FootballPitch} entities into a simplified
//     * {@link FootballPitchDTO} format for use in the controller.
//     *
//     * @return a list of {@link FootballPitchDTO} records, each representing a football pitch entry with simplified data
//     * for the controller layer.
//     */
//    public List<FootballPitchDTO> getAllFootballPitches() {
//        List<FootballPitch> footballPitchList = footballPitchDAO.getAllFootballPitch();
//        List<FootballPitchDTO> footballPitchDTOList = new ArrayList<>();
//
//        for (FootballPitch footballPitch : footballPitchList) {
//            footballPitchDTOList.add(FootballPitchDTO.fromFootballPitch(footballPitch));
//        }
//
//        return footballPitchDTOList;
//    }
//
//    public FootballPitchDTO getFootballPitchById(Long fieldId) {
//        return FootballPitchDTO.fromFootballPitch(footballPitchDAO.getFootballPitchById(fieldId));
//    }
//
//    public FootballPitchIdDTO addNewFootballPitch(NewFootballPitchDTO footballPitchDTO) {
//        return new FootballPitchIdDTO(footballPitchDAO.postNewFootballPitch(footballPitchDTO));
//    }
//
//    public FootballPitchIdDTO updateFootballPitch(Long fieldId, NewFootballPitchDTO footballPitchDTO) {
//        return new FootballPitchIdDTO(footballPitchDAO.updateFootballPitch(fieldId, footballPitchDTO));
//    }
//
//    public FootballPitchDeleteDTO deleteFootballPitch(long fieldId) {
//        return new FootballPitchDeleteDTO(footballPitchDAO.deleteFootballPitch(fieldId));
//    }
//}
