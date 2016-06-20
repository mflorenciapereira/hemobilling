package ar.uba.fi.hemobilling.exception.domain;

public class PrestacionAsociadaPrestacionException extends HemoBillingDomainException {

	private static final long serialVersionUID = 1L;
	private static final String CODIGO = "PrestacionAsociadaPrestacion.code";

	public PrestacionAsociadaPrestacionException( Exception innerEx )
	{
		super.innerException = innerEx;
	}
	
	public PrestacionAsociadaPrestacionException()
	{
		super.innerException = null;
	}
	
	@Override
	public String getCode() {
		return CODIGO;
	}

}
