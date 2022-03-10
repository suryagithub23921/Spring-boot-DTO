package de.aoksystems.idm.config.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import de.aoksystems.idm.config.repository.entity.SimpleEntry;
import de.aoksystems.idm.config.service.to.SimpleEntryDto;

@Mapper
interface SimpleEntryMapper {
	
	@Mapping(source = "character.charactername", target = "character")
	@Mapping(source = "client.name", target = "client")
	SimpleEntryDto map(SimpleEntry entry);
	
	@Mapping(target = "character.charactername", source = "character")
	@Mapping(target = "client.name", source = "client")
	SimpleEntry map(SimpleEntryDto entryDto);
	
	List<SimpleEntryDto> mapPersistent(List<SimpleEntry> entries);
	
	List<SimpleEntry> mapTransient(List<SimpleEntryDto> entries);
}
