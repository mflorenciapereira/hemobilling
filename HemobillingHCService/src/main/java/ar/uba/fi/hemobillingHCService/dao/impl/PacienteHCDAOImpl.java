package ar.uba.fi.hemobillingHCService.dao.impl;

import java.util.Collection;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.commons.dao.impl.GenericHibernateDAO;
import ar.uba.fi.hemobillingHCService.dao.PacienteHCDAO;
import ar.uba.fi.hemobillingHCService.domain.PacienteHC;

@Repository("pacienteHCDAO")
public class PacienteHCDAOImpl extends GenericHibernateDAO<PacienteHC, Integer> implements PacienteHCDAO 
{
	private Logger logger = Logger.getLogger(PacienteHCDAOImpl.class);
	
	@Resource(name = "sessionFactory")
	public void setAnnotationSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public PacienteHC getPaciente(Integer ID) throws DataAccessException,ObjectNotFoundException 
	{
		logger.info("Se obtiene al paciente con numero de HC: "+ID.toString() );
		
		//Si el numero de HC es de menos de 4 cifras lo completo a 4 cifras
		String numeroHC = ID.toString();
		int cantidadDigitos = numeroHC.length();
		if( cantidadDigitos<4 )
		{
			for( int digito=0 ; digito<(4-cantidadDigitos) ; digito++ )
			{
				numeroHC = "0"+numeroHC;
			}
		}

		DetachedCriteria criterio = DetachedCriteria.forClass( PacienteHC.class );
		criterio.add( Restrictions.eq("numHistoriaClinica", numeroHC ) );
		
		Collection<PacienteHC> pacientes = this.findByCriteria(criterio);
		
		if( pacientes.isEmpty() ) throw new ObjectNotFoundException();
		
		return pacientes.iterator().next();
	}

}
