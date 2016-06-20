package ar.uba.fi.hemobilling.domain.listasprecio;


import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OrderBy;




@Entity
@Table(name="listasprecio")
public class ListaPrecio {
	
	

	@Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
	@Column(name = "codigo")
	private Long codigo;
	
	@Column(name = "nombre")
	private String nombre;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idlistaPrecioPertenece")
    @OrderBy(clause = "prestacionid DESC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<ItemListaPrecio> items= new ArrayList<ItemListaPrecio>();
    
    public ListaPrecio(Long codigo){
    	this.codigo=codigo;
    	
    }
    
    public ListaPrecio(){
    	
    	
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

	public Collection<ItemListaPrecio> getItems() {
		return items;
	}

	public void setItems(Collection<ItemListaPrecio> items) {
		this.items = items;
	}


}
