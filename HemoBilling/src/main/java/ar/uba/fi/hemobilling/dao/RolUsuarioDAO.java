package ar.uba.fi.hemobilling.dao;

import java.util.Collection;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import ar.uba.fi.hemobilling.domain.usuarios.RolUsuario;

public interface RolUsuarioDAO {
	
	public Collection<RolUsuario> getRoles()  throws DataAccessException;
	public Map<String,RolUsuario> getMapaRoles()  throws DataAccessException;

}
