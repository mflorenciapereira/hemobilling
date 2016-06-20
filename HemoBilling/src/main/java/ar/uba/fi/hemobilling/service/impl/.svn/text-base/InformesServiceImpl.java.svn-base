package ar.uba.fi.hemobilling.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import ar.uba.fi.hemobilling.archivosCSV.generador.ArchivoCSV;
import ar.uba.fi.hemobilling.archivosCSV.generador.CampoRegistroArchivoCSV;
import ar.uba.fi.hemobilling.archivosCSV.generador.GeneradorArchivoCSV;
import ar.uba.fi.hemobilling.archivosCSV.generador.RegistroArchivoCSV;
import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.commons.dozer.DozerMapper;
import ar.uba.fi.hemobilling.dao.FacturaPrestacionDAO;
import ar.uba.fi.hemobilling.dao.InformesDAO;
import ar.uba.fi.hemobilling.dao.ObraSocialDAO;
import ar.uba.fi.hemobilling.domain.factura.Factura;
import ar.uba.fi.hemobilling.domain.factura.FacturaPrestacion;
import ar.uba.fi.hemobilling.domain.factura.FacturaServicio;
import ar.uba.fi.hemobilling.domain.factura.PrestacionBrindadaFacturada;
import ar.uba.fi.hemobilling.domain.factura.TipoFactura;
import ar.uba.fi.hemobilling.domain.informes.FiltroCartaFacturas;
import ar.uba.fi.hemobilling.domain.informes.FiltroFacturasEmitidas;
import ar.uba.fi.hemobilling.domain.obrassociales.ObraSocial;
import ar.uba.fi.hemobilling.dto.ResultExportacionDTO;
import ar.uba.fi.hemobilling.dto.factura.FacturaPrestacionDTO;
import ar.uba.fi.hemobilling.dto.informes.FiltroCartaFacturasDTO;
import ar.uba.fi.hemobilling.dto.informes.FiltroFacturasEmitidasDTO;
import ar.uba.fi.hemobilling.exception.domain.HBDataAccessException;
import ar.uba.fi.hemobilling.exception.domain.HBErrorCreandoReporte;
import ar.uba.fi.hemobilling.exception.domain.HBObjectNotExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBReporteNoDefinido;
import ar.uba.fi.hemobilling.exception.domain.HBServiceException;
import ar.uba.fi.hemobilling.reportes.cartaDeFacturas.DatoListaReporteCartaDeFacturas;
import ar.uba.fi.hemobilling.reportes.cartaDeFacturas.DatosReporteCartaDeFacturas;
import ar.uba.fi.hemobilling.reportes.listaFacturasEmitidas.DatoListaReporteFacturasEmitidas;
import ar.uba.fi.hemobilling.reportes.listaFacturasEmitidas.DatosReporteListaFacturasEmitidas;
import ar.uba.fi.hemobilling.reportes.reporteador.Reporteador;
import ar.uba.fi.hemobilling.reportes.reporteador.service.ExportType;
import ar.uba.fi.hemobilling.service.InformesService;


@Service("informeService")
public class InformesServiceImpl implements InformesService 
{
	private static final String FORMATO_DDMMAAAA = "dd/MM/yyyy";
	private static final String FORMATO_AAAAMMDD = "yyyyMMdd";
	
	@Resource(name = "obraSocialDAO")
	private ObraSocialDAO obraSocialDAO;
	
	@Resource(name = "informeDAO")
	private InformesDAO informesDAO;
	
	@Resource(name = "reporteador")
	private Reporteador reporteador;
	
	@Resource(name = "facturaPrestacionDAO")
	private FacturaPrestacionDAO facturaPrestacionDAO;
	
	@Resource(name = "dozerMapper")
	private DozerMapper mapper;
	
	@Resource(name = "generadorArchivoCSV")
	private GeneradorArchivoCSV archivoCSVGenerator;
		
	private static Logger logger = Logger.getLogger(InformesServiceImpl.class);


	@Override
	public void crearReporteFacturasEmitidas( FiltroFacturasEmitidasDTO filtroDTO, HttpServletResponse respuesta ) throws HBReporteNoDefinido, HBErrorCreandoReporte{
	
		logger.info("Creacion del reporte de facturas emitidas." );
		
		FiltroFacturasEmitidas filtro = mapper.map(filtroDTO, FiltroFacturasEmitidas.class );
		Collection<Factura> facturasEmitidas = informesDAO.getFacturasPorPeriodo(filtro);
		Iterator<Factura> it = facturasEmitidas.iterator();
		

		DatosReporteListaFacturasEmitidas datosReporte = new DatosReporteListaFacturasEmitidas();
		Collection<DatoListaReporteFacturasEmitidas> listaDatosaListar = new ArrayList<DatoListaReporteFacturasEmitidas>();
		
		while( it.hasNext() )
		{
			Factura factura = it.next();
			DatoListaReporteFacturasEmitidas datoListaReporte = generarDatoFacturasEmitidas(factura);
			listaDatosaListar.add( datoListaReporte );
		}
		
	
		datosReporte.setPeriodoDesde(filtroDTO.getFechaDesde());
		datosReporte.setPeriosoHasta(filtroDTO.getFechaHasta());
		datosReporte.setDatosAListar( listaDatosaListar );
		datosReporte.setTipoReporte(ExportType.PDF);
		ResultExportacionDTO result = reporteador.exportarReporte(datosReporte, respuesta);
		
		if( result.getError() )
			throw new HBErrorCreandoReporte();
	
	}


	private DatoListaReporteFacturasEmitidas generarDatoFacturasEmitidas(Factura factura) {
		
		DatoListaReporteFacturasEmitidas datoListaReporte = new DatoListaReporteFacturasEmitidas();
		
		SimpleDateFormat formatoFecha = new SimpleDateFormat( FORMATO_DDMMAAAA );
		datoListaReporte.setFecha( formatoFecha.format(factura.getFechaEmision()) );
		datoListaReporte.setNroFactura( factura.getNumero() );
		datoListaReporte.setCodigoContable(factura.getCodigoContable());
		
		
		if( factura instanceof FacturaPrestacion )
		{
			FacturaPrestacion f = (FacturaPrestacion)factura;
			
			datoListaReporte.setObraSocial( f.getObraSocialFacturada().getSigla() );
			datoListaReporte.setMontoObraSocial( factura.getMontoTotal().setScale(2, BigDecimal.ROUND_DOWN) );
			datoListaReporte.setMontoOtros(BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_DOWN));
			
			if( factura.getAnulada() )
			{
				datoListaReporte.setDestinatario( "ANULADA" );
			}
			else
			{
				if( f.getObraSocialFacturada().getTipoFactura().equals("D") ) {
					String nombrePaciente = f.getPacienteFacturado().getNombreyApellido();
					datoListaReporte.setDestinatario( nombrePaciente.substring(0,nombrePaciente.length()>=200?200:nombrePaciente.length()) );
				} else
					datoListaReporte.setDestinatario( "" );	
			}	

		}
		else if( factura instanceof FacturaServicio )
		{
			FacturaServicio f = (FacturaServicio)factura;
			
			if( factura.getAnulada() )
			{
				datoListaReporte.setDestinatario( "ANULADA" );
			}
			else
			{
				datoListaReporte.setDestinatario( f.getClienteFacturado().getNombre() );
			}			
			
			datoListaReporte.setObraSocial( "" );
			
			datoListaReporte.setMontoObraSocial( factura.getMontoTotal().setScale(2, BigDecimal.ROUND_DOWN) );
			datoListaReporte.setMontoObraSocial( BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_DOWN) );
		}
		
		if(factura.getAnulada()){
			datoListaReporte.setDestinatario( "ANULADO" );
		}
		return datoListaReporte;
	};


	
	public void crearCSVdeFactura( FacturaPrestacionDTO facturaDTO , HttpServletResponse res ) throws HBDataAccessException, HBObjectNotExistsException, HBServiceException
	{
		try 
		{
			FacturaPrestacion factura = facturaPrestacionDAO.obtener( facturaDTO.getId() );			
			
			ArchivoCSV archivo = new ArchivoCSV();
			archivo.setRegistros( new ArrayList<RegistroArchivoCSV>() );
			archivo.setFinLinea( ArchivoCSV.FIN_LINEA_UNIX );
			archivo.setSeparadorCampo( ArchivoCSV.SEPARADOR_PUNTO_COMA );
			
			for( PrestacionBrindadaFacturada pbf: factura.getPrestacionesBrindadasFacturadas() )
			{
				RegistroArchivoCSV registro = new RegistroArchivoCSV();
				registro.setCampos( new ArrayList<CampoRegistroArchivoCSV>() );
				
				//Campos fecha de Orden y de prestacion ------------------------------------------------ 
				CampoRegistroArchivoCSV campo = new CampoRegistroArchivoCSV();
				
				SimpleDateFormat formatoFecha = new SimpleDateFormat( FORMATO_AAAAMMDD );
				String fecha = formatoFecha.format( pbf.getPrestacionBrindada().getFecha()  );
				
				campo.setDato( fecha );
				campo.setAlineacion( CampoRegistroArchivoCSV.ALINEACION_DERECHA );
				campo.setLongitud(8);
				campo.setTipoRelleno( CampoRegistroArchivoCSV.TIPO_RELLENO_BLANCO );
				
				//Los dos primeros son iguales. Luego Mary los controla.
				registro.getCampos().add(campo);
				registro.getCampos().add(campo);
				
				//Campo nro de credencial --------------------------------------------------------------
				
				campo = new CampoRegistroArchivoCSV();
				campo.setAlineacion( CampoRegistroArchivoCSV.ALINEACION_DERECHA );
				campo.setLongitud(13);
				campo.setTipoRelleno( CampoRegistroArchivoCSV.TIPO_RELLENO_CERO );
				
				if( factura.getObraSocialFacturada().getTipoFactura().equals( TipoFactura.TOTALIZADA.getTipo()) )
				{
					//Totalizada: En la misma factura hay una linea por paciente
					campo.setDato( pbf.getPacienteFacturado().getNumAfiliadoOSActual() );
				}
				else
				{
					//Detallada: La factura es de un paciente solo 
					campo.setDato( factura.getPacienteFacturado().getNumAfiliadoOSActual() );
				}
				
				registro.getCampos().add(campo);
				
				//Codigo de Prestacion --------------------------------------------------------------
				
				campo = new CampoRegistroArchivoCSV();
				campo.setAlineacion( CampoRegistroArchivoCSV.ALINEACION_DERECHA );
				campo.setLongitud(8);
				campo.setTipoRelleno( CampoRegistroArchivoCSV.TIPO_RELLENO_CERO );
				campo.setDato( pbf.getPrestacionBrindada().getCodigo().toString() );
				
				registro.getCampos().add(campo);
				
				//Cantidad de Prestaciones --------------------------------------------------------------
				
				campo = new CampoRegistroArchivoCSV();
				campo.setAlineacion( CampoRegistroArchivoCSV.ALINEACION_DERECHA );
				campo.setLongitud(2);
				campo.setTipoRelleno( CampoRegistroArchivoCSV.TIPO_RELLENO_CERO );
				campo.setDato( pbf.getCantidad().toString() );
				
				registro.getCampos().add(campo);
				
				//Matricula del profesional --------------------------------------------------------------
				
				campo = new CampoRegistroArchivoCSV();
				campo.setAlineacion( CampoRegistroArchivoCSV.ALINEACION_DERECHA );
				campo.setLongitud(6);
				campo.setTipoRelleno( CampoRegistroArchivoCSV.TIPO_RELLENO_CERO );
				
				if( pbf.getPrestacionBrindada().getProfesional()==null )
					campo.setDato( "000000" );				
				else
					campo.setDato( pbf.getPrestacionBrindada().getProfesional() );
				
				registro.getCampos().add(campo);
				
				//Precio -------------------------------------------------------------------------------
		
				campo = new CampoRegistroArchivoCSV();
				campo.setAlineacion( CampoRegistroArchivoCSV.ALINEACION_DERECHA );
				campo.setLongitud(10);
				campo.setTipoRelleno( CampoRegistroArchivoCSV.TIPO_RELLENO_CERO );
				campo.setDato( pbf.getImporteUnitario().setScale(2,RoundingMode.HALF_UP).toString().replace(".", ",") );
				
				registro.getCampos().add(campo);
				
				//Listo!
				
				archivo.getRegistros().add(registro);
			}
			
			
			ResultExportacionDTO result = archivoCSVGenerator.generarArchivo(archivo, factura.getNumero() , res);
			
			if( result.getError() ) throw new HBServiceException();
			
		}
		catch (DataAccessException e) {
			logger.error("Se produjo un problema de acceso a la base de datos cuando se creaba el CSV de una factura" , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro la factura cuando se queria crear su CSV " , e );
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
		
		catch( Exception e )
		{
			logger.error("Se produjo una excepcion Runtime al momento de crear un CSV" , e );
			HBServiceException ex = new HBServiceException(e);
			throw ex;
		}
		
	}


	@Override
	public void crearReporteCartaFacturas(FiltroCartaFacturasDTO filtroDTO,
			HttpServletResponse res) throws DataAccessException, ObjectNotFoundException, HBReporteNoDefinido, HBErrorCreandoReporte {
logger.info("Creacion del reporte de facturas emitidas." );
		
		FiltroCartaFacturas filtro = mapper.map(filtroDTO, FiltroCartaFacturas.class );
		filtro.setObraSocial(new ObraSocial(filtroDTO.getObraSocialActual().getCodigo()));
		Collection<FacturaPrestacion> facturasEmitidas = informesDAO.getFacturasPorPeriodoOS(filtro);
		Iterator<FacturaPrestacion> it = facturasEmitidas.iterator();
		

		DatosReporteCartaDeFacturas datosReporte = new DatosReporteCartaDeFacturas();
		Collection<DatoListaReporteCartaDeFacturas> listaDatosaListar = new ArrayList<DatoListaReporteCartaDeFacturas>();
		
		while( it.hasNext() )
		{
			FacturaPrestacion factura = it.next();
			DatoListaReporteCartaDeFacturas datoListaReporte = generarDatoCarta(factura);
			listaDatosaListar.add( datoListaReporte );
		}
		
		ObraSocial os=obraSocialDAO.getObraSocial(filtroDTO.getObraSocialActual().getCodigo());
		Locale locale = new Locale("es");
	    
		Calendar cal=Calendar.getInstance(locale);
		 DateFormat mesF = new SimpleDateFormat("MMMMM", locale);
		
		datosReporte.setFecha("Buenos Aires, "+cal.get(Calendar.DAY_OF_MONTH)+" de "+mesF.format(cal.getTime())+" de "+cal.get(Calendar.YEAR));
		datosReporte.setObraSocialDestino(os.getNombre());
		datosReporte.setDatosAListar( listaDatosaListar );
		datosReporte.setTipoReporte(ExportType.PDF);
		reporteador.exportarReporte(datosReporte, res);
		
	}


	private DatoListaReporteCartaDeFacturas generarDatoCarta(FacturaPrestacion factura) {
		DatoListaReporteCartaDeFacturas datoListaReporte = new DatoListaReporteCartaDeFacturas();
		
		SimpleDateFormat formatoFecha = new SimpleDateFormat( FORMATO_DDMMAAAA );
		datoListaReporte.setFecha( formatoFecha.format(factura.getFechaEmision()) );
		datoListaReporte.setNroFactura( factura.getNumero() );
		
		datoListaReporte.setDestinatario(factura.getPacienteFacturado().getNumAfiliadoOSActual()+"-"+factura.getPacienteFacturado().getNombreyApellido());
		datoListaReporte.setCodigo(factura.getObraSocialFacturada().getCodigoContable());
		datoListaReporte.setMonto(factura.getMontoTotal().setScale(2, BigDecimal.ROUND_DOWN));

		return datoListaReporte;
	}
	

};

