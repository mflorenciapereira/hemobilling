package ar.uba.fi.hemobillingLaboService.dao.impl;

import java.util.Collection;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.commons.dao.impl.GenericHibernateDAO;
import ar.uba.fi.hemobillingLaboService.dao.PacienteLaboratorioDAO;
import ar.uba.fi.hemobillingLaboService.domain.PacienteLaboratorio;

@Repository("pacienteLaboratorioDAO")
public class PacienteLaboratorioDAOImpl extends GenericHibernateDAO<PacienteLaboratorio,Integer> implements PacienteLaboratorioDAO 
{
	private Logger logger = Logger.getLogger(PacienteLaboratorioDAOImpl.class);
	
	@Resource(name = "sessionFactoryComplementos")
	public void setAnnotationSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public PacienteLaboratorio getPaciente( Integer ID ) throws DataAccessException, ObjectNotFoundException
	{
		logger.info("Se obtiene al paciente con numero de HC: "+ID.toString() );
		
		DetachedCriteria criterio = DetachedCriteria.forClass( PacienteLaboratorio.class );
		criterio.add( Restrictions.eq("numHistoriaClinica", ID ) );
		
		Collection<PacienteLaboratorio> pacientes = this.findByCriteria(criterio);
		
		if( pacientes.isEmpty() ) throw new ObjectNotFoundException();
		
		return pacientes.iterator().next();
	}

	@Override
	public void actualizar(PacienteLaboratorio paciente) throws DataAccessException,ObjectNotFoundException 
	{
		logger.info("Se actualiza al paciente HC: "+paciente.getNumHistoriaClinica().toString() );

		//Con Access hay que hacerlo del modo tradicional
		Session sesion = getSessionFactory().openSession();
		Transaction tx = sesion.beginTransaction();
		
		sesion.update(paciente);
		
		tx.commit();
		sesion.flush();
		sesion.clear();		
		sesion.close();
	}

}
