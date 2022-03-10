package de.aoksystems.idm.config.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import de.aoksystems.idm.config.repository.entity.Character;
import de.aoksystems.idm.config.service.to.CharacterDto;

@Mapper
interface CharacterMapper {
	
	@Mapping(source = "charactername", target = "type")
	CharacterDto map(Character character);
	
	List<CharacterDto> mapPersistent(List<Character> characters);
}
