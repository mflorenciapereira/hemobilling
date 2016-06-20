package ar.uba.fi.hemobilling.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ar.uba.fi.hemobilling.dto.BasicDTO;
import ar.uba.fi.hemobilling.dto.FiltroConsultaDTO;
import ar.uba.fi.hemobilling.dto.FiltroPaginadoDTO;
import ar.uba.fi.hemobilling.dto.listasprecio.ActualizacionMasivaDTO;
import ar.uba.fi.hemobilling.dto.listasprecio.FiltroConsultaListasPrecioDTO;
import ar.uba.fi.hemobilling.dto.listasprecio.ItemListaPrecioDTO;
import ar.uba.fi.hemobilling.dto.listasprecio.ListaPrecioDTO;
import ar.uba.fi.hemobilling.dto.listasprecio.ResultVerObrasSocialesListaActualDTO;
import ar.uba.fi.hemobilling.dto.obrassociales.ObraSocialDTO;
import ar.uba.fi.hemobilling.dto.prestaciones.PrestacionDTO;
import ar.uba.fi.hemobilling.exception.domain.HBDataAccessException;
import ar.uba.fi.hemobilling.exception.domain.HBEntityRelationViolation;
import ar.uba.fi.hemobilling.exception.domain.HBObjectExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBObjectNotExistsException;
import ar.uba.fi.hemobilling.mvc.JsonView;
import ar.uba.fi.hemobilling.service.ListaPrecioService;
import ar.uba.fi.hemobilling.service.PrestacionService;

import com.google.gson.Gson;

@Controller
@SessionAttributes({ "listapreciodto", "nuevaListaPreciodto", "actualizarListaPrecioDTO", "filtroDTO",
		"filtroPaginadoDTO", "actualizacionMastivaDTO", "previsualizarCambioListaDTO" })
public class ListasPrecioController extends AbstractController {
	
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

	@Resource(name = "prestacionService")
	private PrestacionService prestacionService;

	@Resource(name = "listaPrecioService")
	private ListaPrecioService listaPrecioService;

	private static final String JSP_LISTASPRECIO = "listasPrecio";
	private static final String JSP_NUEVA_LISTAPRECIO = "nuevaListaPrecio";
	private static final String JSP_EDITAR_LISTAPRECIO = "editarListaPrecio";
	
	private static final String LISTAPRECIODTO = "listapreciodto";
	private static final String NUEVALISTAPRECIODTO = "nuevaListaPrecioDTO";
	private static final String ACTUALIZACIONDTO = "actualizarListaPrecioDTO";
	private static final String LISTASPRECIODTO = "listaspreciodto";
	private static final String RESULTOPERACIONDTO = "resultOperacionDTO";
	private static final String FILTROLISTASPRECIODTO = "filtroDTO";
	private static final String FILTROPAGINADODTO = "filtroPaginadoDTO";
	
	private static final String ACTUALIZACIONMASIVADTO = "actualizacionMastivaDTO";
	private static final String PREVISUALIZARCAMBIODTO = "previsualizarCambioListaDTO";

	private static final String MENSAJE_LISTAPRECIO_YA_EXISTE = "listaPrecio.listaPrecioYaExisteEnBD";
	private static final String MENSAJE_LISTAPRECIO_NO_EXISTE = "listaPrecio.listaPrecioNoExisteEnBD";
	private static final String MENSAJE_LISTAPRECIO_AUN_USADA = "listaPrecio.listaPrecioAunUtilizada";

	private static final String MENSAJE_LISTAPRECIO_AGREGADA_OK = "listaPrecio.listaPrecioAgregadaExitosamente";
	private static final String MENSAJE_LISTAPRECIO_EDITADA_OK = "listaPrecio.listaPrecioEditadaExitosamente";
	private static final String MENSAJE_LISTAPRECIO_ELIMINADA_OK = "listaPrecio.listaPrecioEliminadaExitosamente";
	private static final String MENSAJE_LISTAPRECIO_DUPLICADA_OK = "listaPrecio.listaPrecioDuplicadaExitosamente";

	private static final String JSP_PRECIOS = "verPrecios";
	
	private static final String LISTASPOSIBLESDTO = "listasPosibles";
	private String jsonString="";

	private static final String JSP_NUEVA_ACTUALIZACION_MASIVA = "actualizacionMasivaListaPrecio";

	private static final String MENSAJE_ACTUALIZACION_MASIVA_OK = "listaPrecio.actualizacionMasivaOK";;

	private static Logger logger = Logger.getLogger(ListasPrecioController.class);

	private FiltroConsultaListasPrecioDTO getFiltroBaseListasPrecioDTO() {
		FiltroConsultaListasPrecioDTO filtro = new FiltroConsultaListasPrecioDTO();

		filtro.setCodigo(FiltroConsultaDTO.TODOS_DESC);
		filtro.setNombre(FiltroConsultaDTO.TODOS_DESC);
		filtro.setDesde(null);
		filtro.setHasta(null);
		filtro.setObraSocial(new ObraSocialDTO());

		return filtro;
	}

	private FiltroPaginadoDTO getFiltroPaginadoBase() {
		FiltroPaginadoDTO filtro = new FiltroPaginadoDTO();

		filtro.setNumeroPaginaActual(1);
		filtro.setRegPorPagina(cantidadRegistrosPorPagina);
		filtro.setError(false);

		return filtro;
	}

	private ModelAndView getPaginaConsulta(FiltroConsultaListasPrecioDTO filtroDTO, FiltroPaginadoDTO paginadoDTO,
			BasicDTO resultDTO) {
		ModelAndView mav = new ModelAndView(JSP_LISTASPRECIO);
		ListaPrecioDTO lista = new ListaPrecioDTO();

		if (filtroDTO == null)
			filtroDTO = getFiltroBaseListasPrecioDTO();
		if (paginadoDTO == null)
			paginadoDTO = getFiltroPaginadoBase();
		if (resultDTO == null)
			resultDTO = new BasicDTO();

		Collection<ListaPrecioDTO> listas;

		try {
			listas = listaPrecioService.buscarListasPrecioParaListar(filtroDTO, paginadoDTO);

		}
		catch (HBDataAccessException e) {

			listas = new ArrayList<ListaPrecioDTO>();
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
		}

		mav.addObject(FILTROPAGINADODTO, paginadoDTO);
		mav.addObject(FILTROLISTASPRECIODTO, filtroDTO);
		mav.addObject(LISTAPRECIODTO, lista);
		mav.addObject(LISTASPRECIODTO, listas);
		mav.addObject(RESULTOPERACIONDTO, resultDTO);

		return mav;

	}

	/*-------- PAGINAS DE CONSULTA ------ */

	@RequestMapping("/listasPrecio.htm")
	public ModelAndView getPaginaBaseListaListasPrecio(HttpServletRequest req, HttpSession session) {
		logger.info("Llega solicitud de listado de listas de precios");
		return getPaginaConsulta(null, null, null);
	}

	@RequestMapping(value = "/refreshListasPrecio.htm", method = RequestMethod.POST)
	public ModelAndView refreshListaListasPrecio(
			@ModelAttribute(FILTROLISTASPRECIODTO) FiltroConsultaListasPrecioDTO filtroDTO,
			@ModelAttribute(FILTROPAGINADODTO) FiltroPaginadoDTO paginadoDTO, HttpServletRequest req,
			HttpSession session) {
		logger.info("Actualizo la pagina de listas de precio, por medio del filtro o paginado");
		return getPaginaConsulta(filtroDTO, paginadoDTO, null);
	}

	@RequestMapping(value = "/volverListasPrecio.htm", method = RequestMethod.POST)
	public ModelAndView volverListasPrecio(@ModelAttribute(RESULTOPERACIONDTO) BasicDTO resultDTO,
			HttpServletRequest req, HttpSession session) {
		logger.info("Actualizacion de la pagina de listas de precio, por medio del filtro o paginado");
		return getPaginaConsulta(null, null, resultDTO);
	}

	/* ------------ NUEVA LISTAPRECIO ------------ */

	@RequestMapping("/nuevaListaPrecio.htm")
	public ModelAndView nuevaListaPrecio(HttpServletRequest req, HttpSession session) {

		logger.info("Solicitud de nueva lista de precio.");

		ModelAndView mav = new ModelAndView(JSP_NUEVA_LISTAPRECIO);

		ListaPrecioDTO nuevaListaPrecioDTO = new ListaPrecioDTO();

		BasicDTO resultDTO = new BasicDTO();

		mav.addObject(NUEVALISTAPRECIODTO, nuevaListaPrecioDTO);
		mav.addObject(RESULTOPERACIONDTO, resultDTO);

		return mav;
	}

	@RequestMapping(value = "/doNuevaListaPrecio.htm", method = RequestMethod.POST)
	public ModelAndView doNuevaListaPrecio(@ModelAttribute(NUEVALISTAPRECIODTO) ListaPrecioDTO nuevaListaPrecioDTO,
			HttpServletRequest req, HttpSession session) {
		logger.info("Creacion de nueva lista de precio.");

		BasicDTO resultDTO = new BasicDTO();
		try {
			listaPrecioService.agregarListaPrecio(nuevaListaPrecioDTO);
			resultDTO.setError(false);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_LISTAPRECIO_AGREGADA_OK) );
		}

		catch (HBDataAccessException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
		}

		catch (HBObjectExistsException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_LISTAPRECIO_YA_EXISTE));
		}

		ModelAndView mav = new ModelAndView(JsonView.instance);
		mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(resultDTO));

		return mav;
	}

	/* ----------- EDITAR LISTAPRECIO ----------- */

	@RequestMapping(value = "/editarListaPrecio.htm", method = RequestMethod.POST)
	public ModelAndView editarListaPrecio(@ModelAttribute(LISTAPRECIODTO) ListaPrecioDTO listaDTO,
			HttpServletRequest req, HttpSession session) {
		logger.info("Modificacion de Lista Precio");

		ModelAndView mav = new ModelAndView(JSP_EDITAR_LISTAPRECIO);
		BasicDTO resultDTO = new BasicDTO();

		ListaPrecioDTO listaEditar;
		try {

			listaEditar = listaPrecioService.getListaPrecioParaListar(listaDTO.getCodigo());

		}
		catch (HBDataAccessException e) {

			listaEditar = new ListaPrecioDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
		}
		catch (HBObjectNotExistsException e) {
			listaEditar = new ListaPrecioDTO();

			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_LISTAPRECIO_NO_EXISTE));
		}

		mav.addObject(LISTAPRECIODTO, listaDTO);
		mav.addObject(RESULTOPERACIONDTO, resultDTO);
		mav.addObject(ACTUALIZACIONDTO, listaEditar);

		return mav;
	}

	@RequestMapping(value = "/doEditarListaPrecio.htm", method = RequestMethod.POST)
	public ModelAndView doEditarListaPrecio(@ModelAttribute(ACTUALIZACIONDTO) ListaPrecioDTO listaDTO,
			HttpServletRequest req, HttpSession session) 
	{
		logger.info("Modificacion de lista de precio.");

		BasicDTO resultDTO = new BasicDTO();

		try {
			listaPrecioService.actualizarListaPrecio(listaDTO);

			resultDTO.setError(false);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_LISTAPRECIO_EDITADA_OK));
		}

		catch (HBDataAccessException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
		}
		catch (HBObjectNotExistsException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_LISTAPRECIO_NO_EXISTE));
		}

		ModelAndView mav = new ModelAndView(JsonView.instance);
		mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(resultDTO));

		return mav;
	}
	
	
	/* ------------- DUPLICAR LISTA -----------------*/
	
	@RequestMapping(value = "/doDuplicarListaPrecio.htm", method = RequestMethod.POST)
	public ModelAndView doDuplicarListaPrecio(  @ModelAttribute(LISTAPRECIODTO) ListaPrecioDTO listaDTO,
												HttpServletRequest req, HttpSession session)
	{
		logger.info("Modificacion de lista de precio.");

		BasicDTO resultDTO = new BasicDTO();

		try {
			listaPrecioService.duplicarListaPrecio(listaDTO);

			resultDTO.setError(false);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_LISTAPRECIO_DUPLICADA_OK));
		}

		catch (HBDataAccessException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
		}
		catch (HBObjectNotExistsException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_LISTAPRECIO_NO_EXISTE));
		}
		catch (HBObjectExistsException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_LISTAPRECIO_NO_EXISTE));
		}

		return this.getPaginaConsulta(getFiltroBaseListasPrecioDTO(), getFiltroPaginadoBase(), resultDTO);
	}
	
	@RequestMapping(value = "/doDuplicarListaPrecio.htm")
	public ModelAndView doDuplicarListaPrecioMock( HttpServletRequest req, HttpSession session)
	{
		return this.getPaginaConsulta(getFiltroBaseListasPrecioDTO(), getFiltroPaginadoBase(), null);
	}
	
	
	/* ----------- ELIMINAR LISTAPRECIO ----------- */

	@RequestMapping(value = "/doEliminarListaPrecio.htm", method = RequestMethod.POST)
	public ModelAndView doEliminarListaPrecio(@ModelAttribute(LISTAPRECIODTO) ListaPrecioDTO listaDTO,
			HttpServletRequest req, HttpSession session) {
		logger.info("Eliminar prestacion con codigo" + listaDTO.getCodigo() + " .");

		BasicDTO resultDTO = new BasicDTO();

		try {
			ListaPrecioDTO dto = listaPrecioService.getListaPrecio(listaDTO.getCodigo());
			listaPrecioService.eliminarListaPrecio(dto);

			resultDTO.setError(false);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_LISTAPRECIO_ELIMINADA_OK));
		}
		catch (HBDataAccessException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
		}
		catch (HBObjectNotExistsException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_LISTAPRECIO_NO_EXISTE));
		}
		catch (HBEntityRelationViolation e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_LISTAPRECIO_AUN_USADA));
		}

		return this.volverListasPrecio(resultDTO, req, session);

	}

	/**
	 * METODOS QUE MAPEAN CONTRA DIRECCIONES QUE SON MANEJADAS POR POST, PARA
	 * QUE SI EL CLIENTE DA REFRESH NO TIRE EXCEPCION. TODAS REDIRIGEN AL
	 * CONSULTAR LISTAPRECIO
	 */

	@RequestMapping(value = "/doEditarListaPrecio.htm")
	// Invocacion Ajax
	public ModelAndView doEditarListaPrecioSinPost(HttpServletRequest req, HttpSession session) {
		return this.getPaginaBaseListaListasPrecio(req, session);
	}

	@RequestMapping(value = "/editarListaPrecio.htm")
	// Invocacion Ajax
	public ModelAndView editarListaPrecioSinPost(HttpServletRequest req, HttpSession session) {
		return this.getPaginaBaseListaListasPrecio(req, session);
	}

	@RequestMapping(value = "/doEliminarListaPrecio.htm")
	// Invocacion sincronica
	public ModelAndView doEliminarListaPrecioSinPost(HttpServletRequest req, HttpSession session) {

		return null;
	}

	@RequestMapping(value = "/doNuevaListaPrecio.htm")
	// Invocacion Ajax
	public ModelAndView doNuevaListaPrecioSinPost(HttpServletRequest req, HttpSession session) {
		return this.getPaginaBaseListaListasPrecio(req, session);
	}

	@RequestMapping("/volverListasPrecio.htm")
	// Invocacion Sincronica
	public ModelAndView volverListasPrecioSinPOST(HttpServletRequest req, HttpSession session) {
		return this.getPaginaBaseListaListasPrecio(req, session);
	}

	@RequestMapping("/refreshListasPrecio.htm")
	// Invocacion Sincronica
	public ModelAndView refreshListasPrecioSinPOST(HttpServletRequest req, HttpSession session) {
		return this.getPaginaBaseListaListasPrecio(req, session);
	}

	/* -------- PRECIOS ---------- */

	private Map<Long,ItemListaPrecioDTO> getMapaItems( Collection<ItemListaPrecioDTO> itemsDTO )
	{
		HashMap<Long,ItemListaPrecioDTO > mapa = new HashMap<Long, ItemListaPrecioDTO>();
		Iterator<ItemListaPrecioDTO> it = itemsDTO.iterator();
		
		while( it.hasNext() )
		{
			ItemListaPrecioDTO item = it.next();
			item.setIncluido(true);
			mapa.put( item.getPrestacion().getCodigo() , item );
		}
		
		return mapa;		
	}
	
	@RequestMapping(value = "/verPrecios.htm", method = RequestMethod.POST)
	public ModelAndView verPrecios(@ModelAttribute(LISTAPRECIODTO) ListaPrecioDTO listaDTO, HttpServletRequest req, HttpSession session) 
	{
		logger.info("Viendo precios para la lista de precio " + listaDTO.getCodigo().toString());

		try 
		{
			listaDTO = listaPrecioService.getListaPrecioParaListar( listaDTO.getCodigo() );
		
			Collection<PrestacionDTO> prestaciones = prestacionService.getPrestacionesById();
			Iterator<PrestacionDTO> itPrestaciones = prestaciones.iterator();
			
			Collection<ItemListaPrecioDTO> itemsDTO = this.listaPrecioService.getItemsListaPrecio(listaDTO.getCodigo());
			Map<Long,ItemListaPrecioDTO > mapaItemsDTO = getMapaItems(itemsDTO);
	
			while (itPrestaciones.hasNext()) 
			{
				PrestacionDTO pdto = itPrestaciones.next();
				
				if( !mapaItemsDTO.containsKey( pdto.getCodigo() ) ) 
				{
					ItemListaPrecioDTO itemNuevo = new ItemListaPrecioDTO();
					itemNuevo.setCodigo("");
					itemNuevo.setIdlistaPrecioPertenece(listaDTO.getCodigo());
					itemNuevo.setIncluido(false);
					itemNuevo.setPrecio(0D);
					itemNuevo.setPrestacion(pdto);
					itemsDTO.add(itemNuevo);
				}
			}
			
			listaDTO.getItemsAuto().addAll(itemsDTO);
			
			ModelAndView mav = new ModelAndView(JSP_PRECIOS);
			mav.addObject(LISTAPRECIODTO, listaDTO);
			mav.addObject(RESULTOPERACIONDTO, new BasicDTO());
			return mav;
		
		
		}
		catch (HBDataAccessException e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
			
			return this.getPaginaConsulta(getFiltroBaseListasPrecioDTO(), getFiltroPaginadoBase(), resultDTO);
		}
		catch (HBObjectNotExistsException e) 
		{
			BasicDTO resultDTO = new BasicDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_LISTAPRECIO_NO_EXISTE));
			
			return this.getPaginaConsulta(getFiltroBaseListasPrecioDTO(), getFiltroPaginadoBase(), resultDTO);
		}
	}

	@RequestMapping(value = "/doActualizarPrecios.htm", method = RequestMethod.POST)
	public ModelAndView doActualizarPrecios(@ModelAttribute(LISTAPRECIODTO) ListaPrecioDTO listaDTO,
			HttpServletRequest req, HttpSession session)
	{
		logger.info("Modificacion de precios.");

		BasicDTO resultDTO = new BasicDTO();

		try {
			listaPrecioService.actualizarPrecios(listaDTO);

			resultDTO.setError(false);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_LISTAPRECIO_EDITADA_OK));
		}

		catch (HBDataAccessException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
		}
		catch (HBObjectNotExistsException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_LISTAPRECIO_NO_EXISTE));
		}

		ModelAndView mav = new ModelAndView(JsonView.instance);
		mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(resultDTO));

		return mav;
	}
	
	/* ------ Actualizacion masiva --------- */
	
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
	
	@RequestMapping("/actualizacionMasivaListaPrecio.htm")
	public ModelAndView actualizacionMasivaListaPrecio(HttpServletRequest req, HttpSession session) {

		ModelAndView mav = new ModelAndView(JSP_NUEVA_ACTUALIZACION_MASIVA);
		BasicDTO resultDTO = new BasicDTO();
		try {
			this.generarListasPosibles();
			logger.info("Solicitud de actualizacion masiva de lista de precio.");
			ActualizacionMasivaDTO actualizacionDTO = new ActualizacionMasivaDTO();
			ActualizacionMasivaDTO previsualizarCambioListaDTO=new ActualizacionMasivaDTO();
			mav.addObject(LISTASPOSIBLESDTO,this.jsonString);
			mav.addObject(ACTUALIZACIONMASIVADTO, actualizacionDTO);
			mav.addObject(PREVISUALIZARCAMBIODTO, previsualizarCambioListaDTO);
			mav.addObject(RESULTOPERACIONDTO, resultDTO);
			
		} catch (HBDataAccessException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
		}
		return mav;
		
		
		
	}
	
	
	@RequestMapping(value="/doVerObrasSocialesListaActual.htm" ,method=RequestMethod.POST)
	public ModelAndView doVerObrasSocialesListaActual( @ModelAttribute(PREVISUALIZARCAMBIODTO) ActualizacionMasivaDTO actualizacionDTO, 
												  HttpServletRequest req,HttpSession session )
	{
		ModelAndView mav = new ModelAndView( JsonView.instance );
		
		ResultVerObrasSocialesListaActualDTO resultDTO = new ResultVerObrasSocialesListaActualDTO();
		try 
		{
			resultDTO = listaPrecioService.getObrasSocialesListaActual(actualizacionDTO);
			resultDTO.setError(false);
			
		} 
		catch (HBDataAccessException e) 
		{
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		}	
		catch (ParseException e) 
		{
			resultDTO.setError(true);
			resultDTO.setErrorMessage( "Fecha ingresada invalida." );
		}
		
		
		mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(resultDTO));
		
		return mav;
	}
	
	@RequestMapping(value="/doActualizacionMasivaListaPrecio.htm" ,method=RequestMethod.POST)
	private ModelAndView doActualizacionMasivaListaPrecio(@ModelAttribute(ACTUALIZACIONMASIVADTO) ActualizacionMasivaDTO dto,
			HttpServletRequest req, HttpSession session)
	{
		logger.info("Actualizacion masiva de obras sociales.");
		
		BasicDTO resultDTO = new BasicDTO();
		String[] asociacionesSeleccionadas=req.getParameterValues("seleccionados");
	    Long[] asociacionesSeleccionadasLong = new Long[asociacionesSeleccionadas.length];  
	    for (int i = 0; i < asociacionesSeleccionadas.length; i++) {  
	    	asociacionesSeleccionadasLong[i] = Long.valueOf(asociacionesSeleccionadas[i]);  
	    }  
		try{
		
		this.listaPrecioService.actualizarListaActual(dto,asociacionesSeleccionadasLong);
		
		resultDTO.setError(false);
		resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ACTUALIZACION_MASIVA_OK));
		}catch (HBDataAccessException e) 
		{
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		} catch (ParseException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage("Fecha ingresada invalida." );
		}

		

		ModelAndView mav = new ModelAndView(JsonView.instance);
		mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(resultDTO));

		return mav;
	}


}