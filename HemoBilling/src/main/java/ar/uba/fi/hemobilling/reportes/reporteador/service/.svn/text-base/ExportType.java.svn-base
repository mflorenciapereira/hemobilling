package ar.uba.fi.hemobilling.reportes.reporteador.service;


/**
 * Enum que representa los tipos de archivos
 * a los que se puede exportar. 
 *	
 */
public enum ExportType {
	TXT("txt", "txt"),
	CSV("csv", "csv"),
	EXCEL("excel", "xls"),
	PDF("pdf", "pdf");
	
	private String extension;
	private String typeName;
	
	/**
	 * Constructor privado del Enum.
	 * @param type tipo de exportacion.
	 * @param extension extension asociada al tipo de exportacion.
	 */
	private ExportType(String type, String extension){
		this.typeName = type;
		this.extension = extension;
	}
	
	/**
	 * 
	 * Retorna la extension asociada al tipo de exportacion.
	 *
	 * @return extension asociada al tipo de exportacion.
	 */
	public String getExtension() {
		return extension;
	}
	
	/**
	 * 
	 * Retorna el nombre del tipo de exportacion.
	 *
	 * @return nombre del tipo de exportacion.
	 */
	public String getTypeName() {
		return typeName;
	}
	
	/**
	 * 
	 * Retorna el valor del Enum correspondiente al tipo de exportacion.
	 *
	 * @param type tipo de exportacion buscada.
	 * @return valor del Enum correspondiente al tipo de exportacion.
	 */
	public static ExportType getExportType(String type){
		
		if(TXT.getTypeName().equals(type)){
			return TXT;
		}
		
		if(CSV.getTypeName().equals(type)){
			return CSV;
		}
		
		if(EXCEL.getTypeName().equals(type)){
			return EXCEL;
		}
		
		if(PDF.getTypeName().equals(type)){
			return PDF;
		}
		
		return null;
	}
}



