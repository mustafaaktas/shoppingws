package com.shoppingws.security;

import com.shoppingws.dao.role.RoleDao;
import com.shoppingws.model.base.ResponseStatus;
import com.shoppingws.model.user.User;
import com.shoppingws.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	
	@Autowired
	RoleDao roleDao;
	@Autowired
	private UserServices userService;


	private String doLogin(String username, String password) {
		String status ;
		User critea=new User();
			ResponseStatus rs = userService.login(critea);
			if(ResponseStatus.OK.equals(rs)) {
				status = "OK";
			}else {
				status = rs.getMessage();
			}
			
		return status;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String name 		= authentication.getName();
		String password 	= authentication.getCredentials().toString();
		String loginStatus 	= doLogin(name, password);
				
		if ("OK".equalsIgnoreCase(loginStatus)) {			
			return new UsernamePasswordAuthenticationToken(name, password,roleDao.getUserAuth(name));
		} else {
			throw new BadCredentialsException(loginStatus);
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {

	    return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}