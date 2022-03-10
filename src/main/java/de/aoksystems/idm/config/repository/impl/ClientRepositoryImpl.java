package de.aoksystems.idm.config.repository.impl;

import static de.aoksystems.idm.config.repository.entity.QClient.client;

import java.util.List;

import org.springframework.stereotype.Component;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import de.aoksystems.idm.config.repository.ClientRepository;
import de.aoksystems.idm.config.repository.entity.Client;

@Component
public class ClientRepositoryImpl extends GenericRepositoryImpl<Client> implements ClientRepository {

	@Override
	public List<Client> findAll() {
		final JPQLQuery<Client> query = new JPAQueryFactory(getEntityManager()).
				selectFrom(client);
		
		return query.fetch();
	}

	@Override
	public Client find(String name) {
		final JPQLQuery<Client> query = new JPAQueryFactory(getEntityManager()).
				selectFrom(client).
				where(client.hasName(name));
		
		return query.fetchFirst();
	}

}
