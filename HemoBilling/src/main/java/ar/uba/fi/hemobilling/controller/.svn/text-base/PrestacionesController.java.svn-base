package ar.uba.fi.hemobilling.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.dto.BasicDTO;
import ar.uba.fi.hemobilling.dto.FiltroConsultaDTO;
import ar.uba.fi.hemobilling.dto.FiltroPaginadoDTO;
import ar.uba.fi.hemobilling.dto.prestaciones.FiltroConsultaPrestacionesDTO;
import ar.uba.fi.hemobilling.dto.prestaciones.PrestacionDTO;
import ar.uba.fi.hemobilling.exception.domain.AsociacionPrestacionDuplicadaException;
import ar.uba.fi.hemobilling.exception.domain.HBDataAccessException;
import ar.uba.fi.hemobilling.exception.domain.HBEntityRelationViolation;
import ar.uba.fi.hemobilling.exception.domain.HBObjectExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBObjectNotExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBServiceException;
import ar.uba.fi.hemobilling.exception.domain.PrestacionAsociadaPrestacionException;
import ar.uba.fi.hemobilling.mvc.JsonView;
import ar.uba.fi.hemobilling.mvc.JSONPrestacion;
import ar.uba.fi.hemobilling.service.PrestacionService;

import com.google.gson.Gson;

@Controller
@SessionAttributes({ "prestaciondto", "nuevaprestaciondto", "actualizarPrestacionDTO", "filtroDTO", "filtroPaginadoDTO" })
public class PrestacionesController extends AbstractController {

	private static final String PRESTACIONESPOSIBLESDTO = "prestacionesPosibles";

	@Resource(name = "prestacionService")
	private PrestacionService prestacionService;

	private static final String JSP_PRESTACIONES = "prestaciones";
	private static final String JSP_NUEVA_PRESTACION = "nuevaPrestacion";
	private static final String JSP_EDITAR_PRESTACION = "editarPrestacion";

	private static final String PRESTACIONDTO = "prestaciondto";
	private static final String NUEVAPRESTACIONDTO = "nuevaPrestacionDTO";
	private static final String ACTUALIZACIONDTO = "actualizarPrestacionDTO";
	private static final String PRESTACIONESDTO = "prestacionesdto";
	private static final String RESULTOPERACIONDTO = "resultOperacionDTO";
	private static final String FILTROPRESTACIONESDTO = "filtroDTO";
	private static final String FILTROPAGINADODTO = "filtroPaginadoDTO";

	private static final String MENSAJE_PRESTACION_YA_EXISTE = "prestacion.prestacionYaExisteEnBD";
	private static final String MENSAJE_PRESTACION_NO_EXISTE = "prestacion.prestacionNoExisteEnBD";

	private static final String MENSAJE_PRESTACION_AGREGADA_OK = "prestacion.prestacionAgregadaExitosamente";
	private static final String MENSAJE_PRESTACION_EDITADA_OK = "prestacion.prestacionEditadaExitosamente";
	private static final String MENSAJE_PRESTACION_ELIMINADA_OK = "prestacion.prestacionEliminadaExitosamente";
	private static final String MENSAJE_PRESTACION_AUN_UTILIZADA = "prestacion.prestacionAunUtilizada";

	private static final String MENSAJE_ERROR_NO_ENCONTRADO = "prestacion.prestacionNoEncontrada";

	private static final String MENSAJE_PRESTACION_ASOCIADA_PRESTACION = "prestacion.prestacionAsociadaPrestacion";

	private static final String MENSAJE_ASOCIACION_DUPLICADA = "prestacion.prestacionAsociadaDuplicada";

	String jsonString = "";

	private static Logger logger = Logger.getLogger(PrestacionesController.class);

	private FiltroConsultaPrestacionesDTO getFiltroBasePrestacionesDTO() {
		FiltroConsultaPrestacionesDTO filtro = new FiltroConsultaPrestacionesDTO();

		filtro.setCodigo(FiltroConsultaDTO.TODOS_DESC);
		filtro.setDescripcion(FiltroConsultaDTO.TODOS_DESC);

		return filtro;
	}

	private FiltroPaginadoDTO getFiltroPaginadoBase() {
		FiltroPaginadoDTO filtro = new FiltroPaginadoDTO();

		filtro.setNumeroPaginaActual(1);
		filtro.setRegPorPagina(cantidadRegistrosPorPagina);
		filtro.setError(false);

		return filtro;
	}

	private ModelAndView getPaginaConsulta(FiltroConsultaPrestacionesDTO filtroDTO, FiltroPaginadoDTO paginadoDTO,
			BasicDTO resultDTO) {
		ModelAndView mav = new ModelAndView(JSP_PRESTACIONES);
		PrestacionDTO prestacion = new PrestacionDTO();

		if (filtroDTO == null)
			filtroDTO = getFiltroBasePrestacionesDTO();
		if (paginadoDTO == null)
			paginadoDTO = getFiltroPaginadoBase();
		if (resultDTO == null)
			resultDTO = new BasicDTO();

		Collection<PrestacionDTO> prestaciones;

		try {
			prestaciones = prestacionService.getPrestaciones(filtroDTO, paginadoDTO);

		}
		catch (HBDataAccessException e) {

			prestaciones = new ArrayList<PrestacionDTO>();
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
		}

		mav.addObject(FILTROPAGINADODTO, paginadoDTO);
		mav.addObject(FILTROPRESTACIONESDTO, filtroDTO);
		mav.addObject(PRESTACIONDTO, prestacion);
		mav.addObject(PRESTACIONESDTO, prestaciones);
		mav.addObject(RESULTOPERACIONDTO, resultDTO);

		return mav;

	}

	/* ----- LISTADOS DE PRESTACIONES PLANOS ----- */

	private void generarPrestacionesPosibles() throws HBDataAccessException {
		Collection<PrestacionDTO> prestacionesPosibles = prestacionService.getPrestacionesParaListar();
		List<JSONPrestacion> jsons = new ArrayList<JSONPrestacion>();
		Iterator<PrestacionDTO> it = prestacionesPosibles.iterator();
		while (it.hasNext()) {
			PrestacionDTO prestacion = it.next();
			jsons.add(new JSONPrestacion(prestacion.getCodigo() + " - " + prestacion.getDescripcion(), prestacion
					.getCodigo() + "", prestacion.getDescripcion()));

		}
		Gson gson = new Gson();
		jsonString = gson.toJson(jsons);

	}

	/*-------- PAGINAS DE CONSULTA ------ */

	@RequestMapping("/prestaciones.htm")
	public ModelAndView getPaginaBaseListaPrestaciones(HttpServletRequest req, HttpSession session) {
		logger.info("Llega solicitud de lista de prestaciones");
		return getPaginaConsulta(null, null, null);
	}

	@RequestMapping(value = "/refreshPrestaciones.htm", method = RequestMethod.POST)
	public ModelAndView refreshListaPrestaciones(
			@ModelAttribute(FILTROPRESTACIONESDTO) FiltroConsultaPrestacionesDTO filtroDTO,
			@ModelAttribute(FILTROPAGINADODTO) FiltroPaginadoDTO paginadoDTO, HttpServletRequest req,
			HttpSession session) {
		logger.info("Actualizo la pagina de prestaciones, por medio del filtro o paginado");
		return getPaginaConsulta(filtroDTO, paginadoDTO, null);
	}

	@RequestMapping(value = "/volverPrestaciones.htm", method = RequestMethod.POST)
	public ModelAndView volverPrestaciones(@ModelAttribute(RESULTOPERACIONDTO) BasicDTO resultDTO,
			HttpServletRequest req, HttpSession session) {
		logger.info("Actualizacion de la pagina de prestaciones, por medio del filtro o paginado");
		return getPaginaConsulta(null, null, resultDTO);
	}

	/* ------------ NUEVA PRESTACION ------------ */

	@RequestMapping("/nuevaPrestacion.htm")
	public ModelAndView nuevaPrestacion(HttpServletRequest req, HttpSession session) {

		logger.info("Solicitud de nueva prestacion.");

		ModelAndView mav = new ModelAndView(JSP_NUEVA_PRESTACION);

		PrestacionDTO nuevaPrestacionDTO = new PrestacionDTO();
		BasicDTO resultDTO = new BasicDTO();

		try {
			this.generarPrestacionesPosibles();
			mav.addObject(NUEVAPRESTACIONDTO, nuevaPrestacionDTO);
			mav.addObject(PRESTACIONESPOSIBLESDTO, this.jsonString);

		}
		catch (HBDataAccessException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));

		}
		mav.addObject(RESULTOPERACIONDTO, resultDTO);

		return mav;
	}

	@RequestMapping(value = "/doNuevaPrestacion.htm", method = RequestMethod.POST)
	public ModelAndView doNuevaPrestacion(@ModelAttribute(NUEVAPRESTACIONDTO) PrestacionDTO nuevaPrestacionDTO,
			HttpServletRequest req, HttpSession session) {
		logger.info("Creacion de nueva prestacion.");

		BasicDTO resultDTO = new BasicDTO();
		try {

			prestacionService.agregarPrestacion(nuevaPrestacionDTO);
			resultDTO.setError(false);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_PRESTACION_AGREGADA_OK));
		}

		catch (HBDataAccessException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
		}

		catch (HBObjectExistsException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_PRESTACION_YA_EXISTE));
		}
		catch (HBServiceException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_SERVICIO));
		}
		catch (ObjectNotFoundException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_NO_ENCONTRADO));
		}
		catch (AsociacionPrestacionDuplicadaException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ASOCIACION_DUPLICADA));
		}

		ModelAndView mav = new ModelAndView(JsonView.instance);
		mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(resultDTO));

		return mav;
	}

	/* ----------- EDITAR PRESTACION ----------- */

	@RequestMapping(value = "/editarPrestacion.htm", method = RequestMethod.POST)
	public ModelAndView editarPrestacion(@ModelAttribute(PRESTACIONDTO) PrestacionDTO prestacionDTO,
			HttpServletRequest req, HttpSession session) {
		logger.info("Modificacion de Prestacion");

		ModelAndView mav = new ModelAndView(JSP_EDITAR_PRESTACION);
		BasicDTO resultDTO = new BasicDTO();
		PrestacionDTO prestacionEditar;
		try {

			this.generarPrestacionesPosibles();
			mav.addObject(PRESTACIONESPOSIBLESDTO, this.jsonString);
			prestacionEditar = prestacionService.getPrestacion(prestacionDTO.getCodigo());
		}
		catch (HBDataAccessException e) {

			prestacionEditar = new PrestacionDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
		}
		catch (HBObjectNotExistsException e) {
			prestacionEditar = new PrestacionDTO();

			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_PRESTACION_NO_EXISTE));
		}

		mav.addObject(RESULTOPERACIONDTO, resultDTO);
		mav.addObject(ACTUALIZACIONDTO, prestacionEditar);

		return mav;
	}

	@RequestMapping(value = "/doEditarPrestacion.htm", method = RequestMethod.POST)
	public ModelAndView doEditarPrestacion(@ModelAttribute(ACTUALIZACIONDTO) PrestacionDTO prestacionDTO,
			HttpServletRequest req, HttpSession session) {
		logger.info("Modificacion de prestacion.");

		BasicDTO resultDTO = new BasicDTO();

		try {
			prestacionService.actualizarPrestacion(prestacionDTO);

			resultDTO.setError(false);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_PRESTACION_EDITADA_OK));
		}

		catch (HBDataAccessException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
		}
		catch (HBObjectNotExistsException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_PRESTACION_NO_EXISTE));
		}
		catch (HBServiceException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_SERVICIO));
		}
		catch (AsociacionPrestacionDuplicadaException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ASOCIACION_DUPLICADA));
		}
		catch (HBObjectExistsException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_PRESTACION_YA_EXISTE));
		}

		ModelAndView mav = new ModelAndView(JsonView.instance);
		mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(resultDTO));

		return mav;
	}

	/* ----------- ELIMINAR PRESTACION ----------- */

	@RequestMapping(value = "/doEliminarPrestacion.htm", method = RequestMethod.POST)
	public ModelAndView doEliminarPrestacion(@ModelAttribute(PRESTACIONDTO) PrestacionDTO prestacionDTO,
			HttpServletRequest req, HttpSession session) {
		logger.info("Eliminar prestacion con codigo" + prestacionDTO.getCodigo() + " .");

		BasicDTO resultDTO = new BasicDTO();

		try {
			PrestacionDTO dto = prestacionService.getPrestacion(prestacionDTO.getCodigo());
			prestacionService.eliminarPrestacion(dto);

			resultDTO.setError(false);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_PRESTACION_ELIMINADA_OK));
		}
		catch (HBDataAccessException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
		}
		catch (HBObjectNotExistsException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_PRESTACION_NO_EXISTE));
		}
		catch (PrestacionAsociadaPrestacionException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_PRESTACION_ASOCIADA_PRESTACION));
		}
		catch (HBEntityRelationViolation e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_PRESTACION_AUN_UTILIZADA));
		}

		return this.volverPrestaciones(resultDTO, req, session);

	}

	/**
	 * METODOS QUE MAPEAN CONTRA DIRECCIONES QUE SON MANEJADAS POR POST, PARA
	 * QUE SI EL CLIENTE DA REFRESH NO TIRE EXCEPCION. TODAS REDIRIGEN AL
	 * CONSULTAR PRESTACION
	 */

	@RequestMapping(value = "/doEditarPrestacion.htm")
	// Invocacion Ajax
	public ModelAndView doEditarPrestacionSinPost(HttpServletRequest req, HttpSession session) {
		return this.getPaginaBaseListaPrestaciones(req, session);
	}

	@RequestMapping(value = "/editarPrestacion.htm")
	// Invocacion Ajax
	public ModelAndView editarPrestacionSinPost(HttpServletRequest req, HttpSession session) {
		return this.getPaginaBaseListaPrestaciones(req, session);
	}

	@RequestMapping(value = "/doEliminarPrestacion.htm")
	// Invocacion sincronica
	public ModelAndView doEliminarPrestacionSinPost(HttpServletRequest req, HttpSession session) {

		return null;
	}

	@RequestMapping(value = "/doNuevaPrestacion.htm")
	// Invocacion Ajax
	public ModelAndView doNuevaPrestacionSinPost(HttpServletRequest req, HttpSession session) {
		return this.getPaginaBaseListaPrestaciones(req, session);
	}

	@RequestMapping("/volverPrestaciones.htm")
	// Invocacion Sincronica
	public ModelAndView volverPrestacionesSinPOST(HttpServletRequest req, HttpSession session) {
		return this.getPaginaBaseListaPrestaciones(req, session);
	}

	@RequestMapping("/refreshPrestaciones.htm")
	// Invocacion Sincronica
	public ModelAndView refreshPrestacionesSinPOST(HttpServletRequest req, HttpSession session) {
		return this.getPaginaBaseListaPrestaciones(req, session);
	}

}