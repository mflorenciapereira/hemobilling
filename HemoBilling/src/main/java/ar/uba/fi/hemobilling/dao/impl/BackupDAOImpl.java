package ar.uba.fi.hemobilling.dao.impl;

import java.util.Collection;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectFoundException;
import ar.uba.fi.hemobilling.commons.dao.impl.GenericHibernateDAO;
import ar.uba.fi.hemobilling.dao.BackupDAO;
import ar.uba.fi.hemobilling.domain.backup.BackupRealizado;

@Repository("backupDAOImpl")
public class BackupDAOImpl extends GenericHibernateDAO <BackupRealizado, Long> implements BackupDAO
{

	@Resource(name = "sessionFactory")
	public void setAnnotationSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	
	@Override
	public void agregar(BackupRealizado backup) throws ObjectFoundException 
	{
		this.save(backup);
	}

	@Override
	public Collection<BackupRealizado> getBackups()
	{
		DetachedCriteria criteria = DetachedCriteria.forClass( BackupRealizado.class );
		criteria.addOrder(Order.desc("id"));
		Collection<BackupRealizado> datos = this.findByCriteria(criteria);
		return datos;
	}

}
