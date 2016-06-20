package ar.uba.fi.hemobilling.dao.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.bouncycastle.asn1.isismtt.x509.Restriction;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
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
import ar.uba.fi.hemobilling.dao.FacturaPrestacionDAO;
import ar.uba.fi.hemobilling.domain.FiltroConsulta;
import ar.uba.fi.hemobilling.domain.FiltroPaginado;
import ar.uba.fi.hemobilling.domain.factura.DatosFacturacionPrestacionesBrindadas;
import ar.uba.fi.hemobilling.domain.factura.FacturaPrestacion;
import ar.uba.fi.hemobilling.domain.factura.FiltroConsultaFacturasPrestacion;
import ar.uba.fi.hemobilling.domain.obrassociales.ObraSocial;
import ar.uba.fi.hemobilling.domain.paciente.Paciente;
import ar.uba.fi.hemobilling.domain.prestaciones.Prestacion;
import ar.uba.fi.hemobilling.domain.prestacionesBrindadas.PrestacionBrindada;
import ar.uba.fi.hemobilling.reportes.factura.DatoListaReporteFactura;

@Repository("facturaPrestacionDAO")
public class FacturaPrestacionDAOImpl extends GenericHibernateDAO <FacturaPrestacion, Long> implements FacturaPrestacionDAO
{
	private static final String QUERY_FACTURACION_SELECT = "select " +
			"PB.codigo , " +
			"PB.fecha, " +
			"PB.cantidadDePrestaciones, " +
			"PB.idPaciente, " +
			"PB.precioManual, " +
			"PA.nombreyApellido, " +
			"PA.numHistoriaClinica, " +
			"PB.codigoPrestacion, " +
			"PE.descripcion, " +
			"PA.codObraSocialActual, " +
			"OS.nombre, " +
			"OS.sigla, " +
			"OS.tipoFactura, " +
			"OS.contabilizaFactura, " +
			"ILP.precio, " +
			"ILP.codigoSegunOS " + 
			"from prestacionesbrindadas PB " +
			"inner join prestaciones PE on PE.codigo = PB.codigoPrestacion " + 
			"inner join pacientes PA on PA.id = PB.idPaciente " + 
			"inner join asociacionoslp OSLP on OSLP.obrasocialid = PA.codObraSocialActual " + 
			"left outer join itemslistaprecio ILP on OSLP.listaprecioid = ILP.listaprecioid AND PB.codigoPrestacion = ILP.prestacionid " +
			"inner join obrassociales OS ON OS.codigo = PA.codObraSocialActual ";
	
	private static final String QUERY_FACTURACION_WHERE = "WHERE OSLP.desde <= PB.fecha AND (OSLP.hasta >= PB.fecha  OR OSLP.hasta is null ) AND PB.fecha BETWEEN :FECHA_DESDE AND :FECHA_HASTA ";
	
	private static final String QUERY_FACTURACION_ORDER = "ORDER BY PA.codObraSocialActual ASC, PA.numHistoriaClinica ASC, PB.fecha ASC, PB.codigoPrestacion ASC ";

	private static final String QUERY_ITEMS_TOTALIZADOS = "SELECT CONCAT(p.numHistoriaClinica,' ',p.nombreyApellido) as prestacion, CAST(ROUND(SUM(pf.importeUnitario*pf.cantidad),2) AS char) as total  FROM prestacionesbrindadasfacturadas as pf JOIN asociacionprestacionesbrindadasfacturadas afp ON  pf.id=afp.id_prestacionBrindadaFacturada JOIN pacientes p 	ON pf.idPacienteFacturado = p.id WHERE afp.id_factura=:nroFactura GROUP BY prestacion ORDER BY p.numHistoriaClinica ASC;";
	
	private Criteria getCriteriosBusqueda( FiltroConsultaFacturasPrestacion filtro )
	{
		Criteria criteria = this.getSession().createCriteria(FacturaPrestacion.class);
		
		//DetachedCriteria criteria = DetachedCriteria.forClass( FacturaPrestacion.class );
		
		if( !filtro.getNumero().equals( FiltroConsulta.TODOS_DESC))
		{
			criteria.add( Restrictions.eq("numero", filtro.getNumero() ) );
		}
		
		if( filtro.getFechaDesde()!=null && filtro.getFechaHasta()==null )
		{
			criteria.add( Restrictions.ge( "fechaEmision", filtro.getFechaDesde() ) );
		}
		
		if( filtro.getFechaDesde()==null && filtro.getFechaHasta() != null )
		{
			criteria.add( Restrictions.le( "fechaEmision", filtro.getFechaHasta() ) );
		}
		
		if( filtro.getFechaDesde() != null && filtro.getFechaHasta() != null )
		{
			criteria.add( Restrictions.between( "fechaEmision", filtro.getFechaDesde() , filtro.getFechaHasta() ) );
		}
		
		if( !filtro.getObrasSociales().isEmpty() )
		{
			Disjunction disjunction = Restrictions.disjunction();
			
			Iterator<ObraSocial> it = filtro.getObrasSociales().iterator();
			while( it.hasNext() )
			{
				ObraSocial os = it.next();
				Criterion criterioOS = Restrictions.eq("obraSocialFacturada.codigo", os.getCodigo() );
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
	public FacturaPrestacion obtener(Long id) throws ObjectNotFoundException, DataAccessException {
		return this.findById(id);
	}

	@Override
	public void agregar(FacturaPrestacion newFactura) throws ObjectFoundException, DataAccessException {
		this.save(newFactura);
		
	}

	@Override
	public void actualizar(FacturaPrestacion updatedFactura) throws ObjectNotFoundException, DataAccessException {
		if( !exists(updatedFactura.getId() ) ) throw new ObjectNotFoundException();
		this.update(updatedFactura);
		
	}

	@Override
	public Integer getCantidadConsulta(FiltroConsultaFacturasPrestacion filtro) throws DataAccessException {
		Criteria criteria = getCriteriosBusqueda(filtro);
		criteria.setProjection( Projections.rowCount() );
		
		Integer count = (Integer)criteria.uniqueResult();
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<FacturaPrestacion> consultar(FiltroConsultaFacturasPrestacion filtro, FiltroPaginado filtroPaginado) throws DataAccessException 
	{
		if( filtro!=null )
		{
			Criteria criteria = getCriteriosBusqueda(filtro);
			
			Integer primero = (filtroPaginado.getNumeroPaginaActual()-1) * filtroPaginado.getRegPorPagina();
			
			criteria.setFirstResult(primero);
			criteria.setMaxResults( filtroPaginado.getRegPorPagina() );
			return (Collection<FacturaPrestacion>)criteria.list();
		}
		else
		{
			return getAll();
		}
	}
	
	@Override
	public Collection<FacturaPrestacion> consultarMinimo(FiltroConsultaFacturasPrestacion filtro, FiltroPaginado filtroPaginado) throws DataAccessException 
	{
		if( filtro!=null )
		{			
			Criteria criteria = getCriteriosBusqueda(filtro);
			
			ProjectionList proList = Projections.projectionList();
			proList.add(Projections.property("id") );
			proList.add(Projections.property("numero") );
			proList.add(Projections.property("fechaEmision") );
			proList.add(Projections.property("anulada") );

			criteria.createAlias("obraSocialFacturada", "os" , Criteria.LEFT_JOIN );
			proList.add(Projections.property("os.codigo") );
			proList.add(Projections.property("os.nombre") );
			proList.add(Projections.property("os.sigla") );
			proList.add(Projections.property("os.tipoFactura") );
			
			criteria.createCriteria("pacienteFacturado", "pa" , Criteria.LEFT_JOIN ); 
			proList.add(Projections.property("pa.id") );
			proList.add(Projections.property("pa.nombreyApellido") );
			proList.add(Projections.property("pa.numHistoriaClinica") );
			
			criteria.setProjection(proList);
			
			criteria.setFirstResult( (filtroPaginado.getNumeroPaginaActual()-1) * filtroPaginado.getRegPorPagina() );
			criteria.setMaxResults( filtroPaginado.getRegPorPagina() );
			
			@SuppressWarnings("unchecked")
			Collection<Object[]> lista = (Collection<Object[]>)criteria.list();
			
			Collection<FacturaPrestacion> resultado = new ArrayList<FacturaPrestacion>();
			if( !lista.isEmpty() )
			{
				Iterator<Object[]> it = lista.iterator();
				while( it.hasNext() )
				{
					Object[] dato = it.next();
					FacturaPrestacion factura = new FacturaPrestacion();
					
					factura.setId( (Long)dato[0] );
					factura.setNumero( (String)dato[1] );
					
					Timestamp tst = (Timestamp )dato[2];
					factura.setFechaEmision( new Date(tst.getTime()) );
					
					factura.setAnulada( (Boolean)dato[3] );
					
					ObraSocial os = new ObraSocial();
					os.setCodigo(  (Long)dato[4]  );
					os.setNombre( (String)dato[5] );
					os.setSigla( (String)dato[6] );
					os.setTipoFactura( (String)dato[7] );
					factura.setObraSocialFacturada(os);
					
					Paciente pa = null;
					if( dato[8]!=null )
					{
						pa = new Paciente();
						pa.setId( (Long)dato[8] );
						pa.setNombreyApellido( (String)dato[9] );
						pa.setNumHistoriaClinica( (Long)dato[10] );
					}
					factura.setPacienteFacturado(pa);					
					
					resultado.add(factura);
				}
			}
			
			return resultado;
			
		}
		else
		{
			return getAll();
		}
	}


	@Override
	public void eliminar(FacturaPrestacion factura) throws ObjectNotFoundException, DataAccessException {
		if( !exists(factura.getId() )) throw new ObjectNotFoundException();
		super.delete(factura);	
	}

	@Override
	public Boolean existe(long id) throws DataAccessException 
	{
		DetachedCriteria criteria = DetachedCriteria.forClass( FacturaPrestacion.class );
		criteria.setProjection( Projections.rowCount() );
		criteria.add( Restrictions.eq("id", id ) );
		
		Integer count = (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
		return (count!=0);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<DatosFacturacionPrestacionesBrindadas> getDatosFacturacion( FiltroConsultaFacturasPrestacion filtro ) throws DataAccessException
	{
		String sql = QUERY_FACTURACION_SELECT + QUERY_FACTURACION_WHERE;
		
		if( !filtro.getObrasSociales().isEmpty() )
		{
			sql += " AND (";
			Iterator<ObraSocial> it = filtro.getObrasSociales().iterator();
			
			while( it.hasNext() )
			{
				ObraSocial os = it.next();
				
				String nombreVariable="OBRASOCIAL"+os.getCodigo().toString();
				sql += " PA.codObraSocialActual = :"+nombreVariable+" ";
				
				if( it.hasNext() )
					sql += " OR ";
				else
					sql += " ) ";
			}	
		}
		
		sql += QUERY_FACTURACION_ORDER;
		
		SQLQuery query = this.getSession().createSQLQuery( sql );
		query.setResultTransformer( Transformers.aliasToBean(DatosFacturacionPrestacionesBrindadas.class) );
		
		query.setParameter("FECHA_DESDE", filtro.getFechaDesde() );
		query.setParameter("FECHA_HASTA", filtro.getFechaHasta() );
		
		if( !filtro.getObrasSociales().isEmpty() )
		{
			Iterator<ObraSocial> it = filtro.getObrasSociales().iterator();
			
			while( it.hasNext() )
			{
				ObraSocial os = it.next();
				String nombreVariable="OBRASOCIAL"+os.getCodigo().toString();
				query.setParameter( nombreVariable , os.getCodigo() );
			}
		}

		Collection<DatosFacturacionPrestacionesBrindadas> lista = query.list();

		return lista;
	}

	@Override
	public FacturaPrestacion obtenerOrdenado(Long id) throws DataAccessException, ObjectNotFoundException
	{
		Criteria primaria=this.getSession().createCriteria(FacturaPrestacion.class);
		primaria.add(Restrictions.idEq(id));
		Criteria secundaria=primaria.createCriteria("prestacionesBrindadasFacturadas");
		secundaria.addOrder(Order.asc("pacienteFacturado.id"));
		FacturaPrestacion factura = (FacturaPrestacion) primaria.uniqueResult();
		
		if( factura!=null )
			return factura;
		else
			throw new ObjectNotFoundException();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DatoListaReporteFactura> obtenerItemsTotalizados(Long id) {
		
		String sql = QUERY_ITEMS_TOTALIZADOS;
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setParameter("nroFactura", id);
		query.setResultTransformer(Transformers.aliasToBean(DatoListaReporteFactura.class));
		return query.list();
		
		
	}

	@Override
	public void eliminar(Long[] idsFacturas) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass( FacturaPrestacion.class );
		criteria.add(Restrictions.in("id", idsFacturas));
		List<FacturaPrestacion> facturas = getHibernateTemplate().findByCriteria(criteria);
		for(FacturaPrestacion factura : facturas){
			super.delete(factura);
		}
	
		
	}

	@Override
	public Collection<Prestacion> obtenerModulosConPrestacionesAsociadas(Collection<Long> idsItems) {
		Criteria criteria = getSession().createCriteria(Prestacion.class);
		criteria.createAlias("prestacionesAsociadas", "pa")
		.add(Restrictions.in("pa.codigo", idsItems));
		return criteria.list();
		
	}
	
}
