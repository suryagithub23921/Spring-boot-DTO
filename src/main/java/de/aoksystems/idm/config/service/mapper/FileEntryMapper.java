package de.aoksystems.idm.config.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import de.aoksystems.idm.config.repository.entity.FileEntry;
import de.aoksystems.idm.config.service.to.FileEntryDto;

@Mapper
interface FileEntryMapper {
	
	@Mapping(source = "character.charactername", target = "character")
	@Mapping(source = "client.name", target = "client")
	FileEntryDto map(FileEntry entry);
	
	@Mapping(target = "character.charactername", source = "character")
	@Mapping(target = "client.name", source = "client")
	FileEntry map(FileEntryDto entryDto);
	
	List<FileEntryDto> mapPersistent(List<FileEntry> entries);
	
	List<FileEntry> mapTransient(List<FileEntryDto> entries);
}
