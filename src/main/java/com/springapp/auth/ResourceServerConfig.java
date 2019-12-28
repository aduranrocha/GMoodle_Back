package com.springapp.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/*
 * Se registra como una clase de configuración
 * Y habilita esta configuración en el proyecto 
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	
	// Se agregan las reglas por el lado de oAuth	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		// Se agregan todas las rutas publicas (sin importar si el usuario esta logueado o no) y se especifica el tipo de metodo
		.antMatchers(HttpMethod.GET, "/api/clientes").permitAll()
		// Se le asignan permisos a todas las rutas faltantes (esta configuración siempre va al final)
		.anyRequest().authenticated();
	}
	
}
