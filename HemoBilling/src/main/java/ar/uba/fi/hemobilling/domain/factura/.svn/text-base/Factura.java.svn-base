package ar.uba.fi.hemobilling.domain.factura;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="facturas")
@Inheritance( strategy=InheritanceType.JOINED)
public abstract class Factura {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	protected Long id;
	
	@Column(name = "numero")
	protected String numero;

	@Column(name = "anulada")
	protected Boolean anulada = Boolean.FALSE;
	
	@Column(name = "fecha")
	protected Date fechaEmision;
	
	@Column(name = "codigoContable")
	protected String codigoContable;
	
	
	@Column(name = "observaciones")
	protected String observaciones;
	

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getCodigoContable() {
		return codigoContable;
	}

	public void setCodigoContable(String codigoContable) {
		this.codigoContable = codigoContable;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	
	public abstract BigDecimal getMontoTotal();

	public Boolean getAnulada() {
		return anulada;
	}

	public void setAnulada(Boolean anulada) {
		this.anulada = anulada;
	}
	
	
}
