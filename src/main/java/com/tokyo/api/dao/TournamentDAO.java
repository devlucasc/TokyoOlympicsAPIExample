package com.tokyo.api.dao;

import com.tokyo.api.model.Tournament;

import java.util.List;

public interface TournamentDAO {

    List<Tournament> listAll();

    List<Tournament> getByModality(String modality);

    List<Tournament> getByLocation(String location);

    List<Tournament> getByModalityAndLocation(String modality, String location);

    boolean insertTournament(Tournament tournament);
}
