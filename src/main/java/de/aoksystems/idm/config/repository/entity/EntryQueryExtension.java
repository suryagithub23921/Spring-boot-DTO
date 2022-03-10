package de.aoksystems.idm.config.repository.entity;

import com.querydsl.core.annotations.QueryDelegate;
import com.querydsl.core.types.dsl.BooleanExpression;

public class EntryQueryExtension {
	
	@QueryDelegate(Entry.class)
	public static BooleanExpression hasKey(QEntry entry, String key) {
		return entry.key.equalsIgnoreCase(key);
	}
	
	@QueryDelegate(Entry.class)
	public static BooleanExpression hasId(QEntry entry, Long entryId) {
		return entry.id.eq(entryId);
	}
}
