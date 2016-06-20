package ar.uba.fi.hemobilling.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectFoundException;
import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.commons.dao.impl.GenericHibernateDAO;
import ar.uba.fi.hemobilling.dao.ListaPrecioDAO;
import ar.uba.fi.hemobilling.domain.FiltroConsulta;
import ar.uba.fi.hemobilling.domain.FiltroPaginado;
import ar.uba.fi.hemobilling.domain.listasprecio.FiltroConsultaListasPrecio;
import ar.uba.fi.hemobilling.domain.listasprecio.ItemListaPrecio;
import ar.uba.fi.hemobilling.domain.listasprecio.ListaPrecio;
import ar.uba.fi.hemobilling.domain.obrassociales.AsociacionObraSociaListaPrecio;
import ar.uba.fi.hemobilling.domain.prestaciones.Prestacion;
import ar.uba.fi.hemobilling.dto.listasprecio.ListaPrecioDTO;

@Repository("listaPrecioDAO")
public class ListaPrecioDAOImpl extends GenericHibernateDAO <ListaPrecio, Long> implements ListaPrecioDAO {

	@Resource(name = "sessionFactory")
	public void setAnnotationSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	private DetachedCriteria getCriteriosBusqueda( FiltroConsultaListasPrecio filtro )
	{
		DetachedCriteria criteria = DetachedCriteria.forClass( ListaPrecio.class );
		
		if( !filtro.getCodigo().equals( FiltroConsulta.TODOS_DESC))
		{
			criteria.add( Restrictions.eq("codigo", Long.parseLong(filtro.getCodigo())) );
		}
		
		if( !filtro.getNombre().equals( FiltroConsulta.TODOS_DESC))
		{
			criteria.add( Restrictions.ilike("nombre", filtro.getNombre(), MatchMode.ANYWHERE) );
		}		
		
		return criteria;
	}
	
	@Override
	public ListaPrecio getListaPrecio(long codigo) throws ObjectNotFoundException, DataAccessException{
		ListaPrecio lista = this.findById(codigo);
		return lista;
	}
	
	@Override
	public Collection<ItemListaPrecio> getItemsListaPrecios(long codigo) throws ObjectNotFoundException, DataAccessException
	{
		String sql = "SELECT I.id, I.codigoSegunOS, I.precio, I.prestacionid, P.descripcion " +
					 "FROM itemslistaprecio AS I, prestaciones AS P  " +
					 "WHERE I.listaprecioid = :CODIGO_LP AND I.prestacionid=P.codigo ";
	
		SQLQuery query = this.getSession().createSQLQuery( sql );
		query.setParameter("CODIGO_LP", codigo );
	
		@SuppressWarnings("unchecked")
		Collection<Object[]> result = (Collection<Object[]>)query.list();
	
		Collection<ItemListaPrecio> lista = new ArrayList<ItemListaPrecio>();
		
		if( !result.isEmpty() )
		{
			Iterator<Object[]> it = result.iterator();
			while( it.hasNext() )
			{	
				Object[] o = it.next();
				
				ItemListaPrecio item = new ItemListaPrecio();
				Prestacion prestacion = new Prestacion();
				
				BigInteger id = (BigInteger)o[0];
				item.setId( id.longValue() );
				
				item.setCodigo( (String)o[1] );
							
				item.setPrecio( (Double)o[2] );
				
				item.setIdlistaPrecioPertenece( codigo );
				
				id = (BigInteger)o[3];
				prestacion.setCodigo( id.longValue() );
				
				prestacion.setDescripcion( (String)o[4] );
				
				item.setPrestacion(prestacion);
				
				lista.add(item);
			}
		}
		
		return lista;
	}


	@Override
	public Long agregar(ListaPrecio lista) throws ObjectFoundException, DataAccessException {
		if (lista != null) {
			if((lista.getCodigo()!=null) && (exists(lista.getCodigo()))) throw new ObjectFoundException();
		
			Long id= (Long) getHibernateTemplate().save(lista);
			return id;
		}else return null;
		
		
		
	}

	@Override
	public void actualizar(ListaPrecio lista) throws ObjectNotFoundException, DataAccessException {
		this.getListaPrecioParaListar(lista.getCodigo() );
		super.update(lista);
	
	}

	@Override
	public Collection<ListaPrecio> buscarListasPrecio( FiltroConsultaListasPrecio filtro , FiltroPaginado filtroPaginado ) throws DataAccessException 
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
	public void eliminar(ListaPrecio lista) throws ObjectNotFoundException, DataAccessException {
		this.getListaPrecioParaListar(lista.getCodigo() );
		super.delete(lista);
		
	}

	@Override
	public Integer getCantListasPrecio(FiltroConsultaListasPrecio filtro) throws DataAccessException 
	{
		DetachedCriteria criteria = getCriteriosBusqueda(filtro);
		criteria.setProjection( Projections.rowCount() );
		
		Integer count = (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
		return count;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<ListaPrecio> getListasPrecio() throws DataAccessException {
		Criteria criteria = this.getSession().createCriteria(ListaPrecio.class );
		criteria.addOrder(Order.desc("nombre"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<ListaPrecio> getListasPrecioParaListar() throws DataAccessException 
	{
		DetachedCriteria criteria = DetachedCriteria.forClass( ListaPrecio.class );
		
		ProjectionList projList = Projections.projectionList();
		projList.add( Projections.property("codigo") , "codigo"  );
		projList.add( Projections.property("nombre") , "nombre" );
		criteria.setProjection( projList );
		
		criteria.addOrder(Order.desc("nombre"));
		
		criteria.setResultTransformer( Transformers.aliasToBean(ListaPrecio.class) );
		
		Collection<ListaPrecio> result = (Collection<ListaPrecio>)getHibernateTemplate().findByCriteria(criteria);
		
		return result;
	}

	@Override
	public Collection<ListaPrecio> buscarListasPrecioParaListar(FiltroConsultaListasPrecio filtro, FiltroPaginado filtroPaginado) throws DataAccessException 
	{
		if( filtro!=null )
		{
			DetachedCriteria criteria = getCriteriosBusqueda(filtro);
			
			ProjectionList proList = Projections.projectionList();
			proList.add( Projections.property("codigo") , "codigo"  );
			proList.add( Projections.property("nombre") , "nombre" );
			criteria.setProjection(proList);
			
			criteria.setResultTransformer( Transformers.aliasToBean(ListaPrecio.class) );
			
			return findByCriteria(criteria , filtroPaginado.getNumeroPaginaActual() , filtroPaginado.getRegPorPagina() );
		}
		else
		{
			return getAll();
		}
	}

	@Override
	public ListaPrecio getListaPrecioParaListar(long codigo) throws ObjectNotFoundException, DataAccessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass( ListaPrecio.class );
		
		ProjectionList proList = Projections.projectionList();
		proList.add(Projections.property("codigo") , "codigo" );
		proList.add(Projections.property("nombre") , "nombre" );
		criteria.setProjection(proList);
		
		criteria.add( Restrictions.eq("this.codigo", codigo) );
		
		criteria.setResultTransformer( new AliasToBeanResultTransformer(ListaPrecio.class) );
		
		Collection<ListaPrecio> lista = this.findByCriteria(criteria);
		
		if( !lista.isEmpty() )
			return lista.iterator().next();
		else
			throw new ObjectNotFoundException();
	}

	@Override
	public Collection<AsociacionObraSociaListaPrecio> getObrasSocialesLista(
			ListaPrecioDTO listaActual, Date fecha) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass( AsociacionObraSociaListaPrecio.class );
		criteria.add( Restrictions.eq("listaPrecio.codigo", listaActual.getCodigo() ));
		criteria.add( Restrictions.le("desde", fecha));
		criteria.add(Restrictions.or(Restrictions.ge("hasta", fecha),Restrictions.isNull("hasta")));

		Collection<AsociacionObraSociaListaPrecio> result = (Collection<AsociacionObraSociaListaPrecio>)getHibernateTemplate().findByCriteria(criteria);
		return result;
	}

	@Override
	public Collection<AsociacionObraSociaListaPrecio> getObrasSocialesLista(
			Long[] seleccionadas) {
		DetachedCriteria criteria = DetachedCriteria.forClass( AsociacionObraSociaListaPrecio.class );
		criteria.add(Restrictions.in("id", seleccionadas));
		return (Collection<AsociacionObraSociaListaPrecio>)getHibernateTemplate().findByCriteria(criteria);
		
	}

}
