package de.aoksystems.idm.config.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.aoksystems.idm.config.repository.CharacterRepository;
import de.aoksystems.idm.config.repository.ClientRepository;
import de.aoksystems.idm.config.repository.FileEntryRepository;
import de.aoksystems.idm.config.repository.SimpleEntryRepository;
import de.aoksystems.idm.config.repository.entity.Character;
import de.aoksystems.idm.config.repository.entity.Client;
import de.aoksystems.idm.config.repository.entity.Entry;
import de.aoksystems.idm.config.repository.entity.FileEntry;
import de.aoksystems.idm.config.repository.entity.SimpleEntry;
import de.aoksystems.idm.config.service.to.FileEntryDto;
import de.aoksystems.idm.config.service.to.SimpleEntryDto;

/**
 * Provides maintain operation for file and simple based configuration entries.
 */
@Component
public class EntryMaintenance {
	
	@Autowired
	private FileEntryRepository fileEntryRepository;
	
	@Autowired
	private SimpleEntryRepository simpleEntryRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private CharacterRepository characterRepository;
	
	@Autowired
	private FileEntryMapper fileEntryMapper;
	
	@Autowired
	private SimpleEntryMapper simpleEntryMapper;
	
	public void deleteFile(Long entryId) {
		this.fileEntryRepository.delete(entryId);
	}

	public void deleteSimple(Long entryId) {
		this.simpleEntryRepository.delete(entryId);
	}
	
	public SimpleEntryDto save(SimpleEntryDto entry) {
		SimpleEntry simpleEntry = this.simpleEntryMapper.map(entry);
		
		applyBaseDataChanges(simpleEntry);
		applyValidityDefaulting(simpleEntry);
		
		simpleEntry = this.simpleEntryRepository.save(simpleEntry);
		return this.simpleEntryMapper.map(simpleEntry);
	}
	

	public FileEntryDto save(FileEntryDto entry) {
		FileEntry fileEntry = this.fileEntryMapper.map(entry);
		
		applyBaseDataChanges(fileEntry);
		applyValidityDefaulting(fileEntry);
		
		fileEntry = this.fileEntryRepository.save(fileEntry);
		return this.fileEntryMapper.map(fileEntry);
	}
	
	private void applyBaseDataChanges(Entry entry) {
		final Client client = this.clientRepository.find(entry.getClient().getName());
		final Character character = this.characterRepository.find(entry.getCharacter().getCharactername());
		entry.setClient(client);
		entry.setCharacter(character);
	}
	
	private void applyValidityDefaulting(Entry entry) {
		if (entry.getValid() == null) {
			entry.setValid(Boolean.TRUE);
        }
	}
}
