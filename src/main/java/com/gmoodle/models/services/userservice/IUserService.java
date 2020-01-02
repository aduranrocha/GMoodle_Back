package com.gmoodle.models.services.userservice;

import com.gmoodle.models.entity.Users;

public interface IUserService {
	public Users findByUsername(String username);
}
