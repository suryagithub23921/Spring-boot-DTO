package de.aoksystems.idm.config.service;

import java.util.List;

import de.aoksystems.idm.config.service.to.CharacterDto;
import de.aoksystems.idm.config.service.to.ClientDto;

public interface BaseDataEvaluationService {
	
	List<ClientDto> findAllClients();
	
	List<CharacterDto> findAllCharacters();
}
