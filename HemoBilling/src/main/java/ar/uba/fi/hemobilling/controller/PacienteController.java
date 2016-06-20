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

import ar.uba.fi.hemobilling.commons.dto.PacienteHCDTO;
import ar.uba.fi.hemobilling.commons.dto.PacienteLaboratorioDTO;
import ar.uba.fi.hemobilling.dto.BasicDTO;
import ar.uba.fi.hemobilling.dto.FiltroPaginadoDTO;
import ar.uba.fi.hemobilling.dto.obrassociales.ObraSocialDTO;
import ar.uba.fi.hemobilling.dto.paciente.FiltroConsultaPacienteDTO;
import ar.uba.fi.hemobilling.dto.paciente.PacienteDTO;
import ar.uba.fi.hemobilling.dto.paciente.ResultConsultaPacienteHCDTO;
import ar.uba.fi.hemobilling.dto.paciente.ResultConsultaPacienteLaboratorioDTO;
import ar.uba.fi.hemobilling.exception.domain.HBDataAccessException;
import ar.uba.fi.hemobilling.exception.domain.HBEntityRelationViolation;
import ar.uba.fi.hemobilling.exception.domain.HBObjectExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBObjectNotExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBRemoteException;
import ar.uba.fi.hemobilling.exception.domain.HBServiceException;
import ar.uba.fi.hemobilling.mvc.JSONObraSocial;
import ar.uba.fi.hemobilling.mvc.JsonView;
import ar.uba.fi.hemobilling.service.ObraSocialService;
import ar.uba.fi.hemobilling.service.PacienteService;
import ar.uba.fi.hemobilling.service.RemoteDataService;

import com.google.gson.Gson;

@Controller
@SessionAttributes({"pacienteDTO","nuevoPacienteDTO","filtroDTO","filtroPaginadoDTO"})
public class PacienteController extends AbstractController
{
	private static final String PACIENTEDTO = "pacienteDTO";
	private static final String NUEVOPACIENTEDTO = "nuevoPacienteDTO";
	private static final String NUEVOPACIENTEHCDTO = "nuevoPacienteHCDTO";
	private static final String NUEVOPACIENTELABODTO = "nuevoPacienteLaboDTO";
	private static final String FILTROPACIENTESDTO = "filtroDTO";
	private static final String FILTROPAGINADODTO = "filtroPaginadoDTO";
	private static final String LISTAPACIENTESDTO = "listapacientesDTO";
	private static final String RESULTOPERACIONDTO = "resultOperacionDTO";
	private static final String LISTAPOSIBLESOSDTO = "listaPosiblesOSDTO";

	private static final String MENSAJE_ERROR_PACIENTE_NO_EXISTE = "paciente.pacienteNoExisteEnBD";
	private static final String MENSAJE_ERROR_PACIENTE_YA_EXISTE = "paciente.pacienteYaExisteEnBD";
	private static final String MENSAJE_ERROR_REMOTO = "paciente.errorConexionSistemasRemotos";
	
	private static final String MENSAJE_PACIENTE_AGREGADO_OK = "paciente.pacienteAgregadoExitosamente";
	private static final String MENSAJE_PACIENTE_EDITADO_OK = "paciente.pacienteEditadoExitosamente";
	private static final String MENSAJE_PACIENTE_ELIMINADO_OK = "paciente.pacienteEliminadoExitosamente";
	private static final String MENSAJE_ERROR_CAMBIANDO_IDS = "paciente.IDsNoCambiados";
	private static final String MENSAJE_IDS_CAMBIADOS_OK = "paciente.IDsCambiadosOK";
	private static final String MENSAJE_PACIENTE_UTILIZADO = "paciente.pacienteAunUtilizado";
	
	private static final String MENSAJE_PACIENTE_NO_EXISTE_EN_HC = "remote.HCService.pacienteNoExiste";
	private static final String MENSAJE_SERVICIO_HC_NO_RESPONDE = "remote.HCService.excepcion";
	private static final String MENSAJE_PACIENTE_NO_EXISTE_EN_LABO = "remote.LaboService.pacienteNoExiste";
	private static final String MENSAJE_SERVICIO_LABO_NO_RESPONDE = "remote.LaboService.excepcion";
	
	private static final String JSP_PACIENTES = "pacientes";
	private static final String JSP_NUEVO_PACIENTE = "nuevoPaciente";
	private static final String JSP_EDITAR_PACIENTE = "editarPaciente";
	
	@Resource(name = "pacienteService")
	private PacienteService pacienteService;
	
	@Resource(name = "obraSocialService")
	private ObraSocialService obraSocialService;
	
	@Resource(name = "remoteDataService")
	private RemoteDataService remoteDataService;
	
	private static Logger logger = Logger.getLogger(PacienteController.class);
	
	
	
	
	/**
	 * Metodos privados -------------------------------------------------------------
	 * 
	 */
	
	private FiltroConsultaPacienteDTO getFiltroBasePacientesDTO()
	{
		FiltroConsultaPacienteDTO filtro = new FiltroConsultaPacienteDTO();
		
		filtro.setNombreyApellido( FiltroConsultaPacienteDTO.TODOS_DESC );
		filtro.setNroDNI( FiltroConsultaPacienteDTO.TODOS_DESC );
		filtro.setNumHistoriaClinica( FiltroConsultaPacienteDTO.TODOS_DESC );
		
		return filtro;		
	}
	
	private FiltroPaginadoDTO getFiltroPaginadoBase()
	{
		FiltroPaginadoDTO filtro = new FiltroPaginadoDTO();
		
		filtro.setNumeroPaginaActual(1);
		filtro.setRegPorPagina(cantidadRegistrosPorPagina);
		filtro.setError(false);
		
		return filtro;
	}
	
	private ModelAndView getPaginaConsulta( FiltroConsultaPacienteDTO filtroDTO , FiltroPaginadoDTO paginadoDTO , BasicDTO resultDTO  )
	{
		ModelAndView mav = new ModelAndView( JSP_PACIENTES );

		//DTO creado que se utiliza para llamar a los forms de edicion y eliminacion
		PacienteDTO pacienteDTO = new PacienteDTO();
		
		/* DTO que representa el filtro de busqueda.
		 * Si viene null creo uno desde una base de consulta, sino uso el que me dieron
		 */
		if( filtroDTO==null )
		{
			filtroDTO = getFiltroBasePacientesDTO();
		}
		
		/* DTO que representa el filtro de paginado.
		 * Si me lo dieron lo uso, sino creo uno base
		 */
		if( paginadoDTO==null )
		{
			paginadoDTO = getFiltroPaginadoBase();
		}
		
		/* DTO basico creado para ser utilizado cuando se quiere mostrar un mensaje de error
		 * Si me lo dieron lo uso, sino creo uno nuevo
		 */
		if( resultDTO==null )
		{
			resultDTO = new BasicDTO();
		}

		Collection<PacienteDTO> pacientes;
		
		try 
		{				
			//Lista de los Pacientes a la GUI, el base al filtro y paginado
			pacientes = pacienteService.getPacientes(filtroDTO , paginadoDTO );
		} 
		catch (HBDataAccessException e) 
		{
			//Error de acceso a la base de datos. Las mando vacia y con el mensaje de error
			pacientes = new ArrayList<PacienteDTO>();
			
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		}

		mav.addObject(FILTROPAGINADODTO , paginadoDTO );
		mav.addObject(FILTROPACIENTESDTO , filtroDTO );
		mav.addObject(PACIENTEDTO, pacienteDTO);
		mav.addObject(RESULTOPERACIONDTO, resultDTO);
		mav.addObject(LISTAPACIENTESDTO, pacientes);
		
		return mav;
		
	}
	
	private String generarPrestacionesPosibles() throws HBDataAccessException 
	{
		Collection<ObraSocialDTO> listaObrasSociales = obraSocialService.getObrasSocialesParaListar();
		
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
	
	
	
	
	
	
	/**
	 * PAGINAS DE CONSULTA --------------------------------------------------------------------------------------------
	 */
	

	/**
	 * Devuelve la pagina inicial de pacientes. Es por donde se entra al Caso de Uso.
	 * Crea todos los DTOs de sesion que se utilizaran en otros metodos
	 */

	@RequestMapping("/pacientes.htm") //Invocacion Sincronica
	public ModelAndView getPaginaBaseListaPacientes( HttpServletRequest req , HttpSession session )
	{
		logger.info("Llega solicitud de lista de pacientes");
		return getPaginaConsulta(null, null, null);
	}
	
	/**
	 * Devuelve la pagina inicial de pacientes, pero con los pacientes correspondientes al filtrado realizado.
	 * A esta se lleva por medio del comando filtrar desde la pagina base de la consulta.
	 */
	@RequestMapping(value="/refreshPacientes.htm" ,method=RequestMethod.POST) //Invocacion Sincronica
	public ModelAndView refreshListaPacientes(  @ModelAttribute(FILTROPACIENTESDTO) FiltroConsultaPacienteDTO filtroDTO ,
												@ModelAttribute(FILTROPAGINADODTO) FiltroPaginadoDTO paginadoDTO , 
												HttpServletRequest req , HttpSession session )
	{
		logger.info("Actualizo la pagina de pacientes, por medio del filtro o paginado");
		return getPaginaConsulta(filtroDTO, paginadoDTO, null);
	}	
	
	/**
	 * Vuelve a a lista de pacientes despues de una accion de agregado o edicion, mostrando o no el mensaje
	 * que se le suministra via resultDTO. El filtro se reinicializa para que se muestre cuando se llega 
	 * al CU
	 */
	@RequestMapping(value="/volverPacientes.htm" ,method=RequestMethod.POST) //Invocacion sincronica
	public ModelAndView volverPacientes( @ModelAttribute(RESULTOPERACIONDTO) BasicDTO resultDTO , HttpServletRequest req , HttpSession session )
	{
		logger.info("Se vuelve a la pagina de Pacientes");
		return getPaginaConsulta(null, null, resultDTO);
	}
	
	
	@RequestMapping(value="/getDetallePaciente.htm" ,method=RequestMethod.POST) //Invocacion Ajax
	public ModelAndView getDetallePaciente( @ModelAttribute(PACIENTEDTO) PacienteDTO pacienteElegido , HttpServletRequest req , HttpSession session )
	{
		logger.info("Se solicita informacion de un paciente");
		
		PacienteDTO pacienteFull = null; 
		try {
			pacienteFull = pacienteService.getPaciente( Long.parseLong( pacienteElegido.getId() ) );
			pacienteFull.setError(false);
		} 
		
		catch (HBDataAccessException e) 
		{
			pacienteFull = new PacienteDTO();
			pacienteFull.setError(true);
			pacienteFull.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		} 
		catch (HBObjectNotExistsException e) 
		{
			pacienteFull = new PacienteDTO();
			pacienteFull.setError(true);
			pacienteFull.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_PACIENTE_NO_EXISTE) );
		}
		
		ModelAndView mav = new ModelAndView( JsonView.instance );
		mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(pacienteFull));
		
		return mav;
	}	
	
	
	
	@RequestMapping(value="/getInformacionPacienteLaboratorio.htm" ,method=RequestMethod.POST) //Invocacion Ajax
	public ModelAndView getInformacionPacienteLaboratorio( @ModelAttribute(NUEVOPACIENTELABODTO) PacienteDTO pacienteElegido , HttpServletRequest req , HttpSession session )
	{
		logger.info("Se solicita informacion de un paciente del sistema de laboratorio");
		
		PacienteLaboratorioDTO pacienteLaboratorio= null; 
		BasicDTO resultDTO = new BasicDTO();
		
		try {
			pacienteLaboratorio = remoteDataService.getInformacionPacienteSistemaLaboratorio( pacienteElegido.getId() );
			resultDTO.setError(false);
			
			logger.info("Informacion de Paciente de Laboratorio Obtenida OK");
		} 
		
		catch (HBRemoteException e) 
		{
			pacienteLaboratorio = new PacienteLaboratorioDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty( MENSAJE_SERVICIO_LABO_NO_RESPONDE ) );
			
			logger.error("Error de conexion al obtener la informacion de paciente de Laboratorio");
		} 
		catch (HBServiceException e) 
		{
			pacienteLaboratorio = new PacienteLaboratorioDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_SERVICIO) );
			
			logger.error("Error en el servicio al obtener la informacion de paciente de Laboratorio");
		}
		
		ModelAndView mav = new ModelAndView( JsonView.instance );
		ResultConsultaPacienteLaboratorioDTO result = new ResultConsultaPacienteLaboratorioDTO();
		
		if( !resultDTO.getError() && pacienteLaboratorio==null )
		{
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_PACIENTE_NO_EXISTE_EN_LABO) );
			resultDTO.setError(true);
		}
		
		result.setResult(resultDTO);
		result.setPaciente(pacienteLaboratorio);
		
		mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(result));
		
		return mav;
	}
	
	
	@RequestMapping(value="/getInformacionPacienteHC.htm" ,method=RequestMethod.POST) //Invocacion Ajax
	public ModelAndView getInformacionPacienteHC( @ModelAttribute(NUEVOPACIENTEDTO) PacienteDTO pacienteElegido , HttpServletRequest req , HttpSession session )
	{
		logger.info("Se solicita informacion de un paciente del sistema de HC");
		
		PacienteHCDTO pacienteHC= null; 
		BasicDTO resultDTO = new BasicDTO();
		
		try {
			pacienteHC = remoteDataService.getInformacionPacienteSistemaHC( pacienteElegido.getNumHistoriaClinica() );
			
			logger.info("Informacion de Paciente del sistema de HC Obtenida OK");
		} 
		
		catch (HBRemoteException e) 
		{
			pacienteHC = new PacienteHCDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty( MENSAJE_SERVICIO_HC_NO_RESPONDE ) );
			
			logger.error("Error de conexion al obtener la informacion de paciente de HC");
		} 
		catch (HBServiceException e) 
		{
			pacienteHC = new PacienteHCDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_SERVICIO) );
			
			logger.error("Error en el servicio al obtener la informacion de paciente de HC");
		}
		
		ModelAndView mav = new ModelAndView( JsonView.instance );
		
		ResultConsultaPacienteHCDTO result = new ResultConsultaPacienteHCDTO();
		
		if( !resultDTO.getError() && pacienteHC==null )
		{
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_PACIENTE_NO_EXISTE_EN_HC) );
			resultDTO.setError(true);
		}
		
		result.setPaciente(pacienteHC);
		result.setResult(resultDTO);
		
		mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(result));
	
		return mav;
	}	


	
	/**
	 * PAGINAS DE NUEVO PACIENTE ---------------------------------------------------------------------------------------
	 */

	@RequestMapping("/nuevoPaciente.htm") //Invocacion Sincronica
	public ModelAndView nuevoPaciente( HttpServletRequest req , HttpSession session )
	{
		logger.info("Llega solicitud para ir a la pagina de nuevo paciente");
		
		ModelAndView mav = new ModelAndView( JSP_NUEVO_PACIENTE );
		
		PacienteDTO nuevoPacienteDTO = new PacienteDTO();
		PacienteDTO nuevoPacienteHCDTO = new PacienteDTO();
		PacienteDTO nuevoPacienteLaboDTO = new PacienteDTO();

		String listaObrasSocialesPosibles;
		BasicDTO resultDTO = new BasicDTO();
		try 
		{
			listaObrasSocialesPosibles = generarPrestacionesPosibles();
		} 
		catch (HBDataAccessException e) 
		{
			//Problemas levantando la lista de OS. Doy una vacia y reporto
			//Como es un valor obligatorio, muestro la GUI igual. Total, no va a 
			//poder cargar el paciente nuevo
			listaObrasSocialesPosibles = "";
			
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		}
		
		mav.addObject(NUEVOPACIENTEDTO,nuevoPacienteDTO);
		mav.addObject(NUEVOPACIENTEHCDTO,nuevoPacienteHCDTO);
		mav.addObject(NUEVOPACIENTELABODTO,nuevoPacienteLaboDTO);
		
		mav.addObject(RESULTOPERACIONDTO, resultDTO);
		mav.addObject(LISTAPOSIBLESOSDTO,listaObrasSocialesPosibles );		
		
		return mav;
	}

	
	@RequestMapping(value="/doNuevoPaciente.htm" ,method=RequestMethod.POST) //Invocacion Ajax
	public ModelAndView doNuevoPaciente( @ModelAttribute(NUEVOPACIENTEDTO) PacienteDTO nuevoPacienteDTO , HttpServletRequest req , HttpSession session )
	{
		logger.info("Llega solicitud para agregar un nuevo paciente");
		
		BasicDTO resultDTO = new BasicDTO();
		try 
		{	
			if( nuevoPacienteDTO.getNroDNI()==null ) nuevoPacienteDTO.setNroDNI("");
			if( nuevoPacienteDTO.getTipoDoc()==null ) nuevoPacienteDTO.setTipoDoc("");
			if( nuevoPacienteDTO.getNumAfiliadoOSActual()==null ) nuevoPacienteDTO.setNumAfiliadoOSActual("");
			
			pacienteService.agregarPaciente(nuevoPacienteDTO);
			
			resultDTO.setError(false);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_PACIENTE_AGREGADO_OK) );
		} 
		
		catch (HBDataAccessException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		}
		
		catch (HBObjectExistsException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_PACIENTE_YA_EXISTE) );
		} 
		catch (HBServiceException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_SERVICIO) );
		}
		
		ModelAndView mav = new ModelAndView( JsonView.instance );
		mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(resultDTO));
		
		return mav;
	}
	
	
	/**
	 * PAGINAS DE EDITAR PACIENTES ---------------------------------------------------------------------------------------
	 */

	@RequestMapping(value="/editarPaciente.htm" ,method=RequestMethod.POST) //Invocacion Sincronica
	public ModelAndView editarPaciente(@ModelAttribute(PACIENTEDTO) PacienteDTO pacienteDTO, HttpServletRequest req , HttpSession session )
	{
		logger.info("Llega solicitud para ir a la pagina de editar paciente");
		
		ModelAndView mav = new ModelAndView( JSP_EDITAR_PACIENTE );
		BasicDTO resultDTO = new BasicDTO();
		
		PacienteDTO nuevoPacienteHCDTO = new PacienteDTO();
		PacienteDTO nuevoPacienteLaboDTO = new PacienteDTO();
		
		String listaObrasSocialesPosibles;
		PacienteDTO pacienteLoaded;
		
		try 
		{
			pacienteLoaded = pacienteService.getPaciente( Long.parseLong( pacienteDTO.getId() ) );	
			listaObrasSocialesPosibles = generarPrestacionesPosibles();
		} 
		catch (HBDataAccessException e) 
		{
			//Problemas levantando los datos. Doy una vacia y reporto
			//Como es un valor obligatorio, muestro la GUI igual. Total, no va a 
			//poder hacer nada
			
			listaObrasSocialesPosibles = "";
			pacienteLoaded = new PacienteDTO();
			
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		} 
		catch (HBObjectNotExistsException e) 
		{
			//Problemas levantando los datos. Doy una vacia y reporto
			//Como es un valor obligatorio, muestro la GUI igual. Total, no va a 
			//poder hacer nada
			
			listaObrasSocialesPosibles = "";
			pacienteLoaded = new PacienteDTO();
			
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_PACIENTE_NO_EXISTE) );
		}
		
		mav.addObject(RESULTOPERACIONDTO, resultDTO);
		mav.addObject(LISTAPOSIBLESOSDTO,listaObrasSocialesPosibles);
		mav.addObject(NUEVOPACIENTEDTO,pacienteLoaded);
		
		mav.addObject(NUEVOPACIENTELABODTO,nuevoPacienteLaboDTO);
		mav.addObject(NUEVOPACIENTEHCDTO,nuevoPacienteHCDTO);
		
		return mav;
	}
	
	
	@RequestMapping(value="/doEditarPaciente.htm" ,method=RequestMethod.POST) //Invocacion AJAX
	public ModelAndView doEditarPaciente( @ModelAttribute(NUEVOPACIENTEDTO) PacienteDTO pacienteDTO , HttpServletRequest req , HttpSession session )
	{
		logger.info("Llega solicitud para editar un paciente");
		
		BasicDTO resultDTO = new BasicDTO();
		
		try 
		{
			if( pacienteDTO.getNroDNI()==null ) pacienteDTO.setNroDNI("");
			if( pacienteDTO.getTipoDoc()==null ) pacienteDTO.setTipoDoc("");
			if( pacienteDTO.getNumAfiliadoOSActual()==null ) pacienteDTO.setNumAfiliadoOSActual("");
			
			pacienteService.actualizarPaciente(pacienteDTO);
			
			resultDTO.setError(false);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_PACIENTE_EDITADO_OK) );
		} 
		
		catch (HBDataAccessException e) 
		{
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		} 
		catch (HBObjectNotExistsException e) 
		{
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_PACIENTE_NO_EXISTE) );
		}
		catch (HBServiceException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_SERVICIO) );
		}
		
		ModelAndView mav = new ModelAndView( JsonView.instance );
		mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(resultDTO));
		
		return mav;
	}
	
	

	
	/**
	 * PAGINAS DE ELIMINAR PACIENTE ---------------------------------------------------------------------------------------
	 */
	
	@RequestMapping(value="/doEliminarPaciente.htm" ,method=RequestMethod.POST) //Invocacion sincronica
	public ModelAndView doEliminarUsuario( @ModelAttribute(PACIENTEDTO) PacienteDTO pacienteDTO , HttpServletRequest req , HttpSession session ) 
	{
		logger.info("Llega solicitud para eliminar un paciente");
		
		BasicDTO resultDTO = new BasicDTO();

		try 
		{
			PacienteDTO dto = pacienteService.getPaciente( Long.parseLong( pacienteDTO.getId() ) );
			try {
				pacienteService.eliminarPaciente(dto);
			}
			catch (HBEntityRelationViolation e) {
				resultDTO.setError(true);
				resultDTO.setErrorMessage(messageSupport
						.getProperty(MENSAJE_PACIENTE_UTILIZADO));
			}
			
			resultDTO.setError(false);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_PACIENTE_ELIMINADO_OK) );
		} 
		catch (HBDataAccessException e) 
		{
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		} 
		catch (HBObjectNotExistsException e) 
		{
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_PACIENTE_NO_EXISTE) );
		}
		
		return this.volverPacientes(resultDTO, req, session);
			
	}
	
	
	@RequestMapping(value="/cambiarIDSistemaLaboratorio.htm" ,method=RequestMethod.POST) //Invocacion Ajax
	public ModelAndView cambiarIDSistemaLaboratorio( @ModelAttribute(PACIENTEDTO) PacienteDTO pacienteElegido , HttpServletRequest req , HttpSession session )
	{
		logger.info("Se solicita cambiar el ID del paciente en el sistema de laboratorio");
		
		BasicDTO resultDTO = new BasicDTO();
		
		try {
			Boolean result = remoteDataService.cambiarIDLaboratorioPorNumHC(pacienteElegido.getNumHistoriaClinica(), pacienteElegido.getId() );
			
			if( result=false)
				resultDTO.setErrorMessage( messageSupport.getProperty( MENSAJE_ERROR_CAMBIANDO_IDS ) );
			else
				resultDTO.setErrorMessage( messageSupport.getProperty( MENSAJE_IDS_CAMBIADOS_OK ) );
				
			resultDTO.setError(!result);

			logger.info("Operacion de cambio de ID realizada OK");
		} 
		
		catch (HBRemoteException e) 
		{
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty( MENSAJE_ERROR_REMOTO ) );
			
			logger.error("Error de conexion al querer cambiar el ID del paciente en el sistema de laboratorio");
		} 
		catch (HBServiceException e) 
		{
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_SERVICIO) );
			
			logger.error("Error en el servicio al querer cambiar el ID del paciente en el sistema de laboratorio");
		}
		
		ModelAndView mav = new ModelAndView( JsonView.instance );
		mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(resultDTO));
		
		return mav;
	}	
	
	
	
	
	/**
	 * METODOS QUE MAPEAN CONTRA DIRECCIONES QUE SON MANEJADAS POR POST, PARA QUE SI EL CLIENTE DA 
	 * REFRESH NO TIRE EXCEPCION. TODAS REDIRIGEN AL CONSULTAR USUARIO
	 */
	
	@RequestMapping(value="/doEditarPaciente.htm") //Invocacion Ajax
	public ModelAndView doEditarUsuarioSinPost( HttpServletRequest req , HttpSession session )
	{
		return this.getPaginaBaseListaPacientes(req, session);
	}
	
	@RequestMapping(value="/editarPaciente.htm") //Invocacion Ajax
	public ModelAndView editarUsuarioSinPost( HttpServletRequest req , HttpSession session )
	{
		return this.getPaginaBaseListaPacientes(req, session);
	}
	
	@RequestMapping(value="/doEliminarPaciente.htm") //Invocacion sincronica
	public ModelAndView doEliminarUsuarioSinPost( HttpServletRequest req , HttpSession session ) 
	{
		return this.getPaginaBaseListaPacientes(req, session);
	}
	
	@RequestMapping(value="/doNuevoPaciente.htm") //Invocacion Ajax
	public ModelAndView doNuevoUsuarioSinPost( HttpServletRequest req , HttpSession session )
	{
		return this.getPaginaBaseListaPacientes(req, session);
	}
	
	@RequestMapping("/volverPacientes.htm") //Invocacion Sincronica
	public ModelAndView volverUsuariosSinPOST( HttpServletRequest req , HttpSession session )
	{
		return this.getPaginaBaseListaPacientes(req, session);
	}
	
	@RequestMapping("/refreshPacientes.htm") //Invocacion Sincronica
	public ModelAndView refreshUsuariosSinPOST( HttpServletRequest req , HttpSession session )
	{
		return this.getPaginaBaseListaPacientes(req, session);
	}
	
	@RequestMapping("/cambiarIDSistemaLaboratorio.htm") //Invocacion Sincronica
	public ModelAndView cambiarIDSistemaLaboratorio( HttpServletRequest req , HttpSession session )
	{
		return this.getPaginaBaseListaPacientes(req, session);
	}
	
	@RequestMapping("/getInformacionPacienteHC.htm") //Invocacion Sincronica
	public ModelAndView getInformacionPacienteHC( HttpServletRequest req , HttpSession session )
	{
		return this.getPaginaBaseListaPacientes(req, session);
	}
	
	@RequestMapping("/getInformacionPacienteLaboratorio.htm") //Invocacion Sincronica
	public ModelAndView getInformacionPacienteLaboratorio( HttpServletRequest req , HttpSession session )
	{
		return this.getPaginaBaseListaPacientes(req, session);
	}	


}