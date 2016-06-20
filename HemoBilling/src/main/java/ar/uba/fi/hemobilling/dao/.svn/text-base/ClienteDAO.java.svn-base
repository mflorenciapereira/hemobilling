package ar.uba.fi.hemobilling.dao;

import java.util.Collection;

import org.springframework.dao.DataAccessException;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectFoundException;
import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.domain.FiltroPaginado;
import ar.uba.fi.hemobilling.domain.clientes.Cliente;
import ar.uba.fi.hemobilling.domain.clientes.FiltroConsultaClientes;


public interface ClienteDAO {
	
	public Cliente get( Long codigo ) throws ObjectNotFoundException, DataAccessException;
	
	public void agregar( Cliente Cliente ) throws ObjectFoundException;
	
	public void actualizar( Cliente Cliente ) throws ObjectNotFoundException, DataAccessException ;
	
	public Integer getTotal( FiltroConsultaClientes filtro );
	
	public Collection<Cliente> getClientes( FiltroConsultaClientes filtro , FiltroPaginado filtroPaginado );
	
	public void eliminar( Cliente Cliente ) throws ObjectNotFoundException, DataAccessException;

	public Collection<Cliente> getClientes();

	public Collection<Cliente> getClientesParaListar() throws DataAccessException;
	

}
