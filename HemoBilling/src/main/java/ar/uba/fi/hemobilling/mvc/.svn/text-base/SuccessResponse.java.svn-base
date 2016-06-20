package ar.uba.fi.hemobilling.mvc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("response")
public class SuccessResponse extends Response {

	/** Lista de datos que se deben presentar en la vista. */
	private List<Object> data;

	public SuccessResponse() {
		super();
	}
	
	private List<Object> getData() {
        if(this.data == null) {
            this.data = new ArrayList<Object>();
        }	    
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}

	public void addData(Object o) {
	    this.getData().add(o);
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addAllData(Collection o) {
		this.getData().addAll(o);
	}

	
    
}