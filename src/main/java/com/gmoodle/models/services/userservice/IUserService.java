package com.gmoodle.models.services.userservice;

import java.util.List;

import com.gmoodle.models.entity.Users;

public interface IUserService {
	public List<Users> findAll();
	public Users findByUserName(String username);
	public Users findById(Long id);
	public Users save(Users user);
	public void delete(Long id);
}
