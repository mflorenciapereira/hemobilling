package ar.uba.fi.hemobilling.commons.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ar.uba.fi.hemobilling.commons.dao.GenericDAO;
import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;


@SuppressWarnings("unchecked")
public abstract class GenericHibernateDAO<T, PK extends Serializable> extends HibernateDaoSupport implements GenericDAO<T, PK> 
{
	private Class<T> persistentClass;

	public GenericHibernateDAO() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}


	@Override
	public Boolean exists( Serializable id ) throws DataAccessException {
		T o = (T) getHibernateTemplate().get(persistentClass, id);
		return (o!=null);
	}
	
	@Override
	public T findById(Serializable id) throws ObjectNotFoundException, DataAccessException {
		T o = (T) getHibernateTemplate().get(persistentClass, id);
		if (o == null) {
			throw new ObjectNotFoundException();
		}
		return o;
	}

	@Override
	public Collection<T> getAll() throws DataAccessException {
		return getHibernateTemplate().loadAll(persistentClass);
	}

	@Override
	public Collection<T> executeQuery(String hql) throws DataAccessException {
		return getHibernateTemplate().find(hql);
	}

	@Override
	public Collection<T> executeQuery(String hql, Object[] params) throws DataAccessException {
		return getHibernateTemplate().find(hql, params);
	}

	@Override
	public Collection<T> findByCriteria(DetachedCriteria dc) throws DataAccessException {
		return getHibernateTemplate().findByCriteria(dc);
	}
	
	@Override
	public Collection<T> findByCriteria(DetachedCriteria dc, Integer pageNumber, Integer rowsPerPage) throws DataAccessException {
		return getHibernateTemplate().findByCriteria (dc, (pageNumber - 1) * rowsPerPage, rowsPerPage);
	}

	@Override
	public void save(T entity) throws DataAccessException {
		if (entity != null) {
			getHibernateTemplate().save(entity);
		}
	}

	@Override
	public void update(T entity) throws DataAccessException {
		getHibernateTemplate().update(entity);
	}

	@Override
	public void delete(T entity) throws DataAccessException {
		if (entity != null) {
			getHibernateTemplate().delete(entity);

		}
	}
	
	@Override
	public void saveOrUpdate(T entity) {
		if (entity != null) {
			getHibernateTemplate().saveOrUpdate(entity);
		}
	}
}
