package com.codecool.codekickfc.repository;

import com.codecool.codekickfc.repository.model.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    Page<Match> findAllByMatchDateAfterOrderByMatchDate(
            LocalDateTime date,
            Pageable pageable
    );

    Page<Match> findAllByFootballFieldCityEqualsIgnoreCaseAndMatchDateAfterOrderByMatchDate(
            String city,
            LocalDateTime date,
            Pageable pageable
    );

    Page<Match> findAllByFootballFieldDistrictEqualsIgnoreCaseAndMatchDateAfterOrderByMatchDate(
            String district,
            LocalDateTime date,
            Pageable pageable
    );

    List<Match> findByUsersIdAndMatchDateAfter(long userId, LocalDateTime date);
}
