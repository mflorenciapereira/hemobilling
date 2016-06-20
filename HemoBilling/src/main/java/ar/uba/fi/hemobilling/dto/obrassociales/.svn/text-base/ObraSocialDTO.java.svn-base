package ar.uba.fi.hemobilling.dto.obrassociales;

import org.springframework.util.AutoPopulatingList;

import ar.uba.fi.hemobilling.dto.general.TipoIVADTO;

public class ObraSocialDTO 
{
	
	private Long codigo;
	private String nombre;
	private String codigoRNOS;
	private String codigoContable;
	private String sigla;
	private String prestador;
	private String gerenciadora;
	private String cuit;
	private String telefono;
	private String telefonoGratuito;
	private String email;
	private String website;
	private String direccion;
	private String localidad;
	private String provincia;
	private String codigoPostal;
	private int desdeAsociacion=0;
	private int accion=0; //0=editar; 1=asignar lista
	private int cantidadPeriodos;
	
	
	private String pais;
	private TipoIVADTO tipoIVA = new TipoIVADTO();
	private String direccionCompleta;
	private	AutoPopulatingList planesAuto=new AutoPopulatingList(PlanDTO.class);
	private	AutoPopulatingList asociacionListasAuto=new AutoPopulatingList(AsociacionObraSocialListaPrecioDTO.class);
	private AsociacionObraSocialListaPrecioDTO asociacionActual =new AsociacionObraSocialListaPrecioDTO();
	
	private String tipoFactura;
	private Boolean contabilizaFactura;
	
	public ObraSocialDTO() {
		
		
		
	}
	
	public ObraSocialDTO(Long codigoOS) {
		this.codigo=codigoOS;
	}
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getPrestador() {
		return prestador;
	}
	public void setPrestador(String prestador) {
		this.prestador = prestador;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getTelefonoGratuito() {
		return telefonoGratuito;
	}
	public void setTelefonoGratuito(String telefonoGratuito) {
		this.telefonoGratuito = telefonoGratuito;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}


	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getDireccionCompleta() {
		return direccionCompleta;
	}
	public void setDireccionCompleta(String direccionCompleta) {
		this.direccionCompleta = direccionCompleta;
	}
	public TipoIVADTO getTipoIVA() {
		return tipoIVA;
	}
	public void setTipoIVA(TipoIVADTO tipoIVA) {
		this.tipoIVA = tipoIVA;
	}
	public AutoPopulatingList getPlanesAuto() {
		return planesAuto;
	}
	public void setPlanesAuto(AutoPopulatingList planesAuto) {
		this.planesAuto = planesAuto;
	}

	public String getGerenciadora() {
		return gerenciadora;
	}

	public void setGerenciadora(String gerenciadora) {
		this.gerenciadora = gerenciadora;
	}


//	public AutoPopulatingList getAsociacionListasAuto() {
//		return asociacionListasAuto;
//	}
//
//	public void setAsociacionListasAuto(AutoPopulatingList asociacionListasAuto) {
//		this.asociacionListasAuto = asociacionListasAuto;
//	}
//
	public AsociacionObraSocialListaPrecioDTO getAsociacionActual() {
		return asociacionActual;
	}

	public void setAsociacionActual(AsociacionObraSocialListaPrecioDTO asociacionActual) {
		this.asociacionActual = asociacionActual;
	}

	public int getAccion() {
		return accion;
	}

	public void setAccion(int accion) {
		this.accion = accion;
	}

	public int getDesdeAsociacion() {
		return desdeAsociacion;
	}

	public void setDesdeAsociacion(int desdeAsociacion) {
		this.desdeAsociacion = desdeAsociacion;
	}

	public String getCodigoRNOS() {
		return codigoRNOS;
	}

	public void setCodigoRNOS(String codigoRNOS) {
		this.codigoRNOS = codigoRNOS;
	}



	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTipoFactura() {
		return tipoFactura;
	}

	public void setTipoFactura(String tipoFactura) {
		this.tipoFactura = tipoFactura;
	}

	public AutoPopulatingList getAsociacionListasAuto() {
		return asociacionListasAuto;
	}

	public void setAsociacionListasAuto(AutoPopulatingList asociacionListasAuto) {
		this.asociacionListasAuto = asociacionListasAuto;
	}

	public int getCantidadPeriodos() {
		return cantidadPeriodos;
	}

	public void setCantidadPeriodos(int cantidadPeriodos) {
		this.cantidadPeriodos = cantidadPeriodos;
	}

	public String getCodigoContable() {
		return codigoContable;
	}

	public void setCodigoContable(String codigoContable) {
		this.codigoContable = codigoContable;
	}

	public Boolean getContabilizaFactura() {
		return contabilizaFactura;
	}

	public void setContabilizaFactura(Boolean contabilizaFactura) {
		this.contabilizaFactura = contabilizaFactura;
	}






	

}
