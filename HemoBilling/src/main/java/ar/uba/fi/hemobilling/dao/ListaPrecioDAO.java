package ar.uba.fi.hemobilling.dao;

import java.util.Collection;
import java.util.Date;

import org.springframework.dao.DataAccessException;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectFoundException;
import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.domain.FiltroPaginado;
import ar.uba.fi.hemobilling.domain.listasprecio.FiltroConsultaListasPrecio;
import ar.uba.fi.hemobilling.domain.listasprecio.ItemListaPrecio;
import ar.uba.fi.hemobilling.domain.listasprecio.ListaPrecio;
import ar.uba.fi.hemobilling.domain.obrassociales.AsociacionObraSociaListaPrecio;
import ar.uba.fi.hemobilling.dto.listasprecio.ListaPrecioDTO;

public interface ListaPrecioDAO {
	
	public ListaPrecio getListaPrecio( long codigo ) throws ObjectNotFoundException, DataAccessException;
	
	public ListaPrecio getListaPrecioParaListar( long codigo ) throws ObjectNotFoundException, DataAccessException;
	
	public Long agregar( ListaPrecio listaprecio ) throws ObjectFoundException, DataAccessException;
	
	public void actualizar( ListaPrecio listaprecio ) throws ObjectNotFoundException, DataAccessException;
	
	public Integer getCantListasPrecio( FiltroConsultaListasPrecio filtro ) throws DataAccessException;
	
	public Collection<ListaPrecio> buscarListasPrecio( FiltroConsultaListasPrecio filtro , FiltroPaginado filtroPaginado ) throws DataAccessException;
	
	public Collection<ListaPrecio> buscarListasPrecioParaListar( FiltroConsultaListasPrecio filtro , FiltroPaginado filtroPaginado ) throws DataAccessException;
	
	public void eliminar( ListaPrecio listaprecio ) throws ObjectNotFoundException, DataAccessException;

	public Collection<ListaPrecio> getListasPrecio() throws DataAccessException;
	
	public Collection<ListaPrecio> getListasPrecioParaListar() throws DataAccessException;

	public Collection<ItemListaPrecio> getItemsListaPrecios(long codigo) throws ObjectNotFoundException, DataAccessException;

	public Collection<AsociacionObraSociaListaPrecio> getObrasSocialesLista(
			ListaPrecioDTO listaActual, Date fecha);

	public Collection<AsociacionObraSociaListaPrecio> getObrasSocialesLista(
			Long[] seleccionadas);

}
