package com.gmoodle.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmoodle.models.dao.RoleDao;
import com.gmoodle.models.entity.Roles;

@Service
public class RolesServiceImpl implements IRolesService{
	@Autowired
	private RoleDao rolesDao;
	
	@Override
	public List<Roles> findAll() {
		return (List<Roles>) rolesDao.findAll();
	}

	@Override
	public Page<Roles> findAll(Pageable pageable) {
		return rolesDao.findAll(pageable);
	}

	@Override
	public Roles findById(Long id) {
		return rolesDao.findById(id).orElse(null);
	}

	@Override
	public Roles save(Roles roles) {
		return rolesDao.save(roles);
	}

	@Override
	public void delete(Long id) {
		rolesDao.deleteById(id);
	}

}
