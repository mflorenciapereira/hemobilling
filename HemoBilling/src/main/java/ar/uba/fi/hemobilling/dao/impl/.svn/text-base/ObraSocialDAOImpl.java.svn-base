package ar.uba.fi.hemobilling.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
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
import ar.uba.fi.hemobilling.dao.ObraSocialDAO;
import ar.uba.fi.hemobilling.domain.FiltroConsulta;
import ar.uba.fi.hemobilling.domain.FiltroPaginado;
import ar.uba.fi.hemobilling.domain.obrassociales.FiltroConsultaObrasSociales;
import ar.uba.fi.hemobilling.domain.obrassociales.ObraSocial;
import ar.uba.fi.hemobilling.domain.obrassociales.Plan;
import ar.uba.fi.hemobilling.dto.obrassociales.ObraSocialDTO;

@Repository("obraSocialDAO")
public class ObraSocialDAOImpl extends GenericHibernateDAO <ObraSocial, Long> implements ObraSocialDAO {

	@Resource(name = "sessionFactory")
	public void setAnnotationSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	private DetachedCriteria getCriteriosBusqueda( FiltroConsultaObrasSociales filtro )
	{
		DetachedCriteria criteria = DetachedCriteria.forClass( ObraSocial.class );
		
		if( !filtro.getCodigo().equals( FiltroConsulta.TODOS_DESC))
		{
			criteria.add( Restrictions.eq("codigo", Long.parseLong(filtro.getCodigo())) );
		}
		
		if( !filtro.getCodigoRNOS().equals( FiltroConsulta.TODOS_DESC))
		{
			criteria.add( Restrictions.eq("codigoRNOS", filtro.getCodigoRNOS()) );
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
		
		if( !filtro.getPrestador().equals( FiltroConsulta.TODOS_DESC))
		{
			criteria.add( Restrictions.ilike("prestador", filtro.getPrestador(), MatchMode.ANYWHERE) );
		}
		
		
		
		if( !filtro.getSigla().equals( FiltroConsulta.TODOS_DESC))
		{
			criteria.add( Restrictions.ilike("sigla", filtro.getSigla(), MatchMode.ANYWHERE) );
		}
		
		if( (filtro.getTipoIVA()!=null)&&(filtro.getTipoIVA().getId()!=-1))
		{
			criteria.add( Restrictions.eq("tipoIVA.id", filtro.getTipoIVA().getId()) );
		}
				
		
		
		return criteria;
	}
	
	@Override
	public ObraSocial getObraSocial(long codigo) throws ObjectNotFoundException, DataAccessException{
		ObraSocial obrasocial = this.findById(codigo);
		return obrasocial;
	}

	@Override
	public Serializable agregar(ObraSocial obrasocial) throws ObjectFoundException, DataAccessException {
		if (obrasocial != null) {
			if( (obrasocial.getCodigo()!=null)&&(exists(obrasocial.getCodigo()))) throw new ObjectFoundException();
			return getHibernateTemplate().save(obrasocial);
		};
		return null;
		
		
		
	}

	@Override
	public void actualizar(ObraSocial obrasocial) throws ObjectNotFoundException, DataAccessException {
		if( !exists(obrasocial.getCodigo())) throw new ObjectNotFoundException();
		super.update(obrasocial);		
	}

	@Override
	public Collection<ObraSocial> getObrasSociales( FiltroConsultaObrasSociales filtro , FiltroPaginado filtroPaginado ) throws DataAccessException 
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
	
	/**
	 * Devuelve la lista de todas las obras sociales, trayendo solo
	 * los atributos codigo - nombre. Esto sirve para cuando se 
	 * necesita listar las OS en un formulario de edicion - carga
	 */
	@Override
	public Collection<ObraSocial> getObrasSocialesParaListar() throws DataAccessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass( ObraSocial.class );
		ProjectionList proList = Projections.projectionList();
		proList.add(Projections.property("codigo") , "codigo" );
		proList.add(Projections.property("nombre") , "nombre" );
		proList.add(Projections.property("sigla") , "sigla" );
		criteria.setProjection(proList);
		
		criteria.setResultTransformer( Transformers.aliasToBean(ObraSocial.class) );
		
		
		@SuppressWarnings("unchecked")
		Collection<ObraSocial> lista = (Collection<ObraSocial>)getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}
	
	
	@Override
	public Collection<ObraSocial> getObrasSociales() throws DataAccessException 
	{
		return this.getAll();
	}

	@Override
	public void eliminar(ObraSocial obrasocial) throws ObjectNotFoundException, DataAccessException {
		if( !exists(obrasocial.getCodigo())) throw new ObjectNotFoundException();
//		Iterator<Plan> it=obrasocial.getPlanes().iterator();
//		while(it.hasNext()){
//			getHibernateTemplate().delete(it.next());
//		}
		super.delete(obrasocial);
		
	}

	@Override
	public Integer getCantObrasSociales(FiltroConsultaObrasSociales filtro) throws DataAccessException 
	{
		DetachedCriteria criteria = getCriteriosBusqueda(filtro);
		criteria.setProjection( Projections.rowCount() );
		
		Integer count = (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
		return count;
	}

	@Override
	public boolean existePlan(Long codigoOS, String codigoPlan, String nombre) throws DataAccessException {
		Criteria criteria = this.getSession().createCriteria(Plan.class );
		criteria.add( Restrictions.eq("codigo", codigoPlan) );
		criteria.add( Restrictions.eq("nombre", nombre) );
		criteria.add( Restrictions.eq("obraSocial.codigo", codigoOS) );
		return criteria.uniqueResult()!=null;
	}

	@Override
	public void agregar(Plan plan) throws DataAccessException {
		if(plan!=null)
			getHibernateTemplate().save(plan);
	}





	@Override
	public void eliminar(Plan plan) throws ObjectNotFoundException {
		if( !exists(plan.getCodigo())) throw new ObjectNotFoundException();
		
		getHibernateTemplate().delete(plan);
	}
	
	
	@Override
	public Collection<ObraSocial> getObrasSocialesDetalladasParaListar() throws DataAccessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass( ObraSocial.class );
		ProjectionList proList = Projections.projectionList();
		proList.add(Projections.property("codigo") , "codigo" );
		proList.add(Projections.property("nombre") , "nombre" );
		proList.add(Projections.property("sigla") , "sigla" );
		criteria.setProjection(proList);
		String tipo="D";
		criteria.add(Restrictions.eq("tipoFactura",tipo));
		
		criteria.setResultTransformer( Transformers.aliasToBean(ObraSocial.class) );
		
		
		@SuppressWarnings("unchecked")
		Collection<ObraSocial> lista = (Collection<ObraSocial>)getHibernateTemplate().findByCriteria(criteria);
		return lista;
	}

	@Override
	public List<ObraSocialDTO> getObrasSocialesActuales(Long codigo) {
		// TODO Auto-generated method stub
		return null;
	}
	



}
