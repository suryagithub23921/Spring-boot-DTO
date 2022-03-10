package de.aoksystems.idm.config.repository.impl;

import static de.aoksystems.idm.config.repository.entity.QFileEntry.fileEntry;

import java.util.List;

import org.springframework.stereotype.Component;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import de.aoksystems.idm.config.repository.FileEntryRepository;
import de.aoksystems.idm.config.repository.entity.FileEntry;

@Component
public class FileEntryRepositoryImpl extends GenericRepositoryImpl<FileEntry> implements FileEntryRepository {

	@Override
	public List<FileEntry> find(String client) {
		final JPQLQuery<FileEntry> query = new JPAQueryFactory(getEntityManager()).
				selectFrom(fileEntry);
		
		final BooleanBuilder builder = new BooleanBuilder();
		if (client != null) {
			builder.and(fileEntry.client.hasName(client));
		}
		
		query.where(builder);
		
		return query.fetch();
	}

	@Override
	public FileEntry findFile(String client, String key) {
		final JPQLQuery<FileEntry> query = new JPAQueryFactory(getEntityManager()).
				selectFrom(fileEntry).
				where(fileEntry.client.hasName(client),
						fileEntry.hasKey(key));
		
		return query.fetchFirst();
	}

	@Override
	public void delete(Long fileEntryId) {
		final FileEntry entry = getEntityManager().find(FileEntry.class, fileEntryId);
		getEntityManager().remove(entry);
	}
}
