package ar.uba.fi.hemobilling.dao.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import ar.uba.fi.hemobilling.commons.dao.impl.GenericHibernateDAO;
import ar.uba.fi.hemobilling.dao.RolUsuarioDAO;
import ar.uba.fi.hemobilling.domain.usuarios.RolUsuario;

@Repository("rolUsuarioDAO")
public class RolesUsuarioDAOImpl extends GenericHibernateDAO <RolUsuario, String> implements RolUsuarioDAO
{
	@Resource(name = "sessionFactory")
	public void setAnnotationSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public Collection<RolUsuario> getRoles()  throws DataAccessException {
		return getAll();
	}

	@Override
	public Map<String, RolUsuario> getMapaRoles() throws DataAccessException {
		Collection<RolUsuario> roles = getAll();
		
		Map<String,RolUsuario> mapa = new HashMap<String, RolUsuario>();
		for( RolUsuario rol: roles )
		{
			mapa.put( rol.getCodigo(), rol );
		}
		
		return mapa;
	}

}
