package ar.uba.fi.hemobilling.controller;

import java.util.ArrayList;
import java.util.Collection;

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
import ar.uba.fi.hemobilling.dto.clientes.ClienteDTO;
import ar.uba.fi.hemobilling.dto.clientes.FiltroConsultaClientesDTO;
import ar.uba.fi.hemobilling.dto.general.TipoIVADTO;
import ar.uba.fi.hemobilling.exception.domain.HBDataAccessException;
import ar.uba.fi.hemobilling.exception.domain.HBEntityRelationViolation;
import ar.uba.fi.hemobilling.exception.domain.HBObjectExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBObjectNotExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBServiceException;
import ar.uba.fi.hemobilling.mvc.JsonView;
import ar.uba.fi.hemobilling.service.ClienteService;
import ar.uba.fi.hemobilling.service.TipoIVAService;

@Controller
@SessionAttributes({ "clienteDTO", "nuevoclienteDTO",	"actualizarclienteDTO", "filtroDTO", "filtroPaginadoDTO" })
public class ClienteController extends AbstractController
{
	
	@Resource(name = "clienteService")
	private ClienteService clienteService;
	
	
	@Resource(name = "tipoIVAService")
	private TipoIVAService tipoIVAService;

	private static final String JSP_CLIENTES = "clientes";
	private static final String JSP_NUEVO_CLIENTE = "nuevoCliente";
	private static final String JSP_EDITAR_CLIENTE = "editarCliente";

	private static final String CLIENTEDTO = "clienteDTO";
	private static final String NUEVOCLIENTEDTO = "nuevoclienteDTO";
	private static final String ACTUALIZARCLIENTEDTO = "actualizarclienteDTO";
	private static final String CLIENTESDTO = "clientesDTO";
	private static final String RESULTOPERACIONDTO = "resultOperacionDTO";
	private static final String FILTRODTO = "filtroDTO";
	private static final String FILTROPAGINADODTO = "filtroPaginadoDTO";
	
	private static final String TIPOSIVADTO = "tiposivaDTO";

	private static final String MENSAJE_CLIENTE_YA_EXISTE = "cliente.clienteYaExisteEnBD";
	private static final String MENSAJE_CLIENTE_NO_EXISTE = "cliente.clienteNoExisteEnBD";

	private static final String MENSAJE_CLIENTE_AGREGADO_OK = "cliente.clienteAgregadoExitosamente";
	private static final String MENSAJE_CLIENTE_EDITADO_OK = "cliente.clienteEditadoExitosamente";
	private static final String MENSAJE_CLIENTE_ELIMINADO_OK = "cliente.clienteEliminadoExitosamente";
	private static final String MENSAJE_CLIENTE_AUN_UTILIZADO = "cliente.clienteAunUtilizado";

	private static Logger logger = Logger.getLogger(ClienteController.class);
	

	private FiltroConsultaClientesDTO getFiltroBaseClientesDTO() {
		FiltroConsultaClientesDTO filtro = new FiltroConsultaClientesDTO();

		filtro.setCodigo(FiltroConsultaDTO.TODOS_DESC);
		filtro.setCodigoContable(FiltroConsultaDTO.TODOS_DESC);
		filtro.setCuit(FiltroConsultaDTO.TODOS_DESC);
		filtro.setLocalidad(FiltroConsultaDTO.TODOS_DESC);
		filtro.setNombre(FiltroConsultaDTO.TODOS_DESC);
		filtro.setProvincia(FiltroConsultaDTO.TODOS_DESC);
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
			FiltroConsultaClientesDTO filtroDTO,
			FiltroPaginadoDTO paginadoDTO, BasicDTO resultDTO) {
		ModelAndView mav = new ModelAndView(JSP_CLIENTES);
		ClienteDTO cliente = new ClienteDTO();

		if (filtroDTO == null)
			filtroDTO = getFiltroBaseClientesDTO();
		if (paginadoDTO == null)
			paginadoDTO = getFiltroPaginadoBase();
		if (resultDTO == null)
			resultDTO = new BasicDTO();

		Collection<ClienteDTO> clientes;

		try {
			clientes = clienteService.getClientes(filtroDTO, paginadoDTO);
			mav.addObject(TIPOSIVADTO, tipoIVAService.getTiposIVA());

		} catch (HBDataAccessException e) {

			clientes = new ArrayList<ClienteDTO>();
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport
					.getProperty(MENSAJE_ERROR_DB));
		}

		
		mav.addObject(FILTROPAGINADODTO, paginadoDTO);
		mav.addObject(FILTRODTO, filtroDTO);
		mav.addObject(CLIENTEDTO, cliente);
		mav.addObject(CLIENTESDTO, clientes);
		mav.addObject(RESULTOPERACIONDTO, resultDTO);

		return mav;

	}
	
	
	
	

	/*-------- PAGINAS DE CONSULTA ------ */

	@RequestMapping("/clientes.htm")
	public ModelAndView getPaginaBaseListaClientes(HttpServletRequest req,HttpSession session) {
		logger.info("Llega solicitud de lista de clientes");
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

	@RequestMapping(value = "/refreshClientes.htm", method = RequestMethod.POST)
	public ModelAndView refreshListaClientes(
			@ModelAttribute(FILTRODTO) FiltroConsultaClientesDTO filtroDTO,
			@ModelAttribute(FILTROPAGINADODTO) FiltroPaginadoDTO paginadoDTO,
			HttpServletRequest req, HttpSession session) {
		logger.info("Actualizo la pagina de clientes, por medio del filtro o paginado");
		return getPaginaConsulta(filtroDTO, paginadoDTO, null);
	}

	@RequestMapping(value = "/volverClientes.htm", method = RequestMethod.POST)
	public ModelAndView volverClientes(
			@ModelAttribute(RESULTOPERACIONDTO) BasicDTO resultDTO,
			HttpServletRequest req, HttpSession session) {
		logger.info("Actualizacion de la pagina de clientes, por medio del filtro o paginado");
		return getPaginaConsulta(null, null, resultDTO);
	}

	/* ------------ NUEVO ------------ */

	@RequestMapping("/nuevoCliente.htm")
	public ModelAndView nuevoCliente(HttpServletRequest req, HttpSession session) {
		
		
		logger.info("Solicitud de creacion de un nuevo cliente.");
		ModelAndView mav = new ModelAndView(JSP_NUEVO_CLIENTE);
		ClienteDTO nuevoDTO = new ClienteDTO();
		BasicDTO resultDTO = new BasicDTO();
		
		try {
			mav.addObject(TIPOSIVADTO, tipoIVAService.getTiposIVA());
		}
		catch (HBDataAccessException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
		}
		
		mav.addObject(NUEVOCLIENTEDTO, nuevoDTO);
		mav.addObject(RESULTOPERACIONDTO, resultDTO);

		return mav;
	}

	@RequestMapping(value = "/doNuevoCliente.htm", method = RequestMethod.POST)
	public ModelAndView doNuevoCliente(
			@ModelAttribute(NUEVOCLIENTEDTO) ClienteDTO nuevoDTO,
			HttpServletRequest req, HttpSession session) {
		logger.info("Creacion de nuevo cliente con codigo " + nuevoDTO.getCodigo());
		if(nuevoDTO.getTipoIVA().getId()==-1) nuevoDTO.setTipoIVA(null);
		BasicDTO resultDTO = new BasicDTO();
		try {

			clienteService.agregarCliente(nuevoDTO);
			
			resultDTO.setError(false);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_CLIENTE_AGREGADO_OK));
		}

		catch (HBDataAccessException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
		}

		catch (HBObjectExistsException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_CLIENTE_YA_EXISTE));
		} catch (HBServiceException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_SERVICIO));
		
		}
		ModelAndView mav = new ModelAndView(JsonView.instance);
		mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(resultDTO));

		return mav;
	}

	/* ----------- EDITAR ----------- */

	@RequestMapping(value = "/editarCliente.htm", method = RequestMethod.POST)
	public ModelAndView editarCliente(@ModelAttribute(CLIENTEDTO) ClienteDTO objetoDTO,
			HttpServletRequest req, HttpSession session) {
		logger.info("Modificacion de la obra social "+objetoDTO.getCodigo() );

		ModelAndView mav = new ModelAndView(JSP_EDITAR_CLIENTE);
		BasicDTO resultDTO = new BasicDTO();
		ClienteDTO objetoEditar;
		try {

			objetoEditar = clienteService.getCliente(objetoDTO.getCodigo());
			mav.addObject(TIPOSIVADTO, tipoIVAService.getTiposIVA());
			
		} catch (HBDataAccessException e) {

			objetoEditar = new ClienteDTO();
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
		} catch (HBObjectNotExistsException e) {
			objetoEditar = new ClienteDTO();

			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_CLIENTE_NO_EXISTE));
		}


		mav.addObject(RESULTOPERACIONDTO, resultDTO);
		mav.addObject(ACTUALIZARCLIENTEDTO, objetoEditar);

		return mav;
	}

	@RequestMapping(value = "/doEditarCliente.htm", method = RequestMethod.POST)
	public ModelAndView doEditarcliente(
			@ModelAttribute(ACTUALIZARCLIENTEDTO) ClienteDTO editarDTO,
			HttpServletRequest req, HttpSession session)
	{
		
		return doEditar(editarDTO);
	}

//	private ModelAndView doAsociar(clienteDTO editarDTO, HttpServletRequest req, HttpSession session) {
//		clienteService.asociar(editarDTO);
//		BasicDTO resultDTO = new BasicDTO();
//		resultDTO.setError(true);
//		resultDTO.setErrorMessage("Asociacion realizada correctamente");
//		List<AsociacionclienteListaPrecioDTO> asociaciones=clienteService.getAsociaciones(editarDTO.getCodigo());
//		editarDTO.setAsociacionActual(asociaciones.get(asociaciones.size()-1));
//		ModelAndView mav = new ModelAndView(JsonView.instance);
//		mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(resultDTO));
//		return mav;
//		
//
//	}

	private ModelAndView doEditar(ClienteDTO editarDTO)
	{
		logger.info("Modificacion del cliente"+editarDTO.getCodigo());
		if(editarDTO.getTipoIVA().getId()==-1) editarDTO.setTipoIVA(null);
		BasicDTO resultDTO = new BasicDTO();

		try {
			clienteService.actualizarCliente(editarDTO);

			resultDTO.setError(false);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_CLIENTE_EDITADO_OK));
		}

		catch (HBDataAccessException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
		} catch (HBObjectNotExistsException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_CLIENTE_NO_EXISTE));
		} catch (HBServiceException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_SERVICIO));
		}
		catch (HBObjectExistsException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_CLIENTE_YA_EXISTE));
		}

		ModelAndView mav = new ModelAndView(JsonView.instance);
		mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(resultDTO));

		return mav;
	}

	/* ----------- ELIMINAR ----------- */

	@RequestMapping(value = "/doEliminarCliente.htm", method = RequestMethod.POST)
	public ModelAndView doEliminarCliente(
			@ModelAttribute(CLIENTEDTO) ClienteDTO eliminarDTO,
			HttpServletRequest req, HttpSession session) {
		logger.info("Eliminar cliente con codigo"+ eliminarDTO.getCodigo() + " .");

		BasicDTO resultDTO = new BasicDTO();

		try {
			ClienteDTO dto = clienteService.getCliente(eliminarDTO.getCodigo());
			clienteService.eliminarCliente(dto);

			resultDTO.setError(false);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_CLIENTE_ELIMINADO_OK));
		} catch (HBDataAccessException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_ERROR_DB));
		} catch (HBObjectNotExistsException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport.getProperty(MENSAJE_CLIENTE_NO_EXISTE));
		}
		catch (HBEntityRelationViolation e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage(messageSupport
					.getProperty(MENSAJE_CLIENTE_AUN_UTILIZADO));
		}

		return this.volverClientes(resultDTO, req, session);

	}



	 @RequestMapping(value="/doEditarcliente.htm") //Invocacion Ajax
	 public ModelAndView doEditarObrasSocialesSinPost( HttpServletRequest req ,
	 HttpSession session )
	 {
	 return this.getPaginaBaseListaClientes(req, session);
	 }
	
	 @RequestMapping(value="/editarCliente.htm") //Invocacion Ajax
	 public ModelAndView editarPrestacionSinPost( HttpServletRequest req ,
	 HttpSession session )
	 {
	 return this.getPaginaBaseListaClientes(req, session);
	 }
	
	 @RequestMapping(value="/doEliminarCliente.htm") //Invocacion sincronica
	 public ModelAndView doEliminarclienteSinPost( HttpServletRequest req ,
	 HttpSession session )
	 {
	 
	 return null;
	 }
	
	 @RequestMapping(value="/doNuevoCliente.htm") //Invocacion Ajax
	 public ModelAndView doNuevoClienteSinPost( HttpServletRequest req ,
	 HttpSession session )
	 {
	 return this.getPaginaBaseListaClientes(req, session);
	 }
	
	 @RequestMapping("/volverClientes.htm") //Invocacion Sincronica
	 public ModelAndView volverClientesSinPOST( HttpServletRequest req ,
	 HttpSession session )
	 {
	 return this.getPaginaBaseListaClientes(req, session);
	 }
	
	 @RequestMapping("/refreshClientes.htm") //Invocacion Sincronica
	 public ModelAndView refreshClientesSinPOST( HttpServletRequest req ,
	 HttpSession session )
	 {
	 return this.getPaginaBaseListaClientes(req, session);
	 }
	
	 
	 

}
