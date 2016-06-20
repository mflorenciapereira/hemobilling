package ar.uba.fi.hemobilling.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
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

import ar.uba.fi.hemobilling.commons.dto.FiltroConsultaEstudiosLaboratorioDTO;
import ar.uba.fi.hemobilling.domain.prestacionesBrindadas.Observacion;
import ar.uba.fi.hemobilling.dto.BasicDTO;
import ar.uba.fi.hemobilling.dto.FiltroConsultaDTO;
import ar.uba.fi.hemobilling.dto.FiltroPaginadoDTO;
import ar.uba.fi.hemobilling.dto.paciente.PacienteDTO;
import ar.uba.fi.hemobilling.dto.prestaciones.PrestacionDTO;
import ar.uba.fi.hemobilling.dto.prestacionesBrindadas.FiltroConsultaPrestacionBrindadaDTO;
import ar.uba.fi.hemobilling.dto.prestacionesBrindadas.FiltroImportacionAnalisisLaboratorioDTO;
import ar.uba.fi.hemobilling.dto.prestacionesBrindadas.PrestacionBrindadaDTO;
import ar.uba.fi.hemobilling.dto.prestacionesBrindadas.ResultConsultaAnalisisLaboratorioDTO;
import ar.uba.fi.hemobilling.exception.domain.HBDataAccessException;
import ar.uba.fi.hemobilling.exception.domain.HBEntityRelationViolation;
import ar.uba.fi.hemobilling.exception.domain.HBObjectExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBObjectNotExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBPrestacionSinPrecioException;
import ar.uba.fi.hemobilling.exception.domain.HBRemoteException;
import ar.uba.fi.hemobilling.exception.domain.HBServiceException;
import ar.uba.fi.hemobilling.mvc.JSONPrestacion;
import ar.uba.fi.hemobilling.mvc.JsonView;
import ar.uba.fi.hemobilling.service.PacienteService;
import ar.uba.fi.hemobilling.service.PrestacionBrindadaService;
import ar.uba.fi.hemobilling.service.PrestacionService;

import com.google.gson.Gson;

@Controller
@SessionAttributes({ "prestacionbrindadadto", "nuevaprestacionbrindadadto", "actualizarprestacionbrindadaDTO",
		"filtroDTO", "filtroPaginadoDTO" })
public class PrestacionBrindadaController extends AbstractController {

	private static final String PACIENTES_POSIBLES_STRING = "pacientesPosiblesString";
	private static final String PRESTACIONES_POSIBLES_STRING = "prestacionesPosiblesString";
	private static final String OBSERVACIONES_POSIBLES_STRING = "observacionesPosiblesString";
	private static final String JSP_PRESTACION_BRINDADA = "prestacionesBrindadas";

	private static final String JSP_IMPORTAR_LABORATORIO = "importarLaboratorio";

	private static final String FILTROCONSULTAANALISISLABORATORIODTO = "filtroConsultaAnalisisLaboratorioDTO";
	private static final String RESULTADOPREVISUALIZACIONDTO = "resultadosPrevisualizacion";

	private static final String MENSAJE_EXITO_IMPORTACION = "prestacionBrindada.prestacionesImportadasLaboExito";

	private static final String MENSAJE_PRESTACION_NO_EXISTE = "prestacion.prestacionNoExisteEnBD";
	private static final String MENSAJE_PRESTACION_ELIMINADA_OK = "prestacion.prestacionEliminadaExitosamente";
	private static final String MENSAJE_PRESTACION_AUN_UTILIZADA = "prestacionBrindada.prestacionBrindadaAunUtilizada";
	private static final String MENSAJE_PRESTACION_SIN_PRECIO = "prestacionBrindada.prestacionSinPrecio";

	private static final String FILTROPRESTACIONESDTO = "filtroDTO";
	private static final String FILTROPAGINADODTO = "filtroPaginadoDTO";

	private static final String PRESTACIONBRINDADADTO = "prestacionbrindadadto";
	private static final String NUEVAPRESTACIONDTO = "nuevaprestacionbrindadadto";
	private static final String ACTUALIZACIONDTO = "actualizarprestacionbrindadaDTO";
	private static final String CONSULTADTO = "prestacionesbrindadasdto";
	private static final String RESULTOPERACIONDTO = "resultOperacionDTO";

	private static final String FORMATO_DDMMAAAA = "dd/MM/yyyy";
	private static final String JSP_NUEVA_PRESTACION = "nuevaPrestacionBrindada";
	private static final String JSP_EDITAR_PRESTACION = "editarPrestacionBrindada";

	private static final String MENSAJE_PRESTACION_AGREGADA_OK = "prestacion.prestacionAgregadaExitosamente";
	private static final String MENSAJE_PRESTACION_YA_EXISTE = "prestacion.prestacionYaExisteEnBD";
	private static final String MENSAJE_PRESTACION_EDITADA_OK = "prestacion.prestacionEditadaExitosamente";

	public class JSONPaciente {

		public JSONPaciente(String label, String value, String desc, String id) {
			super();
			this.label = label;
			this.value = value;
			this.desc = desc;
			this.id = id;
		}

		String label;

		String value;

		String desc;
		String id;
	}

	@Resource(name = "prestacionBrindadaService")
	private PrestacionBrindadaService prestacionBrindadaService;

	@Resource(name = "prestacionService")
	private PrestacionService prestacionService;

	@Resource(name = "pacienteService")
	private PacienteService pacienteService;

	private static Logger logger = Logger.getLogger(PrestacionBrindadaController.class);

	private FiltroConsultaPrestacionBrindadaDTO getFiltroBasePrestacionesBrindadasDTO() {
		FiltroConsultaPrestacionBrindadaDTO filtro = new FiltroConsultaPrestacionBrindadaDTO();

		filtro.setCodigoPrestacion(FiltroConsultaDTO.TODOS_DESC);
		filtro.setFechaDesde(FiltroConsultaDTO.TODOS_DESC);
		filtro.setFechaHasta(FiltroConsultaDTO.TODOS_DESC);
		filtro.setHistoriaClinica(FiltroConsultaDTO.TODOS_DESC);
		filtro.setNombrePaciente(FiltroConsultaDTO.TODOS_DESC);
		filtro.setNombrePrestacion(FiltroConsultaDTO.TODOS_DESC);
		filtro.setProfesional(FiltroConsultaDTO.TODOS_DESC);

		return filtro;
	}

	private FiltroPaginadoDTO getFiltroPaginadoBase() {
		FiltroPaginadoDTO filtro = new FiltroPaginadoDTO();

		filtro.setNumeroPaginaActual(1);
		filtro.setRegPorPagina(cantidadRegistrosPorPagina);
		filtro.setError(false);

		return filtro;
	}

	private ModelAndView getPaginaConsulta(FiltroConsultaPrestacionBrindadaDTO filtroDTO,
			FiltroPaginadoDTO paginadoDTO, BasicDTO resultDTO) {
		ModelAndView mav = new ModelAndView(JSP_PRESTACION_BRINDADA);
		PrestacionBrindadaDTO prestacion = new PrestacionBrindadaDTO();

		if (filtroDTO == null)
			filtroDTO = getFiltroBasePrestacionesBrindadasDTO();
		if (paginadoDTO == null)
			paginadoDTO = getFiltroPaginadoBase();
		if (resultDTO == null)
			resultDTO = new BasicDTO();

		Collection<PrestacionBrindadaDTO> prestaciones;

		try {
			prestaciones = prestacionBrindadaService.consultar(filtroDTO, paginadoDTO);

		}
		catch (HBDataAccessException e) {

			prestaciones = new ArrayList<PrestacionBrindadaDTO>();
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
		}

		mav.addObject(FILTROPAGINADODTO, paginadoDTO);
		mav.addObject(FILTROPRESTACIONESDTO, filtroDTO);
		mav.addObject(PRESTACIONBRINDADADTO, prestacion);
		mav.addObject(CONSULTADTO, prestaciones);
		mav.addObject(RESULTOPERACIONDTO, resultDTO);

		return mav;

	}

	/*-------- PAGINAS DE CONSULTA ------ */

	@RequestMapping("/prestacionesBrindadas.htm")
	public ModelAndView getPaginaBasePrestacionesBrindadas(HttpServletRequest req, HttpSession session) {
		logger.info("Llega solicitud de lista de prestaciones brindadas.");
		return getPaginaConsulta(null, null, null);
	}

	@RequestMapping(value = "/refreshPrestacionesBrindadas.htm", method = RequestMethod.POST)
	public ModelAndView refreshPrestacionesBrindadas(
			@ModelAttribute(FILTROPRESTACIONESDTO) FiltroConsultaPrestacionBrindadaDTO filtroDTO,
			@ModelAttribute(FILTROPAGINADODTO) FiltroPaginadoDTO paginadoDTO, HttpServletRequest req,
			HttpSession session) {
		logger.info("Actualizo la pagina de prestaciones brindadas, por medio del filtro o paginado");
		return getPaginaConsulta(filtroDTO, paginadoDTO, null);
	}

	@RequestMapping(value = "/volverPrestacionesBrindadas.htm", method = RequestMethod.POST)
	public ModelAndView volverPrestacionesBrindadas(@ModelAttribute(RESULTOPERACIONDTO) BasicDTO resultDTO,
			HttpServletRequest req, HttpSession session) {
		logger.info("Actualizacion de la pagina de prestaciones brindadas, por medio del filtro o paginado");
		return getPaginaConsulta(null, null, resultDTO);
	}

	/* ------------ NUEVA PRESTACION BRINDADA ------------ */

	private String generarPrestacionesPosibles() throws HBDataAccessException {
		Collection<PrestacionDTO> prestacionesPosibles = prestacionService.getPrestacionesParaListar();
		Map<String, JSONPrestacion> jsons = new LinkedHashMap<String, JSONPrestacion>();
		Iterator<PrestacionDTO> it = prestacionesPosibles.iterator();
		while (it.hasNext()) {
			PrestacionDTO prestacion = it.next();
			jsons.put(
					prestacion.getCodigo() + "",
					new JSONPrestacion(prestacion.getCodigo() + " - " + prestacion.getDescripcion(), prestacion
							.getCodigo() + "", prestacion.getDescripcion()));

		}
		Gson gson = new Gson();
		return gson.toJson(jsons);

	}

	private String generarPacientesPosibles() throws HBDataAccessException {
		Collection<PacienteDTO> pacientesPosibles = pacienteService.getPacientesParaListar();
		Map<String, JSONPaciente> jsons = new LinkedHashMap<String, JSONPaciente>();
		Iterator<PacienteDTO> it = pacientesPosibles.iterator();
		while (it.hasNext()) {
			PacienteDTO paciente = it.next();
			jsons.put(
					paciente.getNumHistoriaClinica() + "",
					new JSONPaciente(paciente.getNumHistoriaClinica() + " - " + paciente.getNombreyApellido(), paciente
							.getNumHistoriaClinica() + "", paciente.getNombreyApellido() + "", paciente.getId() + ""));

		}
		Gson gson = new Gson();
		return gson.toJson(jsons);

	}

	private String generarObservacionesPosibles() throws HBDataAccessException {
		Collection<Observacion> observaciones = prestacionBrindadaService.getObservaciones();
		Gson gson = new Gson();
		return gson.toJson(observaciones);

	}

	@RequestMapping("/nuevaPrestacionBrindada.htm")
	public ModelAndView nuevaPrestacionBrindada(HttpServletRequest req, HttpSession session) {

		logger.info("Solicitud de nueva prestacion brindada.");

		ModelAndView mav = new ModelAndView(JSP_NUEVA_PRESTACION);

		PrestacionBrindadaDTO nuevaPrestacionDTO = new PrestacionBrindadaDTO();
		BasicDTO resultDTO = new BasicDTO();

		try {
			mav.addObject(NUEVAPRESTACIONDTO, nuevaPrestacionDTO);
			mav.addObject(PRESTACIONES_POSIBLES_STRING, generarPrestacionesPosibles());
			mav.addObject(PACIENTES_POSIBLES_STRING, generarPacientesPosibles());
			mav.addObject(OBSERVACIONES_POSIBLES_STRING, generarObservacionesPosibles());

		}
		catch (HBDataAccessException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));

		}
		mav.addObject(RESULTOPERACIONDTO, resultDTO);

		return mav;
	}

	@RequestMapping(value = "/doNuevaPrestacionBrindada.htm", method = RequestMethod.POST)
	public ModelAndView doNuevaPrestacionBrindada(
			@ModelAttribute(NUEVAPRESTACIONDTO) PrestacionBrindadaDTO nuevaPrestacionDTO, HttpServletRequest req,
			HttpSession session) {
		logger.info("Creacion de nueva prestacion brindada.");

		BasicDTO resultDTO = new BasicDTO();
		try {

			prestacionBrindadaService.agregar(nuevaPrestacionDTO);
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
		catch (HBPrestacionSinPrecioException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_PRESTACION_SIN_PRECIO));
		}
		;

		ModelAndView mav = new ModelAndView(JsonView.instance);
		mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(resultDTO));

		return mav;
	}

	/* ------ EDICION -------- */

	@RequestMapping(value = "/editarPrestacionBrindada.htm", method = RequestMethod.POST)
	public ModelAndView editarPrestacionBrindada(
			@ModelAttribute(PRESTACIONBRINDADADTO) PrestacionBrindadaDTO prestacionDTO, HttpServletRequest req,
			HttpSession session) {
		logger.info("Modificacion de Prestacion Brindada");

		ModelAndView mav = new ModelAndView(JSP_EDITAR_PRESTACION);
		BasicDTO resultDTO = new BasicDTO();
		PrestacionBrindadaDTO prestacionEditar;
		try {
			prestacionEditar = prestacionBrindadaService.get(Long.parseLong(prestacionDTO.getCodigo()));
			mav.addObject(OBSERVACIONES_POSIBLES_STRING, generarObservacionesPosibles());
		}
		catch (HBDataAccessException e) {

			prestacionEditar = new PrestacionBrindadaDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
		}
		catch (HBObjectNotExistsException e) {
			prestacionEditar = new PrestacionBrindadaDTO();

			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_PRESTACION_NO_EXISTE));
		}

		mav.addObject(RESULTOPERACIONDTO, resultDTO);
		mav.addObject(ACTUALIZACIONDTO, prestacionEditar);

		return mav;
	}

	@RequestMapping(value = "/doEditarPrestacionBrindada.htm", method = RequestMethod.POST)
	public ModelAndView doEditarPrestacionBrindada(
			@ModelAttribute(ACTUALIZACIONDTO) PrestacionBrindadaDTO prestacionDTO, HttpServletRequest req,
			HttpSession session) {
		logger.info("Modificacion de prestacion brindada.");

		BasicDTO resultDTO = new BasicDTO();

		try {
			prestacionBrindadaService.actualizar(prestacionDTO);

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
		catch (HBPrestacionSinPrecioException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_PRESTACION_SIN_PRECIO));
		}

		ModelAndView mav = new ModelAndView(JsonView.instance);
		mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(resultDTO));

		return mav;
	}

	/* ------- ELIMINAR -------- */

	@RequestMapping(value = "/doEliminarPrestacionBrindada.htm", method = RequestMethod.POST)
	public ModelAndView doEliminarPrestacionBrindada(
			@ModelAttribute(PRESTACIONBRINDADADTO) PrestacionBrindadaDTO prestacionDTO, HttpServletRequest req,
			HttpSession session) {
		logger.info("Eliminar prestacion brindada con codigo" + prestacionDTO.getCodigo() + " .");

		BasicDTO resultDTO = new BasicDTO();

		try {
			PrestacionBrindadaDTO dto = prestacionBrindadaService.get(Long.parseLong(prestacionDTO.getCodigo()));
			prestacionBrindadaService.eliminar(dto);

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
		catch (HBEntityRelationViolation e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_PRESTACION_AUN_UTILIZADA));
		}
		;

		return this.volverPrestacionesBrindadas(resultDTO, req, session);

	}

	/**
	 * METODOS DE IMPORTACION DE PRESTACIONES BRINDADAS DESDE EL SISTEMA DE
	 * LABORATORIO
	 * 
	 */

	@RequestMapping("/importarLaboratorio.htm")
	public ModelAndView importarLaboratorio() {
		ModelAndView mav = new ModelAndView(JSP_IMPORTAR_LABORATORIO);
		FiltroConsultaEstudiosLaboratorioDTO filtroDTO = new FiltroConsultaEstudiosLaboratorioDTO();
		ResultConsultaAnalisisLaboratorioDTO resultDTO = new ResultConsultaAnalisisLaboratorioDTO();

		mav.addObject(FILTROCONSULTAANALISISLABORATORIODTO, filtroDTO);
		mav.addObject(RESULTADOPREVISUALIZACIONDTO, resultDTO);
		return mav;
	}

	@RequestMapping(value = "/doPrevisualizarImportacion.htm", method = RequestMethod.POST)
	// Invocacion AJAX
	public ModelAndView doPreviasualizarImportacion(
			@ModelAttribute(FILTROCONSULTAANALISISLABORATORIODTO) FiltroImportacionAnalisisLaboratorioDTO filtroDTO,
			HttpServletRequest req, HttpSession session) {
		logger.info("Se solicita informacion de un paciente del sistema de HC");

		ResultConsultaAnalisisLaboratorioDTO resultDTO = null;

		try {
			FiltroConsultaEstudiosLaboratorioDTO filtroConsultaDTO = new FiltroConsultaEstudiosLaboratorioDTO();
			SimpleDateFormat formatoFecha = new SimpleDateFormat(FORMATO_DDMMAAAA);

			filtroConsultaDTO.setFechaInicio(formatoFecha.parse(filtroDTO.getFechaInicio()));
			filtroConsultaDTO.setFechaFinal(formatoFecha.parse(filtroDTO.getFechaFinal()));

			if(req.getParameter("omitirImportadas")!=null)
				resultDTO = prestacionBrindadaService.importarDesdeLaboratorioOmitirImportadas(filtroConsultaDTO);
			else
				resultDTO = prestacionBrindadaService.importarDesdeLaboratorio(filtroConsultaDTO);
				
			resultDTO.setError(false);

			logger.info("Informacion de Paciente del sistema de HC Obtenida OK");
		}

		catch (HBRemoteException e) {
			resultDTO = new ResultConsultaAnalisisLaboratorioDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_SISTEMA_REMOTO));

			logger.error("Error de conexion al obtener la informacion de paciente de HC");
		}
		catch (HBServiceException e) {
			resultDTO = new ResultConsultaAnalisisLaboratorioDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_SERVICIO));

			logger.error("Error en el servicio al obtener la informacion de paciente de HC");
		}
		catch (ParseException e) {
			resultDTO = new ResultConsultaAnalisisLaboratorioDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_SERVICIO));

			logger.error("Error al momento de parsear las fechas suministradas");
		}
		catch (HBDataAccessException e) {
			resultDTO = new ResultConsultaAnalisisLaboratorioDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));

			logger.error("Error de acceso a la BD cuando se importaba los datos desde Laboratorio");
		}

		ModelAndView mav = new ModelAndView(JsonView.instance);
		mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(resultDTO));

		return mav;

	}

	@RequestMapping(value = "/doImportarLaboratorio.htm", method = RequestMethod.POST)
	// Invocacion AJAX
	public ModelAndView importarPrestacionesLaboratorioElegidas(
			@ModelAttribute(RESULTADOPREVISUALIZACIONDTO) ResultConsultaAnalisisLaboratorioDTO resultAceptado,
			HttpServletRequest req, HttpSession session) {
		BasicDTO resultOperacion = new BasicDTO();
		try {
			prestacionBrindadaService.guardarPrestacionesDeLaboratorioAceptadas(resultAceptado);

			resultOperacion.setError(false);
			resultOperacion.setErrorMessage(messageSupport.getProperty(MENSAJE_EXITO_IMPORTACION));
		}
		catch (HBDataAccessException e) {
			resultOperacion.setError(true);
			resultOperacion.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
		}

		ModelAndView mav = new ModelAndView(JsonView.instance);
		mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(resultOperacion));

		return mav;
	}

	/**
	 * METODOS QUE MAPEAN CONTRA DIRECCIONES QUE SON MANEJADAS POR POST, PARA
	 * QUE SI EL CLIENTE DA REFRESH NO TIRE EXCEPCION. TODAS REDIRIGEN AL
	 * CONSULTAR PRESTACION
	 */

	@RequestMapping(value = "/doEditarPrestacionBrindada.htm")
	public ModelAndView doEditarPrestacionBrindadaSinPost(HttpServletRequest req, HttpSession session) {
		return this.getPaginaBasePrestacionesBrindadas(req, session);
	}

	@RequestMapping(value = "/editarPrestacionBrindada.htm")
	public ModelAndView editarPrestacionBrindadaSinPost(HttpServletRequest req, HttpSession session) {
		return this.getPaginaBasePrestacionesBrindadas(req, session);
	}

	@RequestMapping(value = "/doEliminarPrestacionBrindada.htm")
	// Invocacion sincronica
	public ModelAndView doEliminarPrestacionBrindadaSinPost(HttpServletRequest req, HttpSession session) {

		return null;
	}

	@RequestMapping(value = "/doNuevaPrestacionBrindada.htm")
	// Invocacion Ajax
	public ModelAndView doNuevaPrestacionBrindadaSinPost(HttpServletRequest req, HttpSession session) {
		return this.getPaginaBasePrestacionesBrindadas(req, session);
	}

	@RequestMapping("/volverPrestacionesBrindadas.htm")
	// Invocacion Sincronica
	public ModelAndView volverPrestacionesBrindadaSinPOST(HttpServletRequest req, HttpSession session) {
		return this.getPaginaBasePrestacionesBrindadas(req, session);
	}

	@RequestMapping("/refreshPrestacionesBrindadas.htm")
	// Invocacion Sincronica
	public ModelAndView refreshPrestacionesBrindadasSinPOST(HttpServletRequest req, HttpSession session) {
		return this.getPaginaBasePrestacionesBrindadas(req, session);
	}

}