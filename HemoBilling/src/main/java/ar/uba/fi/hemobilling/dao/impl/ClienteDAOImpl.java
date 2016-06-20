package ar.uba.fi.hemobilling.dao.impl;

import java.util.Collection;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectFoundException;
import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.commons.dao.impl.GenericHibernateDAO;
import ar.uba.fi.hemobilling.dao.ClienteDAO;
import ar.uba.fi.hemobilling.domain.FiltroConsulta;
import ar.uba.fi.hemobilling.domain.FiltroPaginado;
import ar.uba.fi.hemobilling.domain.clientes.Cliente;
import ar.uba.fi.hemobilling.domain.clientes.FiltroConsultaClientes;

@Repository("clienteDAO")
public class ClienteDAOImpl extends GenericHibernateDAO <Cliente, String> implements ClienteDAO
{
		
	@Resource(name = "sessionFactory")
	public void setAnnotationSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	private DetachedCriteria getCriteriosBusqueda( FiltroConsultaClientes filtro )
	{
		DetachedCriteria criteria = DetachedCriteria.forClass( Cliente.class );
		
		if( !filtro.getCodigo().equals( FiltroConsulta.TODOS_DESC))
		{
			criteria.add( Restrictions.eq("codigo", Long.parseLong(filtro.getCodigo())) );
		}
		
		if( !filtro.getCodigoContable().equals( FiltroConsulta.TODOS_DESC))
		{
			criteria.add( Restrictions.ilike("codigoContable", filtro.getCodigoContable(), MatchMode.ANYWHERE));
		}
		
		
		if( !filtro.getCuit().equals( FiltroConsulta.TODOS_DESC))
		{
			criteria.add( Restrictions.ilike("cuit", filtro.getCuit(), MatchMode.ANYWHERE) );
		}
		
		
		if( !filtro.getLocalidad().equals( FiltroConsulta.TODOS_DESC))
			
			
		{
			criteria.add( Restrictions.ilike("direccion.localidad", filtro.getLocalidad(), MatchMode.ANYWHERE) );
			
		}
		
		if( !filtro.getNombre().equals( FiltroConsulta.TODOS_DESC))
		{
			criteria.add( Restrictions.ilike("nombre", filtro.getNombre(), MatchMode.ANYWHERE) );
		}
		
		if( !filtro.getProvincia().equals( FiltroConsulta.TODOS_DESC))
		{
			
			criteria.add( Restrictions.ilike("direccion.provincia", filtro.getProvincia(), MatchMode.ANYWHERE) );
			
		}
		
		
		
		if( (filtro.getTipoIVA()!=null)&&(filtro.getTipoIVA().getId()!=-1))
		{
			criteria.add( Restrictions.eq("tipoIVA.id", filtro.getTipoIVA().getId()) );
		}
				
		
		
		return criteria;
	}
	
	@Override
	public Cliente get(Long codigo) throws ObjectNotFoundException, DataAccessException{
		Cliente Cliente = this.findById(codigo);
		return Cliente;
	}

	@Override
	public void agregar(Cliente cliente) throws ObjectFoundException {
		if (cliente != null) {
			if( (cliente.getCodigo()!=null)&&(exists(cliente.getCodigo()))) throw new ObjectFoundException();
			getHibernateTemplate().save(cliente);
		};
		
		
		
		
	}

	@Override
	public void actualizar(Cliente Cliente) throws ObjectNotFoundException, DataAccessException {
		if( !exists(Cliente.getCodigo())) throw new ObjectNotFoundException();
		super.update(Cliente);		
	}

	@Override
	public Collection<Cliente> getClientes( FiltroConsultaClientes filtro , FiltroPaginado filtroPaginado ) throws DataAccessException 
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
	public Collection<Cliente> getClientes() throws DataAccessException 
	{
		return this.getAll();
	}

	@Override
	public void eliminar(Cliente Cliente) throws ObjectNotFoundException, DataAccessException {
		if( !exists(Cliente.getCodigo())) throw new ObjectNotFoundException();
		super.delete(Cliente);
		
	}

	@Override
	public Integer getTotal(FiltroConsultaClientes filtro) throws DataAccessException 
	{
		DetachedCriteria criteria = getCriteriosBusqueda(filtro);
		criteria.setProjection( Projections.rowCount() );
		
		Integer count = (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
		return count;
	}

	/**
	 * Devuelve la lista de todos los clientes, trayendo solo
	 * los atributos codigo - nombre. Esto sirve para cuando se 
	 * necesita listar los clientes en un formulario de edicion - carga
	 */
	@Override
	public Collection<Cliente> getClientesParaListar() throws DataAccessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass( Cliente.class );
		ProjectionList proList = Projections.projectionList();
		proList.add(Projections.property("codigo") , "codigo" );
		proList.add(Projections.property("nombre") , "nombre" );
		criteria.setProjection(proList);
		
		criteria.setResultTransformer( Transformers.aliasToBean(Cliente.class) );
		
		@SuppressWarnings("unchecked")
		Collection<Cliente> lista = (Collection<Cliente>)getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	



}
