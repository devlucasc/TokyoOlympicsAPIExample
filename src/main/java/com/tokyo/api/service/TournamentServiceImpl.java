package com.tokyo.api.service;

import com.tokyo.api.dao.TournamentDAO;
import com.tokyo.api.model.Tournament;
import com.tokyo.api.util.CustomErrorType;
import com.tokyo.api.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service("tournamentService")
public class TournamentServiceImpl implements TournamentService {

    private final TournamentDAO tournamentDAO;

    private static final Logger logger = LoggerFactory.getLogger(TournamentService.class);

    @Autowired
    public TournamentServiceImpl(TournamentDAO tournamentDAO) {
        this.tournamentDAO = tournamentDAO;
    }

    public List<Tournament> listAllTournament() {
		return tournamentDAO.listAll();
	}

    public void validateInputData(Tournament tournament) throws CustomErrorType {
        if(tournament.getModality() != null
                && tournament.getLocation() != null)

        if (this.checkLocationIsBusy(tournament)) {
            logger.error("Unable to create. Already exists a tournament with the same modality and same schedule in this " +
                    "location: {}", tournament.toString());
            throw new CustomErrorType("Unable to create. Already exists a tournament " +
                            "with the same modality and same schedule in this location.");
        }
        if (!this.checkTournamentMinTime(tournament)) {
            logger.error("Unable to create. Tournament requires at least 30 minutes: {}", tournament.toString());
            throw new CustomErrorType("Unable to create. Tournament requires at least 30 minutes.");
        }
        if (this.checkTournamentLimit(tournament)) {
            logger.error("Unable to create. Maximum limit of the olympic organization for this " +
                    "day and local was reached: {}", tournament.toString());
            throw new CustomErrorType("Unable to create. Maximum limit of the olympic organization " +
                            "for this day and local was reached.");
        }
        if (!(tournament.getStage() == Tournament.StageType.SEMIFINAL
                || tournament.getStage() == Tournament.StageType.FINAL)
                && tournament.getCountry1().equalsIgnoreCase(tournament.getCountry2())) {
            logger.error("Unable to create. Country1 and Country2 must be different: {}", tournament.toString());
            throw new CustomErrorType("Unable to create. Country1 and Country2 must be different.");
        }
    }

    /**
     * Verify if location is busy in the same time for the same modality
     * @param tournament Tournament to check
     * @return true if location is busy or false if else
     */
    private boolean checkLocationIsBusy(Tournament tournament) {
        List<Tournament> tournaments = tournamentDAO.getByModalityAndLocation(tournament.getModality(),
                tournament.getLocation());
        for (Tournament tournament1: tournaments) {
            if(Utils.dateOverlaps(tournament1.getStartDate(), tournament1.getEndDate(),
                                    tournament.getStartDate(), tournament.getEndDate())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verify if tournament has at least 30 minutes
     * @param tournament Tournament to verify
     * @return true if equals or more than 30 minutes or false if else
     */
    private boolean checkTournamentMinTime(Tournament tournament) {
        long diff = tournament.getEndDate().getTime() - tournament.getStartDate().getTime();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        return minutes >= 30;
    }

    /**
     * Count number of tournaments in the same location and same day
     * @param tournament tournament to be verified
     * @return true if exists 4 tournaments or false if less than 4 tournaments
     */
    private boolean checkTournamentLimit(Tournament tournament) {
	    int count = 0;
	    Date startDay = Utils.dateTrunc(tournament.getStartDate());
	    Date endDay = Utils.dateTrunc(tournament.getEndDate());
	    List<Tournament> tournaments = tournamentDAO.getByLocation(tournament.getLocation());
	    for (Tournament tournament1: tournaments) {
	        Date startDay2 = Utils.dateTrunc(tournament1.getStartDate());
	        Date endDay2 = Utils.dateTrunc(tournament1.getEndDate());
	        if((startDay.equals(startDay2) || endDay.equals(endDay2))) {
	            count++;
            }
        }
        return count == 4;
    }

    /**
     * Return a list of tournaments with the same modality
     * @param modality modality to query
     * @return list of tournaments
     */
	public List<Tournament> findByModality(String modality) {
		return tournamentDAO.getByModality(modality);
	}

    /**
     * Add new tournament
     * @param tournament tournament to add
     */
	public boolean addTournament(Tournament tournament) {
		return tournamentDAO.insertTournament(tournament);
	}

}
