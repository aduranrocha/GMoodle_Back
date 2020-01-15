package com.gmoodle.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gmoodle.models.entity.Roles;

public interface IRolesService {
	public List<Roles> findAll();
	// Page the group list
	public Page<Roles> findAll(Pageable pageable);
	public Roles findById(Long id);
	public Roles save(Roles roles);
	public void delete(Long id);
}	
