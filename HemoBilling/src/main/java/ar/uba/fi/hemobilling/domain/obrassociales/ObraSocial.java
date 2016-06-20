package ar.uba.fi.hemobilling.domain.obrassociales;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import ar.uba.fi.hemobilling.domain.general.Direccion;
import ar.uba.fi.hemobilling.domain.general.TipoIVA;

@Entity
@Table(name="obrassociales")
public class ObraSocial
{
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
	@Column(name = "codigo")
	private Long codigo;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "gerenciadora")
	private String gerenciadora;
	
	@Column(name = "RegNOS")
	private String codigoRNOS;
	
	@Column(name = "sigla")
	private String sigla;
	
	@Column(name = "prestador")
	private String prestador;
	
	@Column(name = "cuit")
	private String cuit;
	
	@Column(name = "telefono")
	private String telefono;
	
	@Column(name = "telefonoGratuito")
	private String telefonoGratuito;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "website")
	private String website;
	
	@Column(name = "codigoContable")
	private String codigoContable;
	
	@Embedded
	private Direccion direccion;
	
    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name="tipoIVAid")
	private TipoIVA tipoIVA;
    
//    @ManyToMany(cascade = CascadeType.ALL)
//    @OrderBy(clause = "prestacionid DESC")
//	@JoinTable(name = "asociacionListaPrecioObraSocial", joinColumns = { @JoinColumn(name = "codigoObraSocial") }, inverseJoinColumns = { @JoinColumn(name = "codigoListaPrecio") })
//    private Set<AsociacionObraSociaListaPrecio> asociacionListasPrecio = new HashSet<AsociacionObraSociaListaPrecio>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "obraSocial")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Plan> planes= new HashSet<Plan>();
    
	@Column(name = "tipoFactura")
	private String tipoFactura;
	
	@Column(name = "contabilizaFactura")
	private Boolean contabilizaFactura;
    
    public ObraSocial() {
		
	}

	public ObraSocial(Long codigo) {
		this.codigo=codigo;
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

	public String getCodigoRNOS() {
		return codigoRNOS;
	}

	public void setCodigoRNOS(String codigoRNOS) {
		this.codigoRNOS = codigoRNOS;
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

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public TipoIVA getTipoIVA() {
		return tipoIVA;
	}

	public void setTipoIVA(TipoIVA tipoIVA) {
		this.tipoIVA = tipoIVA;
	}

	public Set<Plan> getPlanes() {
		return planes;
	}

	public void setPlanes(Set<Plan> planes) {
		this.planes = planes;
	}

	public String getGerenciadora() {
		return gerenciadora;
	}

	public void setGerenciadora(String gerenciadora) {
		this.gerenciadora = gerenciadora;
	}
//
//	public Set<AsociacionObraSociaListaPrecio> getAsociacionListasPrecio() {
//		return asociacionListasPrecio;
//	}
//
//	public void setAsociacionListasPrecio(Set<AsociacionObraSociaListaPrecio> asociacionListasPrecio) {
//		this.asociacionListasPrecio = asociacionListasPrecio;
//	}



	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getTipoFactura() {
		return tipoFactura;
	}

	public void setTipoFactura(String tipoFactura) {
		this.tipoFactura = tipoFactura;
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
