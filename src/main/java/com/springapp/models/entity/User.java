package com.springapp.models.entity;

/*Serializable permite convertir de objeto java a json*/
import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/* Indicar que es un objeto de persistencia que esta mapeado a una tabla */
@Entity
/*
 * Indica el nombre de la tabla en caso de que se requiera sea diferente al nombre del modelo,
 * por defecto se crea con el nombre del modelo  
 */
@Table(name = "users")
public class User implements Serializable {

	//Pendiente
	private static final long serialVersionUID = 1L;
	
	//Para indicar que es la llave primaria
	@Id
	//Indicar la estrategia en como se genera el id, al ser mysql se utiliza GenerationType.IDENTITY
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//El usuario es unico y con un ancho de 20 caracteres
	@Column(unique = true, length = 20)
	private String username;
	//Un ancho de 60 caracteres ya que la contraseña se va a cifrar
	@Column(length = 60)
	private String password;
	
	//Para validar si el usuario esta activo o no
	private Boolean enabled;

	/*
	 * Relación muchos a muchos
	 * fecth: carga peresoza
	 * cascade: si el usuario se elimina se van a eliminar todos los roles asignados
	 * y si se crea se van a guardar todos los roles asignados
	*/
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	/*
	 * name: nombre de la tabla intermedia para la relación
	 * joinColumns: nombre de la foreign de la tabla usuarios
	 * inverseJoinColumns: nombre de la foreign de la tabla roles
	 * uniqueConstraints:  uniqueConstraints indica que un usuario solo puede tener un rol, no se puede repetir en la tabla
	 */
	@JoinTable(name="users_roles", joinColumns = @JoinColumn(name = "user_id")
	, inverseJoinColumns = @JoinColumn(name = "role_id")
	, uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "role_id" }) })
	private List<Role> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
