package ar.uba.fi.hemobilling.dto.factura;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;


import org.springframework.util.AutoPopulatingList;

import ar.uba.fi.hemobilling.domain.paciente.Paciente;
import ar.uba.fi.hemobilling.domain.prestaciones.Prestacion;
import ar.uba.fi.hemobilling.dto.BasicDTO;
import ar.uba.fi.hemobilling.dto.obrassociales.ObraSocialDTO;
import ar.uba.fi.hemobilling.dto.paciente.PacienteDTO;

public class FacturaPrestacionDTO extends BasicDTO {
	
	private Long id;
	
	private String numero;
	
	private String fechaEmision;
	
	private Boolean anulada;
	
	private ObraSocialDTO obraSocial = new ObraSocialDTO();
	
	private PacienteDTO paciente = new PacienteDTO();
	
	private boolean fondo=true;
	
	private AutoPopulatingList items =  new AutoPopulatingList(ItemFacturaPrestacionDTO.class);
	
	private AutoPopulatingList itemsNoFacturables =  new AutoPopulatingList(ItemFacturaPrestacionDTO.class);
	
	private String[] seleccionados;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public ObraSocialDTO getObraSocial() {
		return obraSocial;
	}

	public void setObraSocial(ObraSocialDTO obraSocial) {
		this.obraSocial = obraSocial;
	}

	public PacienteDTO getPaciente() {
		return paciente;
	}

	public void setPaciente(PacienteDTO paciente) {
		this.paciente = paciente;
	}
	
	public AutoPopulatingList getItems() {
		return items;
	}

	public void setItems(AutoPopulatingList items) {
		this.items = items;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isFondo() {
		return fondo;
	}

	public void setFondo(boolean fondo) {
		this.fondo = fondo;
	}

	public AutoPopulatingList getItemsNoFacturables() {
		return itemsNoFacturables;
	}

	public void setItemsNoFacturables(AutoPopulatingList itemsNoFacturables) {
		this.itemsNoFacturables = itemsNoFacturables;
	}

	public Boolean getAnulada() {
		return anulada;
	}

	public void setAnulada(Boolean anulada) {
		this.anulada = anulada;
	}

	public String[] getSeleccionados() {
		return seleccionados;
	}

	public void setSeleccionados(String[] seleccionados) {
		this.seleccionados = seleccionados;
	}

	public Collection<Long> obtenerIdsItems() {
		Collection<Long> idsPrestaciones = new ArrayList<Long>();
		Iterator itItems = items.iterator();
		while (itItems.hasNext()) {
			ItemFacturaPrestacionDTO itemDTO = (ItemFacturaPrestacionDTO) itItems
					.next();
			idsPrestaciones.add(itemDTO.getCodigoPrestacion());

		}
		Iterator itItemsNF = itemsNoFacturables.iterator();
		while (itItemsNF.hasNext()) {
			ItemFacturaPrestacionDTO itemDTO = (ItemFacturaPrestacionDTO) itItemsNF
					.next();
			idsPrestaciones.add(itemDTO.getCodigoPrestacion());

		}

		return idsPrestaciones;

	}

	
}
