package ar.uba.fi.hemobilling.exception.domain;

public class HBPrestacionesFacturaException extends HemoBillingDomainException  {

	private static final long serialVersionUID = 1L;
	private static final String CODIGO = "HBPrestacionesFacturaException.code";
	
	@Override
	public String getCode() {
		return CODIGO;
	}

}
