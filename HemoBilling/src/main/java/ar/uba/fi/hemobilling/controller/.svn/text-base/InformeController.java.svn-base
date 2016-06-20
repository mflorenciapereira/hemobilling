package ar.uba.fi.hemobilling.controller;




import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.dto.BasicDTO;
import ar.uba.fi.hemobilling.dto.informes.FiltroCartaFacturasDTO;
import ar.uba.fi.hemobilling.dto.informes.FiltroFacturasEmitidasDTO;
import ar.uba.fi.hemobilling.dto.obrassociales.ObraSocialDTO;
import ar.uba.fi.hemobilling.exception.domain.HBDataAccessException;
import ar.uba.fi.hemobilling.exception.domain.HBErrorCreandoReporte;
import ar.uba.fi.hemobilling.exception.domain.HBReporteNoDefinido;
import ar.uba.fi.hemobilling.mvc.JSONObraSocial;
import ar.uba.fi.hemobilling.service.InformesService;
import ar.uba.fi.hemobilling.service.ObraSocialService;

@Controller
@SessionAttributes({"filtroFacturasEmitidasDTO" ,"filtroPaginadoDTO" })
public class InformeController extends AbstractController{

	@Resource(name = "obraSocialService")
	private ObraSocialService obraSocialService;
	private static final String RESULTOPERACIONDTO = "resultOperacionDTO";
	private static final String JSP_GENERAR_INFORME_FACTURAS_EMITIDAS = "generarInformeFacturasEmitidas";
	private static final String JSP_GENERAR_INFORME_CARTA_FACTURAS = "generarInformeCartaFacturas";
	private static final String FILTRODTO = "filtroFacturasEmitidasDTO";
	private static final String FILTROCFDTO = "filtroCartaFacturaDTO";
	private static final String MENSAJE_ERROR_REPORTE_NO_DEFINIDO = "reportes.reporteIndefinido";
	private static final String MENSAJE_ERROR_CREACION_REPORTE = "reportes.errorCreacion";
	private static final String LISTAPOSIBLESOSDTO = "listaPosiblesOSDTO";	
	
	@Resource(name = "informeService")
	private InformesService informesService;
	

	@RequestMapping(value="/doGenerarInformeFacturasEmitidas.htm",method=RequestMethod.POST)			  
	public ModelAndView doGenerarInformeFacturasEmitidas(@ModelAttribute(FILTRODTO) FiltroFacturasEmitidasDTO filtroDTO,
				 HttpServletRequest req,HttpServletResponse res, HttpSession session )
	{
		ModelAndView mav = new ModelAndView( JSP_GENERAR_INFORME_FACTURAS_EMITIDAS );
		
		BasicDTO resultDTO=new BasicDTO();
		
			try 
			{
				informesService.crearReporteFacturasEmitidas(filtroDTO,res);
				
				return null;
			} 
			catch (HBDataAccessException e) 
			{
				resultDTO.setError(true);
				resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
			} 
			catch (HBReporteNoDefinido e) 
			{	
				resultDTO.setError(true);
				resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_REPORTE_NO_DEFINIDO) );
			} 
			catch (HBErrorCreandoReporte e) 
			{
				resultDTO.setError(true);
				resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_CREACION_REPORTE) );
			}
			
			mav.addObject(RESULTOPERACIONDTO, resultDTO);
			return mav;
	}
	
	@RequestMapping("/generarInformeFacturasEmitidas.htm")
	public ModelAndView generarInformeFacturasEmitidas(HttpServletRequest req,HttpServletResponse res)
	{
		ModelAndView mav = new ModelAndView( JSP_GENERAR_INFORME_FACTURAS_EMITIDAS );
		mav.addObject(FILTRODTO ,  new FiltroFacturasEmitidasDTO());
		return mav;
	};
	
	@RequestMapping("/generarInformeCartaFacturas.htm")
	public ModelAndView generarInformeCartaFacturas(HttpServletRequest req,HttpServletResponse res)
	{
		ModelAndView mav = new ModelAndView( JSP_GENERAR_INFORME_CARTA_FACTURAS );
		mav.addObject(FILTROCFDTO ,  new FiltroCartaFacturasDTO());
		BasicDTO resultDTO = new BasicDTO();
		String listaObrasSocialesPosibles=null;
		try {
			listaObrasSocialesPosibles = generarOSPosibles();
		} catch (HBDataAccessException e) {
			//Problemas levantando los datos. Doy una vacia y reporto
			//Como es un valor obligatorio, muestro la GUI igual. Total, no va a 
			//poder hacer nada
			
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		}
		mav.addObject(LISTAPOSIBLESOSDTO,listaObrasSocialesPosibles);
		return mav;
	};
	

	
	
	
	
	@RequestMapping("/doGenerarInformeFacturasEmitidas.htm")
	public ModelAndView generarInformeFacturasEmitidasMock(HttpServletRequest req,HttpServletResponse res)
	{
		return generarInformeFacturasEmitidas(req,res);
	};
	
	
	
	
	
	@RequestMapping(value="/doGenerarInformeCartaFacturas.htm",method=RequestMethod.POST)			  
	public ModelAndView doGenerarInformeCartaFacturas(@ModelAttribute(FILTROCFDTO) FiltroCartaFacturasDTO filtroDTO,
				 HttpServletRequest req,HttpServletResponse res, HttpSession session )
	{
		ModelAndView mav = new ModelAndView( JSP_GENERAR_INFORME_CARTA_FACTURAS );
		
		BasicDTO resultDTO=new BasicDTO();
		
			try 
			{
				informesService.crearReporteCartaFacturas(filtroDTO,res);
				
				return null;
			} 
			catch (HBReporteNoDefinido e) 
			{	
				resultDTO.setError(true);
				resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_REPORTE_NO_DEFINIDO) );
			} 
			catch (HBErrorCreandoReporte e) 
			{
				resultDTO.setError(true);
				resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_CREACION_REPORTE) );
			} catch (DataAccessException e) {
				resultDTO.setError(true);
				resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
				e.printStackTrace();
			} catch (ObjectNotFoundException e) {
				resultDTO.setError(true);
				resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_SERVICIO) );
			}
			
			mav.addObject(RESULTOPERACIONDTO, resultDTO);
			return mav;
	}
	
	private String generarOSPosibles() throws HBDataAccessException 
	{
		Collection<ObraSocialDTO> listaObrasSociales = obraSocialService.getObrasSocialesDetalladasParaListar();
		
		List<JSONObraSocial> jsons=new ArrayList<JSONObraSocial>();
		Iterator<ObraSocialDTO> it=listaObrasSociales.iterator();
		
		while(it.hasNext())
		{
			ObraSocialDTO os=it.next();
			jsons.add(new JSONObraSocial(os.getCodigo()+" - "+os.getNombre(), os.getCodigo()+"", os.getNombre()));
			
		}
		
		Gson gson=new Gson();
		return gson.toJson(jsons);
		
	}
	
	@RequestMapping(value="/doGenerarInformeCartaFacturas.htm")
	public ModelAndView doGenerarInformeCartaFacturas(HttpServletRequest req,HttpServletResponse res)
	{
		return generarInformeCartaFacturas(req, res);
	}
	
	




}