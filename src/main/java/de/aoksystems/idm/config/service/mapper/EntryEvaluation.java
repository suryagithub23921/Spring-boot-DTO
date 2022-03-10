package de.aoksystems.idm.config.service.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.aoksystems.idm.config.repository.FileEntryRepository;
import de.aoksystems.idm.config.repository.SimpleEntryRepository;
import de.aoksystems.idm.config.repository.entity.FileEntry;
import de.aoksystems.idm.config.repository.entity.SimpleEntry;
import de.aoksystems.idm.config.service.to.FileEntryDto;
import de.aoksystems.idm.config.service.to.SimpleEntryDto;

/**
 * Provides read only operation for file and simple based configuration entries.
 */
@Component
public class EntryEvaluation {
	
	@Autowired
	private SimpleEntryRepository simpleEntryRepository;
	
	@Autowired
	private FileEntryRepository fileEntryRepository;

	@Autowired
	private SimpleEntryMapper simpleEntryMapper;
	
	@Autowired
	private FileEntryMapper fileEntryMapper;
	
	
	public List<SimpleEntryDto> findSimple(String client) {
		final List<SimpleEntry> entries = this.simpleEntryRepository.find(client);
		return this.simpleEntryMapper.mapPersistent(entries);
	}
	
	public List<FileEntryDto> findFile(String client) {
		final List<FileEntry> entries = this.fileEntryRepository.find(client);
		return this.fileEntryMapper.mapPersistent(entries);
	}
	
	public FileEntryDto findFile(String client, String key) {
		final FileEntry entry = this.fileEntryRepository.findFile(client, key);
		return this.fileEntryMapper.map(entry);
	}

	public SimpleEntryDto findSimple(String client, String key) {
		final SimpleEntry entry = this.simpleEntryRepository.findSimple(client, key);
		return this.simpleEntryMapper.map(entry);
	}
}
