package de.aoksystems.idm.config.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.aoksystems.idm.config.service.EntryEvaluationService;
import de.aoksystems.idm.config.service.mapper.EntryEvaluation;
import de.aoksystems.idm.config.service.to.FileEntryDto;
import de.aoksystems.idm.config.service.to.SimpleEntryDto;

@Component
public class EntryEvaluationServiceImpl implements EntryEvaluationService {
	
	@Autowired
	private EntryEvaluation entryEvaluation;

	@Override
	public List<SimpleEntryDto> findAllSimples(String client) {
		return this.entryEvaluation.findSimple(client);
	}

	@Override
	public List<FileEntryDto> findAllFiles(String client) {
		return this.entryEvaluation.findFile(client);
	}

	@Override
	public FileEntryDto findFile(String client, String key) {
		return this.entryEvaluation.findFile(client, key);
	}

	@Override
	public SimpleEntryDto findSimple(String client, String key) {
		return this.entryEvaluation.findSimple(client, key);
	}
}
