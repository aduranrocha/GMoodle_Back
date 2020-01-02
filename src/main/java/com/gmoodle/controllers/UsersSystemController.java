package com.gmoodle.controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@PostMapping("/create")
	public Users CreateUser(@RequestBody Users user)
	{
		//Se obtiene la hora actual del servidor para guardarla en el campo createAt
		dt = new Date(System.currentTimeMillis());
		/*
		 * Se la contraseña del modelo se encripta y se soobrescribe al modelo
		 * guardandolo encriptado en la base de datos 
		 */
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setCreateAt(dt);
		
		return userService.save(user);
	}
	
	@PutMapping("/{id}")
	public Users UpdateUser(@RequestBody Users user, @PathVariable Long id)
	{
		//Se obtiene la hora actual del servidor para guardarla en el campo createAt
		dt = new Date(System.currentTimeMillis());

		/*
		 * Se crea un nuevo objeto para guardar los cambios que se realicen en la base de datos
		 * se toma como referencia el id para sobreescribir los datos actuales
		 */
		Users u = userService.findById(id);
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
		
		return userService.save(u);
	}
	
	@DeleteMapping("/{id}")
	public void DeleteUser(@PathVariable Long id)
	{
		userService.delete(id);
	}
}
