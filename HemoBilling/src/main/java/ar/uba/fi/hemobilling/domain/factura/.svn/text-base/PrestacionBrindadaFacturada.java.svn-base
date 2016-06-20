package ar.uba.fi.hemobilling.domain.factura;

import java.math.BigDecimal;
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
import ar.uba.fi.hemobilling.domain.prestacionesBrindadas.PrestacionBrindada;

@Entity
@Table(name="prestacionesBrindadasFacturadas")
public class PrestacionBrindadaFacturada
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")	
	private Integer id;
	
	@Column(name = "fecha")
	private Date fecha;
	
	@Column(name = "codigo")
	private String codigo;
	
	@Column(name = "cantidad")
	private Long cantidad;
	
	@Column(name = "importeUnitario")
	private BigDecimal importeUnitario;
	
    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name="idPacienteFacturado")
	private Paciente pacienteFacturado;
	
    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name="codPrestacionBrindada")
	private PrestacionBrindada prestacionBrindada;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getImporteUnitario() {
		return importeUnitario;
	}

	public void setImporteUnitario(BigDecimal importeUnitario) {
		this.importeUnitario = importeUnitario;
	}

	public Paciente getPacienteFacturado() {
		return pacienteFacturado;
	}

	public void setPacienteFacturado(Paciente pacienteFacturado) {
		this.pacienteFacturado = pacienteFacturado;
	}

	public PrestacionBrindada getPrestacionBrindada() {
		return prestacionBrindada;
	}

	public void setPrestacionBrindada(PrestacionBrindada prestacionBrindada) {
		this.prestacionBrindada = prestacionBrindada;
	}	
}
