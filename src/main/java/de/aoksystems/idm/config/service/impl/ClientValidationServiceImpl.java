package de.aoksystems.idm.config.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.aoksystems.idm.config.service.mapper.BaseDataEvaluation;
import de.aoksystems.idm.techbase.logic.exception.http.ConstraintType;
import de.aoksystems.idm.techbase.logic.exception.http.ConstraintViolation;
import de.aoksystems.idm.techbase.logic.validation.LocalValidator;

@Component
public class ClientValidationServiceImpl implements LocalValidator<String> {
	
	@Autowired
	private BaseDataEvaluation baseDataEvaluation;
	
	@Override
	public List<ConstraintViolation> validate(String client) {
		final List<ConstraintViolation> violations = new ArrayList<>();
		
		if (baseDataEvaluation.findClient(client) == null) {
			violations.add(new ConstraintViolation(ConstraintType.REQUIRED, "Client not resolvable"));
		}
		
		return violations;
	}

}
