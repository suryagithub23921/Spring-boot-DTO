package de.aoksystems.idm.config.service;

import de.aoksystems.idm.config.service.to.FileEntryDto;
import de.aoksystems.idm.config.service.to.SimpleEntryDto;

public interface EntryMaintenanceService {

	void deleteFile(Long entryId);

	void deleteSimple(Long entryId);
	
	SimpleEntryDto save(SimpleEntryDto entry);

	FileEntryDto save(FileEntryDto entry);
}
