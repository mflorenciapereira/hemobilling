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

import ar.uba.fi.hemobilling.domain.clientes.Cliente;

@Entity
@Table(name="facturasservicios")
public class FacturaServicio extends Factura {

    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name="codigoCliente")
	private Cliente clienteFacturado;
	
	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "asociacionServiciosFacturados", joinColumns = { @JoinColumn(name = "id_factura") }, inverseJoinColumns = { @JoinColumn(name = "id_servicioFacturado") })
	private Collection<ServicioFacturado> serviciosFacturados;

	public Collection<ServicioFacturado> getServiciosFacturados() {
		return serviciosFacturados;
	}

	public void setServiciosFacturados(Collection<ServicioFacturado> serviciosFacturados) {
		this.serviciosFacturados = serviciosFacturados;
	}

	public Cliente getClienteFacturado() {
		return clienteFacturado;
	}

	public void setClienteFacturado(Cliente clienteFacturado) {
		this.clienteFacturado = clienteFacturado;
	}

	@Override
	public BigDecimal getMontoTotal() 
	{
		BigDecimal monto = new BigDecimal(0);
		
		if( anulada ) return monto; 
		
		for( ServicioFacturado s: serviciosFacturados )
		{
			if( s.getCantidad()==null )
			{
				monto = monto.add( s.getImporteUnitario() );
			}
			else
			{
				BigDecimal montoaux = new BigDecimal( s.getCantidad() );
				montoaux = montoaux.multiply(s.getImporteUnitario());
				monto = monto.add( montoaux );
			}
		}

		return monto;
	}
	
	
}
