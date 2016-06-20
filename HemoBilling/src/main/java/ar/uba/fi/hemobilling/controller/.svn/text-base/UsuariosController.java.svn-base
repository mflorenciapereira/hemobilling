package ar.uba.fi.hemobilling.controller;

import java.security.Principal;
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
import ar.uba.fi.hemobilling.dto.FiltroPaginadoDTO;
import ar.uba.fi.hemobilling.dto.usuarios.FiltroConsultaUsuariosDTO;
import ar.uba.fi.hemobilling.dto.usuarios.RolUsuarioDTO;
import ar.uba.fi.hemobilling.dto.usuarios.UsuarioDTO;
import ar.uba.fi.hemobilling.exception.domain.HBDataAccessException;
import ar.uba.fi.hemobilling.exception.domain.HBObjectExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBObjectNotExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBServiceException;
import ar.uba.fi.hemobilling.mvc.JsonView;
import ar.uba.fi.hemobilling.service.UsuarioService;

@Controller
@SessionAttributes({"usuariodto","nuevousuariodto","usuarioActualizarDTO","filtroDTO","filtroPaginadoDTO"})
public class UsuariosController extends AbstractController {
	
	@Resource(name = "usuarioService")
	private UsuarioService usuarioService;
	
	private static final String JSP_USUARIOS = "usuarios";
	private static final String JSP_NUEVO_USUARIO = "nuevoUsuario";
	private static final String JSP_EDITAR_USUARIO = "editarUsuario";
	
	private static final String USUARIODTO = "usuariodto";
	private static final String NUEVOUSUARIODTO = "nuevoUsuarioDTO";
	private static final String NUEVOACTUALIZACIONDTO = "usuarioActualizarDTO";
	private static final String USUARIOSDTO = "usuariosdto";
	private static final String POSIBLESROLESUSUARIOSDTO = "posiblesRolesUsuariosDTO";
	private static final String RESULTOPERACIONDTO = "resultOperacionDTO";
	private static final String FILTROUSUARIOSDTO = "filtroDTO";
	private static final String FILTROPAGINADODTO = "filtroPaginadoDTO";
	
	private static final String MENSAJE_USUARIO_YA_EXISTE = "usuarios.usuarioYaExisteEnBD";
	private static final String MENSAJE_USUARIO_NO_EXISTE = "usuarios.usuarioNoExisteEnBD";
	
	private static final String MENSAJE_USUARIO_AGREGADO_OK = "usuario.usuarioAgregadoExitosamente";
	private static final String MENSAJE_USUARIO_EDITADO_OK = "usuario.usuarioEditadoExitosamente";
	private static final String MENSAJE_USUARIO_ELIMINADO_OK = "usuario.usuarioEliminadoExitosamente";
	
	private static Logger logger = Logger.getLogger(UsuariosController.class);	
	
	
	private FiltroConsultaUsuariosDTO getFiltroBaseUsuariosDTO()
	{
		FiltroConsultaUsuariosDTO filtro = new FiltroConsultaUsuariosDTO();
		
		filtro.setHabilitado( FiltroConsultaUsuariosDTO.TODOS_DESC );
		filtro.setNombreCompleto( FiltroConsultaUsuariosDTO.TODOS_DESC );
		filtro.setNombreUsuario( FiltroConsultaUsuariosDTO.TODOS_DESC );
		filtro.setCodRol( FiltroConsultaUsuariosDTO.TODOS_DESC );
		filtro.setDescRol( FiltroConsultaUsuariosDTO.TODOS_DESC );		
		
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
	
	private ModelAndView getPaginaConsulta( FiltroConsultaUsuariosDTO filtroDTO , FiltroPaginadoDTO paginadoDTO , BasicDTO resultDTO  )
	{
		ModelAndView mav = new ModelAndView( JSP_USUARIOS );

		//DTO creado que se utiliza para llamar a los forms de edicion y eliminacion
		UsuarioDTO usuario = new UsuarioDTO();
		
		/* DTO que representa el filtro de busqueda.
		 * Si viene null creo uno desde una base de consulta, sino uso el que me dieron
		 */
		if( filtroDTO==null )
		{
			filtroDTO = getFiltroBaseUsuariosDTO();
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

		Collection<UsuarioDTO> usuarios;
		Collection<RolUsuarioDTO> rolesPosibles;
		
		try 
		{				
			//Lista de los usuarios a la GUI, el base al filtro y paginado
			usuarios = usuarioService.getUsuarios( filtroDTO , paginadoDTO );
			
			//Lista de roles posibles, para mostrar en el filtro de busqueda
			rolesPosibles = usuarioService.getRolesPosibles();	
		} 
		catch (HBDataAccessException e) 
		{
			//Error de acceso a la base de datos. Las mando vacia y con el mensaje de error
			usuarios = new ArrayList<UsuarioDTO>();
			rolesPosibles = new ArrayList<RolUsuarioDTO>();
			
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		}

		mav.addObject(FILTROPAGINADODTO , paginadoDTO );
		mav.addObject(FILTROUSUARIOSDTO , filtroDTO );
		mav.addObject(USUARIODTO, usuario);
		mav.addObject(USUARIOSDTO, usuarios);
		mav.addObject(RESULTOPERACIONDTO, resultDTO);
		mav.addObject(POSIBLESROLESUSUARIOSDTO,rolesPosibles);
		
		return mav;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	

	@RequestMapping(value="/getUserName.htm" ,method=RequestMethod.POST) //Invocacion Ajax
	public ModelAndView getUserName( HttpServletRequest req , HttpSession session , Principal principal )
	{
		String currentUser;
		try {
			UsuarioDTO usuario = usuarioService.getUsuario(principal.getName());
			currentUser = usuario.getNombreCompleto();
		} catch (HBDataAccessException e) {
			currentUser = null;
		} catch (HBObjectNotExistsException e) {
			currentUser = null;
		}
		
		ModelAndView mav = new ModelAndView( JsonView.instance );
		mav.addObject(JsonView.JSON_ROOT, this.createDataResponse(currentUser));
		
		return mav;
	}
	
	
	
	/**
	 * PAGINAS DE CONSULTA --------------------------------------------------------------------------------------------
	 */
	

	/**
	 * Devuelve la pagina inicial de usuarios. Es por donde se entra al Caso de Uso.
	 * Crea todos los DTOs de sesion que se utilizaran en otros metodos
	 */
	@RequestMapping("/usuarios.htm") //Invocacion Sincronica
	public ModelAndView getPaginaBaseListaUsuarios( HttpServletRequest req , HttpSession session )
	{
		logger.info("Llega solicitud de lista de usuarios");
		return getPaginaConsulta(null, null, null);
	}
	
	/**
	 * Devuelve la pagina inicial de usuarios, pero con los usuarios correspondientes al filtrado realizado.
	 * A esta se lleva por medio del comando filtrar desde la pagina base de la consulta.
	 */
	@RequestMapping(value="/refreshUsuarios.htm" ,method=RequestMethod.POST) //Invocacion Sincronica
	public ModelAndView refreshListaUsuarios(   @ModelAttribute(FILTROUSUARIOSDTO) FiltroConsultaUsuariosDTO filtroDTO ,
												@ModelAttribute(FILTROPAGINADODTO) FiltroPaginadoDTO paginadoDTO , 
												HttpServletRequest req , HttpSession session )
	{
		logger.info("Actualizo la pagina de usuarios, por medio del filtro o paginado");
		return getPaginaConsulta(filtroDTO, paginadoDTO, null);
	}	
	
	/**
	 * Vuelve a a lista de usuarios despues de una accion de agregado o edicion, mostrando o no el mensaje
	 * que se le suministra via resultDTO. El filtro se reinicializa para que se muestre cuando se llega 
	 * al CU
	 */
	@RequestMapping(value="/volverUsuarios.htm" ,method=RequestMethod.POST) //Invocacion sincronica
	public ModelAndView volverUsuarios( @ModelAttribute(RESULTOPERACIONDTO) BasicDTO resultDTO , HttpServletRequest req , HttpSession session )
	{
		logger.info("Actualizo la pagina de usuarios, por medio del filtro o paginado");
		return getPaginaConsulta(null, null, resultDTO);
	}
	
	
	
	/**
	 * PAGINAS DE NUEVO USUARIO ---------------------------------------------------------------------------------------
	 */

	@RequestMapping("/nuevoUsuario.htm") //Invocacion Sincronica
	public ModelAndView nuevoUsuario( HttpServletRequest req , HttpSession session )
	{
		logger.info("Llega solicitud para ir a la pagina de nuevo usuario");
		
		ModelAndView mav = new ModelAndView( JSP_NUEVO_USUARIO );
		
		UsuarioDTO nuevoUsuarioDTO = new UsuarioDTO();
		mav.addObject(NUEVOUSUARIODTO,nuevoUsuarioDTO);

		Collection<RolUsuarioDTO> rolesPosibles;
		BasicDTO resultDTO = new BasicDTO();
		try 
		{
			rolesPosibles = usuarioService.getRolesPosibles();	
		} 
		catch (HBDataAccessException e) 
		{
			//Problemas levantando la lista de roles. Doy una vacia y reporto
			//Como es un valor obligatorio, muestro la GUI igual. Total, no va a 
			//poder cargar el usuario nuevo
			rolesPosibles = new ArrayList<RolUsuarioDTO>();
			
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		}
		
		mav.addObject(RESULTOPERACIONDTO, resultDTO);
		mav.addObject(POSIBLESROLESUSUARIOSDTO,rolesPosibles);
		
		return mav;
	}

	
	@RequestMapping(value="/doNuevoUsuario.htm" ,method=RequestMethod.POST) //Invocacion Ajax
	public ModelAndView doNuevoUsuario( @ModelAttribute(NUEVOUSUARIODTO) UsuarioDTO nuevoUsuarioDTO , HttpServletRequest req , HttpSession session )
	{
		logger.info("Llega solicitud para agregar un nuevo usuario");
		
		BasicDTO resultDTO = new BasicDTO();
		try 
		{	
			usuarioService.agregarUsuario(nuevoUsuarioDTO);
			
			resultDTO.setError(false);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_USUARIO_AGREGADO_OK) );
		} 
		
		catch (HBDataAccessException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		}
		
		catch (HBObjectExistsException e) {
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_USUARIO_YA_EXISTE) );
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
	 * PAGINAS DE EDITAR USUARIO ---------------------------------------------------------------------------------------
	 */

	@RequestMapping(value="/editarUsuario.htm" ,method=RequestMethod.POST) //Invocacion Sincronica
	public ModelAndView editarUsuario(@ModelAttribute(USUARIODTO) UsuarioDTO usuarioDTO, HttpServletRequest req , HttpSession session )
	{
		logger.info("Llega solicitud para ir a la pagina de editar usuario");
		
		ModelAndView mav = new ModelAndView( JSP_EDITAR_USUARIO );
		BasicDTO resultDTO = new BasicDTO();
		
		Collection<RolUsuarioDTO> rolesPosibles;
		UsuarioDTO usuarioLoaded;
		
		try 
		{
			rolesPosibles = usuarioService.getRolesPosibles();	
			usuarioLoaded = usuarioService.getUsuario(usuarioDTO.getNombreUsuario());
		} 
		catch (HBDataAccessException e) 
		{
			//Problemas levantando la lista de roles. Doy una vacia y reporto
			//Como es un valor obligatorio, muestro la GUI igual. Total, no va a 
			//poder cargar el usuario nuevo
			rolesPosibles = new ArrayList<RolUsuarioDTO>();
			usuarioLoaded = new UsuarioDTO();
			
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		} 
		catch (HBObjectNotExistsException e) 
		{
			usuarioLoaded = new UsuarioDTO();
			rolesPosibles = new ArrayList<RolUsuarioDTO>();
			
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_USUARIO_NO_EXISTE) );
		}
		
		mav.addObject(RESULTOPERACIONDTO, resultDTO);
		mav.addObject(POSIBLESROLESUSUARIOSDTO,rolesPosibles);
		mav.addObject(NUEVOACTUALIZACIONDTO,usuarioLoaded);
		
		return mav;
	}
	
	
	@RequestMapping(value="/doEditarUsuario.htm" ,method=RequestMethod.POST) //Invocacion AJAX
	public ModelAndView doEditarUsuario( @ModelAttribute(NUEVOACTUALIZACIONDTO) UsuarioDTO usuarioDTO , HttpServletRequest req , HttpSession session )
	{
		logger.info("Llega solicitud para editar un usuario");
		
		BasicDTO resultDTO = new BasicDTO();
		
		try 
		{
			usuarioService.actualizarUsuario(usuarioDTO);
			
			resultDTO.setError(false);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_USUARIO_EDITADO_OK) );
		} 
		
		catch (HBDataAccessException e) 
		{
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		} 
		catch (HBObjectNotExistsException e) 
		{
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_USUARIO_NO_EXISTE) );
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
	 * PAGINAS DE ELIMINAR USUARIO ---------------------------------------------------------------------------------------
	 */
	
	@RequestMapping(value="/doEliminarUsuario.htm" ,method=RequestMethod.POST) //Invocacion sincronica
	public ModelAndView doEliminarUsuario( @ModelAttribute(USUARIODTO) UsuarioDTO usuarioDTO , HttpServletRequest req , HttpSession session ) 
	{
		logger.info("Llega solicitud para eliminar un usuario");
		
		BasicDTO resultDTO = new BasicDTO();

		try 
		{
			UsuarioDTO dto = usuarioService.getUsuario(usuarioDTO.getNombreUsuario());
			usuarioService.eliminarUsuario(dto);
			
			resultDTO.setError(false);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_USUARIO_ELIMINADO_OK) );
		} 
		catch (HBDataAccessException e) 
		{
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_ERROR_DB) );
		} 
		catch (HBObjectNotExistsException e) 
		{
			resultDTO.setError(true);
			resultDTO.setErrorMessage( messageSupport.getProperty(MENSAJE_USUARIO_NO_EXISTE) );
		}
		
		return this.volverUsuarios(resultDTO, req, session);
			
	}
	

	
	/**
	 * METODOS QUE MAPEAN CONTRA DIRECCIONES QUE SON MANEJADAS POR POST, PARA QUE SI EL CLIENTE DA 
	 * REFRESH NO TIRE EXCEPCION. TODAS REDIRIGEN AL CONSULTAR USUARIO
	 */
	
	@RequestMapping(value="/doEditarUsuario.htm") //Invocacion Ajax
	public ModelAndView doEditarUsuarioSinPost( HttpServletRequest req , HttpSession session )
	{
		return this.getPaginaBaseListaUsuarios(req, session);
	}
	
	@RequestMapping(value="/editarUsuario.htm") //Invocacion Ajax
	public ModelAndView editarUsuarioSinPost( HttpServletRequest req , HttpSession session )
	{
		return this.getPaginaBaseListaUsuarios(req, session);
	}
	
	@RequestMapping(value="/doEliminarUsuario.htm") //Invocacion sincronica
	public ModelAndView doEliminarUsuarioSinPost( HttpServletRequest req , HttpSession session ) 
	{
		//return this.consultar(req, session);
		return null;
	}
	
	@RequestMapping(value="/doNuevoUsuario.htm") //Invocacion Ajax
	public ModelAndView doNuevoUsuarioSinPost( HttpServletRequest req , HttpSession session )
	{
		return this.getPaginaBaseListaUsuarios(req, session);
	}
	
	@RequestMapping("/volverUsuarios.htm") //Invocacion Sincronica
	public ModelAndView volverUsuariosSinPOST( HttpServletRequest req , HttpSession session )
	{
		return this.getPaginaBaseListaUsuarios(req, session);
	}
	
	@RequestMapping("/refreshUsuarios.htm") //Invocacion Sincronica
	public ModelAndView refreshUsuariosSinPOST( HttpServletRequest req , HttpSession session )
	{
		return this.getPaginaBaseListaUsuarios(req, session);
	}


}