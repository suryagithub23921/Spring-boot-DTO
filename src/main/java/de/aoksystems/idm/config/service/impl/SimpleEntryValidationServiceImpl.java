package de.aoksystems.idm.config.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.aoksystems.idm.config.service.mapper.EntryEvaluation;
import de.aoksystems.idm.config.service.to.SimpleEntryDto;
import de.aoksystems.idm.techbase.logic.exception.http.ConstraintType;
import de.aoksystems.idm.techbase.logic.exception.http.ConstraintViolation;
import de.aoksystems.idm.techbase.logic.validation.GlobalValidator;

@Component
public class SimpleEntryValidationServiceImpl extends EntryValidationService<SimpleEntryDto> implements GlobalValidator<SimpleEntryDto> {

	@Autowired
	private EntryEvaluation entryEvaluation;
	
	@Override
	protected void checkConstrains(SimpleEntryDto type, List<ConstraintViolation> messages) {
		super.checkConstrains(type, messages);
		
		if (type.getValue() != null && type.getValue().length() > 75) {
			messages.add(new ConstraintViolation(ConstraintType.MAX, getMessagePrefix() + "#Value"));
		}
	}
	
	@Override
	protected void checkMandatoryAttributes(SimpleEntryDto type, List<ConstraintViolation> messages) {
		super.checkMandatoryAttributes(type, messages);
		
		if (type.getValue() == null || type.getValue().isEmpty()) {
			messages.add(new ConstraintViolation(ConstraintType.REQUIRED, getMessagePrefix() + "#Value"));
		}
	}

	@Override
	public Class<SimpleEntryDto> isResponsibleFor() {
		return SimpleEntryDto.class;
	}

	@Override
	protected SimpleEntryDto findByBusinessKey(SimpleEntryDto type) {
		return this.entryEvaluation.findSimple(type.getClient(), type.getKey());
	}

	@Override
	protected String getMessagePrefix() {
		return "SimpleEntry";
	}

}
