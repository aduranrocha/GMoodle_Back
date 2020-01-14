package com.gmoodle.auth;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

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
		
		/*
		 * SIEMPRE SE EMPIEZAN CON LAS RUTAS MÁS ESPECIFICAS Y AL FINAL LAS MÁS GENERICAS
		 * Se agregan todas las rutas publicas (sin importar si el usuario esta logueado o no) y se especifica el tipo de metodo
		 * 
		 * /images/**: Se les da acceso a la ruta donde se encuentran todas las imagenes en el servidor 
		 * (/src/main/resources/images/**)
		 */
		.antMatchers("/").permitAll()
		.antMatchers(HttpMethod.GET, "/files", "/files/profiles/**")
		.hasAnyRole("ROLE_ADMIN", "ROLE_TEACHER", "ROLE_STUDENT")
		//.antMatchers(HttpMethod.GET, "/admin", "/admin/**").permitAll()
		.anyRequest().authenticated()
		.and().cors().configurationSource(corsConfigurationSource());
	}
	
	
	/*
	 * 
	 * PENDIENTE ULTIMO VIDEO CARPETA 15
	 * PENDIENTE ULTIMO VIDEO CARPETA 15
	 * 
	 * */
	@Bean
	public CorsConfigurationSource corsConfigurationSource()
	{
		CorsConfiguration config = new CorsConfiguration();
		// Se configura el origen que va a poder acceder al backend
		config.setAllowedOrigins(Arrays.asList("http://localhost:4200", "*"));
		// Los metodos que se van a utilizar
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		// Se permiten las credenciales para las autenticaciones
		config.setAllowCredentials(true);
		// Se habilitan las cabeceras
		config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
		
		//Para permitir el acceso a todas las rutas
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		
		return source;
	}
	
	@Bean
	public FilterRegistrationBean<CorsFilter> crosFilter()
	{
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter> 
		(new CorsFilter(corsConfigurationSource()));
		
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		
		return bean;
	}
	
}
