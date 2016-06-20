package ar.uba.fi.hemobilling.dao.impl;

import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectFoundException;
import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.commons.dao.impl.GenericHibernateDAO;
import ar.uba.fi.hemobilling.dao.FacturaServicioDAO;
import ar.uba.fi.hemobilling.domain.FiltroConsulta;
import ar.uba.fi.hemobilling.domain.FiltroPaginado;
import ar.uba.fi.hemobilling.domain.clientes.Cliente;
import ar.uba.fi.hemobilling.domain.factura.FacturaServicio;
import ar.uba.fi.hemobilling.domain.factura.FiltroConsultaFacturaServicio;

@Repository("facturaServicioDAO")
public class FacturaServicioDAOImpl extends GenericHibernateDAO <FacturaServicio, Long> implements FacturaServicioDAO
{
	private DetachedCriteria getCriteriosBusqueda( FiltroConsultaFacturaServicio filtro )
	{
		DetachedCriteria criteria = DetachedCriteria.forClass( FacturaServicio.class );
		
		if( !filtro.getNumero().equals( FiltroConsulta.TODOS_DESC))
		{
			criteria.add( Restrictions.eq("numero", filtro.getNumero() ) );
		}
		
		if( filtro.getFechaDesde() != null && filtro.getFechaHasta() == null )
		{
			criteria.add( Restrictions.ge( "fechaEmision", filtro.getFechaDesde() ) );
		}
		
		if( filtro.getFechaDesde() == null && filtro.getFechaHasta()!=null )
		{
			criteria.add( Restrictions.le( "fechaEmision", filtro.getFechaHasta() ) );
		}
		
		if( filtro.getFechaDesde() != null && filtro.getFechaHasta() != null )
		{
			criteria.add( Restrictions.between( "fecha", filtro.getFechaDesde() , filtro.getFechaHasta() ) );
		}
		
		if( !filtro.getClientes().isEmpty() )
		{
			Disjunction disjunction = Restrictions.disjunction();
			
			Iterator<Cliente> it = filtro.getClientes().iterator();
			while( it.hasNext() )
			{
				Cliente os = it.next();
				Criterion criterioOS = Restrictions.eq("clienteFacturado.codigo", os.getCodigo() );
				disjunction.add(criterioOS);
			}
			
			criteria.add(disjunction);			
		}
		
		
		
		
		return criteria;
	}
	
	@Resource(name = "sessionFactory")
	public void setAnnotationSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public FacturaServicio obtener(Long id) throws ObjectNotFoundException, DataAccessException {
		return this.findById(id);
	}

	@Override
	public void agregar(FacturaServicio newFactura) throws ObjectFoundException, DataAccessException {
		this.save(newFactura);
	}

	@Override
	public void actualizar(FacturaServicio updatedFactura) throws ObjectNotFoundException, DataAccessException {
		if( !exists(updatedFactura.getId() ) ) throw new ObjectNotFoundException();
		this.update(updatedFactura);		
	}

	@Override
	public Integer getCantidadConsulta(FiltroConsultaFacturaServicio filtro) throws DataAccessException {
		DetachedCriteria criteria = getCriteriosBusqueda(filtro);
		criteria.setProjection( Projections.rowCount() );
		
		Integer count = (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
		return count;
	}

	@Override
	public Collection<FacturaServicio> consultar(FiltroConsultaFacturaServicio filtro, FiltroPaginado filtroPaginado) throws DataAccessException 
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
	public void eliminar(FacturaServicio factura) throws ObjectNotFoundException, DataAccessException {
		if( !exists(factura.getId() )) throw new ObjectNotFoundException();
		super.delete(factura);			
	}

	@Override
	public Boolean existe(long id) throws DataAccessException 
	{
		DetachedCriteria criteria = DetachedCriteria.forClass( FacturaServicio.class );
		criteria.setProjection( Projections.rowCount() );
		criteria.add( Restrictions.eq("id", id ) );
		
		Integer count = (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
		return (count!=0);
	}

}
