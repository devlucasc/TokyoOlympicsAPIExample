package com.tokyo.api.dao;

import com.tokyo.api.model.Tournament;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class TournamentRowMapper implements RowMapper<Tournament> {

    @Override
    public Tournament mapRow(ResultSet rs, int i) throws SQLException {
        Integer id = rs.getInt("id");
        String modality = rs.getString("modality");
        String location = rs.getString("location");
        Date startDate = rs.getTimestamp("start_date");
        Date endDate = rs.getTimestamp("end_date");
        String country1 = rs.getString("country1");
        String country2 = rs.getString("country2");
        Tournament.StageType stage = Tournament.StageType.fromString(rs.getString("stage"));

        return new Tournament(id, modality, location, startDate, endDate, country1, country2, stage);
    }

}
