package com.codecool.codekickfc.dao.users;

import com.codecool.codekickfc.dao.model.matches.Match;
import com.codecool.codekickfc.dao.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT m FROM Match m " +
            "JOIN m.users u " +
            "WHERE u.id = :userId AND m.matchDate > CURRENT_TIMESTAMP")
    List<Match> findUpcomingMatchesByUserId(long userId);
}
