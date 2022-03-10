package de.aoksystems.idm.config.service;

import java.util.List;

import de.aoksystems.idm.config.service.to.FileEntryDto;
import de.aoksystems.idm.config.service.to.SimpleEntryDto;

public interface EntryEvaluationService {
	
	List<SimpleEntryDto> findAllSimples(String client);
	
	List<FileEntryDto> findAllFiles(String client);

	FileEntryDto findFile(String client, String key);

	SimpleEntryDto findSimple(String client, String key);
}
