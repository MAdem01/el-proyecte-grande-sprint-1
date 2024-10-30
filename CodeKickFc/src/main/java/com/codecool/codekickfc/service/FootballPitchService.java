package com.codecool.codekickfc.service;

import com.codecool.codekickfc.controller.dto.FootballPitchDTO;
import com.codecool.codekickfc.dao.footballpitch.FootballPitchDAO;
import com.codecool.codekickfc.dao.model.FootballPitch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FootballPitchService {

    private final FootballPitchDAO footballPitchDAO;

    @Autowired
    public FootballPitchService(FootballPitchDAO footballPitchDAO) {
        this.footballPitchDAO = footballPitchDAO;
    }

    public List<FootballPitchDTO> getAllFootballPitches() {
        List<FootballPitch> footballPitches = footballPitchDAO.getAllFootballPitch();
        List<FootballPitchDTO> footballPitchDTOs = new ArrayList<>();

        for (FootballPitch pitch : footballPitches) {
            footballPitchDTOs.add(FootballPitchDTO.fromFootballPitch(pitch));
        }
        return footballPitchDTOs;
    }


}
