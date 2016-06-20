package ar.uba.fi.hemobilling.domain.prestacionesBrindadas;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import ar.uba.fi.hemobilling.domain.paciente.Paciente;
import ar.uba.fi.hemobilling.domain.prestaciones.Prestacion;

@Entity
@Table(name="prestacionesbrindadas")
public class PrestacionBrindada 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "codigo")
	private Long codigo;
	
	@Column(name = "fecha")
	private Date fecha;

	@Column(name = "fechaEgreso")
	private Date fechaEgreso;
	
    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name="idPaciente", updatable=false, nullable=false)
	private Paciente paciente;
	
	@Column(name = "cantidadDePrestaciones")
	private BigInteger cantidadDePrestaciones;
	
    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name="codigoPrestacion", updatable=false, nullable=false)
	private Prestacion prestacion;
	
	@Column(name = "autorizacion")
	private String autorizacion;
	
	@Column(name = "profesional")
	private String profesional;
	
	@Column(name = "observaciones")
	private String observaciones;
	
	@Column(name = "fechaImportacion")
	private Date fechaImportacion;
	
	@Column(name = "precioManual")
	private BigDecimal precioManual;
	
	@Column(name = "codigoEnLaboratorio")
	private Long codigoEnLaboratorio;

	/**
	 * @return the codigoEnLaboratorio
	 */
	public Long getCodigoEnLaboratorio() {
		return codigoEnLaboratorio;
	}

	/**
	 * @param codigoEnLaboratorio the codigoEnLaboratorio to set
	 */
	public void setCodigoEnLaboratorio(Long codigoEnLaboratorio) {
		this.codigoEnLaboratorio = codigoEnLaboratorio;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getProfesional() {
		return profesional;
	}

	public void setProfesional(String profesional) {
		this.profesional = profesional;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public BigInteger getCantidadDePrestaciones() {
		return cantidadDePrestaciones;
	}

	public void setCantidadDePrestaciones(BigInteger cantidadDePrestaciones) {
		this.cantidadDePrestaciones = cantidadDePrestaciones;
	}

	public Prestacion getPrestacion() {
		return prestacion;
	}

	public void setPrestacion(Prestacion prestacion) {
		this.prestacion = prestacion;
	}

	public String getAutorizacion() {
		return autorizacion;
	}

	public void setAutorizacion(String autorizacion) {
		this.autorizacion = autorizacion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Date getFechaEgreso() {
		return fechaEgreso;
	}

	public void setFechaEgreso(Date fechaEgreso) {
		this.fechaEgreso = fechaEgreso;
	}

	public Date getFechaImportacion() {
		return fechaImportacion;
	}

	public void setFechaImportacion(Date fechaImportacion) {
		this.fechaImportacion = fechaImportacion;
	}

	public BigDecimal getPrecioManual() {
		return precioManual;
	}

	public void setPrecioManual(BigDecimal precioManual) {
		this.precioManual = precioManual;
	}
}
