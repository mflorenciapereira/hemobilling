package ar.uba.fi.hemobilling.domain.obrassociales;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import ar.uba.fi.hemobilling.domain.listasprecio.ListaPrecio;




@Entity
@Table(name="asociacionoslp")
public class AsociacionObraSociaListaPrecio {
	
	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
		
    @ManyToOne
    @JoinColumn(name="obrasocialid")
	private ObraSocial obraSocial;
    
    
    @ManyToOne
    @JoinColumn(name="listaprecioid")
	private ListaPrecio listaPrecio;
    
	@Column(name = "desde")
	@Type(type="date")
	private Date desde;
	
	@Column(name = "hasta")
	@Type(type="date")
	private Date hasta;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}





	public ListaPrecio getListaPrecio() {
		return listaPrecio;
	}


	public void setListaPrecio(ListaPrecio listaPrecio) {
		this.listaPrecio = listaPrecio;
	}


	


	public Date getDesde() {
		return desde;
	}


	public void setDesde(Date desde) {
		this.desde = desde;
	}


	public Date getHasta() {
		return hasta;
	}


	public void setHasta(Date hasta) {
		this.hasta = hasta;
	}


	public ObraSocial getObraSocial() {
		return obraSocial;
	}


	public void setObraSocial(ObraSocial obraSocial) {
		this.obraSocial = obraSocial;
	}
    
  
    
   
	
	



}
