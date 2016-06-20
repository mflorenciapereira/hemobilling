package ar.uba.fi.hemobilling.domain.factura;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="serviciosBrindadosFacturados")
public class ServicioFacturado 
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
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "importeUnitario")
	private BigDecimal importeUnitario;

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getImporteUnitario() {
		return importeUnitario;
	}

	public void setImporteUnitario(BigDecimal importeUnitario) {
		this.importeUnitario = importeUnitario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
}
