package com.gmoodle.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmoodle.models.dao.RoleDao;
import com.gmoodle.models.entity.Roles;
import com.gmoodle.models.entity.Users;
import com.gmoodle.models.entity.groupClass;
import com.gmoodle.models.services.IGroupClassService;
import com.gmoodle.models.services.userservice.IUserService;

@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/user")
public class UsersSystemController {

	/*
	 * @Autowired: Se inyectan los servicios necesarios para la ejecución del
	 * controlador
	 * 
	 * @RequestBody: Se obtiene el cuerpo de la petición (lo datos que se mandan por
	 * POST, PUT, DELETE)
	 * 
	 * @PathVariable: Se obtienen los datos que se mandan por la url ResponseEntity:
	 * Se utiliza para retornar cualquier tipo de objeto, de esta manera se puede
	 * retornar el objeto Users o un error en caso de que el usuario no exista (por
	 * ejemplo en el metodo de consulta showOne)
	 * 
	 * @Valid: Se utiliza como interceptor para validar que todos los campos cumplan
	 * con las reglas especificadas en el modelo
	 * 
	 * BindingResult: Contiene todos los mensajes de error en la validación
	 * (configurados en el modelo correspondiente)
	 * 
	 */

	@Autowired
	private IUserService userService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private IGroupClassService groupService;

	private final String MSG_PASSWORD = "[PROTECTED]";

	Date dt;

	// @Secured({ "ROLE_ADMIN" })
	@GetMapping
	public List<Users> index() {
		List<Users> myUsers = userService.findAll();
		List<Users> nonePassUser = new ArrayList<Users>();
		for (Users user : myUsers) {
			user.setPassword(MSG_PASSWORD);
			nonePassUser.add(user);
		}

		return nonePassUser;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> showOne(@PathVariable Long id) {
		/*
		 * Se declara el objeto user como null para poder validarlo en caso de que no
		 * exista en la base de datos El objeto response de tipo Map se utiliza para
		 * asignar los mensajes en caso de error DataAccessException: Es propio de
		 * spring y se utiliza para manejar un error en la consulta sql, en caso de que
		 * exista algún error, este entrara en el catch asignara los mensajes al objeto
		 * response y los retornara con un error 500 Si el usuario es null se asigna el
		 * mensaje correspondiente y se regresa un error 404
		 */
		Users user = null;
		Map<String, Object> response = new HashMap<>();
		try {
			user = userService.findById(id);
		} catch (DataAccessException e) {
			response.put("message", "Error: Connecting with DB");
			response.put("error", e.getMessage() + " : " + e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (user == null) {
			response.put("message", "The user with ID: ".concat(id.toString().concat(" doesn't exist")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		user.setPassword(MSG_PASSWORD);
		return new ResponseEntity<Users>(user, HttpStatus.OK);
	}
	/*
	 * Method that will 
	 * @return a list of all the admin
	 */
	@GetMapping("/admin")
	public ResponseEntity<?> showAdmin(){
		Map<String,Object> response = new HashMap<>();
		List<Users> userList = userService.findAll();
		List<Users> adminList = new ArrayList<>();
		try {
			for(Users u : userList) {
				if(u.getRoles().get(0).getName().equals("ROLE_ADMIN")) {
					u.setPassword(MSG_PASSWORD);
					adminList.add(u);
				}
			}
		}catch (DataAccessException e) {
			response.put("message", "Error: Connecting with DB");
			response.put("error", e.getMessage() + " : " + e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("admin", adminList);
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	/*
	 * Method that will 
	 * @return a list of all the students
	 */
	@GetMapping("/students")
	public ResponseEntity<?> showStudent() {
		Map<String, Object> response = new HashMap<>();
		List<Users> userList = userService.findAll();
		List<Users> studentList = new ArrayList<>();
		try {
			for (Users u : userList) {
				if (u.getRoles().get(0).getName().equals("ROLE_STUDENT")) {
					u.setPassword(MSG_PASSWORD);
					studentList.add(u);
				}
			}
		} catch (DataAccessException e) {
			response.put("message", "Error: Connecting with DB");
			response.put("error", e.getMessage() + " : " + e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("students", studentList);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	/*
	 * Method that will 
	 * @return a list of all the teacher
	 */
	@GetMapping("/teachers")
	public ResponseEntity<?> showTeacher(){
		Map<String,Object> response = new HashMap<>();
		List<Users> userList = userService.findAll();
		List<Users> teacherList = new ArrayList<>();
		try {
			for(Users u: userList) {
				if(u.getRoles().get(0).getName().equals("ROLE_TEACHER")) {
					u.setPassword(MSG_PASSWORD);
					teacherList.add(u);
				}
			}
		}catch (DataAccessException e) {
			response.put("message", "Error: Connecting with DB");
			response.put("error", e.getMessage() + " : " + e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("teacher", teacherList);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/username/{username}")
	public ResponseEntity<?> showByUsername(@PathVariable String username) {
		Users user = null;
		Map<String, Object> response = new HashMap<>();
		try {
			user = userService.findByUsername(username);
		} catch (DataAccessException e) {
			response.put("message", "Error: Connecting with DB");
			response.put("error", e.getMessage() + " : " + e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (user == null) {
			response.put("message", "The user with Username: ".concat(username.toString().concat(" doesn't exist")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		user.setPassword(MSG_PASSWORD);
		return new ResponseEntity<Users>(user, HttpStatus.OK);
	}

	/*
	 * @GetMapping("/enrolStudent") public ResponseEntity<?> availableGroups(){
	 * groupClass newGroup = null; for(groupClass g:) return null;
	 * 
	 * }
	 */
	@Secured({ "ROLE_STUDENT" })
	@PutMapping("/enrolStudent/{id}")
	public ResponseEntity<?> enrolStundent(@RequestBody groupClass group, BindingResult result, @PathVariable Long id) {

		// Obtener tu objeto groupClass
		groupClass existGroup = null;
		Users existUser = null;
		Users updated = null;
		groupClass newGroup = null;
		Map<String, Object> response = new HashMap<>();

		existGroup = groupService.findById(group.getIdGroup());
		existUser = userService.findById(id);

		if (existGroup == null) {
			response.put("error", "Group does not exist in DB");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			// return error
		} else if (existUser == null) {
			response.put("error", "User does not exist in DB");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (group.getEnrolmentKey() != existGroup.getEnrolmentKey()) {
			response.put("error", "The given key does not match");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		existUser.setGroup(newGroup);

		newGroup = groupService.findById(id);
		if (newGroup.getNumberMax() >= newGroup.getCountNumber()) {
			newGroup.setCountNumber((byte) (newGroup.getCountNumber() + 1));
		} else if (newGroup.getNumberMax() <= newGroup.getCountNumber()) {
			response.put("error", "The group it is already full");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}

		updated = userService.save(existUser);

		response.put("message", "The user was added with success");
		response.put("user", updated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_TEACHER", "ROLE_STUDENT" })
	@PostMapping
	public ResponseEntity<?> CreateUser(@Valid @RequestBody Users user, BindingResult result) {

		// Se obtiene la hora actual del servidor para guardarla en el campo createAt
		dt = new Date(System.currentTimeMillis());
		Users nUser = null;
		Map<String, Object> response = new HashMap<>();

		/*
		 * Validamos si existe un error al momento de recibir los datos, en caso de ser
		 * así se recorren todos los elementos para obtener todos los errores uno por
		 * uno y se returnan junto a un error 400
		 */
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(e -> e.getField() + " " + e.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		// Se crea un objeto con el mismo tipo de dato del modelo en la propiedad Roles
		// para realizar la relación
		List<Roles> roles = new ArrayList<>();

		// Se obtiene la contraseña enviada por el usuario y se encripta para mayor
		// seguridad
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setCreateAt(dt);

		/*
		 * Desde el frontend por json se recibe la propiedad roles como un array de un
		 * solo elemento tipo json el cual contiene un unico campo llamado "name" y que
		 * contiene el nombre del rol para obtenerlo posteriormente y realizar la
		 * relación en la base de datos Pendiente Options
		 */
		Optional<Roles> userRol = roleDao.findByName(user.getRoles().get(0).getName());
		roles.add(userRol.get());

		/*
		 * Se envia el atributo para posteriormente guardarlo en la tabla intermediaria
		 * con las anotaciones @ManyToMany y @JoinTables
		 */
		user.setRoles(roles);

		/*
		 * Se intenta guardar el usuario, en caso de error se regresa un fallo se
		 * ejecuta el catch y retorna un error 500 con su respectivo mensaje
		 */
		try {
			nUser = userService.save(user);
		} catch (DataAccessException e) {
			response.put("message", "Error: insterting user into DB");
			response.put("error", e.getMessage() + " : " + e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		nUser.setPassword(MSG_PASSWORD);
		// Se retorna el usuario con el estatus 201
		return new ResponseEntity<Users>(nUser, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> UpdateUser(@RequestBody Users user, BindingResult result, @PathVariable Long id) {
		// Se obtiene la hora actual del servidor para guardarla en el campo updateAt
		dt = new Date(System.currentTimeMillis());
		Map<String, Object> response = new HashMap<>();
		List<Roles> roles = new ArrayList<>();

		/*
		 * Validamos si existe un error al momento de recibir los datos, en caso de ser
		 * así se recorren todos los elementos para obtener todos los errores uno por
		 * uno y se returnan junto a un error 400
		 */
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(e -> e.getField() + " " + e.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		/*
		 * u: Se utiliza para buscar el usuario a modificar en la base de datos
		 * userUploaded: Se utiliza al momento de ejecutar y guardar los datos
		 * actualizados en caso de error se maneja con la sentencia try
		 */
		Users u = null;
		Users userUploaded = null;
		u = userService.findById(id);

		if (u == null || u.getIsEnabled() == false) {
			response.put("message", "Error: Update fail, the user with ID:  "
					.concat(id.toString().concat(" doesn't exist or is unable")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		Optional<Roles> userRol = roleDao.findByName(user.getRoles().get(0).getName());
		roles.add(userRol.get());
		u.setRoles(roles);

		u.setUsername(user.getUsername());
		u.setName(user.getName());
		u.setLastName(user.getLastName());
		u.setEmail(user.getEmail());
		u.setDegree(user.getDegree());
		// Se encripta la contraseña para mayor seguridad
		u.setPassword(passwordEncoder.encode(user.getPassword()));
		u.setIsEnabled(user.getIsEnabled());
		u.setAddress(user.getAddress());
		u.setPhoneNumber(user.getPhoneNumber());
		u.setBirthDate(user.getBirthDate());
		u.setPhoto(user.getPhoto());
		u.setGender(user.getGender());
		u.setIsDemoUser(user.getIsDemoUser());
		u.setUpdateAt(dt);

		/*
		 * Se intenta realizar la actualización del usuario, en caso de un fallo se
		 * ejecuta el catch y regresa un error 500 con su respectivo mensaje.
		 */
		try {
			userUploaded = userService.save(u);
		} catch (DataAccessException e) {
			response.put("message", "Error: updating user into DB");
			response.put("error", e.getMessage() + " : " + e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		userUploaded.setPassword(MSG_PASSWORD);
		// Se retorna el usuario actualizado con el estatus 200
		return new ResponseEntity<Users>(userUploaded, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> DeleteUser(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		Users user = userService.findById(id);

		/*
		 * Se intenta eliminar el usuario, en caso de fallo se ejecuta el catch y
		 * regresa un error 500 con su respectivo mensaje
		 */

		if (user == null) {
			response.put("error", "The user does not exist in DB");

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			user.setRoles(null);
			userService.save(user);
			userService.delete(id);
		} catch (DataAccessException e) {
			response.put("message", "Error: Connecting with DB");
			response.put("error", e.getMessage() + " : " + e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// Al eliminar correctamente el usuario se retorna un mensaje de exito con un
		// estatus 200
		response.put("message", "The user has been DELETED successfully!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
