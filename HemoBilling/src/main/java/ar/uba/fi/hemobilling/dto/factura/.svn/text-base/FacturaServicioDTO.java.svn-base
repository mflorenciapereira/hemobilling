package ar.uba.fi.hemobilling.dto.factura;

import org.springframework.util.AutoPopulatingList;

import ar.uba.fi.hemobilling.dto.clientes.ClienteDTO;

public class FacturaServicioDTO 
{
	private Long id;
	
	private String numero;
	
	private String fecha;
	
	private ClienteDTO cliente = new ClienteDTO();
	
	private Boolean anulada;
	
	private boolean fondo=true;

	private AutoPopulatingList items =  new AutoPopulatingList(ServicioFacturadoDTO.class);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public ClienteDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}

	public AutoPopulatingList getItems() {
		return items;
	}

	public void setItems(AutoPopulatingList items) {
		this.items = items;
	}

	public boolean isFondo() {
		return fondo;
	}

	public void setFondo(boolean fondo) {
		this.fondo = fondo;
	}

	public Boolean getAnulada() {
		return anulada;
	}

	public void setAnulada(Boolean anulada) {
		this.anulada = anulada;
	}
	
	

}
