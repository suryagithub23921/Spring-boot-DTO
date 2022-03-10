package de.aoksystems.idm.config.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import de.aoksystems.idm.config.repository.entity.Client;
import de.aoksystems.idm.config.service.to.ClientDto;

@Mapper
interface ClientMapper {
	
	ClientDto map(Client client);
	
	List<ClientDto> mapPersistent(List<Client> clients);
}
