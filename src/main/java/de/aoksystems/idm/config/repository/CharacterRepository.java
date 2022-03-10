package de.aoksystems.idm.config.repository;

import java.util.List;

import de.aoksystems.idm.config.repository.entity.Character;

public interface CharacterRepository {
	List<Character> findAll();
	
	Character find(String name);
}
