package com.gmoodle.controllers;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmoodle.models.entity.Users;
import com.gmoodle.models.services.userservice.IUserService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/admin")
public class UsersSystemController {
	
	/*
	 * @RequestBody: Se obtiene el cuerpo de la petición (lo datos que se mandan por POST, PUT, DELETE)
	 * @PathVariable: Se obtienen los datos que se mandan por la url 
	 * ResponseEntity: Se utiliza para retornar cualquier tipo de objeto, de esta manera se puede retornar el objeto
	 * 				   Users o un error en caso de que el usuario no exista (por ejemplo en el metodo de consulta showOne)
	 * 
	 */

	@Autowired
	private IUserService userService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	Date dt;
	
	@GetMapping
	public List<Users> index()
	{
		return userService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> showOne(@PathVariable Long id)
	{
		/*
		 * Se declara el objeto user como null para poder validarlo en caso de que no exista en la base de datos
		 * El objeto response de tipo Map se utiliza para asignar los mensajes en caso de error
		 * DataAccessException: Es propio de spring y se utiliza para manejar un error en la consulta sql, en caso de que
		 * 						exista algún error, este entrara en el catch asignara los mensajes al objeto response y 
		 * 						los retornara con un error 500
		 * Si el usuario es null se asigna el mensaje correspondiente y se regresa un error 404 
		 */
		Users user = null;
		Map<String, Object> response = new HashMap<>();
		try 
		{
			user = userService.findById(id);
		} catch(DataAccessException e)
		{
			response.put("mensaje", "Error al consultar la base de datos");
			response.put("error", e.getMessage() + " : " + e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (user == null)
		{
			response.put("mensaje", "No existe el usuario en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Users>(user, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> CreateUser(@RequestBody Users user)
	{
		//Se obtiene la hora actual del servidor para guardarla en el campo createAt
		dt = new Date(System.currentTimeMillis());
		Users nUser = null;
		Map<String, Object> response = new HashMap<>();
		/*
		 * Se la contraseña del modelo se encripta y se soobrescribe al modelo
		 * guardandolo encriptado en la base de datos 
		 */
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setCreateAt(dt);
		
		try
		{
			nUser = userService.save(user);
		} catch(DataAccessException e)
		{
			response.put("mensaje", "Error al crear el usuario");
			response.put("error", e.getMessage() + " : " + e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Users>(nUser, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> UpdateUser(@RequestBody Users user, @PathVariable Long id)
	{
		//Se obtiene la hora actual del servidor para guardarla en el campo createAt
		dt = new Date(System.currentTimeMillis());
		Map<String, Object> response = new HashMap<>();
		
		/*
		 * Se crea un nuevo objeto para guardar los cambios que se realicen en la base de datos
		 * se toma como referencia el id para sobreescribir los datos actuales
		 */
		Users u = null;
		Users userUploaded = null;
		u = userService.findById(id);
		
		if (u == null)
		{
			response.put("mensaje", "El usuario no se encuentra ne la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		u.setUserName(user.getUserName());
		u.setName(user.getName());
		u.setLastName(user.getLastName());
		u.setEmail(user.getEmail());
		//Se encripa la contraseña para mayor seguridad
		u.setPassword(passwordEncoder.encode(user.getPassword()));
		u.setEnabled(user.getEnabled());
		u.setAddress(user.getAddress());
		u.setPhoneNumber(user.getPhoneNumber());
		u.setBirthDate(user.getBirthDate());
		u.setPhoto(user.getPhoto());
		
		
		u.setUpdateAt(dt);
		try
		{
			userUploaded = userService.save(u);			
		} catch (DataAccessException e)
		{
			response.put("mensaje", "Error al actualizar el usuario en la base de datos, intenta más tarde");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Users>(userUploaded, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public void DeleteUser(@PathVariable Long id)
	{
		userService.delete(id);
	}
}
