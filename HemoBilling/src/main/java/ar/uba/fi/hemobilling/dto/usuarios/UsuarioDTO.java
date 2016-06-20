package ar.uba.fi.hemobilling.dto.usuarios;

public class UsuarioDTO 
{
	private String nombreUsuario;
	private String password;
	private String nombreCompleto;
	private Boolean habilitado;
	
	private String codigosRolesSerializados;
	private String descripRolesSerializados;
	
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public Boolean getHabilitado() {
		return habilitado;
	}
	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}
	public String getCodigosRolesSerializados() {
		return codigosRolesSerializados;
	}
	public void setCodigosRolesSerializados(String codigosRolesSerializados) {
		this.codigosRolesSerializados = codigosRolesSerializados;
	}
	public String getDescripRolesSerializados() {
		return descripRolesSerializados;
	}
	public void setDescripRolesSerializados(String descripRolesSerializados) {
		this.descripRolesSerializados = descripRolesSerializados;
	}

}
