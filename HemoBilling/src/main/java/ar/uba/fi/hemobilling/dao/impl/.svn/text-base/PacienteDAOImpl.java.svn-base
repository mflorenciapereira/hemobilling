package ar.uba.fi.hemobilling.dao.impl;

import java.util.Collection;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectFoundException;
import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.commons.dao.impl.GenericHibernateDAO;
import ar.uba.fi.hemobilling.dao.PacienteDAO;
import ar.uba.fi.hemobilling.domain.FiltroConsulta;
import ar.uba.fi.hemobilling.domain.FiltroPaginado;
import ar.uba.fi.hemobilling.domain.paciente.FiltroConsultaPacientes;
import ar.uba.fi.hemobilling.domain.paciente.Paciente;

@Repository("pacienteDAO")
public class PacienteDAOImpl extends GenericHibernateDAO <Paciente, Long> implements PacienteDAO 
{
	@Resource(name = "sessionFactory")
	public void setAnnotationSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	private DetachedCriteria getCriteriosBusqueda( FiltroConsultaPacientes filtro )
	{
		DetachedCriteria criteria = DetachedCriteria.forClass( Paciente.class );
		
		if( !filtro.getNombreyApellido().equals( FiltroConsulta.TODOS_DESC))
		{
			criteria.add( Restrictions.like("nombreyApellido", "%"+filtro.getNombreyApellido()+"%" ) );
		}
		
		if( !filtro.getNroDNI().equals( FiltroConsulta.TODOS_DESC) )
		{
			try{
				Long dni = Long.parseLong( filtro.getNroDNI() );
				criteria.add( Restrictions.like("nroDNI", dni ) );
			}
			catch( Exception e ){}
		}
		
		
		if( !filtro.getNumHistoriaClinica().equals( FiltroConsulta.TODOS_DESC) )
		{
			try{
				Long hc = Long.parseLong( filtro.getNumHistoriaClinica() );
				criteria.add( Restrictions.like("numHistoriaClinica", hc ) );
			}
			catch( Exception e ){}
		}
		
		return criteria;
	}

	@Override
	public Paciente obtener(Long id) throws ObjectNotFoundException, DataAccessException 
	{
		return this.findById(id);
	}

	@Override
	public void agregar(Paciente newPaciente) throws ObjectFoundException, DataAccessException 
	{
		super.save(newPaciente);		
	}

	@Override
	public void actualizar(Paciente updatedPaciente) throws ObjectNotFoundException, DataAccessException 
	{
		if( !exists(updatedPaciente.getId() ) ) throw new ObjectNotFoundException();
		super.update(updatedPaciente);
	}

	@Override
	public Integer getCantidadConsulta(FiltroConsultaPacientes filtro) throws DataAccessException 
	{
		DetachedCriteria criteria = getCriteriosBusqueda(filtro);
		criteria.setProjection( Projections.rowCount() );
		
		Integer count = (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
		return count;
	}

	@Override
	public Collection<Paciente> consultar(FiltroConsultaPacientes filtro, FiltroPaginado filtroPaginado) throws DataAccessException 
	{
		if( filtro!=null )
		{
			DetachedCriteria criteria = getCriteriosBusqueda(filtro);			
			return findByCriteria(criteria , filtroPaginado.getNumeroPaginaActual() , filtroPaginado.getRegPorPagina() );
		}
		else
		{
			return getAll();
		}
	}

	@Override
	public void eliminar(Paciente paciente) throws ObjectNotFoundException, DataAccessException 
	{
		if( !exists(paciente.getId() )) throw new ObjectNotFoundException();
		super.delete(paciente);		
	}
	
	@Override
	public Boolean existePaciente(long id) throws DataAccessException 
	{
		DetachedCriteria criteria = DetachedCriteria.forClass( Paciente.class );
		criteria.setProjection( Projections.rowCount() );
		criteria.add( Restrictions.eq("id", id ) );
		
		Integer count = (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
		return (count!=0);
	}

	@Override
	public Paciente obtenerPorNumeroHC(Long numHC) throws ObjectNotFoundException, DataAccessException 
	{
		DetachedCriteria criteria = DetachedCriteria.forClass( Paciente.class );
		
		criteria.add( Restrictions.eq("numHistoriaClinica", numHC ) );
		
		Collection<Paciente> result = findByCriteria(criteria);
		
		if( !result.isEmpty() )
			return result.iterator().next();
		else
			throw new ObjectNotFoundException();
	}
	
	public Paciente obtenerPorNumeroHCParaListar(Long numHC) throws ObjectNotFoundException, DataAccessException 
	{
		DetachedCriteria criteria = DetachedCriteria.forClass( Paciente.class );
		
		ProjectionList projList = Projections.projectionList();
		projList.add( Projections.property("id") , "id"  );
		projList.add( Projections.property("nombreyApellido") , "nombreyApellido" );
		criteria.setProjection( projList );
		
		criteria.setResultTransformer( Transformers.aliasToBean(Paciente.class) );
		
		criteria.add( Restrictions.eq("numHistoriaClinica", numHC ) );
		
		@SuppressWarnings("unchecked")
		Collection<Paciente> result = (Collection<Paciente>)getHibernateTemplate().findByCriteria(criteria);
		
		if( !result.isEmpty() )
		{
			Paciente paciente = result.iterator().next();
			paciente.setNumHistoriaClinica( numHC );
			return paciente;
		}
		else
			throw new ObjectNotFoundException();
	}

	@Override
	public Collection<Paciente> getPacientesParaListar() throws DataAccessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass( Paciente.class );
		
		ProjectionList projList = Projections.projectionList();
		projList.add( Projections.property("id") , "id"  );
		projList.add( Projections.property("nombreyApellido") , "nombreyApellido" );
		projList.add( Projections.property("numHistoriaClinica") , "numHistoriaClinica" );
		criteria.setProjection( projList );
		
		criteria.addOrder(Order.asc("numHistoriaClinica"));
		
		criteria.setResultTransformer( Transformers.aliasToBean(Paciente.class) );
		
		@SuppressWarnings("unchecked")
		Collection<Paciente> result = (Collection<Paciente>)getHibernateTemplate().findByCriteria(criteria);
		   
		return result;
	}

}
