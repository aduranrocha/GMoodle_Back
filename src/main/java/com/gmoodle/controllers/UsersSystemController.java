package com.gmoodle.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmoodle.models.entity.Users;
import com.gmoodle.models.services.userservice.IUserService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/admin")
public class UsersSystemController {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping
	public List<Users> index()
	{
		return userService.findAll();
	}
	
	@PostMapping("/create")
	public Users createUser(@RequestBody Users user)
	{
		/*
		 * Se la contraseña del modelo se encripta y se soobrescribe al modelo
		 * guardandolo encriptado en la base de datos 
		 */
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userService.save(user);
	}
}
