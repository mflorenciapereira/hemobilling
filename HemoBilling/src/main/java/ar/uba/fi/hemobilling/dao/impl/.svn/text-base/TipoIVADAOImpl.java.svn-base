package ar.uba.fi.hemobilling.dao.impl;

import java.util.Collection;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.commons.dao.impl.GenericHibernateDAO;
import ar.uba.fi.hemobilling.dao.TipoIVADAO;
import ar.uba.fi.hemobilling.domain.general.TipoIVA;

@Repository("tipoIVADAO")
public class TipoIVADAOImpl extends GenericHibernateDAO <TipoIVA, Long> implements TipoIVADAO 
{
	@Resource(name = "sessionFactory")
	public void setAnnotationSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public TipoIVA getTipoIVA(long id) throws ObjectNotFoundException, DataAccessException{
		TipoIVA tipo = this.findById(id);
		return tipo;
	}

	
	
	@Override
	public Collection<TipoIVA> getTiposIVA() throws DataAccessException 
	{
		return this.getAll();
	}

}
