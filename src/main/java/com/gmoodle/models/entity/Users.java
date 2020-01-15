package com.gmoodle.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
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
	private Long idUser;

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
	private Boolean isEnabled;

	// true si el usuario se ha graduado
	private boolean gender;

	@NotEmpty(message = "cannot be empty")
	private String address;

	@NotEmpty(message = "cannot be empty")
	private String phoneNumber;
	
	@NotEmpty(message = "cannot be empty")
	private String degree;

	// @DateTimeFormat(style = "dd/mm/yyyy")
	// Notese la M mayúscula para el mes en el pattern
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date birthDate;
	
	private String photo;

	// @DateTimeFormat(style = "dd/mm/yyyy")
	// Notese la M mayúscula para el mes en el pattern
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date createAt;
	
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}
	// @DateTimeFormat(style = "dd/mm/yyyy")
	// Notese la M mayúscula para el mes en el pattern
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date updateAt;
	
	private boolean isDemoUser;

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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idGroup")
    private groupClass group;
	
	/*
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "users_members", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "group_id"), uniqueConstraints = {
			@UniqueConstraint(columnNames = { "user_id", "group_id" }) })
	private List<groupClass> group = new ArrayList<>();
	/**
	 * Relation OneToMany from User to Course
	 * mappedBy is the table that will produce the relation
	 * The method will 
	 * return a list of the courses
	 * 
	 */
	@OneToMany(mappedBy="users", cascade = CascadeType.ALL)
	private List<Course> course = new ArrayList<>();
	/**
	 * Relation OneToMany from User to Document
	 * mappedBy is the table that will produce the relation
	 * The method will 
	 * return a list of the Documents
	 * 
	 */
	@OneToMany(mappedBy="users", cascade = CascadeType.ALL)
    private List<Document> document = new ArrayList<>();
	/**
	 * Relation OneToMany from User to Activity
	 * mappedBy is the table that will produce the relation
	 * The method will 
	 * return a list of the Activity
	 * 
	 */
	@OneToMany(mappedBy="users", cascade = CascadeType.ALL)
    private List<Activity> activity = new ArrayList<>();
	
	
	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
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

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
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

	public boolean getGender() {
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

	/**
	 * @return the isDemoUser
	 */
	public boolean getIsDemoUser() {
		return isDemoUser;
	}

	/**
	 * @param isDemoUser the isDemoUser to set
	 */
	public void setIsDemoUser(boolean isDemoUser) {
		this.isDemoUser = isDemoUser;
	}

	/**
	 * @return the degree
	 */
	public String getDegree() {
		return degree;
	}

	/**
	 * @param degree the degree to set
	 */
	public void setDegree(String degree) {
		this.degree = degree;
	}
	
	public List<Roles> getRoles() {
		return roles;
	}

	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}
	
	public groupClass getGroup() {
		return group;
	}
	
	public void setGroup(groupClass group) {
		this.group = group;
	}
	
	public List<Map<String, Object>> getCourse() {
		List<Map<String, Object>> courseList = new ArrayList<>();
		for (Course c : course)
		{
			Map<String, Object> myMap = new HashMap<>();
			myMap.put("idCourse", c.getIdCourse());
			myMap.put("nameCourse", c.getNameCourse());
			
			courseList.add(myMap);
		}
		return courseList;
	}

	public void setCourse(List<Course> course) {
		this.course = course;
	}
	
	public List<Map<String,Object>> getDocument() {
		
		List<Map<String,Object>> myDocumentList = new ArrayList<>();
		
		for (Document d : document){
			Map<String,Object> myDocumentMap = new HashMap<>();
			myDocumentMap.put("id", d.getIdDocument());
			myDocumentMap.put("path", d.getPathDoucument());
			
			myDocumentList.add(myDocumentMap);
		}
		
		return myDocumentList;
		
		//return document;
	}

	public void setDocument(List<Document> document) {
		this.document = document;
	}
	
	public List<Map<String,Object>> getActivity() {
		List<Map<String,Object>> myActivityList = new ArrayList<>();
		
		for(Activity a : activity) {
			Map<String,Object> myActivityMap = new HashMap<>();
			myActivityMap.put("idActivity",a.getIdActivity());
			myActivityMap.put("titleActivity", a.getTitleActivity());
			
			myActivityList.add(myActivityMap);
		}
		return myActivityList;
	}

	public void setActivity(List<Activity> activity) {
		this.activity = activity;
	}
	
}
