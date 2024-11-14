package com.codecool.codekickfc.dao.matches;

import com.codecool.codekickfc.dao.model.matches.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    @Query("SELECT m FROM Match m WHERE m.matchDate > CURRENT_TIMESTAMP " +
            "ORDER BY m.matchDate ASC")
    Page<Match> findUpcomingMatchesOrderByDateAsc(Pageable pageable);

    @Query("SELECT m FROM Match m JOIN m.footballField f " +
            "WHERE m.matchDate > CURRENT_TIMESTAMP AND f.city ILIKE %:city%" +
            " ORDER BY m.matchDate ASC")
    Page<Match> findUpcomingMatchesOrderByDateAscAndByCity(String city, Pageable pageable);

    @Query("SELECT m FROM Match m JOIN m.footballField f " +
            "WHERE m.matchDate > CURRENT_TIMESTAMP AND f.district = :district" +
            " ORDER BY m.matchDate ASC")
    Page<Match> findUpcomingMatchesOrderByDateAscAndByDistrict(String district, Pageable pageable);
}
