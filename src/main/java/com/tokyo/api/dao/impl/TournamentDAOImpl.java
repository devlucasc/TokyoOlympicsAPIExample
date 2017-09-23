package com.tokyo.api.dao.impl;

import com.tokyo.api.dao.TournamentDAO;
import com.tokyo.api.dao.TournamentRowMapper;
import com.tokyo.api.model.Tournament;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TournamentDAOImpl implements TournamentDAO{

    private static final Logger log = LoggerFactory.getLogger(TournamentDAOImpl.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String GET_BY_MODALITY = "SELECT * FROM tournaments WHERE UPPER(modality) = UPPER(:modality)";
    private static final String GET_BY_LOCATION = "SELECT * FROM tournaments WHERE UPPER(location) = UPPER(:location)";
    private static final String GET_BY_MODALITY_AND_LOCATION = "SELECT * FROM tournaments WHERE UPPER(modality) = UPPER(:modality)" +
            " AND UPPER(location) = UPPER(:location)";
    private static final String INSERT_TOURNAMENT = "INSERT INTO tournaments (" +
            "modality, location, start_date, end_date, country1, country2, stage) " +
            "VALUES (:modality, :location, :start_date, :end_date, :country1, :country2, :stage)";
    private static final String GET_ALL = "SELECT * FROM tournaments";

    @Autowired
    public TournamentDAOImpl(@Qualifier(value = "jdbcDBCfg") NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Tournament> listAll() {
        return jdbcTemplate.query(GET_ALL, new TournamentRowMapper());
    }

    public List<Tournament> getByModality(String modality) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("modality", modality);
        return jdbcTemplate.query(GET_BY_MODALITY, params, new TournamentRowMapper());
    }

    public List<Tournament> getByLocation(String location) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("location", location);
        return jdbcTemplate.query(GET_BY_LOCATION, params, new TournamentRowMapper());
    }

    public List<Tournament> getByModalityAndLocation(String modality, String location) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("modality", modality);
        params.addValue("location", location);
        return jdbcTemplate.query(GET_BY_MODALITY_AND_LOCATION, params, new TournamentRowMapper());
    }

    public boolean insertTournament(Tournament tournament) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("modality", tournament.getModality());
        params.addValue("location", tournament.getLocation());
        params.addValue("country1", tournament.getCountry1());
        params.addValue("country2", tournament.getCountry2());
        params.addValue("start_date", tournament.getStartDate());
        params.addValue("end_date", tournament.getEndDate());
        params.addValue("stage", tournament.getStage().getValue());
        return jdbcTemplate.update(INSERT_TOURNAMENT, params) == 1;
    }

}
