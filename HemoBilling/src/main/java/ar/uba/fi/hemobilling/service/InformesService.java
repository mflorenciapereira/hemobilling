package ar.uba.fi.hemobilling.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.dto.factura.FacturaPrestacionDTO;
import ar.uba.fi.hemobilling.dto.informes.FiltroCartaFacturasDTO;
import ar.uba.fi.hemobilling.dto.informes.FiltroFacturasEmitidasDTO;
import ar.uba.fi.hemobilling.exception.domain.HBDataAccessException;
import ar.uba.fi.hemobilling.exception.domain.HBErrorCreandoReporte;
import ar.uba.fi.hemobilling.exception.domain.HBObjectNotExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBReporteNoDefinido;
import ar.uba.fi.hemobilling.exception.domain.HBServiceException;

public interface InformesService 
{
	public void crearReporteFacturasEmitidas( FiltroFacturasEmitidasDTO filtroDTO, HttpServletResponse res )  throws HBDataAccessException, HBReporteNoDefinido, HBErrorCreandoReporte;
	
	public void crearCSVdeFactura( FacturaPrestacionDTO facturaDTO , HttpServletResponse res ) throws HBDataAccessException, HBObjectNotExistsException, HBServiceException;

	public void crearReporteCartaFacturas(FiltroCartaFacturasDTO filtroDTO,
			HttpServletResponse res) throws DataAccessException, ObjectNotFoundException, HBReporteNoDefinido, HBErrorCreandoReporte;
	
}
