package com.springapp.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springapp.models.dao.UserDao;
import com.springapp.models.entity.User;

/*
 * Se registra como un componente de spring de tipo service
 * 
 */
@Service
/*
 * UserDetailsService : Para trabajar con cualquier tipo de proveedor (JPA ETC...) para trabajar con login
 * 						y construir el usuario
 */
public class UserService implements UserDetailsService {
	
	private Logger logger = LoggerFactory.getLogger(UserService.class);

	//Se inyecta la interfaz
	@Autowired
	private UserDao userDao;

	//Para sobreescribir
	@Override
	//Pendiente (Solo para lectura)
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		//Obtener el usuario a traves del username
		User user = userDao.findByUsername(username);
		
		// Se comprueba si el usuario es null
		if (user == null)
		{
			// Se escribe un mensaje de error en el log en caso de que el usuario no exista
			logger.error("El usuario " + user.getUsername() + " no existe en el sistema!");
			System.out.println("El ususario no existe en el sistema");
			// Se lanza excepción de error en caso de que el usuario no exista
			throw new UsernameNotFoundException("El usuario " + user.getUsername() + " no existe en el sistema!");
		}
		
		
		/*
		 * Se obtienen los roles 
		 */
		List<GrantedAuthority> authorities = user.getRoles()
				// Convertimos a stream para obtener cada elemento y convertirlo a GrantedAuthority
				.stream()
				// (tipo for each) se convierte cada elemento a SimpleGrantedAuthority
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				// Escribir cada uno de los roles en el archivo log tipo info
				.peek(authority -> logger.info("Role: " + authority.getAuthority()))
				// Convertimos a tipo colección (Lista)
				.collect(Collectors.toList());

		/*
		 * Se retorna una instancia de User (Se importa de spring security)
		 */
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword()
				, user.getEnabled(), true, true, true, authorities);
	}

}
