package com.tokyo.api.service;

import com.tokyo.api.model.Tournament;
import com.tokyo.api.util.CustomErrorType;

import java.util.List;

public interface TournamentService {
	
	List<Tournament> findByModality(String name);
	
	boolean addTournament(Tournament tournament);

	List<Tournament> listAllTournament();

	void validateInputData(Tournament tournament) throws CustomErrorType;
}
