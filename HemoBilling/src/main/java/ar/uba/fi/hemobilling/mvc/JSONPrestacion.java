package ar.uba.fi.hemobilling.mvc;

public class JSONPrestacion{
	
	public JSONPrestacion(String label, String value, String desc) {
		super();
		this.label = label;
		this.value = value;
		this.desc=desc;
	}

	String label;
	
	String value;
	
	String desc;	
}