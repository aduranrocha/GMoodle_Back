package com.gmoodle.models.services.userservice;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gmoodle.models.entity.Users;

public interface IUserService {
	public List<Users> findAll();
	public Page<Users> findAll(Pageable pageable);
	public Users findByUsername(String username);
	public Users findById(Long id);
	public Users save(Users user);
	public void delete(Long id);
}
