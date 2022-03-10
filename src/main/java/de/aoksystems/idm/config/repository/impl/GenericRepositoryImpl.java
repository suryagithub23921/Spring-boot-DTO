package de.aoksystems.idm.config.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.aoksystems.idm.config.repository.GenericRepository;

abstract class GenericRepositoryImpl<T> implements GenericRepository<T> {
	
	@PersistenceContext
	private EntityManager em;

	public EntityManager getEntityManager() {
		return this.em;
	}

	@Override
	public T save(T t) {
		T result = null;
		
		if (t != null) {
			result = getEntityManager().merge(t);
			
			getEntityManager().flush();
		}
		
		return result;
	}
}
