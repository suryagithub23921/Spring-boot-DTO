package de.aoksystems.idm.config.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.aoksystems.idm.config.service.mapper.BaseDataEvaluation;
import de.aoksystems.idm.config.service.to.CharacterDto;
import de.aoksystems.idm.config.service.to.EntryDto;
import de.aoksystems.idm.techbase.logic.exception.http.ConstraintType;
import de.aoksystems.idm.techbase.logic.exception.http.ConstraintViolation;

abstract class EntryValidationService<T extends EntryDto> {
	
	@Autowired
	private BaseDataEvaluation baseDataEvaluation;
	
	@Autowired
	private ClientValidationServiceImpl clientValidation;
	

	public List<ConstraintViolation> validate(T type) {
		final List<ConstraintViolation> messages = new ArrayList<>();
		
		checkMandatoryAttributes(type, messages);
		
		checkReferences(type, messages);
		
		checkBusinessKey(type, messages);
		
		checkConstrains(type, messages);
		
		return messages;
	}
	
	protected void checkMandatoryAttributes(T type, List<ConstraintViolation> messages) {
		if (type.getClient() == null || type.getClient().isEmpty()) {
			messages.add(new ConstraintViolation(ConstraintType.REQUIRED, getMessagePrefix() + "#Client"));
		}
		
		if (type.getCharacter() == null || type.getCharacter().isEmpty()) {
			messages.add(new ConstraintViolation(ConstraintType.REQUIRED, getMessagePrefix() + "#Character"));
		}
		
		if (type.getKey() == null || type.getKey().isEmpty()) {
			messages.add(new ConstraintViolation(ConstraintType.REQUIRED, getMessagePrefix() + "#Key"));
		}
		
		if (type.getValid() == null && type.getId() != null) {
			messages.add(new ConstraintViolation(ConstraintType.REQUIRED, getMessagePrefix() + "#Valid"));
		}
	}
	
	protected void checkReferences(T type, List<ConstraintViolation> messages) {
		if (type.getClient() != null) {
			messages.addAll(this.clientValidation.validate(type.getClient()));
		}
		
		if (type.getCharacter() != null) {
			final CharacterDto characterReference = this.baseDataEvaluation.findCharater(type.getCharacter());
			if (characterReference == null) {
				messages.add(new ConstraintViolation(ConstraintType.PATTERN, getMessagePrefix() + "#Character not resolvable"));
			}
		}
	}
	
	protected void checkConstrains(T type, List<ConstraintViolation> messages) {
		if (type.getKey() != null && type.getKey().length() > 50) {
			messages.add(new ConstraintViolation(ConstraintType.MAX, getMessagePrefix() + "#Key"));
		}
	}
	
	protected void checkBusinessKey(T type, List<ConstraintViolation> messages) {
		if (type.getClient() != null && !type.getClient().isEmpty() && type.getKey() != null && !type.getKey().isEmpty()) {
			final T foundEntry = findByBusinessKey(type);
			
			if (foundEntry != null && (type.getId() == null || type.getId() != null && !type.getId().equals(foundEntry.getId()))) {
				messages.add(new ConstraintViolation(ConstraintType.UNIQUE, getMessagePrefix() + "#Business Key"));
			}
		}
	}
	
	protected abstract T findByBusinessKey(T type);
	
	protected abstract String getMessagePrefix();
}
