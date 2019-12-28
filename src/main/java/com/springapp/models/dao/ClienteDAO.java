package com.springapp.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.springapp.models.entity.Cliente;

public interface ClienteDAO extends CrudRepository<Cliente, Long> {

}
