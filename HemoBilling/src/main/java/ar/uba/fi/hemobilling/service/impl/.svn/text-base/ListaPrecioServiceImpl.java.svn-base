package ar.uba.fi.hemobilling.service.impl;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectFoundException;
import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.commons.dozer.DozerMapper;
import ar.uba.fi.hemobilling.dao.AsociacionListaPrecioObraSocialDAO;
import ar.uba.fi.hemobilling.dao.ListaPrecioDAO;
import ar.uba.fi.hemobilling.dao.ObraSocialDAO;
import ar.uba.fi.hemobilling.domain.FiltroPaginado;
import ar.uba.fi.hemobilling.domain.listasprecio.FiltroConsultaListasPrecio;
import ar.uba.fi.hemobilling.domain.listasprecio.ItemListaPrecio;
import ar.uba.fi.hemobilling.domain.listasprecio.ListaPrecio;
import ar.uba.fi.hemobilling.domain.obrassociales.AsociacionObraSociaListaPrecio;
import ar.uba.fi.hemobilling.dto.FiltroPaginadoDTO;
import ar.uba.fi.hemobilling.dto.listasprecio.ActualizacionMasivaDTO;
import ar.uba.fi.hemobilling.dto.listasprecio.FiltroConsultaListasPrecioDTO;
import ar.uba.fi.hemobilling.dto.listasprecio.ItemListaPrecioDTO;
import ar.uba.fi.hemobilling.dto.listasprecio.ListaPrecioDTO;
import ar.uba.fi.hemobilling.dto.listasprecio.ResultVerObrasSocialesListaActualDTO;
import ar.uba.fi.hemobilling.dto.obrassociales.AsociacionObraSocialListaPrecioDTO;
import ar.uba.fi.hemobilling.dto.obrassociales.ObraSocialDTO;
import ar.uba.fi.hemobilling.exception.domain.HBDataAccessException;
import ar.uba.fi.hemobilling.exception.domain.HBEntityRelationViolation;
import ar.uba.fi.hemobilling.exception.domain.HBObjectExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBObjectNotExistsException;
import ar.uba.fi.hemobilling.service.ListaPrecioService;
import ar.uba.fi.hemobilling.service.ObraSocialService;

@Service("listaPrecioService")
public class ListaPrecioServiceImpl implements ListaPrecioService {

	@Resource(name = "listaPrecioDAO")
	private ListaPrecioDAO listaPrecioDAO;

	@Resource(name = "obraSocialDAO")
	private ObraSocialDAO obraSocialDAO;
	
	@Resource(name = "oslpDAO")
	private AsociacionListaPrecioObraSocialDAO asociacionDAO;

	@Resource(name = "obraSocialService")
	private ObraSocialService obraSocialService;

	@Resource(name = "dozerMapper")
	private DozerMapper mapper;

	private static Logger logger = Logger
			.getLogger(ListaPrecioServiceImpl.class);
	
	private static final String FORMATO_DIA = "dd/MM/yyyy";

	@Override
	public Long agregarListaPrecio(ListaPrecioDTO prestacionDTO)
			throws HBDataAccessException, HBObjectExistsException {
		Long id = null;
		try {
			ListaPrecio lista = mapper.map(prestacionDTO, ListaPrecio.class);
			id = listaPrecioDAO.agregar(lista);

			logger.info("Se agrego una nueva lista de precio satisfactoriamente");
		} catch (DataAccessException e) {
			logger.error(
					"Se produjo un problema de acceso a la base de datos cuando se queria agregar una lista de precio",
					e);
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} catch (ObjectFoundException e) {
			logger.error(
					"Se encontro una lista de precio que ya existia en la BD cuando se queria agregar una lista de precio",
					e);
			HBObjectExistsException ex = new HBObjectExistsException(e);
			throw ex;
		}

		return id;

	}

	@Override
	public Long duplicarListaPrecio(ListaPrecioDTO datosNuevaListaDTO)
			throws HBDataAccessException, HBObjectExistsException,
			HBObjectNotExistsException {
		Long id = null;
		try {
			ListaPrecio ListaNueva = mapper.map(datosNuevaListaDTO,
					ListaPrecio.class);
			ListaNueva.setCodigo(null);
			Long idNuevo = listaPrecioDAO.agregar(ListaNueva);

			Collection<ItemListaPrecio> itemsViejos = listaPrecioDAO
					.getItemsListaPrecios(datosNuevaListaDTO.getCodigo());
			Iterator<ItemListaPrecio> it = itemsViejos.iterator();
			while (it.hasNext()) {
				ItemListaPrecio item = it.next();
				item.setId(null);
				item.setIdlistaPrecioPertenece(idNuevo);

				ListaNueva.getItems().add(item);
			}

			listaPrecioDAO.actualizar(ListaNueva);

			logger.info("Se duplico la lista de precios "
					+ datosNuevaListaDTO.getCodigo().toString());
			return id;
		} catch (DataAccessException e) {
			logger.error(
					"Se produjo un problema de acceso a la base de datos cuando se queria duplicar una lista de precio",
					e);
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} catch (ObjectFoundException e) {
			logger.error(
					"Se encontro una lista de precio que ya existia en la BD cuando se queria duplicar una lista de precio",
					e);
			HBObjectExistsException ex = new HBObjectExistsException(e);
			throw ex;
		} catch (ObjectNotFoundException e) {
			logger.error(
					"No se encontro una lista de precio en la BD cuando se queria duplicar una lista de precio",
					e);
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}

	}

	@Override
	public void actualizarListaPrecio(ListaPrecioDTO lista)
			throws HBDataAccessException, HBObjectNotExistsException {

		try {
			ListaPrecio listaPrecioActualizada = mapper.map(lista,
					ListaPrecio.class);
			listaPrecioDAO.actualizar(listaPrecioActualizada);
			logger.info("Se actualizo la lista de precio satisfactoriamente");
		}

		catch (DataAccessException e) {
			logger.error(
					"Se produjo un problema de acceso a la base de datos cuando se queria actualizar una lista de precio",
					e);
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} catch (ObjectNotFoundException e) {
			logger.error(
					"No se encontro una lista de precios en el sistema cuando se queria actualizar una lista de precios",
					e);
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}

	}

	@Override
	public void eliminarListaPrecio(ListaPrecioDTO listadto)
			throws HBDataAccessException, HBObjectNotExistsException,
			HBEntityRelationViolation {
		ListaPrecio listaeliminar;

		try {

			listaeliminar = listaPrecioDAO.getListaPrecio(listadto.getCodigo());
			listaPrecioDAO.eliminar(listaeliminar);
			logger.info("Se elimino la lista de precio satisfactoriamente");
		} catch (DataAccessException e) {
			logger.error(
					"Se produjo un problema de acceso a la base de datos cuando se queria eliminar la lista de precion",
					e);

			if (e.getCause() instanceof ConstraintViolationException) {
				HBEntityRelationViolation ex = new HBEntityRelationViolation(e);
				throw ex;
			} else {
				HBDataAccessException ex = new HBDataAccessException(e);
				throw ex;
			}

		} catch (ObjectNotFoundException e) {
			logger.error(
					"No se encontro la prestacion en la BD cuando se queria eliminar una lista de precio",
					e);
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
	}

	@Override
	public Collection<ListaPrecioDTO> buscarListasPrecio(
			FiltroConsultaListasPrecioDTO filtroDTO,
			FiltroPaginadoDTO filtroPaginadoDTO) throws HBDataAccessException {
		try {
			FiltroConsultaListasPrecio filtro = mapper.map(filtroDTO,
					FiltroConsultaListasPrecio.class);
			FiltroPaginado filtroPaginado = mapper.map(filtroPaginadoDTO,
					FiltroPaginado.class);

			filtroPaginado.setCantTotalRegs(listaPrecioDAO
					.getCantListasPrecio(filtro));

			Collection<ListaPrecio> listas = listaPrecioDAO.buscarListasPrecio(
					filtro, filtroPaginado);
			Collection<ListaPrecioDTO> listasDTO = mapper.map(listas,
					ListaPrecioDTO.class);

			logger.info("Se leyeron las listas de precio satisfactoriamente");
			filtroPaginadoDTO.setCantMaxPaginas(filtroPaginado
					.getCantMaxPaginas());
			filtroPaginadoDTO.setCantTotalRegs(filtroPaginado
					.getCantTotalRegs());
			return listasDTO;
		}

		catch (DataAccessException e) {
			logger.error(
					"Se produjo un problema de acceso a la base de datos cuando se queria leer las listas de precio",
					e);
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}

	}

	@Override
	public ListaPrecioDTO getListaPrecio(long codigo)
			throws HBDataAccessException, HBObjectNotExistsException {
		try {
			ListaPrecio lista = listaPrecioDAO.getListaPrecio(codigo);
			ListaPrecioDTO listaDTO = mapper.map(lista, ListaPrecioDTO.class);

			logger.info("Se obtuvo la lista de precio satisfactoriamente");
			return listaDTO;
		} catch (DataAccessException e) {
			logger.error(
					"Se produjo un problema de acceso a la base de datos cuando se queria obtener una lista de precio",
					e);
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} catch (ObjectNotFoundException e) {
			logger.error(
					"No se encontro la prestacion cuando se queria obtener una lista de precio",
					e);
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
	}

	@Override
	public ListaPrecioDTO getListaPrecioParaListar(long codigo)
			throws HBDataAccessException, HBObjectNotExistsException {
		try {
			ListaPrecio lista = listaPrecioDAO.getListaPrecioParaListar(codigo);
			ListaPrecioDTO listaDTO = mapper.map(lista, ListaPrecioDTO.class);

			logger.info("Se obtuvo la lista de precio satisfactoriamente");
			return listaDTO;
		} catch (DataAccessException e) {
			logger.error(
					"Se produjo un problema de acceso a la base de datos cuando se queria obtener una lista de precio",
					e);
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} catch (ObjectNotFoundException e) {
			logger.error(
					"No se encontro la prestacion cuando se queria obtener una lista de precio",
					e);
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
	}

	@Override
	public Collection<ItemListaPrecioDTO> getItemsListaPrecio(long codigo)
			throws HBDataAccessException, HBObjectNotExistsException {
		try {
			Collection<ItemListaPrecio> lista = listaPrecioDAO
					.getItemsListaPrecios(codigo);

			Collection<ItemListaPrecioDTO> itemsDTO = mapper.map(lista,
					ItemListaPrecioDTO.class);

			logger.info("Se obtuvo la lista de precio satisfactoriamente");
			return itemsDTO;
		} catch (DataAccessException e) {
			logger.error(
					"Se produjo un problema de acceso a la base de datos cuando se queria obtener una lista de precio",
					e);
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		} catch (ObjectNotFoundException e) {
			logger.error(
					"No se encontro la prestacion cuando se queria obtener una lista de precio",
					e);
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actualizarPrecios(ListaPrecioDTO listaDTO)
			throws HBDataAccessException, HBObjectNotExistsException {

		Iterator<ItemListaPrecioDTO> it = listaDTO.getItemsAuto().iterator();

		try {

			ListaPrecio lista = new ListaPrecio();
			lista.setCodigo(listaDTO.getCodigo());
			lista.setNombre(listaDTO.getNombre());
			lista.setItems(new ArrayList<ItemListaPrecio>());

			while (it.hasNext()) {
				ItemListaPrecioDTO itemdto = it.next();
				if (itemdto.isIncluido()) {
					ItemListaPrecio item = mapper.map(itemdto,
							ItemListaPrecio.class);
					if (item.getPrecio() == null)
						item.setPrecio(0.0);
					item.setIdlistaPrecioPertenece(listaDTO.getCodigo());
					lista.getItems().add(item);
				}
			}

			listaPrecioDAO.actualizar(lista);
		}

		catch (DataAccessException e) {
			logger.error(
					"Se produjo un problema de acceso a la base de datos cuando se queria editar una lista de precio",
					e);
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}

		catch (ObjectNotFoundException e) {
			logger.error(
					"No se encontro la prestacion cuando se queria obtener una lista de precio",
					e);
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
	}

	@Override
	public Collection<ListaPrecioDTO> getListasPrecio()
			throws HBDataAccessException {
		try {

			Collection<ListaPrecio> listas = listaPrecioDAO.getListasPrecio();
			Collection<ListaPrecioDTO> listasDTO = mapper.map(listas,
					ListaPrecioDTO.class);

			logger.info("Se leyo la lista de posibles prestaciones satisfactoriamente");

			return listasDTO;
		}

		catch (DataAccessException e) {
			logger.error(
					"Se produjo un problema de acceso a la base de datos cuando se queria leer la lista de prestaciones",
					e);
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}

	}

	@Override
	public Collection<ListaPrecioDTO> getListasPrecioParaListar()
			throws HBDataAccessException {
		try {

			Collection<ListaPrecio> listas = listaPrecioDAO
					.getListasPrecioParaListar();
			Collection<ListaPrecioDTO> listasDTO = mapper.map(listas,
					ListaPrecioDTO.class);

			logger.info("Se leyo la lista de posibles prestaciones satisfactoriamente");

			return listasDTO;
		}

		catch (DataAccessException e) {
			logger.error(
					"Se produjo un problema de acceso a la base de datos cuando se queria leer la lista de prestaciones",
					e);
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
	}

	@Override
	public Collection<ListaPrecioDTO> buscarListasPrecioParaListar(
			FiltroConsultaListasPrecioDTO filtroDTO,
			FiltroPaginadoDTO filtroPaginadoDTO) throws HBDataAccessException {
		try {
			FiltroConsultaListasPrecio filtro = mapper.map(filtroDTO,
					FiltroConsultaListasPrecio.class);
			FiltroPaginado filtroPaginado = mapper.map(filtroPaginadoDTO,
					FiltroPaginado.class);

			filtroPaginado.setCantTotalRegs(listaPrecioDAO
					.getCantListasPrecio(filtro));

			Collection<ListaPrecio> listas = listaPrecioDAO
					.buscarListasPrecioParaListar(filtro, filtroPaginado);
			Collection<ListaPrecioDTO> listasDTO = mapper.map(listas,
					ListaPrecioDTO.class);

			logger.info("Se leyeron las listas de precio satisfactoriamente");
			filtroPaginadoDTO.setCantMaxPaginas(filtroPaginado
					.getCantMaxPaginas());
			filtroPaginadoDTO.setCantTotalRegs(filtroPaginado
					.getCantTotalRegs());
			return listasDTO;
		}

		catch (DataAccessException e) {
			logger.error(
					"Se produjo un problema de acceso a la base de datos cuando se queria leer las listas de precio",
					e);
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
	}

	@Override
	public ResultVerObrasSocialesListaActualDTO getObrasSocialesListaActual(
			ActualizacionMasivaDTO actualizacionDTO)
			throws HBDataAccessException, ParseException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_DIA);

			Collection<AsociacionObraSociaListaPrecio> asociaciones = listaPrecioDAO
					.getObrasSocialesLista(actualizacionDTO.getListaActual(), sdf.parse(actualizacionDTO.getFechaBusqueda()));
			logger.info("Se obtuvieron las obras sociales relacionadas con la lista de precio actual exitosamente.");
			ResultVerObrasSocialesListaActualDTO result = new ResultVerObrasSocialesListaActualDTO();
			Iterator<AsociacionObraSociaListaPrecio> it=asociaciones.iterator();
			while(it.hasNext()){
				AsociacionObraSociaListaPrecio asoc = it.next();
				AsociacionObraSocialListaPrecioDTO adto=new AsociacionObraSocialListaPrecioDTO();
				adto.setDesde(sdf.format(asoc.getDesde()));
				adto.setHasta(asoc.getHasta()!=null?sdf.format(asoc.getHasta()):null);
				adto.setId(asoc.getId());
				adto.setListaPrecio(mapper.map(asoc.getListaPrecio(), ListaPrecioDTO.class));
				adto.setNombreObraSocial(asoc.getObraSocial().getNombre());
				result.getObrasSociales().add(adto);
			}
			
			return result;

		}

		catch (DataAccessException e) {
			logger.error(
					"Se produjo un problema de acceso a la base de datos cuando se queria ver las obras sociales asociadas a la lista de precio actual.",
					e);
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}

	}

	@Override
	public void actualizarListaActual(ActualizacionMasivaDTO dto)
			throws HBDataAccessException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_DIA);
		Collection<AsociacionObraSociaListaPrecio> obrassociales = listaPrecioDAO.getObrasSocialesLista(dto.getListaActual(),sdf.parse(dto.getFechaNueva()));
		Iterator<AsociacionObraSociaListaPrecio> it = obrassociales.iterator();
		while (it.hasNext()) {
			ObraSocialDTO dtoOS = mapper.map(it.next().getObraSocial(),
					ObraSocialDTO.class);
			dtoOS.getAsociacionActual().setListaPrecio(
					new ListaPrecioDTO(dto.getListaNueva().getCodigo()));
			try {
				this.obraSocialService.asociar(dtoOS);
			} catch (DataAccessException e) {
				logger.error(
						"Se produjo un problema de acceso a la base de datos cuando se queria actualizar una obra social",
						e);
				HBDataAccessException ex = new HBDataAccessException(e);
				throw ex;
			}
		}
		;

	}
	
	
	@Override
	public void actualizarListaActual(ActualizacionMasivaDTO dto,Long[] seleccionadas)
			throws HBDataAccessException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_DIA);
		
		Calendar cal=Calendar.getInstance();
		java.util.Date diaNuevo = sdf.parse(dto.getFechaNueva());
		java.sql.Date dateNuevoSQL = new java.sql.Date(diaNuevo.getTime());
			
		
		cal.setTime(diaNuevo);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date diaAnterior =new java.sql.Date(cal.getTimeInMillis()) ;
		
		ListaPrecio listaNueva = mapper.map(dto.getListaNueva(), ListaPrecio.class);
		
		Collection<AsociacionObraSociaListaPrecio> asociaciones = listaPrecioDAO.getObrasSocialesLista(seleccionadas);
		Iterator<AsociacionObraSociaListaPrecio> it = asociaciones.iterator();
		while (it.hasNext()) {
			AsociacionObraSociaListaPrecio asociacion = it.next();
			Date fechaDesdeVieja = asociacion.getHasta();
			asociacion.setHasta(diaAnterior);
			asociacionDAO.actualizar(asociacion);
			AsociacionObraSociaListaPrecio nuevaAsociacion = new AsociacionObraSociaListaPrecio();
			nuevaAsociacion.setDesde(dateNuevoSQL);
			nuevaAsociacion.setHasta(fechaDesdeVieja);
			nuevaAsociacion.setListaPrecio(listaNueva);
			nuevaAsociacion.setObraSocial(asociacion.getObraSocial());
			asociacionDAO.agregar(nuevaAsociacion);
			
		}

	}

}
