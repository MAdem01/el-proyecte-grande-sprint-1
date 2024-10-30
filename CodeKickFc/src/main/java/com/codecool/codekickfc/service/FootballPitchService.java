package com.codecool.codekickfc.service;

import com.codecool.codekickfc.dao.footballpitch.FootballPitchDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FootballPitchService {

    private final FootballPitchDAO footballPitchDAO;

    @Autowired
    public FootballPitchService(FootballPitchDAO footballPitchDAO) {
        this.footballPitchDAO = footballPitchDAO;
    }


}
