package ar.uba.fi.hemobilling.dao.impl;

import java.util.Collection;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectFoundException;
import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.commons.dao.impl.GenericHibernateDAO;
import ar.uba.fi.hemobilling.dao.UsuarioDAO;
import ar.uba.fi.hemobilling.domain.FiltroConsulta;
import ar.uba.fi.hemobilling.domain.FiltroPaginado;
import ar.uba.fi.hemobilling.domain.usuarios.FiltroConsultaUsuarios;
import ar.uba.fi.hemobilling.domain.usuarios.Usuario;

@Repository("usuarioDAO")
public class UsuarioDAOImpl extends GenericHibernateDAO <Usuario, String> implements UsuarioDAO 
{
	@Resource(name = "sessionFactory")
	public void setAnnotationSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private DetachedCriteria getCriteriosBusqueda( FiltroConsultaUsuarios filtro )
	{
		DetachedCriteria criteria = DetachedCriteria.forClass( Usuario.class );
		
		if( !filtro.getNombreUsuario().equals( FiltroConsulta.TODOS_DESC))
		{
			criteria.add( Restrictions.ilike("nombreUsuario", filtro.getNombreUsuario(), MatchMode.ANYWHERE) );
		}
		
		if( !filtro.getNombreCompleto().equals( FiltroConsulta.TODOS_DESC))
		{
			criteria.add( Restrictions.ilike("nombreCompleto", filtro.getNombreCompleto(), MatchMode.ANYWHERE) );
		}
		
		if( !filtro.getCodRol().equals( FiltroConsulta.TODOS_DESC) )
		{
			DetachedCriteria rolesCriteria = criteria.createCriteria("roles");  
			rolesCriteria.add( Restrictions.eq("codigo", filtro.getCodRol() ) );
		}
		
		if( !filtro.getHabilitado().equals( FiltroConsulta.TODOS_DESC ) )
		{
			if( filtro.getHabilitado().equals("Si") )
				criteria.add( Restrictions.eq("habilitado", true) );
			else
				criteria.add( Restrictions.eq("habilitado", false) );
		}
		
		return criteria;
	}
	
	@Override
	public Usuario getUsuario(String username) throws ObjectNotFoundException, DataAccessException{
		Usuario user = this.findById(username);
		return user;
	}

	@Override
	public void agregar(Usuario newUser) throws ObjectFoundException, DataAccessException {
		if( exists(newUser.getNombreUsuario())) throw new ObjectFoundException();
		super.save(newUser);
	}

	@Override
	public void actualizar(Usuario updatedUser) throws ObjectNotFoundException, DataAccessException {
		if( !exists(updatedUser.getNombreUsuario())) throw new ObjectNotFoundException();
		super.update(updatedUser);		
	}

	@Override
	public Collection<Usuario> getUsuarios( FiltroConsultaUsuarios filtro , FiltroPaginado filtroPaginado ) throws DataAccessException 
	{
		if( filtro!=null )
		{
			DetachedCriteria criteria = getCriteriosBusqueda(filtro);			
			return findByCriteria(criteria , filtroPaginado.getNumeroPaginaActual() , filtroPaginado.getRegPorPagina() );
		}
		else
		{
			return getAll();
		}
	}

	@Override
	public void eliminar(Usuario user) throws ObjectNotFoundException, DataAccessException {
		if( !exists(user.getNombreUsuario())) throw new ObjectNotFoundException();
		super.delete(user);
		
	}

	@Override
	public Integer getCantUsuarios(FiltroConsultaUsuarios filtro) throws DataAccessException 
	{
		DetachedCriteria criteria = getCriteriosBusqueda(filtro);
		criteria.setProjection( Projections.rowCount() );
		
		Integer count = (Integer)getHibernateTemplate().findByCriteria(criteria).get(0);
		return count;
	}

}
