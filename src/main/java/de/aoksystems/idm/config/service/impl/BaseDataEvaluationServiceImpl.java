package de.aoksystems.idm.config.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.aoksystems.idm.config.service.BaseDataEvaluationService;
import de.aoksystems.idm.config.service.mapper.BaseDataEvaluation;
import de.aoksystems.idm.config.service.to.CharacterDto;
import de.aoksystems.idm.config.service.to.ClientDto;

@Component
public class BaseDataEvaluationServiceImpl implements BaseDataEvaluationService {

	@Autowired
	private BaseDataEvaluation baseDataEvaluation;
	
	@Override
	public List<ClientDto> findAllClients() {
		return this.baseDataEvaluation.findAllClients();
	}

	@Override
	public List<CharacterDto> findAllCharacters() {
		return this.baseDataEvaluation.findAllCharacters();
	}
}
