package ar.uba.fi.hemobilling.domain.factura;

import java.math.BigDecimal;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import ar.uba.fi.hemobilling.domain.obrassociales.ObraSocial;
import ar.uba.fi.hemobilling.domain.paciente.Paciente;

@Entity
@Table(name="facturasprestaciones")
public class FacturaPrestacion extends Factura 
{
	@ManyToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name="codigoOS")
	private  ObraSocial obraSocialFacturada;
    
	@ManyToOne
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name="idPaciente")
    private Paciente pacienteFacturado;
	
	@OneToMany( cascade = CascadeType.ALL )
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "asociacionPrestacionesBrindadasFacturadas", joinColumns = { @JoinColumn(name = "id_factura") }, inverseJoinColumns = { @JoinColumn(name = "id_prestacionBrindadaFacturada") })
	private  Collection<PrestacionBrindadaFacturada> prestacionesBrindadasFacturadas;

	public ObraSocial getObraSocialFacturada() {
		return obraSocialFacturada;
	}

	public void setObraSocialFacturada(ObraSocial obraSocialFacturada) {
		this.obraSocialFacturada = obraSocialFacturada;
	}

	public Paciente getPacienteFacturado() {
		return pacienteFacturado;
	}

	public void setPacienteFacturado(Paciente pacienteFacturado) {
		this.pacienteFacturado = pacienteFacturado;
	}

	public Collection<PrestacionBrindadaFacturada> getPrestacionesBrindadasFacturadas() {
		return prestacionesBrindadasFacturadas;
	}

	public void setPrestacionesBrindadasFacturadas(Collection<PrestacionBrindadaFacturada> prestacionesBrindadasFacturadas) {
		this.prestacionesBrindadasFacturadas = prestacionesBrindadasFacturadas;
	}

	@Override
	public BigDecimal getMontoTotal() 
	{
		BigDecimal monto = new BigDecimal(0);
		
		if( anulada ) return monto; 
		
		for( PrestacionBrindadaFacturada p: prestacionesBrindadasFacturadas )
		{
			BigDecimal montoaux = new BigDecimal( p.getCantidad() );
			montoaux = montoaux.multiply(p.getImporteUnitario());
			monto = monto.add( montoaux );
		}

		return monto;
	}
	
	

}
