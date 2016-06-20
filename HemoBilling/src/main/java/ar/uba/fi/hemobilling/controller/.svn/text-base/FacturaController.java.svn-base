package ar.uba.fi.hemobilling.controller;



import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.AutoPopulatingList;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ar.uba.fi.hemobilling.domain.FiltroConsulta;
import ar.uba.fi.hemobilling.dto.BasicDTO;
import ar.uba.fi.hemobilling.dto.FiltroPaginadoDTO;
import ar.uba.fi.hemobilling.dto.clientes.ClienteDTO;
import ar.uba.fi.hemobilling.dto.factura.FacturaPrestacionDTO;
import ar.uba.fi.hemobilling.dto.factura.FacturaServicioDTO;
import ar.uba.fi.hemobilling.dto.factura.FiltroConsultaFacturasPrestacionDTO;
import ar.uba.fi.hemobilling.dto.factura.FiltroConsultaFacturasServicioDTO;
import ar.uba.fi.hemobilling.dto.factura.ResultConsultaFacturacionMasivaPrestacionesDTO;
import ar.uba.fi.hemobilling.dto.obrassociales.ObraSocialDTO;
import ar.uba.fi.hemobilling.exception.domain.HBDataAccessException;
import ar.uba.fi.hemobilling.exception.domain.HBErrorCreandoReporte;
import ar.uba.fi.hemobilling.exception.domain.HBObjectExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBObjectNotExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBReporteNoDefinido;
import ar.uba.fi.hemobilling.exception.domain.HBServiceException;
import ar.uba.fi.hemobilling.mvc.JsonView;
import ar.uba.fi.hemobilling.service.ClienteService;
import ar.uba.fi.hemobilling.service.FacturacionService;
import ar.uba.fi.hemobilling.service.InformesService;
import ar.uba.fi.hemobilling.service.ObraSocialService;

@Controller
@SessionAttributes({"filtroConsultaDTO" ,"filtroPaginadoDTO","facturaServicioDTO" })
public class FacturaController extends AbstractController
{
	private static final String FILTROCONSULTADTO = "filtroConsultaDTO";
	private static final String RESULTOPERACIONDTO = "resultOperacionDTO";
	private static final String RESULTCONSULTADTO = "resultConsultaDTO";
	private static final String FILTROPAGINADODTO = "filtroPaginadoDTO";
	
	private static final String LISTA_OBRAS_SOCIALES_POSIBLES_DTO = "osPosiblesDTO";
	
	private static final String RESULTPREVISUALIZARFACTURASDTO = "resultPrevisualizarFacturasDTO";

	private static final String FACTURAPRESTACIONDTO = "facturaPrestacionDTO";
	private static final String FACTURASERVICIODTO = "facturaServicioDTO";
	private static final String CLIENTESPOSIBLESDTO = "clientesPosiblesDTO";
	
	private static final String JSP_FACTURAS_PRESTACIONES = "facturasPrestaciones";
	private static final String JSP_FACTURAR_PRESTACIONES = "facturarPrestaciones";

	private static final String JSP_FACTURAS_SERVICIOS = "facturasServicios";
	private static final String JSP_FACTURAR_SERVICIOS = "facturarServicios";
	
	private static final String MENSAJE_EXITO_FACTURACION = "facturacion.prestacionesFacturadasExito";
	private static final String MENSAJE_EXITO_FACTURACION_SERVICIOS = "facturacion.serviciosFacturadosExito";
	private static final String MENSAJE_EXITO_ELIMINACION_FACTURAS = "facturacion.eliminacionExito";
	private static final String MENSAJE_ERROR_SISTEMA_REPORTE = "reportes.errorCreacion";
	
	private static final String MENSAJE_ANULACION_OK = "facturacion.anulacionExito";
	private static final String MENSAJE_DESANULACION_OK = "facturacion.noAnulacionExito";
	
	
	@Resource(name = "facturacionService")
	private FacturacionService facturacionService;
	
	@Resource(name = "obraSocialService")
	private ObraSocialService obraSocialService;

	@Resource(name = "clienteService")
	private ClienteService clienteService;
	
	@Resource(name = "informeService")
	private InformesService informeService;
	
	
	private FiltroPaginadoDTO getFiltroPaginadoBase()
	{
		FiltroPaginadoDTO filtro = new FiltroPaginadoDTO();
		
		filtro.setNumeroPaginaActual(1);
		filtro.setRegPorPagina(cantidadRegistrosPorPagina);
		filtro.setError(false);
		
		return filtro;
	}
	
	
	
	
	/*
	 * METODOS DE CONSULTA DE FACTURAS DE PRESTACIONES BRINDADAS
	 */
	
	private FiltroConsultaFacturasPrestacionDTO getFiltroBaseFacturasPrestacionDTO()
	{
		FiltroConsultaFacturasPrestacionDTO filtro = new FiltroConsultaFacturasPrestacionDTO();
		
		filtro.setFechaDesde(null);
		filtro.setFechaHasta(null);
		filtro.setNumero( FiltroConsulta.TODOS_DESC );
		
		return filtro;		
	}
	
	private ModelAndView getPaginaConsultaFacturasPrestaciones( FiltroConsultaFacturasPrestacionDTO filtroDTO, FiltroPaginadoDTO paginadoDTO , BasicDTO resultDTO  )
	{
		ModelAndView mav = new ModelAndView( JSP_FACTURAS_PRESTACIONES );
		
		if( filtroDTO==null )
		{
			filtroDTO = getFiltroBaseFacturasPrestacionDTO();
		}
		else
		{
			if( filtroDTO.getFechaDesde()!=null && filtroDTO.getFechaDesde().isEmpty() )
				filtroDTO.setFechaDesde( null );
			
			if( filtroDTO.getFechaHasta()!=null && filtroDTO.getFechaHasta().isEmpty() )
				filtroDTO.setFechaHasta( null );
			
			if( filtroDTO.getNumero()!=null && filtroDTO.getNumero().isEmpty() )
				filtroDTO.setNumero( FiltroConsulta.TODOS_DESC );
		}
		
		if( resultDTO==null )
		{
			resultDTO = new BasicDTO();
		}
		
		if( paginadoDTO==null )
		{
			paginadoDTO = getFiltroPaginadoBase();
		}
		
		Collection<ObraSocialDTO> osPosibles = null;
		
		try 
		{
			osPosibles = obraSocialService.getObrasSocialesParaListar();
		}
		catch (HBDataAccessException e) 
		{
			osPosibles = new ArrayList<ObraSocialDTO>();
			
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		}
		
		Collection<FacturaPrestacionDTO> facturasDTO = null;
		try 
		{
			facturasDTO = facturacionService.consultarFacturasPrestaciones(filtroDTO, paginadoDTO);
			filtroDTO.setObrasSociales(new AutoPopulatingList(ObraSocialDTO.class) );
		}
		catch (HBDataAccessException e) 
		{
			facturasDTO = new ArrayList<FacturaPrestacionDTO>();
			
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		}
		
		
		mav.addObject(FILTROCONSULTADTO , filtroDTO );
		mav.addObject(LISTA_OBRAS_SOCIALES_POSIBLES_DTO , osPosibles );
		mav.addObject(RESULTOPERACIONDTO , resultDTO );
		mav.addObject(RESULTCONSULTADTO , facturasDTO );
		mav.addObject(FILTROPAGINADODTO , paginadoDTO );
		mav.addObject( FACTURAPRESTACIONDTO , new FacturaPrestacionDTO() );
		
		return mav;
	}
	
	
	@RequestMapping("/consultarFacturasPrestaciones.htm")
	public ModelAndView consultarFacturasPrestaciones( HttpServletRequest req,HttpSession session )
	{
		return getPaginaConsultaFacturasPrestaciones(null, null, null);
	}
	
	
	@RequestMapping(value="/refreshConsultaFacturasPrestaciones.htm" ,method=RequestMethod.POST )
	public ModelAndView refreshConsultaFacturasPrestaciones( @ModelAttribute(FILTROCONSULTADTO) FiltroConsultaFacturasPrestacionDTO filtroDTO,
			  							 @ModelAttribute(FILTROPAGINADODTO) FiltroPaginadoDTO filtroPaginadoDTO,
			  							 HttpServletRequest req,HttpSession session )
	{
		return getPaginaConsultaFacturasPrestaciones(filtroDTO, filtroPaginadoDTO, null);
	}
	

	@RequestMapping(value="/refreshConsultaFacturasPrestaciones.htm" )
	public ModelAndView refreshConsultaFPMock(HttpServletRequest req,HttpSession session )
	{
		return getPaginaConsultaFacturasPrestaciones(null, null, null);
	}
	
	
	/*
	 * METODOS DE CONSULTA DE DATOS DE FP
	 */
	
	@RequestMapping(value="/verDatosPantalla.htm" ,method=RequestMethod.POST )
	public ModelAndView verDatosPantalla( @ModelAttribute(FACTURAPRESTACIONDTO) FacturaPrestacionDTO facturaPrestacionDTO, 
			  							 HttpServletRequest req,HttpSession session )
	{
		ModelAndView mav = new ModelAndView( JsonView.instance );
		
		FacturaPrestacionDTO fpDTO = new FacturaPrestacionDTO();
		try 
		{
			fpDTO = facturacionService.obtenerFacturaPrestacion(facturaPrestacionDTO.getId() );
			fpDTO.setError(false);
		} 
		catch (HBDataAccessException e) 
		{
			fpDTO.setError(true);
			fpDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		}
		catch (HBObjectNotExistsException e) {
			fpDTO.setError(true);
			fpDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		}
		
		mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(fpDTO));
		
		return mav;
	}
	
	@RequestMapping(value="/verDatosPantalla.htm" )
	public ModelAndView verDatosPantallaMock( HttpServletRequest req,HttpSession session )
	{
		return getPaginaConsultaFacturasPrestaciones(null, null, null);
	}
	
	
	
	/*
	 * METODOS DE CONSULTA DE FACTURAS DE SERVICIOS BRINDADOS
	 */
	
	private FiltroConsultaFacturasServicioDTO getFiltroBaseFacturasServicioDTO()
	{
		FiltroConsultaFacturasServicioDTO filtro = new FiltroConsultaFacturasServicioDTO();
		
		filtro.setFechaDesde(null);
		filtro.setFechaHasta(null);
		filtro.setNumero( FiltroConsulta.TODOS_DESC );
		
		return filtro;		
	}
	
	private ModelAndView getPaginaConsultaFacturasServicios( FiltroConsultaFacturasServicioDTO filtroDTO, FiltroPaginadoDTO paginadoDTO , BasicDTO resultDTO  )
	{
		ModelAndView mav = new ModelAndView( JSP_FACTURAS_SERVICIOS );
		
		if( filtroDTO==null )
		{
			filtroDTO = getFiltroBaseFacturasServicioDTO();
		}
		else
		{
			if( filtroDTO.getFechaDesde()!=null && filtroDTO.getFechaDesde().isEmpty() )
				filtroDTO.setFechaDesde( null );
			
			if( filtroDTO.getFechaHasta() !=null && filtroDTO.getFechaHasta().isEmpty() )
				filtroDTO.setFechaHasta( null );
			
			if( filtroDTO.getNumero()!=null && filtroDTO.getNumero().isEmpty() )
				filtroDTO.setNumero( FiltroConsulta.TODOS_DESC );
		}
		
		if( resultDTO==null )
		{
			resultDTO = new BasicDTO();
		}
		
		if( paginadoDTO==null )
		{
			paginadoDTO = getFiltroPaginadoBase();
		}
		
		Collection<ClienteDTO> clientesPosibles = null;
		
		try 
		{
			clientesPosibles = clienteService.getClientasParaListar();
		}
		catch (HBDataAccessException e) 
		{
			clientesPosibles = new ArrayList<ClienteDTO>();
			
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		}
		
		Collection<FacturaServicioDTO> facturasDTO = null;
		try 
		{
			facturasDTO = facturacionService.consultarFacturasServicios(filtroDTO, paginadoDTO);
		}
		catch (HBDataAccessException e) 
		{
			facturasDTO = new ArrayList<FacturaServicioDTO>();
			
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		}
		
		
		mav.addObject(FILTROCONSULTADTO , filtroDTO );
		mav.addObject(CLIENTESPOSIBLESDTO , clientesPosibles );
		mav.addObject(RESULTOPERACIONDTO , resultDTO );
		mav.addObject(RESULTCONSULTADTO , facturasDTO );
		mav.addObject(FILTROPAGINADODTO , paginadoDTO );
		mav.addObject(FACTURASERVICIODTO , new FacturaServicioDTO() );
		
		return mav;
	}
	
	@RequestMapping("/consultarFacturasServicios.htm")
	public ModelAndView consultarFS( HttpServletRequest req,HttpSession session )
	{
		return getPaginaConsultaFacturasServicios(null, null, null);
	}
	
	@RequestMapping(value="/refreshConsultaFacturasServicios.htm" ,method=RequestMethod.POST )
	public ModelAndView refreshConsultaFacturasServicios( @ModelAttribute(FILTROCONSULTADTO) FiltroConsultaFacturasServicioDTO filtroDTO,
			  							 @ModelAttribute(FILTROPAGINADODTO) FiltroPaginadoDTO filtroPaginadoDTO,
			  							 HttpServletRequest req,HttpSession session )
	{
		return getPaginaConsultaFacturasServicios(filtroDTO, filtroPaginadoDTO, null);
	}
	

	@RequestMapping(value="/refreshConsultaFacturasServicios.htm" )
	public ModelAndView refreshConsultaFSMock(HttpServletRequest req,HttpSession session )
	{
		return getPaginaConsultaFacturasServicios(null, null, null);
	}
	
	
	
	
	
	
	
	
	/*
	 * METODOS DE IMPORTACION MASIVA DE FACTURAS DE PRESTACIONES BRINDADAS
	 */
	
	@RequestMapping("/facturarPrestaciones.htm")
	public ModelAndView facturar( HttpServletRequest req,HttpSession session )
	{
		ModelAndView mav = new ModelAndView( JSP_FACTURAR_PRESTACIONES );
		
		FiltroConsultaFacturasPrestacionDTO filtroConsulta = new FiltroConsultaFacturasPrestacionDTO();
		Collection<ObraSocialDTO> osPosibles = null;
		
		try 
		{
			osPosibles = obraSocialService.getObrasSocialesParaListar();
		}
		catch (HBDataAccessException e) 
		{
			osPosibles = new ArrayList<ObraSocialDTO>();
		}
		
		ResultConsultaFacturacionMasivaPrestacionesDTO resultDTO = new ResultConsultaFacturacionMasivaPrestacionesDTO();
		
		mav.addObject(FILTROCONSULTADTO , filtroConsulta );
		mav.addObject(LISTA_OBRAS_SOCIALES_POSIBLES_DTO , osPosibles );
		mav.addObject(RESULTPREVISUALIZARFACTURASDTO , resultDTO );
		
		return mav;
	}
	
	@RequestMapping(value="/previsualizarFacturacion.htm" ,method=RequestMethod.POST)
	public ModelAndView previsualizarFacturacion( @ModelAttribute(FILTROCONSULTADTO) FiltroConsultaFacturasPrestacionDTO filtroDTO, 
												  HttpServletRequest req,HttpSession session )
	{
		ModelAndView mav = new ModelAndView( JsonView.instance );
		
		ResultConsultaFacturacionMasivaPrestacionesDTO resultDTO = new ResultConsultaFacturacionMasivaPrestacionesDTO();
		try 
		{
			resultDTO = facturacionService.getFacturacionPrestaciones(filtroDTO);
			resultDTO.setError(false);
			filtroDTO.setObrasSociales( new AutoPopulatingList(ObraSocialDTO.class) );
		} 
		catch (HBDataAccessException e) 
		{
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		}
		
		mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(resultDTO));
		
		return mav;
	}

	@RequestMapping(value="/doFacturar.htm" ,method=RequestMethod.POST)
	public ModelAndView doFacturar( @ModelAttribute(RESULTPREVISUALIZARFACTURASDTO) ResultConsultaFacturacionMasivaPrestacionesDTO facturasSeleccionadasDTO, 
									HttpServletRequest req,HttpSession session )
	{
		ModelAndView mav = new ModelAndView( JsonView.instance );
		BasicDTO result = new BasicDTO();
		
		try 
		{
			facturacionService.agregarFacturasPrestaciones(facturasSeleccionadasDTO);
			result.setError(false);
			result.setErrorMessage( messageSupport.getProperty(MENSAJE_EXITO_FACTURACION) );
		} 
		catch (HBDataAccessException e) 
		{
			result.setError(true);
			result.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		}
		catch (HBObjectExistsException e) {
			result.setError(true);
			result.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		}
		
		mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(result));
		
		return mav;
	}
	
	
	@RequestMapping(value="/previsualizarFacturacion.htm")
	public ModelAndView previsualizarFacturacionMock( HttpServletRequest req,HttpSession session )
	{
		return facturar(req,session);
	}
	
	@RequestMapping(value="/doFacturar.htm")
	public ModelAndView doFacturarMock( HttpServletRequest req,HttpSession session )
	{
		return facturar(req,session);
	}
	
	
	
	
	
	
	
	
	
	/*
	 * METODOS DE FACTURACION DE SERVICIOS BRINDADOS
	 */
	
	@RequestMapping("/facturarServicios.htm")
	public ModelAndView facturarServicio( HttpServletRequest req,HttpSession session )
	{
		ModelAndView mav = new ModelAndView( JSP_FACTURAR_SERVICIOS );
		
		Collection<ClienteDTO> clientesPosibles = null;
		BasicDTO resultadoOperacion = new BasicDTO();
		
		try
		{
			clientesPosibles = clienteService.getClientasParaListar();
		}
		catch( HBDataAccessException e )
		{
			clientesPosibles = new ArrayList<ClienteDTO>();
			resultadoOperacion.setError(true);
			resultadoOperacion.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		}
		
		FacturaServicioDTO facturaDTO = new FacturaServicioDTO();
		
		mav.addObject(FACTURASERVICIODTO,facturaDTO );
		mav.addObject(CLIENTESPOSIBLESDTO,clientesPosibles );
		mav.addObject(RESULTOPERACIONDTO,resultadoOperacion );
		
		return mav;
	}
	
	
	@RequestMapping(value="/doFacturarServicio.htm" ,method=RequestMethod.POST)
	public ModelAndView dofacturarServicio( @ModelAttribute(FACTURASERVICIODTO) FacturaServicioDTO facturaServicioDTO , HttpServletRequest req,HttpSession session )
	{
		ModelAndView mav = new ModelAndView( JsonView.instance );
		
		BasicDTO resultDTO = new BasicDTO();
		try 
		{
			facturacionService.agregarFacturaServicio(facturaServicioDTO);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_EXITO_FACTURACION_SERVICIOS) );
			resultDTO.setError(false);
		} 
		catch (HBDataAccessException e) 
		{
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		}
		catch (HBObjectExistsException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		}
		
		mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(resultDTO));
		
		return mav;
	}
	
	
	@RequestMapping(value="/doFacturarServicio.htm" )
	public ModelAndView dofacturarServicioMock(  HttpServletRequest req,HttpSession session )
	{
		return facturarServicio(req,session);
	}
	
	
	
	
	/*METODOS DE ELIMINACION DE FACTURAS DE SERVICIOS ------------------------------------------------------- */
	
	@RequestMapping(value="/doEliminarFacturaServicios.htm",method=RequestMethod.POST)
	public ModelAndView doEliminarFacturaServicios( @ModelAttribute(FACTURASERVICIODTO) FacturaServicioDTO facturaServicioDTO , HttpServletRequest req,HttpSession session )
	{
		BasicDTO resultDTO = new BasicDTO();
		
		try 
		{
			facturacionService.eliminarFS(facturaServicioDTO);
			
			resultDTO.setError(false);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_EXITO_ELIMINACION_FACTURAS) );
		}
		catch (HBDataAccessException e) 
		{
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		}
		catch (HBObjectNotExistsException e)
		{
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		}
		
		return getPaginaConsultaFacturasServicios(null, null, resultDTO);
	}
	
	@RequestMapping(value="/doEliminarFacturaServicios.htm" )
	public ModelAndView doEliminarFacturaServiciosMock( HttpServletRequest req,HttpSession session )
	{
		return getPaginaConsultaFacturasServicios(null, null, null);
	}
	
	
	
	/*METODOS DE ELIMINACION DE FACTURAS DE PRESTACIONES ------------------------------------------------------- */
	
	@RequestMapping(value="/doEliminarFacturaPrestaciones.htm",method=RequestMethod.POST)
	public ModelAndView doEliminarFacturaPrestaciones( @ModelAttribute(FACTURAPRESTACIONDTO) FacturaPrestacionDTO facturaPrestacionDTO , HttpServletRequest req,HttpSession session )
	{
		BasicDTO resultDTO = new BasicDTO();
		
		try 
		{
			facturacionService.eliminarFP(facturaPrestacionDTO);
			
			resultDTO.setError(false);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_EXITO_ELIMINACION_FACTURAS) );
		}
		catch (HBDataAccessException e) 
		{
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		}
		catch (HBObjectNotExistsException e)
		{
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		}
		
		return getPaginaConsultaFacturasPrestaciones(null, null, resultDTO);
	}
	
	@RequestMapping(value="/doEliminarFacturaPrestaciones.htm" )
	public ModelAndView doEliminarFacturaPrestacionesMock( HttpServletRequest req,HttpSession session )
	{
		return getPaginaConsultaFacturasPrestaciones(null, null, null);
	}
	
	
	
	
	/*METODOS PARA GENERAR EL CSV DE FACTURA DE PRESTACION ------------------------------------------------------- */
	
	@RequestMapping(value="/doGenerarCSVFacturaPrestacion.htm",method=RequestMethod.POST)
	public ModelAndView doGenerarCSVFacturaPrestacion(  @ModelAttribute(FACTURAPRESTACIONDTO) FacturaPrestacionDTO facturaPrestacionDTO, 
														@ModelAttribute(FILTROCONSULTADTO) FiltroConsultaFacturasPrestacionDTO filtroDTO,
														 @ModelAttribute(FILTROPAGINADODTO) FiltroPaginadoDTO filtroPaginadoDTO,
														HttpServletRequest req,HttpSession session, HttpServletResponse res ) 
	{
		BasicDTO resultDTO = new BasicDTO();
		
		try 
		{
			informeService.crearCSVdeFactura(facturaPrestacionDTO , res);
			
			return null;
		}
		catch (HBDataAccessException e) 
		{
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
			
			return getPaginaConsultaFacturasPrestaciones(filtroDTO, filtroPaginadoDTO, resultDTO);
		}
		catch (HBObjectNotExistsException e)
		{
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
			
			return getPaginaConsultaFacturasPrestaciones(filtroDTO, filtroPaginadoDTO, resultDTO);
		}
		catch (HBServiceException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_SERVICIO));
			
			return getPaginaConsultaFacturasPrestaciones(filtroDTO, filtroPaginadoDTO, resultDTO);
		}
	}
	
	@RequestMapping(value="/doGenerarCSVFacturaPrestacion.htm" )
	public ModelAndView doGenerarCSVMock( HttpServletRequest req,HttpSession session )
	{
		return getPaginaConsultaFacturasPrestaciones(null, null, null);
	}
	
	
	
	
	
	
	/*METODOS DE IMPRESION DE FACTURAS ------------------------------------------------------- */
	
	@RequestMapping(value = "/vistaPreviaFacturaPrestacion.htm", method = RequestMethod.POST)
	public ModelAndView vistaPreviaFacturaPrestacion(  @ModelAttribute(FILTROCONSULTADTO) FiltroConsultaFacturasPrestacionDTO filtroDTO,
				 @ModelAttribute(FILTROPAGINADODTO) FiltroPaginadoDTO filtroPaginadoDTO,
			@ModelAttribute(FACTURAPRESTACIONDTO) FacturaPrestacionDTO filtroFacturaDTO,
			HttpServletRequest req,HttpServletResponse res, HttpSession session) {
		

		try 
		{
			facturacionService.crearReporteFacturasConFondo(filtroFacturaDTO,res);
			return null;
		} 
		catch (HBReporteNoDefinido e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_SISTEMA_REPORTE) );
			
			return getPaginaConsultaFacturasPrestaciones(filtroDTO, filtroPaginadoDTO, resultDTO);
		} 
		catch (HBErrorCreandoReporte e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_SISTEMA_REPORTE) );
			
			return getPaginaConsultaFacturasPrestaciones(filtroDTO, filtroPaginadoDTO, resultDTO);
		} 
		catch (HBDataAccessException e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
			
			return getPaginaConsultaFacturasPrestaciones(filtroDTO, filtroPaginadoDTO, resultDTO);
		} 
		catch (HBObjectNotExistsException e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
			
			return getPaginaConsultaFacturasPrestaciones(filtroDTO, filtroPaginadoDTO, resultDTO);
		}
	}
	
	@RequestMapping(value="/vistaPreviaFacturaPrestacion.htm" )
	public ModelAndView vistaPreviaFacturaPrestacionMock( HttpServletRequest req,HttpSession session )
	{
		return getPaginaConsultaFacturasPrestaciones(null, null, null);
	}
	
	
	@RequestMapping(value = "/imprimirFacturasPrestacion.htm", method = RequestMethod.POST)
	public ModelAndView imprimirFacturasPrestacion( @ModelAttribute(FILTROCONSULTADTO) FiltroConsultaFacturasPrestacionDTO filtroDTO,
										  @ModelAttribute(FILTROPAGINADODTO) FiltroPaginadoDTO filtroPaginadoDTO,
										  HttpServletRequest req,HttpServletResponse res, HttpSession session) 
	{	
		try 
		{
			String[] facturas=req.getParameterValues("facturasSeleccionadas");
			facturacionService.crearReporteFacturasPrestacion(facturas,res);
			return null;
		}
		catch (HBReporteNoDefinido e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_SISTEMA_REPORTE) );
			
			return getPaginaConsultaFacturasPrestaciones(filtroDTO, filtroPaginadoDTO, resultDTO);
		} 
		catch (HBErrorCreandoReporte e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_SISTEMA_REPORTE) );
			
			return getPaginaConsultaFacturasPrestaciones(filtroDTO, filtroPaginadoDTO, resultDTO);
		} 
		catch (HBDataAccessException e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
			
			return getPaginaConsultaFacturasPrestaciones(filtroDTO, filtroPaginadoDTO, resultDTO);
		} 
		catch (HBObjectNotExistsException e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
			
			return getPaginaConsultaFacturasPrestaciones(filtroDTO, filtroPaginadoDTO, resultDTO);
		}
	}
	
	@RequestMapping(value="/imprimirFacturasPrestacion.htm" )
	public ModelAndView imprimirFacturasPrestacionesMock( HttpServletRequest req,HttpSession session )
	{
		return getPaginaConsultaFacturasPrestaciones(null, null, null);
	}
	
	
	@RequestMapping(value = "/imprimirFacturasServicios.htm", method = RequestMethod.POST)
	public ModelAndView imprimirFacturasServicios( @ModelAttribute(FILTROCONSULTADTO) FiltroConsultaFacturasServicioDTO filtroDTO,
										  @ModelAttribute(FILTROPAGINADODTO) FiltroPaginadoDTO filtroPaginadoDTO,
										  HttpServletRequest req,HttpServletResponse res, HttpSession session) 
	{	
		try 
		{
			String[] facturas=req.getParameterValues("facturasSeleccionadas");
			facturacionService.crearReporteFacturasServicio(facturas,res);
			return null;
		}
		catch (HBReporteNoDefinido e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_SISTEMA_REPORTE) );
			
			return getPaginaConsultaFacturasServicios(filtroDTO, filtroPaginadoDTO, resultDTO);
		} 
		catch (HBErrorCreandoReporte e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_SISTEMA_REPORTE) );
			
			return getPaginaConsultaFacturasServicios(filtroDTO, filtroPaginadoDTO, resultDTO);
		} 
		catch (HBDataAccessException e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
			
			return getPaginaConsultaFacturasServicios(filtroDTO, filtroPaginadoDTO, resultDTO);
		} 
		catch (HBObjectNotExistsException e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
			
			return getPaginaConsultaFacturasServicios(filtroDTO, filtroPaginadoDTO, resultDTO);
		}
	}
	
	@RequestMapping(value="/imprimirFacturasServicios.htm" )
	public ModelAndView imprimirFacturasServiciosMock( HttpServletRequest req,HttpSession session )
	{
		return getPaginaConsultaFacturasServicios(null, null, null);
	}
	
	
	
	
	@RequestMapping(value = "/vistaPreviaFacturaServicio.htm", method = RequestMethod.POST)
	public ModelAndView vistaPreviaFacturaServicio( @ModelAttribute(FILTROCONSULTADTO) FiltroConsultaFacturasServicioDTO filtroDTO,
				 									@ModelAttribute(FILTROPAGINADODTO) FiltroPaginadoDTO filtroPaginadoDTO, 
				 									@ModelAttribute(FACTURASERVICIODTO) FacturaServicioDTO filtroFacturaDTO,
				 									HttpServletRequest req,HttpServletResponse res, HttpSession session) 
	{
		try 
		{
			facturacionService.crearReporteFacturasConFondo(filtroFacturaDTO,res);
			return null;
		} 
		catch (HBReporteNoDefinido e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_SISTEMA_REPORTE) );
			
			return getPaginaConsultaFacturasServicios(filtroDTO, filtroPaginadoDTO, resultDTO);
		} 
		catch (HBErrorCreandoReporte e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_SISTEMA_REPORTE) );
			
			return getPaginaConsultaFacturasServicios(filtroDTO, filtroPaginadoDTO, resultDTO);
		} 
		catch (HBDataAccessException e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
			
			return getPaginaConsultaFacturasServicios(filtroDTO, filtroPaginadoDTO, resultDTO);
		} 
		catch (HBObjectNotExistsException e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
			
			return getPaginaConsultaFacturasServicios(filtroDTO, filtroPaginadoDTO, resultDTO);
		}
	}
	
	@RequestMapping(value="/vistaPreviaFacturaServicio.htm" )
	public ModelAndView vistaPreviaFacturaServicioMock( HttpServletRequest req,HttpSession session )
	{
		return getPaginaConsultaFacturasServicios(null, null, null);
	}
	
	
	
	
	@RequestMapping(value = "/detalleFacturaPrestacion.htm", method = RequestMethod.POST)
	public ModelAndView detalleFacturaPrestacion(   @ModelAttribute(FILTROCONSULTADTO) FiltroConsultaFacturasPrestacionDTO filtroDTO,
													@ModelAttribute(FILTROPAGINADODTO) FiltroPaginadoDTO filtroPaginadoDTO,
													@ModelAttribute(FACTURAPRESTACIONDTO) FacturaPrestacionDTO filtroFacturaDTO,
													HttpServletRequest req,HttpServletResponse res, HttpSession session) 
	{	
		try 
		{
			facturacionService.crearReporteDetalle(filtroFacturaDTO,res);
			return null;
		}
		catch (HBReporteNoDefinido e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_SISTEMA_REPORTE) );
			
			return getPaginaConsultaFacturasPrestaciones(filtroDTO, filtroPaginadoDTO, resultDTO);
		} 
		catch (HBErrorCreandoReporte e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_SISTEMA_REPORTE) );
			
			return getPaginaConsultaFacturasPrestaciones(filtroDTO, filtroPaginadoDTO, resultDTO);
		} 
		catch (HBDataAccessException e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
			
			return getPaginaConsultaFacturasPrestaciones(filtroDTO, filtroPaginadoDTO, resultDTO);
		} 
		catch (HBObjectNotExistsException e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
			
			return getPaginaConsultaFacturasPrestaciones(filtroDTO, filtroPaginadoDTO, resultDTO);
		}
	}
	
	@RequestMapping(value="/detalleFacturaPrestacion.htm" )
	public ModelAndView detalleFacturaPrestacionMock( HttpServletRequest req,HttpSession session )
	{
		return getPaginaConsultaFacturasPrestaciones(null, null, null);
	}
	
	
	@RequestMapping(value = "/resumenFacturaPrestacion.htm", method = RequestMethod.POST)
	public ModelAndView resumenFacturaPrestacion( @ModelAttribute(FILTROCONSULTADTO) FiltroConsultaFacturasPrestacionDTO filtroDTO,
												@ModelAttribute(FILTROPAGINADODTO) FiltroPaginadoDTO filtroPaginadoDTO,
												@ModelAttribute(FACTURAPRESTACIONDTO) FacturaPrestacionDTO filtroFacturaDTO,
												HttpServletRequest req,HttpServletResponse res, HttpSession session) 
	{
		try 
		{
			facturacionService.crearReporteResumen(filtroFacturaDTO,res);
			return null;
		}
		catch (HBReporteNoDefinido e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_SISTEMA_REPORTE) );
			
			return getPaginaConsultaFacturasPrestaciones(filtroDTO, filtroPaginadoDTO, resultDTO);
		} 
		catch (HBErrorCreandoReporte e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_SISTEMA_REPORTE) );
			
			return getPaginaConsultaFacturasPrestaciones(filtroDTO, filtroPaginadoDTO, resultDTO);
		} 
		catch (HBDataAccessException e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
			
			return getPaginaConsultaFacturasPrestaciones(filtroDTO, filtroPaginadoDTO, resultDTO);
		} 
		catch (HBObjectNotExistsException e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
			
			return getPaginaConsultaFacturasPrestaciones(filtroDTO, filtroPaginadoDTO, resultDTO);
		}
	}
	
	@RequestMapping(value="/resumenFacturaPrestacion.htm" )
	public ModelAndView resumenFacturaPrestacionMock( HttpServletRequest req,HttpSession session )
	{
		return getPaginaConsultaFacturasPrestaciones(null, null, null);
	}
	
	
	//ANULACION DE FACTURAS ---------------------------------------------------------------------------------
	
	@RequestMapping(value = "/anularFacturaServicio.htm", method = RequestMethod.POST)
	public ModelAndView anularFacturaServicio(  @ModelAttribute(FILTROCONSULTADTO) FiltroConsultaFacturasServicioDTO filtroDTO,
												@ModelAttribute(FILTROPAGINADODTO) FiltroPaginadoDTO filtroPaginadoDTO, 
												@ModelAttribute(FACTURASERVICIODTO) FacturaServicioDTO facturaDTO,
												HttpServletRequest req,HttpServletResponse res, HttpSession session	) 
	{
		BasicDTO resultDTO = new BasicDTO();
		
		try {
			facturacionService.anularFacturaServicio(facturaDTO);
			
			resultDTO.setError(false);
			
			if( facturaDTO.getAnulada() )
				resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ANULACION_OK ) );
			else
				resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_DESANULACION_OK ) );
		}
		catch (HBDataAccessException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		}
		catch (HBObjectNotExistsException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		}
		
		return getPaginaConsultaFacturasServicios(filtroDTO, filtroPaginadoDTO, resultDTO);	
	}
	
	@RequestMapping(value="/anularFacturaServicio.htm" )
	public ModelAndView anularFacturaServicioMock( HttpServletRequest req,HttpSession session )
	{
		return getPaginaConsultaFacturasServicios(null, null, null);
	}
	

	@RequestMapping(value = "/anularFacturaPrestacion.htm", method = RequestMethod.POST)
	public ModelAndView anularFacturaPrestacion( @ModelAttribute(FILTROCONSULTADTO) FiltroConsultaFacturasPrestacionDTO filtroDTO,
												 @ModelAttribute(FILTROPAGINADODTO) FiltroPaginadoDTO filtroPaginadoDTO,
												 @ModelAttribute(FACTURAPRESTACIONDTO) FacturaPrestacionDTO facturaDTO,
												 HttpServletRequest req,HttpServletResponse res, HttpSession session)
	{
		BasicDTO resultDTO = new BasicDTO();
		
		try {
			facturacionService.anularFacturaPrestacion(facturaDTO);
			
			resultDTO.setError(false);
			
			if( facturaDTO.getAnulada() )
				resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ANULACION_OK ) );
			else
				resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_DESANULACION_OK ) );
		}
		catch (HBDataAccessException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		}
		catch (HBObjectNotExistsException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		}
		
		return getPaginaConsultaFacturasPrestaciones(filtroDTO, filtroPaginadoDTO, resultDTO);	
	}
	
	@RequestMapping(value="/anularFacturaPrestacion.htm" )
	public ModelAndView anularFacturaPrestacionMock( HttpServletRequest req,HttpSession session )
	{
		return getPaginaConsultaFacturasPrestaciones(null, null, null);
	}
	
	/* ------ SINTESIS ---------- */
	
	@RequestMapping(value = "/sintesisFacturaPrestacion.htm", method = RequestMethod.POST)
	public ModelAndView sintesisFacturaPrestacion( @ModelAttribute(FILTROCONSULTADTO) FiltroConsultaFacturasPrestacionDTO filtroDTO,
												@ModelAttribute(FILTROPAGINADODTO) FiltroPaginadoDTO filtroPaginadoDTO,
												@ModelAttribute(FACTURAPRESTACIONDTO) FacturaPrestacionDTO filtroFacturaDTO,
												HttpServletRequest req,HttpServletResponse res, HttpSession session) 
	{
		try 
		{
			facturacionService.crearReporteSintesis(filtroFacturaDTO,res);
			return null;
		}
		catch (HBReporteNoDefinido e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_SISTEMA_REPORTE) );
			
			return getPaginaConsultaFacturasPrestaciones(filtroDTO, filtroPaginadoDTO, resultDTO);
		} 
		catch (HBErrorCreandoReporte e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_SISTEMA_REPORTE) );
			
			return getPaginaConsultaFacturasPrestaciones(filtroDTO, filtroPaginadoDTO, resultDTO);
		} 
		catch (HBDataAccessException e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
			
			return getPaginaConsultaFacturasPrestaciones(filtroDTO, filtroPaginadoDTO, resultDTO);
		} 
		catch (HBObjectNotExistsException e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
			
			return getPaginaConsultaFacturasPrestaciones(filtroDTO, filtroPaginadoDTO, resultDTO);
		}
	}
	
	@RequestMapping(value="/sintesisFacturaPrestacion.htm" )
	public ModelAndView sintesisFacturaPrestacion( HttpServletRequest req,HttpSession session )
	{
		return getPaginaConsultaFacturasPrestaciones(null, null, null);
	}
	
	
	
	
	
	@RequestMapping(value = "/eliminarFacturasPrestacion.htm")
	public ModelAndView eliminarSeleccionadosFacturaPrestacion(
			 @ModelAttribute(FILTROCONSULTADTO) FiltroConsultaFacturasPrestacionDTO filtroDTO,
				@ModelAttribute(FILTROPAGINADODTO) FiltroPaginadoDTO filtroPaginadoDTO,
				@ModelAttribute(FACTURAPRESTACIONDTO) FacturaPrestacionDTO filtroFacturaDTO,
			 
			 HttpServletRequest req,HttpServletResponse res, HttpSession session
			){	
			String facturasSel=req.getParameter("seleccionados");
			String[] facturas=facturasSel.split(",");
			
			Long[] idsFacturas = new Long[facturas.length];  
		    for (int i = 0; i < facturas.length; i++) {  
		    	idsFacturas[i] = Long.valueOf(facturas[i]);  
		    }
			facturacionService.eliminarFacturasPrestacion(idsFacturas);
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(false);
			resultDTO.setErrorMessage("Se eliminaron las facturas exitosamente.");
			return getPaginaConsultaFacturasPrestaciones(filtroDTO, filtroPaginadoDTO, resultDTO);
			
	}
	
	
	
}
