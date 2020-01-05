package com.gmoodle.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.gmoodle.models.entity.Users;
import com.gmoodle.models.services.userservice.IUserService;;

/*
 * La clase se utiliza como un potenciador para poder agregar más atributos al token 
 */
@Component
public class InfoAdicionalToken implements TokenEnhancer {
	
	@Autowired
	private IUserService userService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> info = new HashMap<>();
		
		/*
		 * Se obtienen los datos del usuario mediante la autenticación. 
		 */
		Users user = userService.findByUsername(authentication.getName());
		
		info.put("nombre", user.getName());
		info.put("apellido", user.getLastName());
		info.put("email", user.getEmail());
		
		
		/*
		 * Se utiliza para agregar información adicional al token, se utiliza DefaultOAuth2AccessToekn ya que OAuth2AccessToken
		 * es una interfaz que no puede utilizar el metodo setAdditionalInformation, se castea con los parentesis para utilizar
		 * dicho metodo.
		 */
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		
		return accessToken;
	}

}
