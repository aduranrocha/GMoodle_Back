package com.gmoodle.models.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

/* Indicar que es un objeto de persistencia que esta mapeado a una tabla */
@Entity
/*
 * Indica el nombre de la tabla en caso de que se requiera sea diferente al
 * nombre del modelo, por defecto se crea con el nombre del modelo
 */
@Table(name = "users")
public class Users implements Serializable{

	/*
	 * @Id indicar que es una llave primaria
	 * 
	 * @GeneratedValue Indica como se genera el id en la base de datos.
	 * 
	 * @Column Asigna las propiedades del campo en la base de datos unique = true si
	 * el registro debe ser único o false para aceptar campos ducplicados length =
	 * El ancho (número de caracteres de la columna)
	 * 
	 * VALIDADORES:
	 * 
	 * @NotEmpty: Indica que el campo no puede ir vacío
	 * 
	 * @Size: Indica el minimo y máximo de un campo
	 * 
	 * @Email: Valida que el email tenga un formato correcto
	 */

	// Pendiente
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idUser")
	private Long id;

	@Column(unique = true, length = 20)
	@NotEmpty(message = "cannot be empty")
	@Size(min = 4, max = 20, message = "the size must be between 4 and 20 characteres")
	private String username;

	@Column(nullable = false)
	@NotEmpty(message = "cannot be empty")
	private String name;

	@NotEmpty(message = "cannot be empty")
	private String lastName;

	@Column(unique = true)
	@NotEmpty(message = "cannot be empty")
	private String email;

	// Un ancho de 61 caracteres ya que la contraseña se va a cifrar
	@Column(length = 61)
	@Size(min = 8, message = "the minimun characteres is 8")
	@NotEmpty(message = "cannot be empty")
	private String password;

	// true si el usuario esta activo
	private Boolean enabled;

	// true si el usuario se ha graduado
	private boolean gender;

	@NotEmpty(message = "cannot be empty")
	private String address;

	@NotEmpty(message = "cannot be empty")
	private String phoneNumber;

	// @DateTimeFormat(style = "dd/mm/yyyy")
	// Notese la M mayúscula para el mes en el pattern
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date birthDate;

	private String photo;

	// @DateTimeFormat(style = "dd/mm/yyyy")
	// Notese la M mayúscula para el mes en el pattern
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date createAt;

	// @DateTimeFormat(style = "dd/mm/yyyy")
	// Notese la M mayúscula para el mes en el pattern
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date updateAt;

	/*
	 * @ManyToMany: Relación muchos a muchos fecth: carga peresoza cascade: si el
	 * usuario se elimina se van a eliminar todos los roles asignados y si se crea
	 * se van a guardar todos los roles asignados
	 *
	 * @JoinTable name: nombre de la tabla intermedia para la relación joinColumns:
	 * nombre de la foreign de la tabla usuarios inverseJoinColumns: nombre de la
	 * foreign de la tabla roles uniqueConstraints: uniqueConstraints indica que un
	 * usuario solo puede tener un rol, no se puede repetir en la tabla
	 */
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"), uniqueConstraints = {
			@UniqueConstraint(columnNames = { "user_id", "role_id" }) })
	private List<Roles> roles = new ArrayList<>();
	
	/**
	 * Relation OneToMany from User to Course
	 * mappedBy is the table that will produce the relation
	 * The method will 
	 * return a list of the courses
	 * 
	 */
	@OneToMany(mappedBy="users", cascade = CascadeType.ALL)
	private List<Course> course = new ArrayList<>();
		
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public List<Roles> getRoles() {
		return roles;
	}

	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}

}
