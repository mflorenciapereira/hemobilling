package ar.uba.fi.hemobilling.domain.prestaciones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
@Table(name="prestaciones")
public class Prestacion {

	@Id	
	@Column(name = "codigo")
	private Long codigo;
	
	@Column(name = "descripcion")
	private String descripcion;
	

	
	@OneToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@org.hibernate.annotations.IndexColumn(name="orden")
	@JoinTable(name = "prestacionesAsociadas", joinColumns = { @JoinColumn(name = "codigoModulo") }, inverseJoinColumns = { @JoinColumn(name = "codigoDeterminacion") })
	private List<Prestacion> prestacionesAsociadas=new ArrayList<Prestacion>();
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Prestacion> getPrestacionesAsociadas() {
		return prestacionesAsociadas;
	}


}
