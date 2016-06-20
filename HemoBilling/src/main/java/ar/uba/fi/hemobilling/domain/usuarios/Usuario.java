package ar.uba.fi.hemobilling.domain.usuarios;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
@Table(name="users")
public class Usuario {

	@Id	
	@Column(name = "username")
	private String nombreUsuario;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "name")
	private String nombreCompleto;
	
	@Column(name = "enabled")
	private Boolean habilitado;
	
	@OneToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "authorities", joinColumns = { @JoinColumn(name = "username") }, inverseJoinColumns = { @JoinColumn(name = "authority") })
	private Collection<RolUsuario> roles;

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

	public Collection<RolUsuario> getRoles() {
		return roles;
	}

	public void setRoles(Collection<RolUsuario> roles) {
		this.roles = roles;
	}

	public Boolean getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}

}
