package com.springapp.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.springapp.models.entity.User;

/*
 * <User : La entidad con la que se va a trabajar
 * Long> : Su id
 */
public interface UserDao extends CrudRepository<User, Long> {
	
	//Consultas personalizadas
	
	/*
	 * find : Para buscar
	 * By :  Buscar por una condición
	 * Username : Por el campo que se va a buscar (Id, Status etc...)
	 * And o OR :  Para utilizar más de un parametro
	 */
	public User findByUsername(String username);
	
	
	/*
	 * Consula más personalizada
	 * ?n : una variable que se va a sustituir por el valor que se envia (si se requieren mas variables
	 *      se envian más parametros
	 */
	//@Query("select u from users u where u.username=?1")
	//public User findByUsernameB(String username);
	
}
