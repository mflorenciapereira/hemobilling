package ar.uba.fi.hemobilling.service;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import ar.uba.fi.hemobilling.dto.FiltroPaginadoDTO;
import ar.uba.fi.hemobilling.dto.factura.FacturaPrestacionDTO;
import ar.uba.fi.hemobilling.dto.factura.FacturaServicioDTO;
import ar.uba.fi.hemobilling.dto.factura.FiltroConsultaFacturasPrestacionDTO;
import ar.uba.fi.hemobilling.dto.factura.FiltroConsultaFacturasServicioDTO;
import ar.uba.fi.hemobilling.dto.factura.ResultConsultaFacturacionMasivaPrestacionesDTO;
import ar.uba.fi.hemobilling.exception.domain.HBDataAccessException;
import ar.uba.fi.hemobilling.exception.domain.HBErrorCreandoReporte;
import ar.uba.fi.hemobilling.exception.domain.HBObjectExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBObjectNotExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBReporteNoDefinido;

public interface FacturacionService 
{
	public ResultConsultaFacturacionMasivaPrestacionesDTO getFacturacionPrestaciones( FiltroConsultaFacturasPrestacionDTO filtroDTO ) throws HBDataAccessException ;
	
	public void agregarFacturasPrestaciones( ResultConsultaFacturacionMasivaPrestacionesDTO facturasAceptadasDTO ) throws HBDataAccessException, HBObjectExistsException;
	
	public FacturaPrestacionDTO obtenerFacturaPrestacion( Long id ) throws HBDataAccessException, HBObjectNotExistsException;
	
	public Collection<FacturaPrestacionDTO> consultarFacturasPrestaciones( FiltroConsultaFacturasPrestacionDTO filtroDTO, FiltroPaginadoDTO filtroPaginado ) throws HBDataAccessException;
	
	public void agregarFacturaServicio( FacturaServicioDTO facturaDTO ) throws HBDataAccessException, HBObjectExistsException;
	
	public Collection<FacturaServicioDTO> consultarFacturasServicios( FiltroConsultaFacturasServicioDTO filtroDTO, FiltroPaginadoDTO filtroPaginado ) throws HBDataAccessException;

	public void eliminarFP( FacturaPrestacionDTO facturaDTO) throws HBDataAccessException, HBObjectNotExistsException;
	
	public void eliminarFS( FacturaServicioDTO facturaDTO) throws HBDataAccessException, HBObjectNotExistsException;

	void crearReporteFacturasConFondo(FacturaPrestacionDTO dto, HttpServletResponse res) 
			throws HBReporteNoDefinido, HBErrorCreandoReporte, HBDataAccessException, HBObjectNotExistsException;

	public void crearReporteFacturasConFondo(FacturaServicioDTO filtroDTO,
			HttpServletResponse res) throws HBReporteNoDefinido, HBErrorCreandoReporte, HBDataAccessException, HBObjectNotExistsException;

	public void crearReporteFacturasPrestacion(String[] facturas, HttpServletResponse res) throws HBDataAccessException, HBObjectNotExistsException, HBReporteNoDefinido, HBErrorCreandoReporte;
	public void crearReporteFacturasServicio(String[] facturas, HttpServletResponse res) throws HBDataAccessException, HBObjectNotExistsException, HBReporteNoDefinido, HBErrorCreandoReporte;

	public void crearReporteDetalle(FacturaPrestacionDTO filtroDTO,
			HttpServletResponse res) throws HBDataAccessException, HBObjectNotExistsException, HBReporteNoDefinido, HBErrorCreandoReporte;

	public void crearReporteResumen(FacturaPrestacionDTO filtroDTO, HttpServletResponse res)
			throws HBReporteNoDefinido, HBErrorCreandoReporte, HBDataAccessException, HBObjectNotExistsException;
	
	public void anularFacturaPrestacion( FacturaPrestacionDTO facturaDTO ) throws  HBDataAccessException, HBObjectNotExistsException;
	
	public void anularFacturaServicio( FacturaServicioDTO facturaDTO ) throws  HBDataAccessException, HBObjectNotExistsException;

	void crearReporteSintesis(FacturaPrestacionDTO dto, HttpServletResponse res)
			throws HBReporteNoDefinido, HBErrorCreandoReporte,
			HBDataAccessException, HBObjectNotExistsException;

	public void eliminarFacturasPrestacion(Long[] idsFacturas);
	public void eliminarFacturasServicios(Long[] idsFacturas);

	
	
	
}
