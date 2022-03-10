package de.aoksystems.idm.config.repository;

import java.util.List;

import de.aoksystems.idm.config.repository.entity.SimpleEntry;

public interface SimpleEntryRepository extends GenericRepository<SimpleEntry> {
	
	/**
	 * Search for all entries that belongs to the provided client. The client is an
	 * optional argument and may be null. In this situation no restriction to the
	 * entries happens.
	 * 
	 * @param client to which the entries belongs
	 * @return the found entries, otherwise an empty list.
	 */
	List<SimpleEntry> find(String client);

	/**
	 * Search for an entry that is identified by the provided client and key.
	 * 
	 * @param client identifies the insurance health company
	 * @param key    identifies the entry
	 * @return the found entry, otherwise null.
	 */
	SimpleEntry findSimple(String client, String key);
	
	/**
	 * Deletes the entry by the provided ID.
	 * 
	 * @param simpleEntryId that identifies the entry
	 */
	void delete(Long simpleEntryId);
}
