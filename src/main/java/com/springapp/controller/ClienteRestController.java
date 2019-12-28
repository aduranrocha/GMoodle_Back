package com.springapp.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springapp.models.entity.Cliente;
import com.springapp.models.services.ClienteService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {
	
	//Se instancia el ClienteService para utilizar sus metodos y acceder a la base de datos
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/clientes")
	public List<Cliente> index()
	{
		return clienteService.findAll();
	}
	
	@GetMapping("/clientes/{id}")
	//Retorna 200 para indicar que se a realizado la consulta correctamente
	//@ResponseStatus(HttpStatus.OK)
	public Cliente show(@PathVariable Long id)
	{
		return clienteService.findById(id);
	}
	
	@PostMapping("/clientes")
	//Retorna 201 para indicar que se a creado el registro correctamente
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente create(@RequestBody Cliente cliente)
	{
		System.out.println(cliente.getNombre());
		return clienteService.save(cliente);
	}
	
	@PutMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente update(@RequestBody Cliente cliente, @PathVariable Long id)
	{
		Cliente clienteActual = clienteService.findById(id);
		
		clienteActual.setEmail(cliente.getEmail());
		clienteActual.setNombre(cliente.getNombre());
		clienteActual.setApellido(cliente.getApellido());
		
		return clienteService.save(clienteActual);
	}
	
	@DeleteMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id)
	{
		clienteService.delete(id);
	}

}
