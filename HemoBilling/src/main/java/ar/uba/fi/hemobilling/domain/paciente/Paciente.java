package ar.uba.fi.hemobilling.domain.paciente;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import ar.uba.fi.hemobilling.domain.general.Direccion;
import ar.uba.fi.hemobilling.domain.obrassociales.ObraSocial;

@Entity
@Table(name="pacientes")
public class Paciente   
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "dni")
	private String nroDNI;
	
	@Column(name = "tipoDocumento")
	private String tipoDoc;
	
	@Column(name = "nombreyApellido")
	private String nombreyApellido;
	
	@Column(name = "numHistoriaClinica")
	private Long numHistoriaClinica;
	
	@Column(name = "sexo")
	private String sexo;
	
	@Column(name = "estadoCivil")
	private String estadoCivil;
	
	@Column(name = "fechaNacimiento")
	private Date fechaNacimiento;
	
	@Embedded
	private Direccion ubicacion;
	
	@Column(name = "telefono")
	private String telefono;
	
	@Column(name = "celular")
	private String celular;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "diagnostico")
	private String diagnostico;
	
	@Column(name = "severidad")
	private String severidad;
	
	@Column(name = "usaInibidor")
	private Boolean usaInibidor;
	
	@Column(name = "tipoHemofilia")
	private String tipoHemofilia;

	@Column(name = "grupoSanguineo")
	private String grupoSanguineo;

	@Column(name = "factorSangre")
	private String factor;
	
	@Column(name = "nroAfiliadoOSActual")
	private String numAfiliadoOSActual;
	
    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name="codObraSocialActual")
	private ObraSocial obraSocialActual;
	
	@OneToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "obrassocialespacientes", joinColumns = { @JoinColumn(name = "idPaciente") }, inverseJoinColumns = { @JoinColumn(name = "codObraSocial") })
	private Collection<ObraSocial> obrasSocialesAdheridas;


	public String getNroDNI() {
		return nroDNI;
	}

	public void setNroDNI(String nroDNI) {
		this.nroDNI = nroDNI;
	}

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public String getNombreyApellido() {
		return nombreyApellido;
	}

	public void setNombreyApellido(String nombreyApellido) {
		this.nombreyApellido = nombreyApellido;
	}

	public Long getNumHistoriaClinica() {
		return numHistoriaClinica;
	}

	public void setNumHistoriaClinica(Long numHistoriaClinica) {
		this.numHistoriaClinica = numHistoriaClinica;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public String getSeveridad() {
		return severidad;
	}

	public void setSeveridad(String severidad) {
		this.severidad = severidad;
	}

	public String getNumAfiliadoOSActual() {
		return numAfiliadoOSActual;
	}

	public void setNumAfiliadoOSActual(String numAfiliadoOSActual) {
		this.numAfiliadoOSActual = numAfiliadoOSActual;
	}

	public String getGrupoSanguineo() {
		return grupoSanguineo;
	}

	public void setGrupoSanguineo(String grupoSanguineo) {
		this.grupoSanguineo = grupoSanguineo;
	}

	public String getFactor() {
		return factor;
	}

	public void setFactor(String factor) {
		this.factor = factor;
	}

	public ObraSocial getObraSocialActual() {
		return obraSocialActual;
	}

	public void setObraSocialActual(ObraSocial obraSocialActual) {
		this.obraSocialActual = obraSocialActual;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<ObraSocial> getObrasSocialesAdheridas() {
		return obrasSocialesAdheridas;
	}

	public void setObrasSocialesAdheridas(Collection<ObraSocial> obrasSocialesAdheridas) {
		this.obrasSocialesAdheridas = obrasSocialesAdheridas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getUsaInibidor() {
		return usaInibidor;
	}

	public void setUsaInibidor(Boolean usaInibidor) {
		this.usaInibidor = usaInibidor;
	}

	public String getTipoHemofilia() {
		return tipoHemofilia;
	}

	public void setTipoHemofilia(String tipoHemofilia) {
		this.tipoHemofilia = tipoHemofilia;
	}

	public Direccion getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(Direccion ubicacion) {
		this.ubicacion = ubicacion;
	}
	
}
