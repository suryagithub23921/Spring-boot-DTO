package de.aoksystems.idm.config.repository;

import java.util.List;

import de.aoksystems.idm.config.repository.entity.Client;

public interface ClientRepository {
	
	List<Client> findAll();
	
	Client find(String name);
}
