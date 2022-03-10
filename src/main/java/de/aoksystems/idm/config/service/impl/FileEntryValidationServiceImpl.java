package de.aoksystems.idm.config.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.aoksystems.idm.config.service.mapper.EntryEvaluation;
import de.aoksystems.idm.config.service.to.FileEntryDto;
import de.aoksystems.idm.techbase.logic.exception.http.ConstraintType;
import de.aoksystems.idm.techbase.logic.exception.http.ConstraintViolation;
import de.aoksystems.idm.techbase.logic.validation.GlobalValidator;

@Component
public class FileEntryValidationServiceImpl extends EntryValidationService<FileEntryDto> implements GlobalValidator<FileEntryDto> {

	@Autowired
	private EntryEvaluation entryEvaluation;
	
	@Override
	protected void checkConstrains(FileEntryDto type, List<ConstraintViolation> messages) {
		super.checkConstrains(type, messages);
		
		if (type.getExtension() != null && type.getExtension().length() > 15) {
			messages.add(new ConstraintViolation(ConstraintType.MAX, getMessagePrefix() + "#Extension"));
		}
		
		if (type.getLocation() != null && type.getLocation().length() > 250) {
			messages.add(new ConstraintViolation(ConstraintType.MAX, getMessagePrefix() + "#Location"));
		}
	}

	@Override
	protected void checkMandatoryAttributes(FileEntryDto type, List<ConstraintViolation> messages) {
		super.checkMandatoryAttributes(type, messages);
		
		if (type.getContent() == null || type.getContent().getBinary() == null || type.getContent().getBinary().length == 0) {
			messages.add(new ConstraintViolation(ConstraintType.REQUIRED, getMessagePrefix() + "#Binary"));
		}
	}
	
	@Override
	public Class<FileEntryDto> isResponsibleFor() {
		return FileEntryDto.class;
	}
	
	@Override
	protected FileEntryDto findByBusinessKey(FileEntryDto type) {
		return this.entryEvaluation.findFile(type.getClient(), type.getKey());
	}

	@Override
	protected String getMessagePrefix() {
		return "FileEntry";
	}
}
