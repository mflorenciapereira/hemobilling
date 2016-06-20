package ar.uba.fi.hemobilling.domain.backup;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="backupsRealizados")
public class BackupRealizado 
{
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "fechaRealizado")
	private Date fechaRealizado;

	public BackupRealizado()
	{
		fechaRealizado = new Date();
	}
	
	public Date getFechaRealizado() {
		return fechaRealizado;
	}

	public void setFechaRealizado(Date fechaRealizado) {
		this.fechaRealizado = fechaRealizado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
