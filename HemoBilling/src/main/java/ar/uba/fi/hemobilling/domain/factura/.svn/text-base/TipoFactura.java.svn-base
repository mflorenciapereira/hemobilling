package ar.uba.fi.hemobilling.domain.factura;


public enum TipoFactura {
	TOTALIZADA("Totalizada", "T"),
	DETALLADA("Detallada", "D");
	
	private String nombre;
	private String tipo;
	
	private TipoFactura(String nombre, String tipo){
		this.nombre = nombre;
		this.tipo = tipo;
	}
	
	public static TipoFactura getExportType(String type){
		
		if(TOTALIZADA.getTipo().equals(type)){
			return TOTALIZADA;
		}
		
		if(DETALLADA.getTipo().equals(type)){
			return DETALLADA;
		}
		
		return null;
	}

	public String getTipo() {
		return tipo;
	}



	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}

