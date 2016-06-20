package ar.uba.fi.hemobilling.dao.impl;


import java.util.Collection;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ar.uba.fi.hemobilling.commons.dao.impl.GenericHibernateDAO;
import ar.uba.fi.hemobilling.dao.InformesDAO;
import ar.uba.fi.hemobilling.domain.factura.Factura;
import ar.uba.fi.hemobilling.domain.factura.FacturaPrestacion;
import ar.uba.fi.hemobilling.domain.informes.FiltroCartaFacturas;
import ar.uba.fi.hemobilling.domain.informes.FiltroFacturasEmitidas;

@Repository("informeDAO")
public class InformesDAOImpl extends GenericHibernateDAO <String, Long> implements InformesDAO {

	@Resource(name = "sessionFactory")
	public void setAnnotationSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public Collection<Factura> getFacturasPorPeriodo(FiltroFacturasEmitidas filtro) 
	{		

		
		Criteria criteria = this.getSession().createCriteria(Factura.class );
		criteria.add(Restrictions.between("fechaEmision", filtro.getFechaDesde(), filtro.getFechaHasta()));
		criteria.addOrder(Order.asc("fechaEmision"));
			
		return criteria.list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public Collection<FacturaPrestacion> getFacturasPorPeriodoOS(
			FiltroCartaFacturas filtro) {
		Criteria criteria = this.getSession().createCriteria(FacturaPrestacion.class );
		criteria.add(Restrictions.between("fechaEmision", filtro.getFechaDesde(), filtro.getFechaHasta()));
		criteria.add(Restrictions.eq("obraSocialFacturada.codigo", filtro.getObraSocial().getCodigo()));
		criteria.addOrder(Order.asc("fechaEmision"));
			
		return criteria.list();
	}

	




	
	
	

}
