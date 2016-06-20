package ar.uba.fi.hemobilling.dao.impl;

import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import ar.uba.fi.hemobilling.commons.dao.impl.GenericHibernateDAO;
import ar.uba.fi.hemobilling.dao.AsociacionListaPrecioObraSocialDAO;
import ar.uba.fi.hemobilling.domain.listasprecio.ListaPrecio;
import ar.uba.fi.hemobilling.domain.obrassociales.AsociacionObraSociaListaPrecio;
import ar.uba.fi.hemobilling.domain.obrassociales.ObraSocial;

@Repository("oslpDAO")
public class AsociacionListaPrecioObraSocialDAOImpl extends GenericHibernateDAO <AsociacionObraSociaListaPrecio, Long> implements AsociacionListaPrecioObraSocialDAO {

	@Resource(name = "sessionFactory")
	public void setAnnotationSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public void agregar(AsociacionObraSociaListaPrecio asociacion) throws DataAccessException {
		if(asociacion!=null)
			getHibernateTemplate().save(asociacion);
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<AsociacionObraSociaListaPrecio> getAsociaciones(Long codigoOS) throws DataAccessException {
		Criteria criteria = this.getSession().createCriteria(AsociacionObraSociaListaPrecio.class );
		criteria.add(Restrictions.eq("obraSocial.codigo", codigoOS));
		criteria.addOrder(Order.asc("desde")).addOrder( Order.asc("id") );
		return criteria.list();
	}

	@Override
	public AsociacionObraSociaListaPrecio getUltimaAsociacion(Long codObraSocial) throws DataAccessException 
	{
		String sql = "SELECT OSLP.id, OSLP.desde, OSLP.hasta, LP.codigo, LP.nombre " +
				 	 "FROM asociacionoslp as OSLP " +
				 	 "INNER JOIN obrassociales AS OS ON OSLP.obrasocialid=OS.codigo " +
				 	"INNER JOIN listasprecio AS LP ON LP.codigo=OSLP.listaprecioid " +
				 	 "WHERE OS.codigo = :CODIGO_OS AND OSLP.hasta is null ";
		
		SQLQuery query = this.getSession().createSQLQuery( sql );
		query.setParameter("CODIGO_OS", codObraSocial );
		
		@SuppressWarnings("unchecked")
		Collection<Object[]> result = (Collection<Object[]>)query.list();
		
		if( !result.isEmpty() )
		{
			Object[] dato = result.iterator().next();
			
			AsociacionObraSociaListaPrecio asociacion = new AsociacionObraSociaListaPrecio();
			ObraSocial os = new ObraSocial();
			ListaPrecio lp = new ListaPrecio();
			
			BigInteger id = (BigInteger)dato[0];
			asociacion.setId( id.longValue() );
			asociacion.setDesde( (Date)dato[1] );
			asociacion.setHasta( (Date)dato[2] );
			
			os.setCodigo(codObraSocial);
			
			id = (BigInteger)dato[3];
			lp.setCodigo( id.longValue() );
			lp.setNombre( (String)dato[4]  );
			
			asociacion.setListaPrecio(lp);
			asociacion.setObraSocial(os);
			
			return asociacion;
		}
		else
			return null;
	}

	@Override
	public void actualizar(AsociacionObraSociaListaPrecio ultima) throws DataAccessException {
		if(ultima!=null)
			getHibernateTemplate().update(ultima);
	}
	
	@Override
	public Collection<AsociacionObraSociaListaPrecio> getAsociacionesParaListar(Long codObraSocial) throws DataAccessException 
	{
		String sql = "SELECT OSLP.id, OSLP.desde, OSLP.hasta, LP.codigo, LP.nombre from asociacionoslp as OSLP " +
					 "INNER JOIN obrassociales OS ON OSLP.obrasocialid=OS.codigo " +
					 "INNER JOIN listasprecio LP ON OSLP.listaprecioid = LP.codigo " +
					 "WHERE OS.codigo = :CODIGO_OS " +
					 "ORDER BY desde ASC, id ASC";
		
		SQLQuery query = this.getSession().createSQLQuery( sql );
		
		query.setParameter("CODIGO_OS", codObraSocial );
		
		
		@SuppressWarnings("unchecked")
		Collection<Object[]> result = (Collection<Object[]>)query.list();
		
		Collection<AsociacionObraSociaListaPrecio> lista = new ArrayList<AsociacionObraSociaListaPrecio>();
		Iterator<Object[]> it = result.iterator();
		while( it.hasNext() )
		{
			Object[] dato = it.next();
			
			AsociacionObraSociaListaPrecio asociacion = new AsociacionObraSociaListaPrecio();
			ObraSocial os = new ObraSocial();
			ListaPrecio lp = new ListaPrecio();
			
			BigInteger id = (BigInteger)dato[0];
			asociacion.setId( id.longValue() );
			asociacion.setDesde( (Date)dato[1] );
			asociacion.setHasta( (Date)dato[2] );
			
			os.setCodigo(codObraSocial);
			
			id = (BigInteger)dato[3];
			lp.setCodigo( id.longValue() );
			lp.setNombre( (String)dato[4]  );
			
			asociacion.setListaPrecio(lp);
			asociacion.setObraSocial(os);
			
			lista.add(asociacion);
		}
		
		return lista;
	}

	@Override
	public void eliminarAsociacionConListaPrecios(Long codObraSocial) throws DataAccessException 
	{
		String sql = "DELETE FROM asociacionoslp WHERE obrasocialid = :CODIGO_OS;";
	
		SQLQuery query = this.getSession().createSQLQuery( sql );
	
		query.setParameter("CODIGO_OS", codObraSocial );
		
		query.executeUpdate();		
	}

	@Override
	public AsociacionObraSociaListaPrecio getAsociacionActual(Long codigo) {
		Criteria criteria=this.getSession().createCriteria(AsociacionObraSociaListaPrecio.class);
		criteria.add(Restrictions.eq("obraSocial.codigo", codigo));
		criteria.add(Restrictions.le("desde", new java.util.Date()));
		criteria.add(Restrictions.ge("hasta", new java.util.Date()));
		
		return (AsociacionObraSociaListaPrecio) criteria.uniqueResult();
	}

	@Override
	public boolean existeAsociacion(String desde, String hasta, Long codigolp,
			Long codigoos) {
		String sql = "SELECT * " +
			 	 "FROM asociacionoslp as OSLP " +
			 	 "INNER JOIN obrassociales AS OS ON OSLP.obrasocialid=OS.codigo " +
			 	"INNER JOIN listasprecio AS LP ON LP.codigo=OSLP.listaprecioid " +
			 	 "WHERE OS.codigo = :CODIGO_OS AND OSLP.hasta = :HASTA AND OSLP.desde = :DESDE AND LP.codigo = :CODIGO_LP";
	
	SQLQuery query = this.getSession().createSQLQuery( sql );
	query.setParameter("CODIGO_OS", codigoos );
	query.setParameter("CODIGO_LP", codigolp );
	query.setParameter("DESDE", desde );
	query.setParameter("HASTA", hasta );
	return query.list().size()>0;
	
		
	}

	@Override
	public void borrarAsociaciones(Long codigo) {
		String sql = "DELETE " +
			 	 "FROM asociacionoslp " +
			 	 "WHERE obrasocialid = :CODIGO_OS";
	
	SQLQuery query = this.getSession().createSQLQuery( sql );
	query.setParameter("CODIGO_OS", codigo );
	query.executeUpdate();
	return;
		
	}
}
