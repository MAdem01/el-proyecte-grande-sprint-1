package com.codecool.codekickfc.repository;

import com.codecool.codekickfc.repository.model.FootballPitch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FootballPitchRepository extends JpaRepository<FootballPitch, Long> {
}
