package ar.uba.fi.hemobilling.exception.domain;

public class HBObjectNotExistsException extends HemoBillingDomainException {

	private static final long serialVersionUID = 1L;
	private static final String CODIGO = "HBObjectNotExistsException.code";

	public HBObjectNotExistsException( Exception innerEx )
	{
		super.innerException = innerEx;
	}
	
	public HBObjectNotExistsException()
	{
		super.innerException = null;
	}
	
	@Override
	public String getCode() {
		return CODIGO;
	}

}
