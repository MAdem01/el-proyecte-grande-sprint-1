package com.codecool.codekickfc.dao.footballpitch;

import com.codecool.codekickfc.dao.model.pitches.FootballPitch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FootballPitchRepository extends JpaRepository<FootballPitch, Long> {
}
