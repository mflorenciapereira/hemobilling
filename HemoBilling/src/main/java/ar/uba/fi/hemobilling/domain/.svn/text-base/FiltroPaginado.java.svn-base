package ar.uba.fi.hemobilling.domain;

public class FiltroPaginado 
{
	protected Integer cantTotalRegs = null;
	
	protected Integer regPorPagina = null;
	protected Integer numeroPaginaActual = null;
	
	protected Integer cantMaxPaginas = null;
	
	public Integer getRegPorPagina() {
		return regPorPagina;
	}
	public void setRegPorPagina(Integer regPorPagina) {
		this.regPorPagina = regPorPagina;
	}
	public Integer getCantTotalRegs() {
		return cantTotalRegs;
	}
	public void setCantTotalRegs(Integer cantTotalRegs) {
		this.cantTotalRegs = cantTotalRegs;
		
		if( regPorPagina!=null )
		{
			cantMaxPaginas = (Integer)((cantTotalRegs/regPorPagina)+1);
			if( numeroPaginaActual!=null && numeroPaginaActual>cantMaxPaginas )
			{
				numeroPaginaActual = cantMaxPaginas;
			}
		}
	}
	public Integer getNumeroPaginaActual() {
		return numeroPaginaActual;
	}
	public void setNumeroPaginaActual(Integer numeroPaginaActual) {
		this.numeroPaginaActual = numeroPaginaActual;
	}
	public Integer getCantMaxPaginas() {
		return cantMaxPaginas;
	}
	public void setCantMaxPaginas(Integer cantMaxPaginas) {
		this.cantMaxPaginas = cantMaxPaginas;
	}
	

}
