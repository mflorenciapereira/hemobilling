package ar.uba.fi.hemobilling.exception.domain;

public class HBErrorCreandoReporte extends HemoBillingDomainException {
	
	private static final long serialVersionUID = 1L;

	private static final String CODIGO = "HBErrorCreandoReporte.code";
	
	public HBErrorCreandoReporte( Exception innerEx )
	{
		super.innerException = innerEx;
	}
	
	public HBErrorCreandoReporte()
	{
		super.innerException = null;
	}
	
	@Override
	public String getCode() {
		return CODIGO;
	}


}
