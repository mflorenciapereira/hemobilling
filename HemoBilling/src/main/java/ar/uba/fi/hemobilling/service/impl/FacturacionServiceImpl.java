package ar.uba.fi.hemobilling.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.AutoPopulatingList;

import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectFoundException;
import ar.uba.fi.hemobilling.commons.dao.exceptions.ObjectNotFoundException;
import ar.uba.fi.hemobilling.commons.dozer.DozerMapper;
import ar.uba.fi.hemobilling.dao.FacturaPrestacionDAO;
import ar.uba.fi.hemobilling.dao.FacturaServicioDAO;
import ar.uba.fi.hemobilling.domain.FiltroPaginado;
import ar.uba.fi.hemobilling.domain.clientes.Cliente;
import ar.uba.fi.hemobilling.domain.factura.DatosFacturacionPrestacionesBrindadas;
import ar.uba.fi.hemobilling.domain.factura.Factura;
import ar.uba.fi.hemobilling.domain.factura.FacturaPrestacion;
import ar.uba.fi.hemobilling.domain.factura.FacturaServicio;
import ar.uba.fi.hemobilling.domain.factura.FiltroConsultaFacturaServicio;
import ar.uba.fi.hemobilling.domain.factura.FiltroConsultaFacturasPrestacion;
import ar.uba.fi.hemobilling.domain.factura.PrestacionBrindadaFacturada;
import ar.uba.fi.hemobilling.domain.factura.ServicioFacturado;
import ar.uba.fi.hemobilling.domain.factura.TipoFactura;
import ar.uba.fi.hemobilling.domain.general.TipoIVA;
import ar.uba.fi.hemobilling.domain.obrassociales.ObraSocial;
import ar.uba.fi.hemobilling.domain.paciente.Paciente;
import ar.uba.fi.hemobilling.domain.prestaciones.Prestacion;
import ar.uba.fi.hemobilling.domain.prestacionesBrindadas.PrestacionBrindada;
import ar.uba.fi.hemobilling.dto.FiltroPaginadoDTO;
import ar.uba.fi.hemobilling.dto.clientes.ClienteDTO;
import ar.uba.fi.hemobilling.dto.factura.FacturaPrestacionDTO;
import ar.uba.fi.hemobilling.dto.factura.FacturaServicioDTO;
import ar.uba.fi.hemobilling.dto.factura.FiltroConsultaFacturasPrestacionDTO;
import ar.uba.fi.hemobilling.dto.factura.FiltroConsultaFacturasServicioDTO;
import ar.uba.fi.hemobilling.dto.factura.ItemFacturaPrestacionDTO;
import ar.uba.fi.hemobilling.dto.factura.ResultConsultaFacturacionMasivaPrestacionesDTO;
import ar.uba.fi.hemobilling.dto.factura.ServicioFacturadoDTO;
import ar.uba.fi.hemobilling.dto.obrassociales.ObraSocialDTO;
import ar.uba.fi.hemobilling.dto.paciente.PacienteDTO;
import ar.uba.fi.hemobilling.exception.domain.HBDataAccessException;
import ar.uba.fi.hemobilling.exception.domain.HBErrorCreandoReporte;
import ar.uba.fi.hemobilling.exception.domain.HBObjectExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBObjectNotExistsException;
import ar.uba.fi.hemobilling.exception.domain.HBReporteNoDefinido;
import ar.uba.fi.hemobilling.reportes.detalleFactura.DatoListaReporteDetalleFactura;
import ar.uba.fi.hemobilling.reportes.detalleFactura.DatosReporteDetalleFactura;
import ar.uba.fi.hemobilling.reportes.factura.DatoListaReporteFactura;
import ar.uba.fi.hemobilling.reportes.factura.DatosReporteFactura;
import ar.uba.fi.hemobilling.reportes.planillaFacturacion.DatoListaReportePlanillaFacturacion;
import ar.uba.fi.hemobilling.reportes.planillaFacturacion.DatosReportePlanillaFacturacion;
import ar.uba.fi.hemobilling.reportes.reporteador.DatosReporte;
import ar.uba.fi.hemobilling.reportes.reporteador.Reporteador;
import ar.uba.fi.hemobilling.reportes.reporteador.service.ExportType;
import ar.uba.fi.hemobilling.reportes.sintesis.DatoListaReporteSintesisHC;
import ar.uba.fi.hemobilling.reportes.sintesis.DatosReporteSintesisHC;
import ar.uba.fi.hemobilling.service.FacturacionService;
import ar.uba.fi.hemobilling.service.PrestacionBrindadaService;
import ar.uba.fi.hemobilling.util.HTMLEntities;
import ar.uba.fi.hemobilling.util.PrecioToStringConverter;
import ar.uba.fi.hemobilling.util.PropertiesReader;

@Service("facturacionService")
public class FacturacionServiceImpl implements FacturacionService {
	private static final String X = "X";

	private static final String PREFIJO_NUMERO_FACTURA = "facturacion.prefijoFactura";
	private static final String CANT_MAX_PREST_FACTURA = "facturacion.cantMaxPrestacionesPorFactura";

	private static final String FORMATO_DIA = "dd/MM/yyyy";

	@Resource(name = "facturaPrestacionDAO")
	private FacturaPrestacionDAO facturaPrestacionDAO;

	@Resource(name = "facturaServicioDAO")
	private FacturaServicioDAO facturaServicioDAO;

	@Resource(name = "dozerMapper")
	private DozerMapper mapper;

	@Resource(name = "messageSupport")
	private PropertiesReader messageSupport;

	@Resource(name = "reporteador")
	private Reporteador reporteador;

	private static Logger logger = Logger.getLogger(FacturacionServiceImpl.class);

	private String armarNroFactura(String nroBase) {
		String nroFinal = messageSupport.getProperty(PREFIJO_NUMERO_FACTURA);
		nroFinal += "-";

		for (int c = 0; c < 8 - nroBase.length(); c++) {
			nroFinal += "0";
		}

		nroFinal += nroBase;

		return nroFinal;
	}

	private FacturaPrestacionDTO crearFactura(Collection<DatosFacturacionPrestacionesBrindadas> datosDeFactura) {
		Iterator<DatosFacturacionPrestacionesBrindadas> it = datosDeFactura.iterator();
		FacturaPrestacionDTO facturaDTO = null;

		/*
		 * Itero por todos los datos que van a conformar la factura detallada.
		 * Se entiende que los datos de factura que vinieron son para la misma
		 * OS y mismo cliente, ya que el metodo que lo llama se encarga de
		 * dividir el conjunto inicial de datos de esta manera.
		 * 
		 * Para el primer dato creo la factura, le cargo los datos de OS y
		 * Paciente y finalmente el primer item Para el segundo dato y
		 * siguientes, se agregan solo los items.
		 * 
		 * Cada item corresponde a una prestacion brindada
		 */
		while (it.hasNext()) {
			DatosFacturacionPrestacionesBrindadas dato = it.next();

			if (facturaDTO == null) {
				facturaDTO = new FacturaPrestacionDTO();

				facturaDTO.setObraSocial(new ObraSocialDTO());
				facturaDTO.getObraSocial().setCodigo(dato.getCodObraSocialActual().longValue());
				facturaDTO.getObraSocial().setNombre(HTMLEntities.htmlentities(dato.getNombre()));
				facturaDTO.getObraSocial().setTipoFactura(Character.toString(dato.getTipoFactura()));
				facturaDTO.getObraSocial().setSigla(dato.getSigla());
				facturaDTO.getObraSocial().setContabilizaFactura(dato.getContabilizaFactura());

				if (TipoFactura.DETALLADA.getTipo().equals(Character.toString(dato.getTipoFactura()))) {
					facturaDTO.setPaciente(new PacienteDTO());
					facturaDTO.getPaciente().setId(dato.getIdPaciente().toString());
					facturaDTO.getPaciente().setNombreyApellido(HTMLEntities.htmlentities(dato.getNombreyApellido()));
					facturaDTO.getPaciente().setNumHistoriaClinica(dato.getNumHistoriaClinica().toString());
				}
			}

			ItemFacturaPrestacionDTO item = new ItemFacturaPrestacionDTO();

			item.setCantidad(dato.getCantidadDePrestaciones().toString());

			SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_DIA);
			item.setFecha(sdf.format(dato.getFecha()));

			item.setCodigoPrestacion(dato.getCodigoPrestacion().longValue());
			item.setCodigoPrestacionBrindada(dato.getCodigo().longValue());
			item.setDescripcionPrestacion(HTMLEntities.htmlentities(dato.getDescripcion()));

			item.setIdPaciente(dato.getIdPaciente().longValue());
			item.setNombrePaciente(HTMLEntities.htmlentities(dato.getNombreyApellido()));
			item.setNumeroHC(dato.getNumHistoriaClinica().toString());
			item.setCodigoPrestacionSegunOS( dato.getCodigoSegunOS() );

			
			/* El precio manual tiene prioridad. Si tiene precio manual, ese es el que debe 
			 * ser facturado. Sino el precio de la OS y sino null y no se facturara
			 */
			if( dato.getPrecioManual() != null ){
				item.setPrecio(dato.getPrecioManual().toString());
				facturaDTO.getItems().add(item);
			}
			else if (dato.getPrecio() != null) {
				item.setPrecio(dato.getPrecio().toString());
				facturaDTO.getItems().add(item);
			} else {
				item.setPrecio(null);
				facturaDTO.getItemsNoFacturables().add(item);
			}

		}

		return facturaDTO;
	}

	@Override
	public ResultConsultaFacturacionMasivaPrestacionesDTO getFacturacionPrestaciones(
			FiltroConsultaFacturasPrestacionDTO filtroDTO) throws HBDataAccessException {
		try {
			logger.info("Se solicito realizar la facturacion masiva de prestaciones brindadas");

			FiltroConsultaFacturasPrestacion filtro = mapper.map(filtroDTO, FiltroConsultaFacturasPrestacion.class);

			Integer maximoPrestacionesPorFactura = Integer.parseInt(messageSupport.getProperty(CANT_MAX_PREST_FACTURA));
			
			int cantidadPacientes=0;

			Collection<ObraSocial> listaos = new ArrayList<ObraSocial>();
			@SuppressWarnings("unchecked")
			Iterator<ObraSocialDTO> it = filtroDTO.getObrasSociales().iterator();
			while (it.hasNext()) {
				ObraSocialDTO osDTO = it.next();
				ObraSocial os = new ObraSocial();

				os.setCodigo(osDTO.getCodigo());
				listaos.add(os);
			}

			filtro.setObrasSociales(listaos);

			/*
			 * En la coleccion de datos de facturacion vienen todos los datos
			 * necesarios para armar una factura, sin importar el tipo, de forma
			 * desnormalizada y ordenados por OS, Paciente, Fecha y Prestacion.
			 * Este metodo se encarga de generar facturas dependiendo de estos
			 * datos
			 */
			Collection<DatosFacturacionPrestacionesBrindadas> datos = facturaPrestacionDAO.getDatosFacturacion(filtro);
			Iterator<DatosFacturacionPrestacionesBrindadas> ite = datos.iterator();

			Collection<FacturaPrestacionDTO> facturasDTO = new ArrayList<FacturaPrestacionDTO>();

			FacturaPrestacionDTO facturaDTO = null;
			BigInteger codObraSocial = null;
			BigInteger idPaciente = null;
			Collection<DatosFacturacionPrestacionesBrindadas> datosDeFactura = null;

			while (ite.hasNext()) {
				DatosFacturacionPrestacionesBrindadas dato = ite.next();

				if (codObraSocial == null) {
					/*
					 * Sin importar el tipo de factura, si la obra social es
					 * null hay que crear una factura inicial y poner ese primer
					 * dato
					 */

					datosDeFactura = new ArrayList<DatosFacturacionPrestacionesBrindadas>();
					codObraSocial = dato.getCodObraSocialActual();
					idPaciente = dato.getIdPaciente();
					cantidadPacientes=1;

					datosDeFactura.add(dato);
				}
				else {
					if (!codObraSocial.equals(dato.getCodObraSocialActual())) {
						/*
						 * Cambio la OS. Sin importar que tipo de factura es,
						 * hay que hacer una nueva factura por total o por
						 * detalle
						 */

						facturaDTO = crearFactura(datosDeFactura);
						facturasDTO.add(facturaDTO);

						datosDeFactura = new ArrayList<DatosFacturacionPrestacionesBrindadas>();
						codObraSocial = dato.getCodObraSocialActual();
						idPaciente = dato.getIdPaciente();
						cantidadPacientes=1;

						datosDeFactura.add(dato);
					}
					else {
						if (TipoFactura.TOTALIZADA.getTipo().equals( Character.toString(dato.getTipoFactura()))) {
							/*
							 * La OS es la misma y es totalizada. Sigue
							 * acumulando prestaciones brindadas
							 */
							
							if (!idPaciente.equals(dato.getIdPaciente())){
								cantidadPacientes++;
								idPaciente = dato.getIdPaciente();
								
							}

							if (maximoPrestacionesPorFactura.equals(cantidadPacientes)) {
								/*
								 * La factura tiene una cantidad maxima de
								 * pacientes. Si llego, corto
								 */
								facturaDTO = crearFactura(datosDeFactura);
								facturasDTO.add(facturaDTO);

								cantidadPacientes=1;
								datosDeFactura = new ArrayList<DatosFacturacionPrestacionesBrindadas>();
							}

							datosDeFactura.add(dato);
						}
						else {
							if (idPaciente.equals(dato.getIdPaciente())) {
								/*
								 * El codigo de OS y el id paciente son los
								 * mismos y la factura es por detalle. Almaceno
								 * el dato
								 */

								if (maximoPrestacionesPorFactura.equals(datosDeFactura.size())) {
									/*
									 * La factura tiene una cantidad maxima de
									 * prestaciones. Si llego, corto
									 */
									facturaDTO = crearFactura(datosDeFactura);
									facturasDTO.add(facturaDTO);

									datosDeFactura = new ArrayList<DatosFacturacionPrestacionesBrindadas>();
								}

								datosDeFactura.add(dato);
							}
							else {
								/*
								 * El codigo de OS es igual, el id paciente no
								 * es el mismo y la factura es por detalle.
								 * Realizo la factura por detalle
								 */

								facturaDTO = crearFactura(datosDeFactura);
								facturasDTO.add(facturaDTO);

								datosDeFactura = new ArrayList<DatosFacturacionPrestacionesBrindadas>();
								codObraSocial = dato.getCodObraSocialActual();
								idPaciente = dato.getIdPaciente();

								datosDeFactura.add(dato);
							}
						}
					}
				}
			}

			if (datosDeFactura != null && !datosDeFactura.isEmpty()) {
				facturaDTO = crearFactura(datosDeFactura);
				facturasDTO.add(facturaDTO);
			}
			

			logger.info("La facturacion masiva de prestaciones brindadas se realizo exitosamente");
			ResultConsultaFacturacionMasivaPrestacionesDTO result = new ResultConsultaFacturacionMasivaPrestacionesDTO();
			result.getFacturas().addAll(facturasDTO);
			return result;
		}

		catch (DataAccessException e) {
			logger.error(
					"Se produjo un problema de acceso a la base de datos cuando se realizaba la facturacion masiva de prestaciones",
					e);
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}

	}

	@Override
	public void agregarFacturasPrestaciones(ResultConsultaFacturacionMasivaPrestacionesDTO facturasAceptadasDTO)
			throws HBDataAccessException, HBObjectExistsException {
		@SuppressWarnings("unchecked")
		Iterator<FacturaPrestacionDTO> it = facturasAceptadasDTO.getFacturas().iterator();

		while (it.hasNext()) {
			FacturaPrestacionDTO facturaDTO = it.next();

			if (facturaDTO != null) {
				FacturaPrestacion factura = new FacturaPrestacion();

				try {
					SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_DIA);
					factura.setFechaEmision(sdf.parse(facturaDTO.getFechaEmision()));
				}
				catch (ParseException e) {
					factura.setFechaEmision(null);
				}

//				if(facturaDTO.getObraSocial().getContabilizaFactura())
					factura.setNumero(armarNroFactura(facturaDTO.getNumero()));
	//			else
		//			factura.setNumero("0");
					

				ObraSocial os = mapper.map(facturaDTO.getObraSocial(), ObraSocial.class);

				Paciente paciente;
				if (!facturaDTO.getPaciente().getId().equals("undefined")) {
					paciente = mapper.map(facturaDTO.getPaciente(), Paciente.class);
				}
				else {
					paciente = null;
				}

				factura.setObraSocialFacturada(os);
				factura.setPacienteFacturado(paciente);
				factura.setPrestacionesBrindadasFacturadas(new ArrayList<PrestacionBrindadaFacturada>());

				@SuppressWarnings("unchecked")
				Iterator<ItemFacturaPrestacionDTO> iti = facturaDTO.getItems().iterator();
				while (iti.hasNext()) {
					ItemFacturaPrestacionDTO itemDTO = iti.next();

					if (itemDTO != null) {
						PrestacionBrindadaFacturada pbf = new PrestacionBrindadaFacturada();

						paciente = new Paciente();
						paciente.setId(itemDTO.getIdPaciente());
						paciente.setNombreyApellido(itemDTO.getNombrePaciente());
						paciente.setNumHistoriaClinica(Long.parseLong(itemDTO.getNumeroHC()));

						pbf.setPacienteFacturado(paciente);

						pbf.setCantidad(Long.parseLong(itemDTO.getCantidad()));

						try {
							SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_DIA);
							pbf.setFecha(sdf.parse(itemDTO.getFecha()));
						}
						catch (ParseException e) {
							pbf.setFecha(null);
						}

						BigDecimal importe = new BigDecimal(itemDTO.getPrecio());
						pbf.setImporteUnitario(importe);

						PrestacionBrindada pb = new PrestacionBrindada();
						pb.setCodigo(itemDTO.getCodigoPrestacionBrindada());
						pbf.setPrestacionBrindada(pb);
						pbf.setCodigo( itemDTO.getCodigoPrestacionSegunOS() );

						factura.getPrestacionesBrindadasFacturadas().add(pbf);
					}
				}

				try {
					facturaPrestacionDAO.agregar(factura);
				}
				catch (DataAccessException e) {
					logger.error(
							"Se produjo un problema de acceso a la base de datos cuando se almacenaba las facturas seleccionadas ",
							e);
					HBDataAccessException ex = new HBDataAccessException(e);
					throw ex;
				}
				catch (ObjectFoundException e) {
					logger.error(
							"Se encontro una factura con el mismo ID cuando se queria almacena una factura seleccoinada",
							e);
					HBObjectExistsException ex = new HBObjectExistsException(e);
					throw ex;
				}
			}

		}

	}

	@Override
	public Collection<FacturaPrestacionDTO> consultarFacturasPrestaciones(
			FiltroConsultaFacturasPrestacionDTO filtroDTO, FiltroPaginadoDTO filtroPaginado)
			throws HBDataAccessException {
		FiltroConsultaFacturasPrestacion filtro = mapper.map(filtroDTO, FiltroConsultaFacturasPrestacion.class);
		FiltroPaginado paginado = mapper.map(filtroPaginado, FiltroPaginado.class);

		try {
			Collection<FacturaPrestacion> facturas = facturaPrestacionDAO.consultarMinimo(filtro, paginado);
			Collection<FacturaPrestacionDTO> facturasDTO = mapper.map(facturas, FacturaPrestacionDTO.class);

			Iterator<FacturaPrestacion> ifp = facturas.iterator();
			Iterator<FacturaPrestacionDTO> ifpDTO = facturasDTO.iterator();

			while (ifp.hasNext()) {
				FacturaPrestacion fp = ifp.next();
				FacturaPrestacionDTO fpDTO = ifpDTO.next();

				if( fp.getPrestacionesBrindadasFacturadas()!=null )
				{
					Iterator<PrestacionBrindadaFacturada> ipbf = fp.getPrestacionesBrindadasFacturadas().iterator();
					while (ipbf.hasNext()) {
						PrestacionBrindadaFacturada pbf = ipbf.next();

						ItemFacturaPrestacionDTO itemDTO = mapper.map(pbf, ItemFacturaPrestacionDTO.class);
						fpDTO.getItems().add(itemDTO);
					}
				}
			}

			paginado.setCantTotalRegs(facturaPrestacionDAO.getCantidadConsulta(filtro));

			filtroPaginado.setCantMaxPaginas(paginado.getCantMaxPaginas());
			filtroPaginado.setCantTotalRegs(paginado.getCantTotalRegs());

			filtroDTO.setObrasSociales(new AutoPopulatingList(ObraSocialDTO.class));

			return facturasDTO;
		}

		catch (DataAccessException e) {
			logger.error(
					"Se produjo un problema de acceso a la base de datos cuando se consultaba las facturas de prestaciones brindadas",
					e);
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
	}

	@Override
	public void agregarFacturaServicio(FacturaServicioDTO facturaDTO) throws HBDataAccessException,
			HBObjectExistsException {
		try {
			FacturaServicio factura = mapper.map(facturaDTO, FacturaServicio.class);
			Collection<ServicioFacturado> serviciosFacturados = new ArrayList<ServicioFacturado>();

			@SuppressWarnings("unchecked")
			Iterator<ServicioFacturadoDTO> it = facturaDTO.getItems().iterator();
			while (it.hasNext()) {
				ServicioFacturadoDTO servicioDTO = it.next();
				serviciosFacturados.add(mapper.map(servicioDTO, ServicioFacturado.class));
			}

			factura.setNumero(armarNroFactura(facturaDTO.getNumero()));
			factura.setServiciosFacturados(serviciosFacturados);

			facturaServicioDAO.agregar(factura);
		}
		catch (DataAccessException e) {
			logger.error(
					"Se produjo un problema de acceso a la base de datos cuando se almacenaba una factura de servicio ",
					e);
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
		catch (ObjectFoundException e) {
			logger.error(
					"Se encontro una factura de servicio con el mismo ID cuando se queria almacena una factura de servicio",
					e);
			HBObjectExistsException ex = new HBObjectExistsException(e);
			throw ex;
		}

	}

	@Override
	public Collection<FacturaServicioDTO> consultarFacturasServicios(FiltroConsultaFacturasServicioDTO filtroDTO,
			FiltroPaginadoDTO filtroPaginado) throws HBDataAccessException {
		FiltroConsultaFacturaServicio filtro = mapper.map(filtroDTO, FiltroConsultaFacturaServicio.class);
		FiltroPaginado paginado = mapper.map(filtroPaginado, FiltroPaginado.class);

		@SuppressWarnings("unchecked")
		Iterator<ClienteDTO> itClienteDTO = filtroDTO.getClientes().iterator();
		Collection<Cliente> clientes = new ArrayList<Cliente>();
		while (itClienteDTO.hasNext()) {
			ClienteDTO clienteDTO = itClienteDTO.next();
			clientes.add(mapper.map(clienteDTO, Cliente.class));
		}
		filtro.setClientes(clientes);

		try {
			Collection<FacturaServicio> facturas = facturaServicioDAO.consultar(filtro, paginado);
			Collection<FacturaServicioDTO> facturasDTO = mapper.map(facturas, FacturaServicioDTO.class);

			Iterator<FacturaServicio> ifs = facturas.iterator();
			Iterator<FacturaServicioDTO> ifsDTO = facturasDTO.iterator();

			while (ifs.hasNext()) {
				FacturaServicio fs = ifs.next();
				FacturaServicioDTO fsDTO = ifsDTO.next();

				Iterator<ServicioFacturado> isf = fs.getServiciosFacturados().iterator();
				while (isf.hasNext()) {
					ServicioFacturado sf = isf.next();

					ServicioFacturadoDTO servicioDTO = mapper.map(sf, ServicioFacturadoDTO.class);
					fsDTO.getItems().add(servicioDTO);
				}
			}

			paginado.setCantTotalRegs(facturaServicioDAO.getCantidadConsulta(filtro));

			filtroPaginado.setCantMaxPaginas(paginado.getCantMaxPaginas());
			filtroPaginado.setCantTotalRegs(paginado.getCantTotalRegs());

			filtroDTO.setClientes(new AutoPopulatingList(ClienteDTO.class));
			return facturasDTO;
		}

		catch (DataAccessException e) {
			logger.error(
					"Se produjo un problema de acceso a la base de datos cuando se consultaba las facturas de prestaciones brindadas",
					e);
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
	}

	@Override
	public void eliminarFS(FacturaServicioDTO facturaDTO) throws HBDataAccessException, HBObjectNotExistsException {
		try {
			FacturaServicio factura = facturaServicioDAO.obtener(facturaDTO.getId());

			facturaServicioDAO.eliminar(factura);
			logger.info("Se elimino la factura " + factura.getNumero() + " satisfactoriamente");
		}
		catch (DataAccessException e) {
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria eliminar la factura "
					+ facturaDTO.getNumero(), e);
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro la factura cuando se queria eliminar la factura " + facturaDTO.getNumero(), e);
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
	}

	@Override
	public void eliminarFP(FacturaPrestacionDTO facturaDTO) throws HBDataAccessException, HBObjectNotExistsException {
		try {
			FacturaPrestacion factura = facturaPrestacionDAO.obtener(facturaDTO.getId());

			facturaPrestacionDAO.eliminar(factura);
			logger.info("Se elimino la factura " + factura.getNumero() + " satisfactoriamente");
		}
		catch (DataAccessException e) {
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria eliminar la factura "
					+ facturaDTO.getNumero(), e);
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro la factura cuando se queria eliminar la factura " + facturaDTO.getNumero(), e);
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
	}

	@Override
	public void crearReporteFacturasConFondo(FacturaPrestacionDTO dto, HttpServletResponse res)
			throws HBReporteNoDefinido, HBErrorCreandoReporte, HBDataAccessException, HBObjectNotExistsException {
		logger.info("Creacion del reporte de facturas.");

		try {
			FacturaPrestacion factura = this.facturaPrestacionDAO.obtenerOrdenado(dto.getId());
			DatosReporteFactura datosReporte = this.obtenerDatosFacturaPrestacion(factura, dto.isFondo());
			reporteador.exportarReporte(datosReporte, res);
		}
		catch (DataAccessException e) {
			logger.error(
					"Se produjo un problema de acceso a la base de datos cuando se queria crear un reporte con fondo para la factura "
							+ dto.getNumero(), e);
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
		catch (ObjectNotFoundException e) {
			logger.error(
					"No se encontro la factura cuando se queria crear un reporte con fondo para la factura "
							+ dto.getNumero(), e);
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}

	}

	private Collection<DatoListaReporteFactura> setearItems(FacturaPrestacion factura) {
		Collection<DatoListaReporteFactura> items = new ArrayList<DatoListaReporteFactura>();
		Iterator<PrestacionBrindadaFacturada> it = factura.getPrestacionesBrindadasFacturadas().iterator();
		while (it.hasNext()) {
			PrestacionBrindadaFacturada prestacion = it.next();
			DatoListaReporteFactura dato = completarDatosItem(prestacion);

			items.add(dato);
		}
		return items;
	}

	private void setDatosFacturaTotalizada(FacturaPrestacion factura, DatosReporteFactura datosReporte) {
		ObraSocial os=factura.getObraSocialFacturada();
		datosReporte.setCliente(factura.getObraSocialFacturada().getNombre().toUpperCase());
		datosReporte.setObraSocial(factura.getObraSocialFacturada().getDireccion().toString().toUpperCase());
		datosReporte.setDireccion("");
		datosReporte.setNroAfiliado("");
		datosReporte.setPrestador("Nro. Prest. "+os.getPrestador());
		datosReporte.setCodigoContable("CC "+os.getCodigoContable());
	}

	private void setDatosFacturaDetallada(FacturaPrestacion factura, DatosReporteFactura datosReporte) {
		// caso detallado
		datosReporte.setCliente(factura.getPacienteFacturado().getNumHistoriaClinica() + " "
				+ factura.getPacienteFacturado().getNombreyApellido().toUpperCase());
		ObraSocial os = factura.getObraSocialFacturada();
		
		datosReporte.setPrestador("Prest. Nro. "+os.getPrestador());
		datosReporte.setObraSocial(os.getNombre().toUpperCase());
		datosReporte.setDireccion(os.getDireccion().toString().toUpperCase());
		datosReporte.setNroAfiliado("Nro. " + factura.getPacienteFacturado().getNumAfiliadoOSActual());
		datosReporte.setCodigoContable("CC "+os.getCodigoContable());
	}

	private void setDatosComunesFactura(boolean fondo, FacturaPrestacion factura, DatosReporteFactura datosReporte) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_DIA);
		datosReporte.setCUIT(factura.getObraSocialFacturada().getCuit());
		datosReporte.setDescripcionMonto("SON "+ PrecioToStringConverter.convertNumberToLetter(factura.getMontoTotal().doubleValue()));
		
		if( fondo ) datosReporte.setNroFactura(factura.getNumero().substring(5));
		else datosReporte.setNroFactura("");
		
		datosReporte.setFondo(fondo);
		datosReporte.setTipoReporte(ExportType.PDF);
		datosReporte.setFechaEmision(sdf.format(factura.getFechaEmision()));
		this.setTipoFacturar(factura, datosReporte);
		datosReporte.setCuentaCorriente(X);
		datosReporte.setObservaciones("NUEVA DIRECCION DE EMAIL fundacionfacturacion@fibertel.com.ar");
		datosReporte.setTotal(factura.getMontoTotal().setScale(2, BigDecimal.ROUND_DOWN).toPlainString());
		BigDecimal totalRedondeado = factura.getMontoTotal().setScale(0, RoundingMode.HALF_UP);
		BigDecimal diferencia = totalRedondeado.subtract(factura.getMontoTotal());
		datosReporte.setTotal(totalRedondeado.setScale(2, BigDecimal.ROUND_DOWN).toPlainString());
		datosReporte.setAjusteCentavos(diferencia.setScale(2, BigDecimal.ROUND_DOWN).toPlainString());
		
		
	}

	private void setTipoFacturar(Factura factura, DatosReporteFactura datosReporte) {

		TipoIVA tipoIVA = null;
		if (factura instanceof FacturaPrestacion) {
			FacturaPrestacion fp = (FacturaPrestacion) factura;
			tipoIVA = fp.getObraSocialFacturada().getTipoIVA();
		}
		else if (factura instanceof FacturaServicio) {
			FacturaServicio fs = (FacturaServicio) factura;
			tipoIVA = fs.getClienteFacturado().getTipoIVA();
		}

		if (tipoIVA == null)
			return;
		if (tipoIVA.getId() == 1)
			datosReporte.setRespInsc(X);
		else if (tipoIVA.getId() == 2)
			datosReporte.setResNoInsc(X);
		else if (tipoIVA.getId() == 3)
			datosReporte.setNoResp(X);
		else if (tipoIVA.getId() == 4)
			datosReporte.setExcento(X);
		else if (tipoIVA.getId() == 5)
			datosReporte.setConsFinal(X);

	}

	private DatoListaReporteFactura completarDatosItem(PrestacionBrindadaFacturada prestacion) {
		DatoListaReporteFactura dato = new DatoListaReporteFactura();
		SimpleDateFormat formatoFecha = new SimpleDateFormat(FORMATO_DIA);
		dato.setFecha(formatoFecha.format(prestacion.getFecha()));
		dato.setCodigo(prestacion.getCodigo());
		dato.setPrestacion(prestacion.getPrestacionBrindada().getPrestacion().getDescripcion().toUpperCase());
		dato.setImporte(prestacion.getImporteUnitario().setScale(2, BigDecimal.ROUND_DOWN).toPlainString());
		dato.setHonorarios("");
		dato.setCantidad(prestacion.getCantidad().toString());
		BigDecimal total = prestacion.getImporteUnitario().multiply(new BigDecimal(prestacion.getCantidad()));
		dato.setTotal(total.setScale(2, BigDecimal.ROUND_DOWN).toString());

		return dato;

	}

	@Override
	public void crearReporteFacturasConFondo(FacturaServicioDTO filtroDTO, HttpServletResponse res)
			throws HBReporteNoDefinido, HBErrorCreandoReporte, HBDataAccessException, HBObjectNotExistsException {
		logger.info("Creacion del reporte de facturas.");

		try {

			FacturaServicio factura = this.facturaServicioDAO.obtener(filtroDTO.getId());
			boolean fondo = filtroDTO.isFondo();

			DatosReporteFactura datosReporte = obtenerDatosFacturaServicios(factura, fondo);

			reporteador.exportarReporte(datosReporte, res);
		}
		catch (DataAccessException e) {
			logger.error(
					"Se produjo un problema de acceso a la base de datos cuando se queria crear un reporte factura con fondo para la factura "
							+ filtroDTO.getId(), e);
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
		catch (ObjectNotFoundException e) {
			logger.error(
					"No se encontro la factura cuando se queria crear un reporte factura con fondo para la factura "
							+ filtroDTO.getId(), e);
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
	}

	private DatosReporteFactura obtenerDatosFacturaServicios(FacturaServicio factura, boolean fondo) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_DIA);
		DatosReporteFactura datosReporte = new DatosReporteFactura();
		datosReporte.setCliente(factura.getClienteFacturado().getNombre());
		datosReporte.setCUIT(factura.getClienteFacturado().getCuit());
		datosReporte.setDescripcionMonto("");
		datosReporte.setDireccion(factura.getClienteFacturado().getDireccion() != null ? factura.getClienteFacturado()
				.getDireccion().toString() : " ");
		datosReporte.setNroAfiliado(" ");
		
		if( fondo )
			datosReporte.setNroFactura(factura.getNumero().substring(5));
		else
			datosReporte.setNroFactura("");
			
		datosReporte.setObraSocial(" ");
		datosReporte.setCodigoContable("CC "+factura.getClienteFacturado().getCodigoContable());
		datosReporte.setFondo(fondo);
		datosReporte.setTipoReporte(ExportType.PDF);
		datosReporte.setFechaEmision(sdf.format(factura.getFechaEmision()));
		this.setTipoFacturar(factura, datosReporte);
		datosReporte.setCuentaCorriente(X);
		datosReporte.setObservaciones(factura.getObservaciones());

		datosReporte.setTotal(factura.getMontoTotal().setScale(2, BigDecimal.ROUND_DOWN).toPlainString());

		Collection<DatoListaReporteFactura> items = new ArrayList<DatoListaReporteFactura>();
		Iterator<ServicioFacturado> it = factura.getServiciosFacturados().iterator();
		while (it.hasNext()) {
			ServicioFacturado servicio = it.next();
			DatoListaReporteFactura dato = completarDatosItem(servicio);
			items.add(dato);
		}

		datosReporte.setDatosAListar(items);
		return datosReporte;
	}

	private DatoListaReporteFactura completarDatosItem(ServicioFacturado servicio) {
		DatoListaReporteFactura dato = new DatoListaReporteFactura();
		SimpleDateFormat formatoFecha = new SimpleDateFormat(FORMATO_DIA);
		
		if( servicio.getFecha()!=null )
			dato.setFecha(formatoFecha.format(servicio.getFecha()));
		else
			dato.setFecha( "" );
		
		if( servicio.getCantidad()!=null )
		{
			dato.setCantidad(servicio.getCantidad().toString());
			
			BigDecimal total = servicio.getImporteUnitario().multiply(new BigDecimal(servicio.getCantidad()));
			dato.setTotal(total.setScale(2, BigDecimal.ROUND_DOWN).toString());
		}
		else
		{
			dato.setCantidad("");
			
			BigDecimal total = servicio.getImporteUnitario();
			dato.setTotal(total.setScale(2, BigDecimal.ROUND_DOWN).toString());
		}
		
		dato.setCodigo(servicio.getCodigo());
		dato.setPrestacion(servicio.getDescripcion());
		dato.setImporte(servicio.getImporteUnitario().setScale(2, BigDecimal.ROUND_DOWN).toPlainString());
		
		dato.setHonorarios("");

		return dato;
	}

	@Override
	public void crearReporteFacturasPrestacion(String[] facturas, HttpServletResponse res) throws HBReporteNoDefinido,
			HBErrorCreandoReporte, HBDataAccessException, HBObjectNotExistsException {
		try {
			boolean fondo = false;
			ArrayList<DatosReporte> facturasGeneradas = new ArrayList<DatosReporte>();
			for (int i = 0; i < facturas.length; i++) {

				FacturaPrestacion factura = this.facturaPrestacionDAO.obtener(Long.parseLong(facturas[i]));
				DatosReporteFactura datosReporte = obtenerDatosFacturaPrestacion(factura, fondo);
				facturasGeneradas.add(datosReporte);

			}

			reporteador.exportarReporte(facturasGeneradas, res);

		}
		catch (DataAccessException e) {
			logger.error(
					"Se produjo un problema de acceso a la base de datos cuando se queria crear el reporte de facturas",
					e);
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro la factura cuando se queria crear el reporte de facturas", e);
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}

	}

	@Override
	public void crearReporteFacturasServicio(String[] facturas, HttpServletResponse res) throws HBReporteNoDefinido,
			HBErrorCreandoReporte, HBDataAccessException, HBObjectNotExistsException {
		try {
			boolean fondo = false;
			ArrayList<DatosReporte> facturasGeneradas = new ArrayList<DatosReporte>();
			for (int i = 0; i < facturas.length; i++) {

				FacturaServicio factura = this.facturaServicioDAO.obtener(Long.parseLong(facturas[i]));
				DatosReporteFactura datosReporte = obtenerDatosFacturaServicios(factura, fondo);
				facturasGeneradas.add(datosReporte);

			}

			reporteador.exportarReporte(facturasGeneradas, res);

		}
		catch (DataAccessException e) {
			logger.error(
					"Se produjo un problema de acceso a la base de datos cuando se queria crear el reporte de facturas",
					e);
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro la factura cuando se queria crear el reporte de facturas", e);
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}

	}

	private DatosReporteFactura obtenerDatosFacturaPrestacion(FacturaPrestacion factura, boolean fondo) {
		DatosReporteFactura datosReporte = new DatosReporteFactura();
		setDatosComunesFactura(fondo, factura, datosReporte);
		Collection<DatoListaReporteFactura> items = null;
		if (factura.getObraSocialFacturada().getTipoFactura().compareTo("D") == 0) {
			setDatosFacturaDetallada(factura, datosReporte);
			items = setearItems(factura);
		}
		else if (factura.getObraSocialFacturada().getTipoFactura().compareTo("T") == 0) {
			// caso totalizado
			setDatosFacturaTotalizada(factura, datosReporte);
			items = setearItemsTotalizado(factura);

		}
		;

		datosReporte.setDatosAListar(items);
		return datosReporte;
	}

	private Collection<DatoListaReporteFactura> setearItemsTotalizado(FacturaPrestacion factura) {

		return (facturaPrestacionDAO.obtenerItemsTotalizados(factura.getId()));
		// cambio por esta version optimizada
		// Iterator<PrestacionBrindadaFacturada>
		// it=factura.getPrestacionesBrindadasFacturadas().iterator();
		//
		// BigDecimal total=BigDecimal.ZERO;
		// Long idAnterior=-1L;
		// String nombreAnterior=null;
		//
		// while(it.hasNext()){
		//
		//
		// PrestacionBrindadaFacturada prestacion=it.next();
		//
		//
		// if(idAnterior==-1){
		// idAnterior=prestacion.getPacienteFacturado().getId();
		// nombreAnterior=prestacion.getPacienteFacturado().getNumHistoriaClinica().toString()+' '+prestacion.getPacienteFacturado().getNombreyApellido();
		//
		// }else if(prestacion.getPacienteFacturado().getId()!=idAnterior){
		//
		// idAnterior=prestacion.getPacienteFacturado().getId();
		// DatoListaReporteFactura dato=new DatoListaReporteFactura();
		// dato.setPrestacion(nombreAnterior);
		// dato.setTotal(total.setScale(2, BigDecimal.ROUND_DOWN).toString());
		// items.add(dato);
		// nombreAnterior=prestacion.getPacienteFacturado().getNumHistoriaClinica().toString()+' '+prestacion.getPacienteFacturado().getNombreyApellido();
		// total=BigDecimal.ZERO;
		//
		//
		// };
		//
		// total=total.add(prestacion.getImporteUnitario().multiply(new
		// BigDecimal(prestacion.getCantidad())));
		//
		//
		// }
		// if(idAnterior!=-1){
		// DatoListaReporteFactura dato=new DatoListaReporteFactura();
		// dato.setPrestacion(nombreAnterior);
		// dato.setTotal(total.setScale(2, BigDecimal.ROUND_DOWN).toString());
		// items.add(dato);
		// };
		//
		// return items;
	}

	@Override
	public void crearReporteDetalle(FacturaPrestacionDTO filtroDTO, HttpServletResponse res)
			throws HBDataAccessException, HBObjectNotExistsException, HBReporteNoDefinido, HBErrorCreandoReporte {
		try {
			FacturaPrestacion factura = this.facturaPrestacionDAO.obtenerOrdenado(filtroDTO.getId());
			ArrayList<DatosReporte> datosReporte = obtenerDatosReportes(factura);
			reporteador.exportarReporte(datosReporte, res);
		}
		catch (DataAccessException e) {
			logger.error(
					"Se produjo un problema de acceso a la base de datos cuando se queria crear un reporte detalle para la factura "
							+ filtroDTO.getId(), e);
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro la factura cuando se queria crear un reporte detalle para la factura "
					+ filtroDTO.getId(), e);
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}

	}

	private ArrayList<DatosReporte> obtenerDatosReportes(FacturaPrestacion factura) {
		Long idAnterior = -1L;
		DatosReporteDetalleFactura reporteDetalleActual = null;
		ArrayList<DatosReporte> datosReporte = new ArrayList<DatosReporte>();
		ArrayList<DatoListaReporteDetalleFactura> itemsActual = null;
		Collection<PrestacionBrindadaFacturada> prestaciones = factura.getPrestacionesBrindadasFacturadas();

		SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_DIA);
		for (PrestacionBrindadaFacturada prestacion : prestaciones) {

			Paciente pacienteFacturado = prestacion.getPacienteFacturado();
			if (!idAnterior.equals(pacienteFacturado.getId())) {
				idAnterior = pacienteFacturado.getId();
				reporteDetalleActual = new DatosReporteDetalleFactura();
				reporteDetalleActual.setTipoReporte(ExportType.PDF);
				itemsActual = new ArrayList<DatoListaReporteDetalleFactura>();
				reporteDetalleActual.setDatosAListar(itemsActual);
				reporteDetalleActual.setNroAfiliado(pacienteFacturado.getNumAfiliadoOSActual());
				reporteDetalleActual.setNroHC(pacienteFacturado.getNumHistoriaClinica().toString());
				reporteDetalleActual.setObraSocial(pacienteFacturado.getObraSocialActual().getCodigo() + " "
						+ pacienteFacturado.getObraSocialActual().getNombre());
				reporteDetalleActual.setPaciente(pacienteFacturado.getNumHistoriaClinica() + " "
						+ pacienteFacturado.getNombreyApellido());
				datosReporte.add(reporteDetalleActual);

			}

			DatoListaReporteDetalleFactura item = new DatoListaReporteDetalleFactura();
			item.setCantidad(prestacion.getCantidad().toString());
			item.setCodigo(prestacion.getCodigo());
			
			item.setFecha(sdf.format(prestacion.getFecha()));
			item.setImporte(prestacion.getImporteUnitario().setScale(2, BigDecimal.ROUND_DOWN).toString());
			item.setPrestacion(prestacion.getPrestacionBrindada().getPrestacion().getDescripcion());
			item.setTotal(prestacion.getImporteUnitario().multiply(new BigDecimal(prestacion.getCantidad()))
					.setScale(2, BigDecimal.ROUND_DOWN));
			itemsActual.add(item);

		}
		;

		return datosReporte;
	}

	@Override
	public void crearReporteResumen(FacturaPrestacionDTO filtroDTO, HttpServletResponse res)
			throws HBReporteNoDefinido, HBErrorCreandoReporte, HBDataAccessException, HBObjectNotExistsException {
		FacturaPrestacion factura;
		try {
			factura = this.facturaPrestacionDAO.obtenerOrdenado(filtroDTO.getId());

			SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_DIA);

			DatosReportePlanillaFacturacion datosReporte = new DatosReportePlanillaFacturacion();
			datosReporte.setNombrePrestador("FUNDACION DE LA HEMOFILIA - "
					+ factura.getObraSocialFacturada().getPrestador());
			datosReporte.setNroFactura(factura.getNumero());
			datosReporte.setTipoReporte(ExportType.EXCEL);

			ArrayList<DatoListaReportePlanillaFacturacion> items = new ArrayList<DatoListaReportePlanillaFacturacion>();
			Iterator<PrestacionBrindadaFacturada> it = factura.getPrestacionesBrindadasFacturadas().iterator();
			while (it.hasNext()) {
				PrestacionBrindadaFacturada prestacion = it.next();
				DatoListaReportePlanillaFacturacion item = new DatoListaReportePlanillaFacturacion();
				item.setCantidad(prestacion.getCantidad().toString());
				item.setFechaIngreso(sdf.format(prestacion.getFecha()));
				item.setMonto(prestacion.getImporteUnitario().multiply(new BigDecimal(prestacion.getCantidad()))
						.setScale(2, BigDecimal.ROUND_DOWN));
				item.setNombreAfiliado(prestacion.getPacienteFacturado().getNombreyApellido());
				item.setNroBeneficiario(prestacion.getPacienteFacturado().getNumAfiliadoOSActual());
				item.setNroHC(prestacion.getPacienteFacturado().getNumHistoriaClinica().toString());

				item.setPrestacion(prestacion.getPrestacionBrindada().getPrestacion().getDescripcion());
				item.setNroOp("");
				
				item.setObservaciones(prestacion.getPrestacionBrindada().getObservaciones());
				
				if( prestacion.getPrestacionBrindada().getFechaEgreso()==null )
					item.setFechaEgreso( "" );
				else
					item.setFechaEgreso( sdf.format(prestacion.getPrestacionBrindada().getFechaEgreso()) );
				
				items.add(item);
			}

			datosReporte.setDatosAListar(items);

			reporteador.exportarReporte(datosReporte, res);

		}
		catch (DataAccessException e) {
			logger.error(
					"Se produjo un problema de acceso a la base de datos cuando se queria crear un reporte de resumen de factura",
					e);
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro la factura cuando se queria crear un reporte de resumen de factura", e);
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}

	};
	
	@Override
	public void anularFacturaPrestacion( FacturaPrestacionDTO facturaDTO ) throws  HBDataAccessException, HBObjectNotExistsException
	{
		try {
			FacturaPrestacion factura = facturaPrestacionDAO.obtener( facturaDTO.getId() );
			factura.setAnulada( facturaDTO.getAnulada() );
			facturaPrestacionDAO.actualizar(factura);
		}
		catch (DataAccessException e) {
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria anular una factura",	e);
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro la factura cuando se queria anular una factura", e);
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
	}
	
	@Override
	public void anularFacturaServicio( FacturaServicioDTO facturaDTO ) throws  HBDataAccessException, HBObjectNotExistsException
	{
		try {
			FacturaServicio factura = facturaServicioDAO.obtener( facturaDTO.getId() );
			factura.setAnulada( facturaDTO.getAnulada() );
			facturaServicioDAO.actualizar(factura);
		}
		catch (DataAccessException e) {
			logger.error("Se produjo un problema de acceso a la base de datos cuando se queria anular una factura",	e);
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro la factura cuando se queria anular una factura", e);
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
	}
	
	@Override
	public void crearReporteSintesis(FacturaPrestacionDTO filtroDTO, HttpServletResponse res)
			throws HBDataAccessException, HBObjectNotExistsException, HBReporteNoDefinido, HBErrorCreandoReporte {
		try {
			FacturaPrestacion factura = this.facturaPrestacionDAO.obtenerOrdenado(filtroDTO.getId());
			ArrayList<DatosReporte> datosReporte = obtenerDatosReportesSintesis(factura);
			reporteador.exportarReporte(datosReporte, res);
		}
		catch (DataAccessException e) {
			logger.error(
					"Se produjo un problema de acceso a la base de datos cuando se queria crear un reporte detalle para la factura "
							+ filtroDTO.getId(), e);
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro la factura cuando se queria crear un reporte detalle para la factura "
					+ filtroDTO.getId(), e);
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}

	}



	private ArrayList<DatosReporte> obtenerDatosReportesSintesis(
			FacturaPrestacion factura) {
		Long idAnterior = -1L;
		DatosReporteSintesisHC reporteSintesisActual = null;
		ArrayList<DatosReporte> datosReporte = new ArrayList<DatosReporte>();
		ArrayList<DatoListaReporteSintesisHC> itemsActual = null;
		Collection<PrestacionBrindadaFacturada> prestaciones = factura.getPrestacionesBrindadasFacturadas();
		SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_DIA);
		for (PrestacionBrindadaFacturada prestacion : prestaciones) {

			Paciente pacienteFacturado = prestacion.getPacienteFacturado();
			if (!idAnterior.equals(pacienteFacturado.getId())) {
				idAnterior = pacienteFacturado.getId();
				reporteSintesisActual = new DatosReporteSintesisHC();
				reporteSintesisActual.setTipoReporte(ExportType.PDF);
				itemsActual = new ArrayList<DatoListaReporteSintesisHC>();
				reporteSintesisActual.setDatosAListar(itemsActual);
				reporteSintesisActual.setNroAfiliado(pacienteFacturado.getNumAfiliadoOSActual());
				reporteSintesisActual.setObraSocial(pacienteFacturado.getObraSocialActual().getCodigo() + " "+ pacienteFacturado.getObraSocialActual().getNombre());
				reporteSintesisActual.setPaciente(pacienteFacturado.getNumHistoriaClinica() + " "+ pacienteFacturado.getNombreyApellido());
				reporteSintesisActual.setDiagnostico(pacienteFacturado.getDiagnostico());
				datosReporte.add(reporteSintesisActual);

			}

			DatoListaReporteSintesisHC item = new DatoListaReporteSintesisHC();
			
			item.setFecha(sdf.format(prestacion.getFecha()));
			String observaciones = prestacion.getPrestacionBrindada().getObservaciones();
			item.setPrestacion(observaciones!=null?observaciones:prestacion.getPrestacionBrindada().getPrestacion().getDescripcion());
			itemsActual.add(item);

		}
		;
		
		agruparPrestaciones(datosReporte);

		return datosReporte;
	}

	private void agruparPrestaciones(ArrayList<DatosReporte> datosReporte) {
		Iterator<DatosReporte> it=datosReporte.iterator();
		while(it.hasNext()){
			DatosReporteSintesisHC sintesis=(DatosReporteSintesisHC) it.next();
			agruparLaboratorio(sintesis);
		}
		
	}

	private void agruparLaboratorio(DatosReporteSintesisHC sintesis) {
		@SuppressWarnings("rawtypes")
		Iterator it=sintesis.getDatosAListar().iterator();
		DatoListaReporteSintesisHC actual=null;
		
		while(it.hasNext()){
			DatoListaReporteSintesisHC renglon=(DatoListaReporteSintesisHC)it.next();
			if((actual==null) || (!actual.equals(renglon))){
				actual=renglon;				
			}else{
				if(renglon.getPrestacion().compareTo(PrestacionBrindadaService.OBSERVACION_ANALISIS_LABORATORIO)==0){
					it.remove();
				}
			}
		}
		
	}



	@Override
	public FacturaPrestacionDTO obtenerFacturaPrestacion(Long id) throws HBDataAccessException, HBObjectNotExistsException 
	{
		try 
		{
			FacturaPrestacion fc = facturaPrestacionDAO.obtener(id);
			FacturaPrestacionDTO fcDTO = mapper.map( fc, FacturaPrestacionDTO.class );
			
			if( fcDTO.getPaciente()!=null )
				fcDTO.getPaciente().setNombreyApellido( HTMLEntities.htmlentities(fcDTO.getPaciente().getNombreyApellido()) );
			
			fcDTO.getObraSocial().setNombre( HTMLEntities.htmlentities(fcDTO.getObraSocial().getNombre()) );
			
			Iterator<PrestacionBrindadaFacturada> ifp = fc.getPrestacionesBrindadasFacturadas().iterator();

			while (ifp.hasNext()) {
				PrestacionBrindadaFacturada pbf = ifp.next();
				ItemFacturaPrestacionDTO item = mapper.map( pbf, ItemFacturaPrestacionDTO.class );
				item.setDescripcionPrestacion( HTMLEntities.htmlentities(item.getDescripcionPrestacion()));
				item.setNombrePaciente( HTMLEntities.htmlentities(item.getNombrePaciente()));
				
				fcDTO.getItems().add(item);
			}
			
			return fcDTO;
		}
		catch (DataAccessException e) {
			logger.error( "Se produjo un problema de acceso a la base de datos cuando se queria obtener la factura "+ id.toString(), e);
			HBDataAccessException ex = new HBDataAccessException(e);
			throw ex;
		}
		catch (ObjectNotFoundException e) {
			logger.error("No se encontro la factura cuando se queria obtener la factura " + id.toString() , e);
			HBObjectNotExistsException ex = new HBObjectNotExistsException(e);
			throw ex;
		}
	}

	@Override
	public void eliminarFacturasPrestacion(Long[] idsFacturas) {
		facturaPrestacionDAO.eliminar(idsFacturas);
		
	}

	@Override
	public void eliminarFacturasServicios(Long[] idsFacturas) {
		facturaPrestacionDAO.eliminar(idsFacturas);
		
	}
	

}