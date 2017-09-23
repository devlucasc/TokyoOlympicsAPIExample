package com.tokyo.api.controller;

import com.tokyo.api.exception.CustomError;
import com.tokyo.api.model.Tournament;
import com.tokyo.api.service.TournamentService;
import com.tokyo.api.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TournamentController {

	private static final Logger logger = LoggerFactory.getLogger(TournamentController.class);

	private final TournamentService tournamentService;

    @Autowired
    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @RequestMapping(value = "/tournaments/", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> getAllTournaments() {
        logger.info("Fetching All Tournaments");
        List<Tournament> tournaments = tournamentService.listAllTournament();
        if (tournaments != null && !tournaments.isEmpty()) {
            tournaments.sort(Comparator.comparing(Tournament::getStartDate));
            return new ResponseEntity<>(tournaments, HttpStatus.OK);
        }
        logger.error("Tournaments not found.");
        return new ResponseEntity<>(new CustomError("Tournaments not found"), HttpStatus.NOT_FOUND);
    }

	@RequestMapping(value = "/tournaments/{modality}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getTournamentByModality(@PathVariable("modality") String modality) {
		logger.info("Fetching Tournament with modality {}", modality);
		List<Tournament> tournaments = tournamentService.findByModality(modality);
		if (tournaments != null && !tournaments.isEmpty()) {
            tournaments.sort(Comparator.comparing(Tournament::getStartDate));
            return new ResponseEntity<>(tournaments, HttpStatus.OK);
		}
        logger.error("Tournament with modality {} not found.", modality);
        return new ResponseEntity<>(new CustomError("Tournaments with modality " + modality
                + " not found"), HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/tournament/", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> createTournament(@RequestBody @Valid Tournament tournament) {
		logger.info("Creating Tournament : {}", tournament);

		try {
            tournamentService.validateInputData(tournament);
        } catch (CustomErrorType c) {
		    return new ResponseEntity<>(new CustomError(c.getErrorMessage()), HttpStatus.CONFLICT);
        }
		tournamentService.addTournament(tournament);

		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

}