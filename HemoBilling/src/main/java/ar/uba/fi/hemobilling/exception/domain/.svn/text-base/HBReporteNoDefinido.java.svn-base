package ar.uba.fi.hemobilling.exception.domain;

public class HBReporteNoDefinido extends HemoBillingDomainException {

	private static final long serialVersionUID = 1L;
	
	private static final String CODIGO = "HBReporteNoDefinido.code";
	
	public HBReporteNoDefinido( Exception innerEx )
	{
		super.innerException = innerEx;
	}
	
	public HBReporteNoDefinido()
	{
		super.innerException = null;
	}
	
	@Override
	public String getCode() {
		return CODIGO;
	}

}
