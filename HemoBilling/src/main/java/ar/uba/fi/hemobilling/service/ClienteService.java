package ar.uba.fi.hemobilling.service;

import java.util.Collection;

import ar.uba.fi.hemobilling.dto.FiltroPaginadoDTO;
import ar.uba.fi.hemobilling.dto.clientes.ClienteDTO;
import ar.uba.fi.hemobilling.dto.clientes.FiltroConsultaClientesDTO;
import ar.uba.fi.hemobilling.exception.domain.HBDataAccessException;
import ar.uba.fi.hemobilling.exception.domain.HBEntityRelationViolation;
import ar.uba.fi.hemobilling.exception.domain.HBObjectExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBObjectNotExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBServiceException;

public interface ClienteService {

	public ClienteDTO getCliente( Long codigo ) throws HBDataAccessException, HBObjectNotExistsException;
	
	public void agregarCliente( ClienteDTO nuevoCliente ) throws HBDataAccessException, HBObjectExistsException, HBServiceException;
	
	public void eliminarCliente( ClienteDTO Cliente ) throws HBDataAccessException, HBObjectNotExistsException, HBEntityRelationViolation;
	
	public void actualizarCliente( ClienteDTO Cliente ) throws HBDataAccessException, HBObjectNotExistsException, HBServiceException, HBObjectExistsException;
	
	public Collection<ClienteDTO> getClientes( FiltroConsultaClientesDTO filtro , FiltroPaginadoDTO filtroPaginado ) throws HBDataAccessException;

	public Collection<ClienteDTO> getClientasParaListar()  throws HBDataAccessException;
	
	
}
