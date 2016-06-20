package ar.uba.fi.hemobilling.service;

import java.util.Collection;

import ar.uba.fi.hemobilling.dto.FiltroPaginadoDTO;
import ar.uba.fi.hemobilling.dto.usuarios.FiltroConsultaUsuariosDTO;
import ar.uba.fi.hemobilling.dto.usuarios.RolUsuarioDTO;
import ar.uba.fi.hemobilling.dto.usuarios.UsuarioDTO;
import ar.uba.fi.hemobilling.exception.domain.HBDataAccessException;
import ar.uba.fi.hemobilling.exception.domain.HBObjectExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBObjectNotExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBServiceException;

public interface UsuarioService {
	
	public String getNombreCompletoUsuario( String username ) throws HBDataAccessException, HBObjectNotExistsException;
	
	public UsuarioDTO getUsuario( String username ) throws HBDataAccessException, HBObjectNotExistsException;
	
	public void agregarUsuario( UsuarioDTO nuevoUsuario ) throws HBDataAccessException, HBObjectExistsException, HBServiceException;
	
	public void eliminarUsuario( UsuarioDTO usuario ) throws HBDataAccessException, HBObjectNotExistsException;
	
	public void actualizarUsuario( UsuarioDTO usuario ) throws HBDataAccessException, HBObjectNotExistsException, HBServiceException;
	
	public Collection<UsuarioDTO> getUsuarios( FiltroConsultaUsuariosDTO filtro , FiltroPaginadoDTO filtroPaginado ) throws HBDataAccessException;
	
	public Collection<RolUsuarioDTO> getRolesPosibles()  throws HBDataAccessException;
	
}
