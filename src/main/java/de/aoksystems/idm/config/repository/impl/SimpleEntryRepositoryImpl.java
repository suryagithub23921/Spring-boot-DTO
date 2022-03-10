package de.aoksystems.idm.config.repository.impl;

import static de.aoksystems.idm.config.repository.entity.QSimpleEntry.simpleEntry;

import java.util.List;

import org.springframework.stereotype.Component;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQueryFactory;

import de.aoksystems.idm.config.repository.SimpleEntryRepository;
import de.aoksystems.idm.config.repository.entity.SimpleEntry;

@Component
public class SimpleEntryRepositoryImpl extends GenericRepositoryImpl<SimpleEntry> implements SimpleEntryRepository {

	@Override
	public List<SimpleEntry> find(String client) {
		final JPQLQuery<SimpleEntry> query = new JPAQueryFactory(getEntityManager()).
				selectFrom(simpleEntry);
		
		final BooleanBuilder builder = new BooleanBuilder();
		if (client != null) {
			builder.and(simpleEntry.client.hasName(client));
		}
		
		query.where(builder);
		
		return query.fetch();
	}

	@Override
	public SimpleEntry findSimple(String client, String key) {
		final JPQLQuery<SimpleEntry> query = new JPAQueryFactory(getEntityManager()).
				selectFrom(simpleEntry).
				where(simpleEntry.client.hasName(client),
						simpleEntry.hasKey(key));
		
		return query.fetchFirst();
	}

	@Override
	public void delete(Long simpleEntryId) {
		final JPADeleteClause query = new JPAQueryFactory(getEntityManager()).
				delete(simpleEntry).
				where(simpleEntry.hasId(simpleEntryId));
		
		query.execute();
	}

}
