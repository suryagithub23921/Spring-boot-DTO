package de.aoksystems.idm.config.repository.impl;

import static de.aoksystems.idm.config.repository.entity.QCharacter.character;

import java.util.List;

import org.springframework.stereotype.Component;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import de.aoksystems.idm.config.repository.CharacterRepository;
import de.aoksystems.idm.config.repository.entity.Character;

@Component
public class CharacterRepositoryImpl extends GenericRepositoryImpl<Character> implements CharacterRepository {

	@Override
	public List<Character> findAll() {
		final JPQLQuery<Character> query = new JPAQueryFactory(getEntityManager()).
				selectFrom(character);
		
		return query.fetch();
	}

	@Override
	public Character find(String name) {
		final JPQLQuery<Character> query = new JPAQueryFactory(getEntityManager()).
				selectFrom(character).
				where(character.charactername.equalsIgnoreCase(name));
		
		return query.fetchFirst();
	}

}
