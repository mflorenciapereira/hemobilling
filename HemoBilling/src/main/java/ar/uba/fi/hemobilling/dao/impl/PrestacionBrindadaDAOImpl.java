package ar.uba.fi.hemobilling.dao.impl;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectFoundException;
import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.commons.dao.impl.GenericHibernateDAO;
import ar.uba.fi.hemobilling.dao.PrestacionBrindadaDAO;
import ar.uba.fi.hemobilling.domain.FiltroConsulta;
import ar.uba.fi.hemobilling.domain.FiltroPaginado;
import ar.uba.fi.hemobilling.domain.prestacionesBrindadas.DatosAdicionalesEstudioLaboratorioImportado;
import ar.uba.fi.hemobilling.domain.prestacionesBrindadas.FiltroConsultaPrestacionesBrindadas;
import ar.uba.fi.hemobilling.domain.prestacionesBrindadas.Observacion;
import ar.uba.fi.hemobilling.domain.prestacionesBrindadas.PrestacionBrindada;

@Repository("prestacionBrindadaDAO")
public class PrestacionBrindadaDAOImpl extends GenericHibernateDAO<PrestacionBrindada, Long> implements
		PrestacionBrindadaDAO 
{
	
	private static String PRESTACION_TIENE_PRECIO = "SELECT ILP.id FROM pacientes PA " +
			"INNER JOIN obrassociales OS ON OS.codigo=PA.codObraSocialActual " +
			"INNER JOIN asociacionoslp OSLP ON OSLP.obrasocialid=OS.codigo " +
			"INNER JOIN itemslistaprecio ILP ON OSLP.listaprecioid = ILP.listaprecioid " +
			"WHERE OSLP.desde <= :FECHA AND ( OSLP.hasta >= :FECHA OR OSLP.hasta IS NULL ) " +
			"AND PA.id = :PACIENTEID and ILP.prestacionid = :PRESTACIONID ";
	
	private static String DATOS_ADICIONALES_ESTUDIO_LABO = "SELECT PA.id, PA.nombreyApellido, PE.descripcion FROM pacientes PA, prestaciones PE " +
//			"INNER JOIN obrassociales OS ON OS.codigo=PA.codObraSocialActual " +
//			"INNER JOIN asociacionoslp OSLP ON OSLP.obrasocialid=OS.codigo " +
//			"INNER JOIN itemslistaprecio ILP ON OSLP.listaprecioid = ILP.listaprecioid " +
//			"INNER JOIN prestaciones PE ON ILP.prestacionid=PE.codigo " +
//			"WHERE OSLP.desde <= :FECHA AND ( OSLP.hasta >= :FECHA OR OSLP.hasta IS NULL ) " +
			"WHERE PA.numHistoriaClinica = :NUMHISTCLINICA and PE.codigo = :PRESTACIONID "; 
	
	@Resource(name = "sessionFactory")
	public void setAnnotationSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private DetachedCriteria getCriteriosBusqueda(FiltroConsultaPrestacionesBrindadas filtro) {
		DetachedCriteria criteria = DetachedCriteria.forClass(PrestacionBrindada.class);

		
		criteria.createAlias("paciente", "paciente");
		criteria.createAlias("prestacion", "prestacion");

		if (!filtro.getCodigoPrestacion().equals(FiltroConsulta.TODOS_DESC)) {
			criteria.add(Restrictions.eq("prestacion.codigo", Long.parseLong(filtro.getCodigoPrestacion())));
		}

		if (!filtro.getHistoriaClinica().equals(FiltroConsulta.TODOS_DESC)) {
			criteria.add(Restrictions.eq("paciente.numHistoriaClinica", Long.parseLong(filtro.getHistoriaClinica())));
		}

		if (!filtro.getNombrePaciente().equals(FiltroConsulta.TODOS_DESC)) {
			criteria.add(Restrictions.ilike("paciente.nombreyApellido", filtro.getNombrePaciente(), MatchMode.ANYWHERE));
		}

		if (!filtro.getNombrePrestacion().equals(FiltroConsulta.TODOS_DESC)) {
			criteria.add(Restrictions.ilike("prestacion.descripcion", filtro.getNombrePrestacion(), MatchMode.ANYWHERE));
		}

		if (!filtro.getProfesional().equals(FiltroConsulta.TODOS_DESC)) {
			criteria.add(Restrictions.ilike("profesional", filtro.getProfesional(), MatchMode.ANYWHERE));
		}

		try {

			if (!filtro.getFechaDesde().equals(FiltroConsulta.TODOS_DESC)) {

				Date parse = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(filtro.getFechaDesde());
				criteria.add(Restrictions.ge("fecha", new java.sql.Date(parse.getTime())));
			}

			if (!filtro.getFechaHasta().equals(FiltroConsulta.TODOS_DESC)) {

				Date parse = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(filtro.getFechaHasta());
				criteria.add(Restrictions.le("fecha", new java.sql.Date(parse.getTime())));
			}

		}
		catch (ParseException e) {

			return criteria;
		}

		return criteria;
	}

	@Override
	public PrestacionBrindada obtener(Long id) throws ObjectNotFoundException, DataAccessException {
		return this.findById(id);
	}

	@Override
	public void agregar(PrestacionBrindada newPrestacionBrindada) throws ObjectFoundException, DataAccessException {
		super.save(newPrestacionBrindada);
	}

	@Override
	public void actualizar(PrestacionBrindada updatedPrestacionBrindada) throws ObjectNotFoundException,
			DataAccessException {
		if (!exists(updatedPrestacionBrindada.getCodigo()))
			throw new ObjectNotFoundException();
		super.update(updatedPrestacionBrindada);
	}

	@Override
	public Integer getCantidadConsulta(FiltroConsultaPrestacionesBrindadas filtro) throws DataAccessException {
		DetachedCriteria criteria = getCriteriosBusqueda(filtro);
		criteria.setProjection(Projections.rowCount());

		Integer count = (Integer) getHibernateTemplate().findByCriteria(criteria).get(0);
		return count;
	}

	@Override
	public Collection<PrestacionBrindada> consultar(FiltroConsultaPrestacionesBrindadas filtro,
			FiltroPaginado filtroPaginado) throws DataAccessException {
		if (filtro != null) {
			DetachedCriteria criteria = getCriteriosBusqueda(filtro);
			return findByCriteria(criteria, filtroPaginado.getNumeroPaginaActual(), filtroPaginado.getRegPorPagina());
		}
		else {
			return getAll();
		}
	}

	@Override
	public void eliminar(PrestacionBrindada prestacionBrindada) throws ObjectNotFoundException, DataAccessException {
		if (!exists(prestacionBrindada.getCodigo()))
			throw new ObjectNotFoundException();
		super.delete(prestacionBrindada);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Observacion> getObservaciones() {
		Criteria criteria = this.getSession().createCriteria(Observacion.class);
		criteria.addOrder(Order.asc("descripcion"));
		return criteria.list();
	}

	@Override
	public void agregarObservaciones(String observaciones) {
		if (observaciones.compareTo("") == 0)
			return;
		if (existeObservacion(observaciones))
			return;
		Observacion observacion = new Observacion();
		observacion.setDescripcion(observaciones);
		getHibernateTemplate().save(observacion);

	}

	private boolean existeObservacion(String obs) {
		Criteria criteria = this.getSession().createCriteria(Observacion.class);
		criteria.add(Restrictions.eq("descripcion", obs));
		return !criteria.list().isEmpty();
	}
	
	@Override
	public Boolean tienePrecioAsignadoPBParaOSEnFecha( PrestacionBrindada pb ) throws DataAccessException
	{
		SQLQuery query = this.getSession().createSQLQuery( PRESTACION_TIENE_PRECIO );
		
		query.setParameter("FECHA", pb.getFecha() );
		query.setParameter("PACIENTEID", pb.getPaciente().getId() );
		query.setParameter("PRESTACIONID", pb.getPrestacion().getCodigo() );

		return query.list().size()!=0;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DatosAdicionalesEstudioLaboratorioImportado getDatosAdicionalesEstudioImportado( PrestacionBrindada estudio ) throws DataAccessException , ObjectNotFoundException
	{
		Session session = this.getSession();
		
		SQLQuery query = session.createSQLQuery( DATOS_ADICIONALES_ESTUDIO_LABO );
		
//		query.setParameter("FECHA", estudio.getFecha() );
		query.setParameter("NUMHISTCLINICA", estudio.getPaciente().getNumHistoriaClinica() );
		query.setParameter("PRESTACIONID", estudio.getPrestacion().getCodigo() );		
		
		query.setResultTransformer( Transformers.aliasToBean(DatosAdicionalesEstudioLaboratorioImportado.class) ) ;
		
		Collection<DatosAdicionalesEstudioLaboratorioImportado> lista = query.list();
		
		if( !lista.isEmpty() )
		{
			DatosAdicionalesEstudioLaboratorioImportado dato = lista.iterator().next();
			session.close();
			return dato;
		}
		else
		{
			session.close();
			throw new ObjectNotFoundException();
		}
			
		
	}

	@Override
	public List<Object[]> getCodigosYaImportados(
			Date desde,
			Date hasta) {
		
		Criteria criteria = this.getSession().createCriteria(PrestacionBrindada.class);
		criteria.add(Restrictions.between("fecha", desde, hasta));
		criteria.setProjection(Projections.projectionList().add(Projections.property("codigoEnLaboratorio")));
		return criteria.list();
		
		
	}

}
