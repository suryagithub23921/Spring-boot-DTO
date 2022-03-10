package de.aoksystems.idm.config.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.aoksystems.idm.config.service.EntryEvaluationService;
import de.aoksystems.idm.config.service.EntryMaintenanceService;
import de.aoksystems.idm.config.service.impl.ClientValidationServiceImpl;
import de.aoksystems.idm.config.service.to.FileEntryDto;
import de.aoksystems.idm.techbase.logic.validation.ValidatorService;

@Transactional
@RestController
@RequestMapping(value = "/api", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class FileEntryController {

	@Autowired
	private EntryEvaluationService entryEvaluationService;
	
	@Autowired
	private EntryMaintenanceService entryMaintenanceService;
	
	@Autowired
	private ValidatorService validator;
	
	/**
	 * Search for all entries that belongs to the provided client. The client is an
	 * optional argument and may be null. In this situation no restriction to the
	 * entries happens.
	 * 
	 * @param client to which the entries belongs
	 * @return the found entries, otherwise an empty list.
	 */
    @GetMapping(value = "/files")
    public ResponseEntity<List<FileEntryDto>> retrieveFiles(@RequestParam(value = "client", required = false) String client) {
    	this.validator.validate(client, ClientValidationServiceImpl.class);
    	
    	final List<FileEntryDto> entries = this.entryEvaluationService.findAllFiles(client);
		return new ResponseEntity<>(entries, HttpStatus.OK);
	}
    
	/**
	 * Search for an entry that is identified by the provided client and key.
	 * 
	 * @param client identifies the insurance health company to that the entry belongs
	 * @param key    identifies the entry
	 * @return the found entry, otherwise null.
	 */
    @GetMapping(value = "/file")
    public ResponseEntity<FileEntryDto> retrieveFile(@RequestParam(value = "client", required = true) String client, @RequestParam(value = "key", required = true) String key) {
    	this.validator.validate(client, ClientValidationServiceImpl.class);
    	
    	final FileEntryDto entry = this.entryEvaluationService.findFile(client, key);
    	return new ResponseEntity<>(entry, HttpStatus.OK);
    }
    
	/**
	 * Deletes the entry that is identified by the provided identification number.
	 * No failure is thrown in case the desired entry does not exists.
	 * 
	 * @param entryId that identifies the entry
	 * @return no content
	 */
	@DeleteMapping(value = "/file/{entryId}")
	public ResponseEntity<Object> deleteFile(@PathVariable(value = "entryId", required = true) Long entryId) {
		this.entryMaintenanceService.deleteFile(entryId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Saves the provided entry if the previous validation checks have been passed.
	 * 
	 * @param entry that should be saved
	 * @return the updated entry or the validation failures
	 */
	@PostMapping(path = "/file")
    public ResponseEntity<FileEntryDto> save(@RequestBody(required = true) FileEntryDto entry) {
    	this.validator.validate(entry);
    	
    	final FileEntryDto fileEntry = this.entryMaintenanceService.save(entry);
    	return new ResponseEntity<>(fileEntry, HttpStatus.OK);
    }
}
