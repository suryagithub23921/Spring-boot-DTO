package de.aoksystems.idm.config.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.aoksystems.idm.config.service.BaseDataEvaluationService;
import de.aoksystems.idm.config.service.to.CharacterDto;
import de.aoksystems.idm.config.service.to.ClientDto;

@Transactional
@RestController
@RequestMapping(value = "/api", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class BaseDataController {
	
	@Autowired
	private BaseDataEvaluationService baseDataEvaluationService;

	/**
	 * Provides a list of all clients ​​for which configuration settings can be
	 * managed.
	 * 
	 * @return all clients
	 */
	@GetMapping(value = "/clients")
	public ResponseEntity<List<ClientDto>> retrieveAllClients() {
		final List<ClientDto> applications = this.baseDataEvaluationService.findAllClients();
		return new ResponseEntity<>(applications, HttpStatus.OK);
	}

	/**
	 * Provides a list of all frequencies in which configuration values ​​can be
	 * change (e.g. fixed, changeable).
	 * 
	 * @return all frequencies
	 */
	@GetMapping(value = "/characters")
	public ResponseEntity<List<CharacterDto>> retrieveAllCharacters() {
		final List<CharacterDto> characters = this.baseDataEvaluationService.findAllCharacters();
		return new ResponseEntity<>(characters, HttpStatus.OK);
	}
}
