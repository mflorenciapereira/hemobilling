package ar.uba.fi.hemobilling.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.AutoPopulatingList;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ar.uba.fi.hemobilling.dto.BasicDTO;
import ar.uba.fi.hemobilling.dto.FiltroConsultaDTO;
import ar.uba.fi.hemobilling.dto.FiltroPaginadoDTO;
import ar.uba.fi.hemobilling.dto.general.TipoFacturacionDTO;
import ar.uba.fi.hemobilling.dto.general.TipoIVADTO;
import ar.uba.fi.hemobilling.dto.listasprecio.ListaPrecioDTO;
import ar.uba.fi.hemobilling.dto.obrassociales.AsociacionObraSocialListaPrecioDTO;
import ar.uba.fi.hemobilling.dto.obrassociales.FiltroConsultaObrasSocialesDTO;
import ar.uba.fi.hemobilling.dto.obrassociales.ObraSocialDTO;
import ar.uba.fi.hemobilling.exception.domain.HBDataAccessException;
import ar.uba.fi.hemobilling.exception.domain.HBEntityRelationViolation;
import ar.uba.fi.hemobilling.exception.domain.HBObjectExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBObjectNotExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBServiceException;
import ar.uba.fi.hemobilling.mvc.JsonView;
import ar.uba.fi.hemobilling.service.ListaPrecioService;
import ar.uba.fi.hemobilling.service.ObraSocialService;
import ar.uba.fi.hemobilling.service.TipoIVAService;

import com.google.gson.Gson;

@Controller
@SessionAttributes({ "obrasocialDTO", "nuevaobrasocialDTO",	"actualizarobrasocialDTO", "filtroDTO", "filtroPaginadoDTO" })
public class ObrasSocialesController extends AbstractController {
	
	
	@Resource(name = "obraSocialService")
	private ObraSocialService obraSocialService;
	
	@Resource(name = "listaPrecioService")
	private ListaPrecioService listaPrecioService;
	
	@Resource(name = "tipoIVAService")
	private TipoIVAService tipoIVAService;

	private static final String JSP_OBRAS_SOCIALES = "obrassociales";
	private static final String JSP_NUEVA_OBRA_SOCIAL = "nuevaObraSocial";
	private static final String JSP_EDITAR_OBRA_SOCIAL = "editarObraSocial";
	private static final String JSP_VER_HISTORIA_LISTA_PRECIO = "verHistoriaListaPrecio";

	private static final String OBRASOCIALDTO = "obrasocialDTO";
	private static final String NUEVAOBRASOCIALDTO = "nuevaobrasocialDTO";
	private static final String ACTUALIZAROBRASOCIALDTO = "actualizarobrasocialDTO";
	private static final String OBRASSOCIALESESDTO = "obrassocialesDTO";
	private static final String RESULTOPERACIONDTO = "resultOperacionDTO";
	private static final String FILTRODTO = "filtroDTO";
	private static final String FILTROPAGINADODTO = "filtroPaginadoDTO";
	
	private static final String TIPOSIVADTO = "tiposivaDTO";
	private static final String TIPOSFACTURACIONDTO = "tiposFacturacionDTO";

	private static final String MENSAJE_OBRA_SOCIAL_YA_EXISTE = "obrasocial.obrasocialYaExisteEnBD";
	private static final String MENSAJE_OBRA_SOCIAL_NO_EXISTE = "obrasocial.obrasocialNoExisteEnBD";

	private static final String MENSAJE_OBRA_SOCIAL_AGREGADA_OK = "obrasocial.obrasocialAgregadaExitosamente";
	private static final String MENSAJE_OBRA_SOCIAL_EDITADA_OK = "obrasocial.obrasocialEditadaExitosamente";
	private static final String MENSAJE_OBRA_SOCIAL_ELIMINADA_OK = "obrasocial.obrasocialEliminadaExitosamente";
	private static final String MENSAJE_OBRA_SOCIAL_UTILIZADA = "obrasocial.obrasocialAunUtilizada";

	private static final String LISTASPOSIBLESDTO = "listasPosibles";

	private static final String HISTORICOLISTAS = "historicoListas";
	
	private String jsonString="";

	private static Logger logger = Logger.getLogger(ObrasSocialesController.class);	
	
	private Collection<TipoFacturacionDTO> getTiposPosiblesFacturacion()
	{
		Collection<TipoFacturacionDTO> lista = new ArrayList<TipoFacturacionDTO>();
		
		TipoFacturacionDTO tipo = new TipoFacturacionDTO();
		tipo.setClaveTipoFacturacion( "D" );
		tipo.setDescripcion( "Detallada" );
		lista.add(tipo);
		
		tipo = new TipoFacturacionDTO();
		tipo.setClaveTipoFacturacion( "T" );
		tipo.setDescripcion( "Totalizada" );
		lista.add(tipo);
		
		return lista;		
	}
	
	private class JSONListaPrecio{
		
		public JSONListaPrecio(String label, String value, String desc) {
			super();
			this.label = label;
			this.value = value;
			this.desc=desc;
		}
		@SuppressWarnings("unused")
		String label;
		@SuppressWarnings("unused")
		String value;
		@SuppressWarnings("unused")
		String desc;
		
	}
	
	
	
	

	private FiltroConsultaObrasSocialesDTO getFiltroBaseObrasSocialesDTO() {
		FiltroConsultaObrasSocialesDTO filtro = new FiltroConsultaObrasSocialesDTO();

		filtro.setCodigo(FiltroConsultaDTO.TODOS_DESC);
		filtro.setCodigoRNOS(FiltroConsultaDTO.TODOS_DESC);
		filtro.setCuit(FiltroConsultaDTO.TODOS_DESC);
		filtro.setLocalidad(FiltroConsultaDTO.TODOS_DESC);
		filtro.setNombre(FiltroConsultaDTO.TODOS_DESC);
		filtro.setProvincia(FiltroConsultaDTO.TODOS_DESC);
		filtro.setPrestador(FiltroConsultaDTO.TODOS_DESC);
		filtro.setProvincia(FiltroConsultaDTO.TODOS_DESC);
		filtro.setSigla(FiltroConsultaDTO.TODOS_DESC);
		filtro.setTipoIVA(new TipoIVADTO());
		

		return filtro;
	}

	private FiltroPaginadoDTO getFiltroPaginadoBase() {
		FiltroPaginadoDTO filtro = new FiltroPaginadoDTO();

		filtro.setNumeroPaginaActual(1);
		filtro.setRegPorPagina(cantidadRegistrosPorPagina);
		filtro.setError(false);

		return filtro;
	}

	private ModelAndView getPaginaConsulta(
			FiltroConsultaObrasSocialesDTO filtroDTO,
			FiltroPaginadoDTO paginadoDTO, BasicDTO resultDTO) {
		ModelAndView mav = new ModelAndView(JSP_OBRAS_SOCIALES);
		ObraSocialDTO obrasocial = new ObraSocialDTO();

		if (filtroDTO == null)
			filtroDTO = getFiltroBaseObrasSocialesDTO();
		if (paginadoDTO == null)
			paginadoDTO = getFiltroPaginadoBase();
		if (resultDTO == null)
			resultDTO = new BasicDTO();

		Collection<ObraSocialDTO> obrassociales;

		try {
			obrassociales = obraSocialService.getObrasSociales(filtroDTO, paginadoDTO);
			mav.addObject(TIPOSIVADTO, tipoIVAService.getTiposIVA());

		} catch (HBDataAccessException e) {

			obrassociales = new ArrayList<ObraSocialDTO>();
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport
					.getProperty(MENSAJE_ERROR_DB));
		}

		
		mav.addObject(FILTROPAGINADODTO, paginadoDTO);
		mav.addObject(FILTRODTO, filtroDTO);
		mav.addObject(OBRASOCIALDTO, obrasocial);
		mav.addObject(OBRASSOCIALESESDTO, obrassociales);
		mav.addObject(RESULTOPERACIONDTO, resultDTO);

		return mav;

	}
	
	
	
	

	/*-------- PAGINAS DE CONSULTA ------ */

	@RequestMapping("/obrasSociales.htm")
	public ModelAndView getPaginaBaseListaObrasSociales(HttpServletRequest req,HttpSession session) {
		logger.info("Llega solicitud de lista de obras sociales");
		ModelAndView mav=getPaginaConsulta(null, null, null);
		BasicDTO resultDTO = new BasicDTO();
		try {
			mav.addObject(TIPOSIVADTO, tipoIVAService.getTiposIVA());
		} catch (HBDataAccessException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
		}
		
		return mav;
		
	}

	@RequestMapping(value = "/refreshObrasSociales.htm", method = RequestMethod.POST)
	public ModelAndView refreshListaObrasSociales(
			@ModelAttribute(FILTRODTO) FiltroConsultaObrasSocialesDTO filtroDTO,
			@ModelAttribute(FILTROPAGINADODTO) FiltroPaginadoDTO paginadoDTO,
			HttpServletRequest req, HttpSession session) {
		logger.info("Actualizo la pagina de obras sociales, por medio del filtro o paginado");
		return getPaginaConsulta(filtroDTO, paginadoDTO, null);
	}

	@RequestMapping(value = "/volverObrasSociales.htm", method = RequestMethod.POST)
	public ModelAndView volverObrasSociales(
			@ModelAttribute(RESULTOPERACIONDTO) BasicDTO resultDTO,
			HttpServletRequest req, HttpSession session) {
		logger.info("Actualizacion de la pagina de obras sociales, por medio del filtro o paginado");
		return getPaginaConsulta(null, null, resultDTO);
	}

	/* ------------ NUEVO ------------ */

	@RequestMapping("/nuevaObraSocial.htm")
	public ModelAndView nuevaObraSocial(HttpServletRequest req, HttpSession session) {
		
		
		logger.info("Solicitud de creacion de una nueva obra social.");
		ModelAndView mav = new ModelAndView(JSP_NUEVA_OBRA_SOCIAL);
		ObraSocialDTO nuevoDTO = new ObraSocialDTO();
		BasicDTO resultDTO = new BasicDTO();
		mav.addObject(NUEVAOBRASOCIALDTO, nuevoDTO);
		try {
			
			mav.addObject(TIPOSIVADTO, tipoIVAService.getTiposIVA());
			this.generarListasPosibles();
			mav.addObject(LISTASPOSIBLESDTO,this.jsonString);
		} catch (HBDataAccessException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
		}
		mav.addObject(RESULTOPERACIONDTO, resultDTO);
		mav.addObject(TIPOSFACTURACIONDTO, getTiposPosiblesFacturacion());	

		return mav;
	}

	@RequestMapping(value = "/doNuevaObraSocial.htm", method = RequestMethod.POST)
	public ModelAndView doNuevaObraSocial(
			@ModelAttribute(NUEVAOBRASOCIALDTO) ObraSocialDTO nuevoDTO,
			HttpServletRequest req, HttpSession session) {
		logger.info("Creacion de nueva obra social con codigo " + nuevoDTO.getCodigo());
		if(nuevoDTO.getTipoIVA().getId()==-1) nuevoDTO.setTipoIVA(null);
		BasicDTO resultDTO = new BasicDTO();
		try {

			obraSocialService.agregarObraSocial(nuevoDTO);
			
			resultDTO.setError(false);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_OBRA_SOCIAL_AGREGADA_OK));
		}

		catch (HBDataAccessException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
		}

		catch (HBObjectExistsException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_OBRA_SOCIAL_YA_EXISTE));
		} catch (HBServiceException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_SERVICIO));
		
		}
		ModelAndView mav = new ModelAndView(JsonView.instance);
		mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(resultDTO));

		return mav;
	}

	/* ----------- EDITAR ----------- */

	@RequestMapping(value = "/editarObraSocial.htm", method = RequestMethod.POST)
	public ModelAndView editarObraSocial(@ModelAttribute(OBRASOCIALDTO) ObraSocialDTO objetoDTO,
			HttpServletRequest req, HttpSession session) {
		logger.info("Modificacion de la obra social "+objetoDTO.getCodigo() );

		ModelAndView mav = new ModelAndView(JSP_EDITAR_OBRA_SOCIAL);
		BasicDTO resultDTO = new BasicDTO();
		ObraSocialDTO objetoEditar;
		try {

			this.generarListasPosibles();
			mav.addObject(LISTASPOSIBLESDTO,this.jsonString);
			objetoEditar = obraSocialService.getObraSocial(objetoDTO.getCodigo());
			mav.addObject(TIPOSIVADTO, tipoIVAService.getTiposIVA());
			List<AsociacionObraSocialListaPrecioDTO> asociaciones=obraSocialService.getAsociaciones(objetoDTO.getCodigo());
			if(!asociaciones.isEmpty()) {
				AsociacionObraSocialListaPrecioDTO asociacionActual = asociaciones.get(asociaciones.size()-1);
				if(asociacionActual!=null)objetoEditar.setAsociacionActual(asociacionActual);
				else objetoEditar.setAsociacionActual(new AsociacionObraSocialListaPrecioDTO());
			}
			mav.addObject(HISTORICOLISTAS, asociaciones);

		} catch (HBDataAccessException e) {

			objetoEditar = new ObraSocialDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
		} catch (HBObjectNotExistsException e) {
			objetoEditar = new ObraSocialDTO();

			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_OBRA_SOCIAL_NO_EXISTE));
		}


		mav.addObject(RESULTOPERACIONDTO, resultDTO);
		mav.addObject(ACTUALIZAROBRASOCIALDTO, objetoEditar);
		mav.addObject(TIPOSFACTURACIONDTO, getTiposPosiblesFacturacion());		

		return mav;
	}

	@RequestMapping(value = "/doEditarObraSocial.htm", method = RequestMethod.POST)
	public ModelAndView doEditarObraSocial(
			@ModelAttribute(ACTUALIZAROBRASOCIALDTO) ObraSocialDTO editarDTO,
			HttpServletRequest req, HttpSession session)
	{
		
		return doEditar(editarDTO);
	}

//	private ModelAndView doAsociar(ObraSocialDTO editarDTO, HttpServletRequest req, HttpSession session) {
//		obraSocialService.asociar(editarDTO);
//		BasicDTO resultDTO = new BasicDTO();
//		resultDTO.setError(true);
//		resultDTO.setErrorMessage("Asociacion realizada correctamente");
//		List<AsociacionObraSocialListaPrecioDTO> asociaciones=obraSocialService.getAsociaciones(editarDTO.getCodigo());
//		editarDTO.setAsociacionActual(asociaciones.get(asociaciones.size()-1));
//		ModelAndView mav = new ModelAndView(JsonView.instance);
//		mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(resultDTO));
//		return mav;
//		
//
//	}

	private ModelAndView doEditar(ObraSocialDTO editarDTO)
	{
		logger.info("Modificacion de la obra social "+editarDTO.getCodigo());
		if(editarDTO.getTipoIVA().getId()==-1) editarDTO.setTipoIVA(null);
		BasicDTO resultDTO = new BasicDTO();

		try {
			obraSocialService.actualizarObraSocial(editarDTO);

			resultDTO.setError(false);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_OBRA_SOCIAL_EDITADA_OK));
		}

		catch (HBDataAccessException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
		} catch (HBObjectNotExistsException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_OBRA_SOCIAL_NO_EXISTE));
		} catch (HBServiceException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_SERVICIO));
		}
		catch (HBObjectExistsException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_OBRA_SOCIAL_YA_EXISTE));
		}

		ModelAndView mav = new ModelAndView(JsonView.instance);
		mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(resultDTO));

		return mav;
	}

	/* ----------- ELIMINAR ----------- */

	@RequestMapping(value = "/doEliminarObraSocial.htm", method = RequestMethod.POST)
	public ModelAndView doEliminarObraSocial(
			@ModelAttribute(OBRASOCIALDTO) ObraSocialDTO eliminarDTO,
			HttpServletRequest req, HttpSession session) {
		logger.info("Eliminar obra social con codigo"
				+ eliminarDTO.getCodigo() + " .");

		BasicDTO resultDTO = new BasicDTO();

		try {
			ObraSocialDTO dto = obraSocialService.getObraSocial(eliminarDTO.getCodigo());
			obraSocialService.eliminarObraSocial(dto);

			resultDTO.setError(false);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_OBRA_SOCIAL_ELIMINADA_OK));
		} catch (HBDataAccessException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
		} catch (HBObjectNotExistsException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_OBRA_SOCIAL_NO_EXISTE));
		}
		catch (HBEntityRelationViolation e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport
					.getProperty(MENSAJE_OBRA_SOCIAL_UTILIZADA));
		}

		return this.volverObrasSociales(resultDTO, req, session);

	}



	 @RequestMapping(value="/doEditarObraSocial.htm") //Invocacion Ajax
	 public ModelAndView doEditarObrasSocialesSinPost( HttpServletRequest req ,
	 HttpSession session )
	 {
	 return this.getPaginaBaseListaObrasSociales(req, session);
	 }
	
	 @RequestMapping(value="/editarObraSocial.htm") //Invocacion Ajax
	 public ModelAndView editarPrestacionSinPost( HttpServletRequest req ,
	 HttpSession session )
	 {
	 return this.getPaginaBaseListaObrasSociales(req, session);
	 }
	
	 @RequestMapping(value="/doEliminarObraSocial.htm") //Invocacion sincronica
	 public ModelAndView doEliminarObraSocialSinPost( HttpServletRequest req ,
	 HttpSession session )
	 {
	 
		 return this.getPaginaBaseListaObrasSociales(req, session);
	 }
	
	 @RequestMapping(value="/doNuevaObraSocial.htm") //Invocacion Ajax
	 public ModelAndView doNuevaObraSocialSinPost( HttpServletRequest req ,
	 HttpSession session )
	 {
	 return this.getPaginaBaseListaObrasSociales(req, session);
	 }
	
	 @RequestMapping("/volverObrasSociales.htm") //Invocacion Sincronica
	 public ModelAndView volverObrasSocialesSinPOST( HttpServletRequest req ,
	 HttpSession session )
	 {
	 return this.getPaginaBaseListaObrasSociales(req, session);
	 }
	
	 @RequestMapping("/refreshObrasSociales.htm") //Invocacion Sincronica
	 public ModelAndView refreshObrasSocialesSinPOST( HttpServletRequest req ,
	 HttpSession session )
	 {
	 return this.getPaginaBaseListaObrasSociales(req, session);
	 }
	
	 
	 /* --------- LISTAS DE PRECIO ASOCIADAS ----------- */
		private void generarListasPosibles() throws HBDataAccessException {
			Collection<ListaPrecioDTO> listasPosibles=listaPrecioService.getListasPrecioParaListar();
			List<JSONListaPrecio> jsons=new ArrayList<JSONListaPrecio>();
			Iterator<ListaPrecioDTO> it=listasPosibles.iterator();
			while(it.hasNext()){
				ListaPrecioDTO lista=it.next();
				jsons.add(new JSONListaPrecio(lista.getCodigo()+" - "+lista.getNombre(), lista.getCodigo()+"", lista.getNombre()));
				
			}
			Gson gson=new Gson();
			jsonString=gson.toJson(jsons);
			
		}
		
		
		@RequestMapping(value = "/verHistoriaListaPrecio.htm", method = RequestMethod.POST)
		public ModelAndView verHistoriaListaPrecio(@ModelAttribute(OBRASOCIALDTO) ObraSocialDTO objetoDTO,
				HttpServletRequest req, HttpSession session) {
			logger.info("Historial de asociaciones" );

			ModelAndView mav = new ModelAndView(JSP_VER_HISTORIA_LISTA_PRECIO);
			BasicDTO resultDTO = new BasicDTO();
			ObraSocialDTO objetoEditar;
			try {

				this.generarListasPosibles();
				mav.addObject(LISTASPOSIBLESDTO,this.jsonString);
				objetoEditar = obraSocialService.getObraSocial(objetoDTO.getCodigo());
				List<AsociacionObraSocialListaPrecioDTO> asociaciones=obraSocialService.getAsociaciones(objetoDTO.getCodigo());
				AutoPopulatingList auto=new AutoPopulatingList(AsociacionObraSocialListaPrecioDTO.class);
				auto.addAll(asociaciones);
				objetoEditar.setAsociacionListasAuto(auto);
				
				this.generarListasPosibles();
				mav.addObject(LISTASPOSIBLESDTO,this.jsonString);

			} catch (HBDataAccessException e) {

				objetoEditar = new ObraSocialDTO();
				resultDTO.setError(true);
				resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
			} catch (HBObjectNotExistsException e) {
				objetoEditar = new ObraSocialDTO();

				resultDTO.setError(true);
				resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_OBRA_SOCIAL_NO_EXISTE));
			}

			
			mav.addObject(RESULTOPERACIONDTO, resultDTO);
			mav.addObject(ACTUALIZAROBRASOCIALDTO, objetoEditar);
			

			return mav;
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@RequestMapping(value = "/doHistoriaListaPrecio.htm", method = RequestMethod.POST)
		public ModelAndView doHistoriaListaPrecio(@ModelAttribute(ACTUALIZAROBRASOCIALDTO) ObraSocialDTO editarDTO,
				HttpServletRequest req, HttpSession session)
		{
			
			logger.info("Modificacion de asociaciones");
			BasicDTO resultDTO = new BasicDTO();
			
			try {
				AutoPopulatingList auto = editarDTO.getAsociacionListasAuto();
				if(auto.size()!=editarDTO.getCantidadPeriodos()){
					List lista=auto.subList(0, editarDTO.getCantidadPeriodos());
					obraSocialService.actualizarAsociaciones(lista,editarDTO.getCodigo());	
					
				}else{
					
					obraSocialService.actualizarAsociaciones(auto,editarDTO.getCodigo());	
					
				}
				
			} catch (ParseException e) {
				resultDTO.setError(true);
				resultDTO.setErrorMessage("Error de parseo.");
			}

			resultDTO.setError(false);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_OBRA_SOCIAL_EDITADA_OK));

			ModelAndView mav = new ModelAndView(JsonView.instance);
			mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(resultDTO));

			return mav;
		}

}
