package ar.uba.fi.hemobilling.domain.clientes;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import ar.uba.fi.hemobilling.domain.general.Direccion;
import ar.uba.fi.hemobilling.domain.general.TipoIVA;

@Entity
@Table(name="clientes")
public class Cliente 
{
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
	@Column(name = "codigo")
	private Long codigo;
	
	@Column(name = "codigoContable")
	private String codigoContable;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Embedded
	private Direccion direccion;
	
	@Column(name = "telefono")
	private String telefono;
	
	@Column(name = "telefonoGratuito")
	private String telefonoGratuito;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "website")
	private String website;
	
	@Column(name = "cuit")
	private String cuit;
	
	
    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name="tipoIVAid")
	private TipoIVA tipoIVA;
	
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


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public Direccion getDireccion() {
		return direccion;
	}


	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
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


	public String getCuit() {
		return cuit;
	}


	public void setCuit(String cuit) {
		this.cuit = cuit;
	}


	public TipoIVA getTipoIVA() {
		return tipoIVA;
	}


	public void setTipoIVA(TipoIVA tipoIVA) {
		this.tipoIVA = tipoIVA;
	}


	

}
