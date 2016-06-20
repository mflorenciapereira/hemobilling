package ar.uba.fi.hemobilling.service.impl;

import java.util.Collection;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectFoundException;
import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.commons.dozer.DozerMapper;
import ar.uba.fi.hemobilling.dao.ClienteDAO;
import ar.uba.fi.hemobilling.domain.FiltroPaginado;
import ar.uba.fi.hemobilling.domain.clientes.Cliente;
import ar.uba.fi.hemobilling.domain.clientes.FiltroConsultaClientes;
import ar.uba.fi.hemobilling.dto.FiltroPaginadoDTO;
import ar.uba.fi.hemobilling.dto.clientes.ClienteDTO;
import ar.uba.fi.hemobilling.dto.clientes.FiltroConsultaClientesDTO;
import ar.uba.fi.hemobilling.exception.domain.HBDataAccessException;
import ar.uba.fi.hemobilling.exception.domain.HBEntityRelationViolation;
import ar.uba.fi.hemobilling.exception.domain.HBObjectExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBObjectNotExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBServiceException;
import ar.uba.fi.hemobilling.service.ClienteService;

@Service("clienteService")
public class ClienteServiceImpl implements ClienteService 
{
	static Logger logger = Logger.getLogger(ClienteServiceImpl.class);
	
	@Resource(name = "clienteDAO")
	ClienteDAO clienteDAO;
	
	@Resource(name = "dozerMapper")
	private DozerMapper mapper;

	@Override
	public void agregarCliente(ClienteDTO clienteDTO) throws HBDataAccessException, HBObjectExistsException, HBServiceException{	
		try 
		{
			Cliente cliente = mapper.map(clienteDTO, Cliente.class );	
			clienteDAO.agregar(cliente);
						
			logger.info("Se agrego el cliente "+cliente.getCodigo()+" satisfactoriamente");
		} 
		catch (DataAccessException e) {
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria agregar el cliente "+clienteDTO.getCodigo(), e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} 
		catch (ObjectFoundException e) {
			logger.error("Se encontro una cliente que ya existia en la BD cuando se queria agregar el cliente"+clienteDTO.getCodigo() , e );
			HBObjectExistsException ex = new HBObjectExistsException(e);
			throw ex;
		} 
	
	}




	@Override
	public void actualizarCliente(ClienteDTO cliente) throws HBDataAccessException, HBObjectNotExistsException, HBServiceException, HBObjectExistsException {
		
		try 
		{
			Cliente clienteActualizado = mapper.map(cliente, Cliente.class );
			
			clienteDAO.actualizar(clienteActualizado);
				
			logger.info("Se actualizo el cliente"+clienteActualizado.getCodigo()+" satisfactoriamente");
		} 
		
		catch (DataAccessException e) {
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria actualizar el cliente"+cliente.getCodigo() , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} 
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro el cliente en la BD cuando se queria actualizar el cliente"+cliente.getCodigo() , e );
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		} 

		
	}



	@Override
	public void eliminarCliente(ClienteDTO cliente) throws HBDataAccessException, HBObjectNotExistsException, HBEntityRelationViolation{
		
		try {
			Cliente clienteEliminar = clienteDAO.get(cliente.getCodigo());
			clienteDAO.eliminar(clienteEliminar);
			logger.info("Se elimino el cliente "+cliente.getCodigo()+" satisfactoriamente");
		} 
		catch (DataAccessException e) 
		{
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria eliminar el cliente" +cliente.getCodigo(), e );
			if( e.getCause() instanceof ConstraintViolationException )
			{
				HBEntityRelationViolation ex = new HBEntityRelationViolation(e);
				throw ex;
			}
			else
			{
				HBDataAccessException ex = new HBDataAccessException(e);
				throw ex;
			}
		} 
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro el cliente en la BD cuando se queria eliminar el cliente"+cliente.getCodigo() , e );
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
	}

	@Override
	public Collection<ClienteDTO> getClientes( FiltroConsultaClientesDTO filtroDTO , FiltroPaginadoDTO filtroPaginadoDTO ) throws HBDataAccessException {
		try
		{
			FiltroConsultaClientes filtro = mapper.map(filtroDTO, FiltroConsultaClientes.class);
			FiltroPaginado filtroPaginado = mapper.map(filtroPaginadoDTO, FiltroPaginado.class);
			
			filtroPaginado.setCantTotalRegs( clienteDAO.getTotal(filtro) );
			
			Collection<Cliente> clientes = clienteDAO.getClientes(filtro,filtroPaginado);
			Collection<ClienteDTO> clientesdto = mapper.map( clientes , ClienteDTO.class );
			
			
			logger.info("Se leyo la lista de clientes satisfactoriamente");
			filtroPaginadoDTO.setCantMaxPaginas(filtroPaginado.getCantMaxPaginas());
			filtroPaginadoDTO.setCantTotalRegs( filtroPaginado.getCantTotalRegs() );
			return clientesdto;
		}
		
		catch( DataAccessException e )
		{
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria leer la lista de clientes" , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
		
	}
	
	
		
	@Override
	public ClienteDTO getCliente(Long codigo) throws HBDataAccessException, HBObjectNotExistsException 
	{
		try {
			Cliente cliente = clienteDAO.get(codigo);
			ClienteDTO ClienteDTO = mapper.map(cliente, ClienteDTO.class);
						
			logger.info("Se obtuvo el cliente "+codigo+" satisfactoriamente");
			return ClienteDTO;
		} 
		catch (DataAccessException e) {
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria obtener el cliente"+ codigo , e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} 
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro el cliente "+codigo , e );
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
	}
	
	@Override
	public Collection<ClienteDTO> getClientasParaListar()  throws HBDataAccessException 
	{
		try
		{
			Collection<Cliente> clientes = clienteDAO.getClientesParaListar();
			Collection<ClienteDTO> clientesDTO = mapper.map(clientes, ClienteDTO.class);
			
			return clientesDTO;
		}
		catch (DataAccessException e) {
			logger.error("No se pudo leer los clientes para listar que se solicitaron ", e );
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
	}



	


}
