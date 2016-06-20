package ar.uba.fi.hemobilling.service;

import java.text.ParseException;
import java.util.Collection;

import ar.uba.fi.hemobilling.dto.FiltroPaginadoDTO;
import ar.uba.fi.hemobilling.dto.listasprecio.ActualizacionMasivaDTO;
import ar.uba.fi.hemobilling.dto.listasprecio.FiltroConsultaListasPrecioDTO;
import ar.uba.fi.hemobilling.dto.listasprecio.ItemListaPrecioDTO;
import ar.uba.fi.hemobilling.dto.listasprecio.ListaPrecioDTO;
import ar.uba.fi.hemobilling.dto.listasprecio.ResultVerObrasSocialesListaActualDTO;
import ar.uba.fi.hemobilling.exception.domain.HBDataAccessException;
import ar.uba.fi.hemobilling.exception.domain.HBEntityRelationViolation;
import ar.uba.fi.hemobilling.exception.domain.HBObjectExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBObjectNotExistsException;

public interface ListaPrecioService {
	
	
	public ListaPrecioDTO getListaPrecio( long codigo ) throws HBDataAccessException, HBObjectNotExistsException;
	
	public Long agregarListaPrecio( ListaPrecioDTO nuevaListaPrecio ) throws HBDataAccessException, HBObjectExistsException;
	
	public Long duplicarListaPrecio(ListaPrecioDTO datosNuevaLista) throws HBDataAccessException, HBObjectExistsException, HBObjectNotExistsException;
	
	public void eliminarListaPrecio( ListaPrecioDTO prestacion ) throws HBDataAccessException, HBObjectNotExistsException, HBEntityRelationViolation;
	
	public void actualizarListaPrecio( ListaPrecioDTO prestacion ) throws HBDataAccessException, HBObjectNotExistsException;
	
	public Collection<ListaPrecioDTO> buscarListasPrecio( FiltroConsultaListasPrecioDTO filtro , FiltroPaginadoDTO filtroPaginado ) throws HBDataAccessException;
	
	public Collection<ListaPrecioDTO> buscarListasPrecioParaListar(FiltroConsultaListasPrecioDTO filtroDTO, FiltroPaginadoDTO filtroPaginadoDTO) throws HBDataAccessException;

	public Collection<ItemListaPrecioDTO> getItemsListaPrecio(long codigo) throws HBDataAccessException, HBObjectNotExistsException ;

	public void actualizarPrecios(ListaPrecioDTO listaDTO) throws HBDataAccessException, HBObjectNotExistsException;

	public Collection<ListaPrecioDTO> getListasPrecio() throws HBDataAccessException;
	
	public Collection<ListaPrecioDTO> getListasPrecioParaListar() throws HBDataAccessException;

	public ListaPrecioDTO getListaPrecioParaListar(long codigo) throws HBDataAccessException, HBObjectNotExistsException;

	public ResultVerObrasSocialesListaActualDTO getObrasSocialesListaActual(
			ActualizacionMasivaDTO dto) throws HBDataAccessException, ParseException;

	public void actualizarListaActual(ActualizacionMasivaDTO dto) throws HBDataAccessException, ParseException;

	void actualizarListaActual(ActualizacionMasivaDTO dto,
			Long[] asociacionesSeleccionadasLong) throws HBDataAccessException,
			ParseException;	
	
}
