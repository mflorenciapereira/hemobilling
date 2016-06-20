package ar.uba.fi.hemobillingLaboService.domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="Cabeza")
public class AnalisisLaboratorio 
{	
	@Id	
	@Column(name = "MovID")
	private Integer idAnalisis;
	
	@Column(name = "Paciente")
	private Integer nroHC;
	
	@Column(name = "Fecha")
	private Date fechaRealizacion;
	
	@OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name="IdMovRes")
    private Collection<EstudioLaboratorio> resultados;

	public Integer getIdAnalisis() {
		return idAnalisis;
	}

	public void setIdAnalisis(Integer idAnalisis) {
		this.idAnalisis = idAnalisis;
	}


	public Integer getNroHC() {
		return nroHC;
	}


	public void setNroHC(Integer nroHC) {
		this.nroHC = nroHC;
	}


	public Date getFechaRealizacion() {
		return fechaRealizacion;
	}


	public void setFechaRealizacion(Date fechaRealizacion) {
		this.fechaRealizacion = fechaRealizacion;
	}


	public Collection<EstudioLaboratorio> getResultados() {
		return resultados;
	}


	public void setResultados(Collection<EstudioLaboratorio> resultados) {
		this.resultados = resultados;
	}
	
	

}
