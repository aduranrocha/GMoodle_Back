package com.springapp.models.services;

import java.util.List;

import com.springapp.models.entity.Cliente;

public interface ClienteService {
	public List<Cliente> findAll();
	
	public Cliente findById(Long Id);
	
	public Cliente save(Cliente cliente);
	
	public void delete(Long id);
}
