package com.gmoodle.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
 * Se registra la clase como tipo configuración (componente spring)
 * 
 * @EnableGlobalMethodSecurity: Se activa para proteger las rutas en el controlador mediante la anotación
 */
@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SecuritySpringConfig extends WebSecurityConfigurerAdapter {

	
	//Inyectamos el servicio
	@Autowired
	private UserDetailsService userService;
	
	/*
	 *  BCryptPasswordEncoder: El metodo de spring para encriptar las contraseñas
	 *  @Bean: Se registra como un componente de spring (Se utiliza @Bean ya que retorna un metodo) ya que se va a utilizar más tarde
	 *         (Se registra en el contenedor de spring, se puede inyectar con Autowired y utilizarlo en un controlador, servicio ETC...)
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A);
	}

	
	/*
	 * Se soobrescribe el metodo
	 * Se inyecta el componente  AuthenticationManagerBuilder via argumento
	 */
	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/* 
		 * Se registra el service
		 * Se encripta la contraseña para mayor seguridad
		 */
		auth.userDetailsService(this.userService).passwordEncoder(passwordEncoder());
	}
	
	/*
	 * Se registra como un componente de spring, retorna el metodo padre authenticationManager
	 * @Bean("authenticationManager") se le asigna el nombre con el que se va a registrar el metodo por defecto es el nombre
	 * del metodo
	 */
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	// Se crean las reglas por el lado de Spring
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		// Se le asignan permisos a todas las rutas faltantes (esta configuración siempre va al final)
		.anyRequest().authenticated()
		// Se concatena otra propiedad
		.and()
		// Se deshabilita la protección contra csrf ya que angualr es un frontend separado
		.csrf().disable()
		// Se deja sin estado a las sesiones ya que las guardamos en un token en el lado del cliente
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
}
