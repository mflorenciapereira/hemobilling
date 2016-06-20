package ar.uba.fi.hemobilling.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectFoundException;
import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.commons.dozer.DozerMapper;
import ar.uba.fi.hemobilling.dao.RolUsuarioDAO;
import ar.uba.fi.hemobilling.dao.UsuarioDAO;
import ar.uba.fi.hemobilling.domain.FiltroPaginado;
import ar.uba.fi.hemobilling.domain.usuarios.FiltroConsultaUsuarios;
import ar.uba.fi.hemobilling.domain.usuarios.RolUsuario;
import ar.uba.fi.hemobilling.domain.usuarios.Usuario;
import ar.uba.fi.hemobilling.dto.FiltroPaginadoDTO;
import ar.uba.fi.hemobilling.dto.usuarios.FiltroConsultaUsuariosDTO;
import ar.uba.fi.hemobilling.dto.usuarios.RolUsuarioDTO;
import ar.uba.fi.hemobilling.dto.usuarios.UsuarioDTO;
import ar.uba.fi.hemobilling.exception.domain.HBDataAccessException;
import ar.uba.fi.hemobilling.exception.domain.HBObjectExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBObjectNotExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBServiceException;
import ar.uba.fi.hemobilling.service.UsuarioService;

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {

	@Resource(name = "usuarioDAO")
	private UsuarioDAO usuarioDAO;
	
	@Resource(name = "rolUsuarioDAO")
	private RolUsuarioDAO rolUsuarioDAO;
	
	@Resource(name = "dozerMapper")
	private DozerMapper mapper;
	
	private static Logger logger = Logger.getLogger(UsuarioServiceImpl.class);
	
	private String codificarMD5( String planPass ) throws NoSuchAlgorithmException
	{
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] dataBytes = planPass.getBytes();
		
		md.update(dataBytes, 0, planPass.length() );
		byte[] mdbytes = md.digest();
			
	    //convert the byte to hex format method 1
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < mdbytes.length; i++) {
	    	sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
	    }
	        
	    return sb.toString();
	}
	
	private String getCodigosRolesUsuariosSerializados( Usuario user )
	{
		String codigosRoles = "";
		for( RolUsuario rol: user.getRoles() )
		{
			if( codigosRoles.length()!=0 ) codigosRoles +="-";
			codigosRoles += rol.getCodigo();
		}
		
		return codigosRoles;
	}
	
	private String getDescripRolesUsuariosSerializados( Usuario user )
	{
		String codigosRoles = "";
		for( RolUsuario rol: user.getRoles() )
		{
			if( codigosRoles.length()!=0 ) codigosRoles +=", ";
			codigosRoles += rol.getDescripcion();
		}
		
		return codigosRoles;
	}
	
	private Collection<RolUsuario> desSerializarRoles( String rolesSerializados ) throws DataAccessException
	{
		String[] rolesCod = rolesSerializados.split("-");
		Map<String,RolUsuario> rolesPosibles = rolUsuarioDAO.getMapaRoles();
		Collection<RolUsuario> rolesUsuario = new ArrayList<RolUsuario>();
		
		for( String cod: rolesCod)
		{
			rolesUsuario.add( rolesPosibles.get(cod) );
		}

		return rolesUsuario;
	}
	
	@Override
	public String getNombreCompletoUsuario(String username) throws HBDataAccessException, HBObjectNotExistsException
	{
		try {
			Usuario user = usuarioDAO.getUsuario(username);
			return user.getNombreCompleto();
		} 
		catch (ObjectNotFoundException e) {
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
		
		catch( DataAccessException e ){
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
	}

	@Override
	public void agregarUsuario(UsuarioDTO nuevoUsuario) throws HBDataAccessException, HBObjectExistsException, HBServiceException 
	{	
		try 
		{
			Usuario usuario = mapper.map(nuevoUsuario, Usuario.class );
			usuario.setRoles( this.desSerializarRoles(nuevoUsuario.getCodigosRolesSerializados()) );
			
			usuario.setPassword( this.codificarMD5(usuario.getPassword()) );
			
			usuarioDAO.agregar(usuario);
			
			logger.info("Se agrego un nuevo usuario satisfactoriamente");
		} 
		catch (DataAccessException e) {
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria agregar un usuario" , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} 
		catch (ObjectFoundException e) {
			logger.error("Se encontro un usuario que ya existia en la BD cuando se queria agregar un usuario" , e );
			HBObjectExistsException ex = new HBObjectExistsException(e);
			throw ex;
		} 
		catch (NoSuchAlgorithmException e) {
			logger.error("Fallo el algoritmo MD5 cuando se queria agregar un usuario" , e );
			throw new HBServiceException(e);
		}	
	}

	@Override
	public void actualizarUsuario(UsuarioDTO usuario) throws HBDataAccessException, HBObjectNotExistsException, HBServiceException {
		
		try 
		{
			Usuario userActualizado = mapper.map(usuario, Usuario.class );
			userActualizado.setRoles( this.desSerializarRoles(usuario.getCodigosRolesSerializados()) );
			
			if( userActualizado.getPassword().isEmpty() )
			{
				Usuario oldUser = usuarioDAO.getUsuario(usuario.getNombreUsuario() );
				userActualizado.setPassword( oldUser.getPassword() );
			}
			else
			{
				userActualizado.setPassword( this.codificarMD5( userActualizado.getPassword() ) );
			}
			
			usuarioDAO.actualizar(userActualizado);
			logger.info("Se actualizo un usuario satisfactoriamente");
		} 
		
		catch (DataAccessException e) {
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria actualizar un usuario" , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} 
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro el usuario en la BD cuando se queria actualizar el usuario" , e );
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		} 
		catch (NoSuchAlgorithmException e) {
			logger.error("Fallo el algoritmo MD5 cuando se queria agregar un usuario" , e );
			throw new HBServiceException(e);
		}
		
	}

	@Override
	public void eliminarUsuario(UsuarioDTO usuario) throws HBDataAccessException, HBObjectNotExistsException {
		Usuario user = mapper.map(usuario, Usuario.class );
		try {
			usuarioDAO.eliminar(user);
			logger.info("Se elimino un usuario satisfactoriamente");
		} 
		catch (DataAccessException e) {
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria eliminar un usuario" , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} 
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro el usuario en la BD cuando se queria eliminar un usuario" , e );
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
	}

	@Override
	public Collection<UsuarioDTO> getUsuarios( FiltroConsultaUsuariosDTO filtroDTO , FiltroPaginadoDTO filtroPaginadoDTO ) throws HBDataAccessException {
		try
		{
			FiltroConsultaUsuarios filtro = mapper.map(filtroDTO, FiltroConsultaUsuarios.class);
			FiltroPaginado filtroPaginado = mapper.map(filtroPaginadoDTO, FiltroPaginado.class);
			
			filtroPaginado.setCantTotalRegs( usuarioDAO.getCantUsuarios(filtro) );
			
			Collection<Usuario> usuarios = usuarioDAO.getUsuarios(filtro,filtroPaginado);
			Collection<UsuarioDTO> usuariosDTO = mapper.map( usuarios , UsuarioDTO.class );
			
			for( int u=0 ; u<usuarios.size() ; u++ )
			{
				Usuario usuario = ((ArrayList<Usuario>)usuarios).get(u);
				UsuarioDTO usuarioDTO = ((ArrayList<UsuarioDTO>)usuariosDTO).get(u);
				
				usuarioDTO.setCodigosRolesSerializados( this.getCodigosRolesUsuariosSerializados(usuario) );
				usuarioDTO.setDescripRolesSerializados( this.getDescripRolesUsuariosSerializados(usuario) );
			}
			
			logger.info("Se leyo la lista de usuarios satisfactoriamente");
			filtroPaginadoDTO.setCantMaxPaginas(filtroPaginado.getCantMaxPaginas());
			filtroPaginadoDTO.setCantTotalRegs( filtroPaginado.getCantTotalRegs() );
			return usuariosDTO;
		}
		
		catch( DataAccessException e )
		{
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria leer la lista de usuarios" , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
		
	}

	@Override
	public Collection<RolUsuarioDTO> getRolesPosibles() throws HBDataAccessException {
		try
		{
			Collection<RolUsuarioDTO> roles = mapper.map( rolUsuarioDAO.getRoles(), RolUsuarioDTO.class );
			logger.info("Se leyeron los roles posibles de un usuario satisfactoriamente");
			return roles;
		}
		
		catch( DataAccessException e )
		{
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria leer los roles posibles de un usuario" , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
	}

	@Override
	public UsuarioDTO getUsuario(String username) throws HBDataAccessException, HBObjectNotExistsException 
	{
		try {
			Usuario user = usuarioDAO.getUsuario(username);
			UsuarioDTO usuarioDTO = mapper.map(user, UsuarioDTO.class);
			usuarioDTO.setCodigosRolesSerializados( this.getCodigosRolesUsuariosSerializados(user) );
			usuarioDTO.setDescripRolesSerializados( this.getDescripRolesUsuariosSerializados(user) );
			
			logger.info("Se obtuvo un usuario satisfactoriamente");
			return usuarioDTO;
		} 
		catch (DataAccessException e) {
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria obtener un usuario" , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} 
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro el usuario cuando se queria obtener un usuario" , e );
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
	}


}
