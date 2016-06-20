package ar.uba.fi.hemobilling.dao;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectFoundException;
import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.domain.FiltroPaginado;
import ar.uba.fi.hemobilling.domain.usuarios.FiltroConsultaUsuarios;
import ar.uba.fi.hemobilling.domain.usuarios.Usuario;

public interface UsuarioDAO {
	
	public Usuario getUsuario( String username ) throws ObjectNotFoundException, DataAccessException;
	
	public void agregar( Usuario newUser ) throws ObjectFoundException, DataAccessException;
	
	public void actualizar( Usuario updatedUser ) throws ObjectNotFoundException, DataAccessException;
	
	public Integer getCantUsuarios( FiltroConsultaUsuarios filtro ) throws DataAccessException;
	
	public Collection<Usuario> getUsuarios( FiltroConsultaUsuarios filtro , FiltroPaginado filtroPaginado ) throws DataAccessException;
	
	public void eliminar( Usuario user ) throws ObjectNotFoundException, DataAccessException;
	
}
