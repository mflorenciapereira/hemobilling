package ar.uba.fi.hemobilling.exception.domain;

public class HBObjectExistsException extends HemoBillingDomainException {

	private static final long serialVersionUID = -7591073509406079543L;
	private static final String CODIGO = "HBObjectExistsException.code";

	public HBObjectExistsException( Exception innerEx )
	{
		super.innerException = innerEx;
	}
	
	public HBObjectExistsException()
	{
		super.innerException = null;
	}
	
	@Override
	public String getCode() {
		return CODIGO;
	}


}
