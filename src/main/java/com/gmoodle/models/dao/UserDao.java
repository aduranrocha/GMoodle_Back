package com.gmoodle.models.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.gmoodle.models.entity.Users;
/*
 * <User : La entidad con la que se va a trabajar
 * Long> : Su id
 */
public interface UserDao extends CrudRepository<Users, Long> {
	/*
	 * find : Para buscar
	 * By :  Buscar por una condición
	 * Username : Por el campo que se va a buscar (Id, Status etc...)
	 * And o OR :  Para utilizar más de un parametro
	 */
	public Users findByUsername(String username);
	public Optional<Users> findById(Long idUser);
	public Page<Users> findAll(Pageable pageable);
}
