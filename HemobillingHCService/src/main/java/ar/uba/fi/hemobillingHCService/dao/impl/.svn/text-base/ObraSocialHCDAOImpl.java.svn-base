package ar.uba.fi.hemobillingHCService.dao.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.commons.dao.impl.GenericHibernateDAO;
import ar.uba.fi.hemobillingHCService.dao.ObraSocialHCDAO;
import ar.uba.fi.hemobillingHCService.domain.ObraSocialHC;

@Repository("obraSocialHCDAO")
public class ObraSocialHCDAOImpl extends GenericHibernateDAO<ObraSocialHC, Integer> implements ObraSocialHCDAO{

	private Logger logger = Logger.getLogger(PacienteHCDAOImpl.class);
	
	@Resource(name = "sessionFactory")
	public void setAnnotationSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public ObraSocialHC getObraSocialHC(Integer ID) throws DataAccessException, ObjectNotFoundException {
		logger.info("Se obtiene la OS de HC con numero: "+ID.toString() );
		return findById(ID);
	}

}
