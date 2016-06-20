package ar.uba.fi.hemobilling.reportes.sintesis;

public class DatoListaReporteSintesisHC 
{
	private String fecha;
	private String prestacion;
	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getPrestacion() {
		return prestacion;
	}
	public void setPrestacion(String prestacion) {
		this.prestacion = prestacion;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object otroArg) {
		DatoListaReporteSintesisHC otro=(DatoListaReporteSintesisHC) otroArg;
		if( otro==null ) return false;
		
		return((otro.getFecha().compareTo(this.getFecha())==0)&&(otro.getPrestacion().compareTo(this.prestacion)==0));
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	
}
