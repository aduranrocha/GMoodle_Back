package com.gmoodle.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/* Indicar que es un objeto de persistencia que esta mapeado a una tabla */
@Entity
/*
 * Indica el nombre de la tabla en caso de que se requiera sea diferente al nombre del modelo,
 * por defecto se crea con el nombre del modelo  
 */
@Table(name = "roles")
public class Roles implements Serializable{

	//Pendiente
	private static final long serialVersionUID = 1L;
	
	//Para indicar que es la llave primaria
	@Id
	//Indicar la estrategia en como se genera el id, al ser mysql se utiliza GenerationType.IDENTITY
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idRoles")
	private Long id;

	//Nombre unico y ancho de 20 caracteres
	@Column(unique = true, length = 20)
	private String name;
	
	
	/*// Obtener todos los usuarios por rol (bidireccional)
	// Indica por cual columna esta relacionada
	@ManyToMany(mappedBy="roles")
	private List<Users> users;
	
	

	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}*/
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
