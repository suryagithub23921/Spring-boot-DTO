package de.aoksystems.idm.config.service.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.aoksystems.idm.config.repository.CharacterRepository;
import de.aoksystems.idm.config.repository.ClientRepository;
import de.aoksystems.idm.config.repository.entity.Character;
import de.aoksystems.idm.config.repository.entity.Client;
import de.aoksystems.idm.config.service.to.CharacterDto;
import de.aoksystems.idm.config.service.to.ClientDto;

/**
 * Provides only read operations to base data like the clients or the
 * characters.
 */
@Component
public class BaseDataEvaluation {
	
	@Autowired
	private ClientMapper clientMapper;
	
	@Autowired
	private CharacterMapper characterMapper;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private CharacterRepository characterRepository;
	
	public ClientDto findClient(String name) {
		final Client client = this.clientRepository.find(name);
		return this.clientMapper.map(client);
	}
	
	public CharacterDto findCharater(String name) {
		final Character character = this.characterRepository.find(name);
		return this.characterMapper.map(character);
	}
	
	public List<ClientDto> findAllClients() {
		final List<Client> clients = this.clientRepository.findAll();
		return this.clientMapper.mapPersistent(clients);
	}
	
	public List<CharacterDto> findAllCharacters() {
		final List<Character> characters = this.characterRepository.findAll();
		return this.characterMapper.mapPersistent(characters);
	}
}
