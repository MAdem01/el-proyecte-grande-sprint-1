package com.codecool.codekickfc.dao.matches;

import com.codecool.codekickfc.dao.model.matches.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    @Query("SELECT m FROM Match m WHERE m.matchDate > CURRENT_TIMESTAMP " +
            "ORDER BY m.matchDate DESC")
    List<Match> findUpcomingMatchesOrderByDateDesc();

}
