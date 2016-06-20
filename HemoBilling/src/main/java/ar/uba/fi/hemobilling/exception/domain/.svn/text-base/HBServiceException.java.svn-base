package ar.uba.fi.hemobilling.exception.domain;

public class HBServiceException extends HemoBillingDomainException {

	private static final long serialVersionUID = 1L;
	private static final String CODIGO = "HBServiceException.code";

	public HBServiceException( Exception innerEx )
	{
		super.innerException = innerEx;
	}
	
	public HBServiceException()
	{
		super.innerException = null;
	}
	
	@Override
	public String getCode() {
		return CODIGO;
	}

}
