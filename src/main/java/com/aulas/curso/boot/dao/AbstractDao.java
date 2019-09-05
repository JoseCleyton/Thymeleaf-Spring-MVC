package com.aulas.curso.boot.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public abstract class AbstractDao<T, PK extends Serializable> {

	@SuppressWarnings("unchecked")
	private final Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
			.getActualTypeArguments()[0];

	@PersistenceContext
	private EntityManager entityManager;

	protected EntityManager geEntityManager() {
		return entityManager;
	}

	public void save(T entity) {
		this.entityManager.persist(entity);
	}

	public void update(T entity) {
		this.entityManager.merge(entity);

	}

	public void delete(PK id) {
		this.entityManager.remove(this.entityManager.getReference(this.entityClass, id));
	}

	public T findById(PK id) {
		return this.entityManager.find(this.entityClass, id);
	}

	public List<T> findAll() {
		return this.entityManager.createQuery("from " + this.entityClass.getSimpleName(), this.entityClass)
				.getResultList();
	}

	public List<T> createQuery(String jpql, Object... params) {
		TypedQuery<T> query = this.entityManager.createQuery(jpql, this.entityClass);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}
		return query.getResultList();
	}
}
