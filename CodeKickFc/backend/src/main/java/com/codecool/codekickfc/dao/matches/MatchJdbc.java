//package com.codecool.codekickfc.dao.matches;
//
//import com.codecool.codekickfc.controller.dto.matches.MatchDTO;
//import com.codecool.codekickfc.controller.dto.matches.NewMatchDTO;
//import com.codecool.codekickfc.dao.model.database.DatabaseConnection;
//import com.codecool.codekickfc.dao.model.matches.Match;
//import com.codecool.codekickfc.exceptions.DatabaseAccessException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.relational.core.sql.SQL;
//import org.springframework.stereotype.Repository;
//
//import java.sql.*;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@Repository
//public class MatchJdbc implements MatchDAO{
//
//    private DatabaseConnection databaseConnection;
//
//    @Autowired
//    public MatchJdbc(DatabaseConnection databaseConnection) {
//        this.databaseConnection = databaseConnection;
//    }
//
//    @Override
//    public List<Match> getAllMatches() {
//        List<Match> matches = new ArrayList<>();
//
//        String sql = "SELECT * FROM match";
//
//        try (Connection connection = databaseConnection.getConnection();
//             Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery(sql)) {
//
//            while (resultSet.next()) {
//                matches.add(createMatchRecord(resultSet));
//            }
//
//        } catch (SQLException e) {
//        throw new DatabaseAccessException("Encountered error inserting data into the database.", e);
//        }
//        return matches;
//    }
//
//    public Match getMatchById(int matchId) {
//        String sql = "SELECT * FROM match WHERE match_id = ?";
//
//        try (Connection connection = databaseConnection.getConnection();
//             PreparedStatement statement = connection.prepareStatement(sql)) {
//
//            statement.setInt(1, matchId);
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    return createMatchRecord(resultSet);
//                } else {
//                    return null;
//                }
//            }
//        } catch (SQLException e) {
//            throw new DatabaseAccessException("Encountered error inserting data into the database.", e);
//        }
//    }
//
//    public int postMatch(NewMatchDTO matchDTO) {
//        String sql = "INSERT INTO match (" +
//                "subscribed_players_id," +
//                "match_fee_per_player," +
//                "field_id," +
//                "match_date) " +
//                "VALUES (?, ?, ?, ?) " +
//                "RETURNING match_id";
//
//        try (Connection conn = databaseConnection.getConnection();
//            PreparedStatement statement = conn.prepareStatement(sql)) {
//
//            setMatchParameters(statement, matchDTO, conn);
//
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                return resultSet.getInt("match_id");
//            }
//            return 0;
//
//        } catch (SQLException e) {
//            throw new DatabaseAccessException("Encountered error inserting data into the database.", e);
//        }
//    }
//
//    private void setMatchParameters(PreparedStatement statement, NewMatchDTO match, Connection conn) throws SQLException {
//        List<Short> playersList = match.subscribed_players_id();
//        Array playersArray = conn.createArrayOf("SMALLINT", playersList.toArray());
//        statement.setArray(1, playersArray);
//
//        statement.setDouble(2, match.match_fee_per_players());
//        statement.setInt(3, match.field_id());
//
//        LocalDateTime matchDateTime = match.match_date();
//        statement.setTimestamp(4, Timestamp.valueOf(matchDateTime));
//    }
//
//    private Match createMatchRecord(ResultSet rs) throws SQLException {
//        int id = rs.getInt("match_id");
//        Array subscribed_players = rs.getArray("subscribed_players_id");
//        List<Short> subscribedPlayersList = subscribed_players != null
//                ? Arrays.asList((Short[]) subscribed_players.getArray())
//                : List.of();
//        double match_fee = rs.getDouble("match_fee_per_player");
//        int field_id = rs.getInt("field_id");
//        Timestamp match_date = rs.getTimestamp("match_date");
//
//        return new Match(id, subscribedPlayersList, match_fee, field_id, match_date.toLocalDateTime());
//    }
//}
