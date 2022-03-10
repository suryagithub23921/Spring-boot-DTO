package de.aoksystems.idm.config.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.aoksystems.idm.config.service.EntryMaintenanceService;
import de.aoksystems.idm.config.service.mapper.EntryMaintenance;
import de.aoksystems.idm.config.service.to.FileEntryDto;
import de.aoksystems.idm.config.service.to.SimpleEntryDto;

@Component
public class EntryMaintenanceServiceImpl implements EntryMaintenanceService {
	
	@Autowired
	private EntryMaintenance entryMaintenance;

	@Override
	public void deleteFile(Long entryId) {
		this.entryMaintenance.deleteFile(entryId);
	}

	@Override
	public void deleteSimple(Long entryId) {
		this.entryMaintenance.deleteSimple(entryId);
	}

	@Override
	public SimpleEntryDto save(SimpleEntryDto entry) {
		return this.entryMaintenance.save(entry);
	}

	@Override
	public FileEntryDto save(FileEntryDto entry) {
		return this.entryMaintenance.save(entry);
	}
}
