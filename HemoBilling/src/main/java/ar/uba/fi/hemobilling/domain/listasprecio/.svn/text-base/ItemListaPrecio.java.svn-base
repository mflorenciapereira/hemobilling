package ar.uba.fi.hemobilling.domain.listasprecio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.uba.fi.hemobilling.domain.prestaciones.Prestacion;


@Entity
@Table(name="itemslistaprecio")
public class ItemListaPrecio 
{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "codigoSegunOS")
	private String codigo;
	

	@Column(name = "precio")
	private Double precio;
	
	
    @ManyToOne
    @JoinColumn(name="prestacionid")
	private Prestacion prestacion;
    
    @Column(name = "listaprecioid")
	private Long idlistaPrecioPertenece;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public Double getPrecio() {
		return precio;
	}


	public void setPrecio(Double precio) {
		this.precio = precio;
	}


	public Prestacion getPrestacion() {
		return prestacion;
	}


	public void setPrestacion(Prestacion prestacion) {
		this.prestacion = prestacion;
	}


	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	public Long getIdlistaPrecioPertenece() {
		return idlistaPrecioPertenece;
	}


	public void setIdlistaPrecioPertenece(Long idlistaPrecioPertenece) {
		this.idlistaPrecioPertenece = idlistaPrecioPertenece;
	}
    
  
    
   
	
	



}
