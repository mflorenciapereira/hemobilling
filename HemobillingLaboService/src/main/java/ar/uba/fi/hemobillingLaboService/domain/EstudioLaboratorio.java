package ar.uba.fi.hemobillingLaboService.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Resultados")
public class EstudioLaboratorio 
{
	@Id	
	@Column(name = "ClaveRes")
	private Integer idEstudio;
	
	@Column(name = "Id_Est")
	private Integer tipoEstudio;
	
	@Column(name = "Estudio")
	private String nombreEstudio;
	
	@Column(name = "Resultado")
	private String resultado;

	public Integer getIdEstudio() {
		return idEstudio;
	}

	public void setIdEstudio(Integer idEstudio) {
		this.idEstudio = idEstudio;
	}

	public String getNombreEstudio() {
		return nombreEstudio;
	}

	public void setNombreEstudio(String nombreEstudio) {
		this.nombreEstudio = nombreEstudio;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public void setTipoEstudio(Integer tipoEstudio) {
		this.tipoEstudio = tipoEstudio;
	}

	public Integer getTipoEstudio() {
		return tipoEstudio;
	}

}
