package de.aoksystems.idm.config.repository.entity;

import com.querydsl.core.annotations.QueryDelegate;
import com.querydsl.core.types.dsl.BooleanExpression;

public class ClientQueryExtension {

	@QueryDelegate(Client.class)
	public static BooleanExpression hasName(QClient client, String name) {
		return client.name.equalsIgnoreCase(name);
	}
}
