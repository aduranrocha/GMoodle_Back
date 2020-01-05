package com.gmoodle.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.gmoodle.auth.InfoAdicionalToken;
import com.gmoodle.auth.JwtConfig;

/*
 * Se registra como un componente de tipo configuración
 * @EnableAuthorizationServer habilitamos la configuración (la clase)
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter  {
	/*
	 * Se inyecta desde SecutiryConfig.java y esto nos permite utilizar el metodo BCryptPasswordEncoder
	*/ 
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	// Se inyecta desde InfoAdicionalToken.java para poder registrarlo con TokenEnhancerChain
	@Autowired 
	private InfoAdicionalToken infoAdicionalToken;
	
	/*
	 * Se inyecta para que el ServerConfig lo pueda utilizar para el proceso de login
	 * @Qualifier se utiza para especificar el metodo que estamos utilizando en caso de que tengamos más instancias
	 * del authenticationManager, en este caso esta de más ya que solo tenemos una instancia.
	 */
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.allowFormAuthenticationForClients();
		// Para permitir a un usuario loguearse y generar el token
		security.tokenKeyAccess("permitAll()")
		// Para verificar el token generado y solo los usuarios logueado puede acceder a los recursos
		.checkTokenAccess("isAuthenticated()");
	}

	/*
	 * Para autenticarse con las credenciales de la aplicación y no solo con el usuario
	 * se tendria una doble autenticación
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		System.out.println("ClientDetailsServiceConfigurer");
		clients.inMemory()
		// Nombre de la aplicación que se va a conectar al backend
		.withClient("angularapp")
		// La clave con la que la app se va a conectar y se encriptara con passwordEncoder
		.secret(passwordEncoder.encode("12345"))
		//.secret("secret")
		// Los permisos que va a tener nuestra aplicación en nuestro backend 
		.scopes("read", "write")
		// Asginar el tipo de consesion que tendra la aplicación, del token y como se obtendra el token
		// se utiliza password ya que se requiere validar un username y un password en la base de datos
		.authorizedGrantTypes("password", "refresh_token")
		// Especificar el tiempo de expiración del token
		.accessTokenValiditySeconds(3600)
		// Especificar el tiempo de expiración del refresh token
		.refreshTokenValiditySeconds(3600);
	}

	/*
	 *  Se encarga del proceso de validar el token para que el usuario pueda acceder a los recursos
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		//Se registra el componente InfoAdicionalToken
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken, accessTokenConverter()));
		
		//Se utiliza el authenticationManager inyectado más arriba
		endpoints.authenticationManager(authenticationManager)
		//Es opcional ya que internamente se crea el objeto TokenStore
		.tokenStore(tokenStore())
		.accessTokenConverter(accessTokenConverter())
		.tokenEnhancer(tokenEnhancerChain);
	}

	/*
	 * Se esta creando el JwtTokenStore
	 */
	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	/*
	 *  Almacena los datos del usuario (nombre, email, rol etc...)
	 */
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		
		//Se envia la clave privada para firmar el token
		jwtAccessTokenConverter.setSigningKey(JwtConfig.RSA_PRIVADA);
		//Se envia la clave publica para verificar la firma
		jwtAccessTokenConverter.setVerifierKey(JwtConfig.RSA_PUBLICA);
		
		return jwtAccessTokenConverter;
	}
}
