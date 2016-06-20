package ar.uba.fi.hemobilling.domain.usuarios;

import ar.uba.fi.hemobilling.domain.FiltroConsulta;

public class FiltroConsultaUsuarios extends FiltroConsulta 
{
	private  String nombreUsuario; 		//Criterio - Todos
	private String habilitado; 			//Si - No - Todos
	private String nombreCompleto; 		//Criterio - Todos
	private String codRol;	//Alguno de los posibles - Todos 
	
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getHabilitado() {
		return habilitado;
	}
	public void setHabilitado(String habilitado) {
		this.habilitado = habilitado;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getCodRol() {
		return codRol;
	}
	public void setCodRol(String codRol) {
		this.codRol = codRol;
	}
	

}
