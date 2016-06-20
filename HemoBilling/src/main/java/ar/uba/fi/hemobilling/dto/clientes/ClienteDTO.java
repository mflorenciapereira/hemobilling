package ar.uba.fi.hemobilling.dto.clientes;

import ar.uba.fi.hemobilling.dto.BasicDTO;
import ar.uba.fi.hemobilling.dto.general.TipoIVADTO;


public class ClienteDTO extends BasicDTO
{
	private Long codigo;
	private String nombre;
	private String codigoContable;
	private String cuit;
	private String telefono;
	private String telefonoGratuito;
	private String email;
	private String website;
	private String direccion;
	private String localidad;
	private String provincia;
	private String codigoPostal;
	private String pais;
	private TipoIVADTO tipoIVA = new TipoIVADTO();
	private String direccionCompleta;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCuit() {
		return cuit;
	}
	public void setCuit(String cuit) {
		this.cuit = cuit;
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

	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getCodigoContable() {
		return codigoContable;
	}
	public void setCodigoContable(String codigoContable) {
		this.codigoContable = codigoContable;
	}
	public String getTelefonoGratuito() {
		return telefonoGratuito;
	}
	public void setTelefonoGratuito(String telefonoGratuito) {
		this.telefonoGratuito = telefonoGratuito;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public TipoIVADTO getTipoIVA() {
		return tipoIVA;
	}
	public void setTipoIVA(TipoIVADTO tipoIVA) {
		this.tipoIVA = tipoIVA;
	}
	public String getDireccionCompleta() {
		return direccionCompleta;
	}
	public void setDireccionCompleta(String direccionCompleta) {
		this.direccionCompleta = direccionCompleta;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	

}
