package ar.uba.fi.hemobilling.commons.dao;

import java.io.Serializable;
import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;


public interface GenericDAO<T, PK extends Serializable> {

	T findById(Serializable id) throws ObjectNotFoundException, DataAccessException;

	Collection<T> getAll() throws DataAccessException;

	Collection<T> executeQuery(String sql) throws DataAccessException;

	Collection<T> executeQuery(String sql, Object[] params) throws DataAccessException;

	Collection<T> findByCriteria(DetachedCriteria dc) throws DataAccessException;

	Collection<T> findByCriteria(DetachedCriteria dc, Integer pageNumber, Integer rowsPerPage) throws DataAccessException;

	void update(T entity) throws DataAccessException;

	void save(T entity) throws DataAccessException;

	void delete(T entity) throws DataAccessException;

	void saveOrUpdate(T entity) throws DataAccessException;
	
	Boolean exists( Serializable id ) throws DataAccessException;

}
