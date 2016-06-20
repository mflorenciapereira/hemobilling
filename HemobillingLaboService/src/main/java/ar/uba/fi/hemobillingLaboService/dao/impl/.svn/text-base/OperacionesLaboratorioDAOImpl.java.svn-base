package ar.uba.fi.hemobillingLaboService.dao.impl;

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
import ar.uba.fi.hemobillingLaboService.dao.OperacionesLaboratorioDAO;
import ar.uba.fi.hemobillingLaboService.domain.AnalisisLaboratorio;
import ar.uba.fi.hemobillingLaboService.domain.FiltroConsultaAnalisisLaboratorio;

@Repository("operacionesLaboratorioDAO")
public class OperacionesLaboratorioDAOImpl extends GenericHibernateDAO<AnalisisLaboratorio,Integer> implements OperacionesLaboratorioDAO
{
	private Logger logger = Logger.getLogger(OperacionesLaboratorioDAOImpl.class);
	
	@Resource(name = "sessionFactoryOperaciones")
	public void setAnnotationSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public Collection<AnalisisLaboratorio> buscar( FiltroConsultaAnalisisLaboratorio filtro) throws DataAccessException, ObjectNotFoundException
	{
		logger.info("Se pidio buscar analisis de laboratorio" );
		
		DetachedCriteria criterio = DetachedCriteria.forClass( AnalisisLaboratorio.class );
		criterio.add( Restrictions.between("fechaRealizacion", filtro.getFechaInicio(), filtro.getFechaFinal() ) );
		
		Collection<AnalisisLaboratorio> analisis = findByCriteria(criterio);
		
		if( analisis.isEmpty() ) throw new ObjectNotFoundException();
		
		logger.info("Se devuelve la lista de analisis resultado" );
		
		return analisis;
	}
}
