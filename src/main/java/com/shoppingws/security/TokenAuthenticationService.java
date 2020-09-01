package com.shoppingws.security;

import com.vor.jwtutil.JWTUtil;
import com.vor.onlinecheckin.model.Token;
import com.vor.onlinecheckin.model.user.Role;
import com.vor.onlinecheckin.model.user.User;
import com.vor.onlinecheckin.util.Constants;
import com.vor.onlinecheckin.util.Helper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;


public class TokenAuthenticationService {
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";


	private TokenAuthenticationService() {

	}
	
	public static String generateJWTToken(User kullanici) {
		if(kullanici==null || kullanici.getUserName()==null || kullanici.getRoles()==null || kullanici.getRoles().isEmpty()) {
			return "";
		}
		List<String> roller = new ArrayList<>();
		for(Role rol:kullanici.getRoles()) {
			roller.add(rol.getRoleCode());
		}
		return generateJWTToken(kullanici.getUserName(), roller);
	}
	public static String generateJWTToken( String username,List<String> roller) {
		Map<String, String> claims = new HashMap<>();
		claims.put("username", username);
		claims.put("roles",Arrays.toString(roller.toArray()).replace("[", "").replace("]",""));

		JWTUtil jwtUtil = JWTUtil.create().setEnvironment(Constants.APP_ACTIVE_PROFILE).build();//dk
		String token = "";
		try {
			token = jwtUtil.generateTokenWithJWT(claims);
		} catch (UnsupportedEncodingException e) {
			Helper.errorLogger(TokenAuthenticationService.class, e);
		}
		return token;
	}
	
	public static void addAuthentication(HttpServletResponse res, String username, List<String> roller) {
		
		String token = generateJWTToken(username, roller);		
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + token);
		try {
			Token _token=new Token();
			_token.setAccessToken(token);
			_token.setTokenExpires(15);
			res.getOutputStream().println(Helper.convert2Json(_token));
			res.setContentType("application/json");

		} catch (IOException e) {			
		}
	}

	public static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);

		if (token != null) {
			token = token.replace(TOKEN_PREFIX,"").trim();
			String user         = JWTUtil.getDataFromKey(token,"username");
			String strRoller    = JWTUtil.getDataFromKey(token,"roles");

			List<Role> roller = new ArrayList<>();
			if(strRoller!=null) {
				String[] arryRol = strRoller.split(",");
				for(String strRol:arryRol){
					roller.add(new Role(0,"ROLE_"+strRol.trim().toUpperCase(),strRol.trim().toUpperCase()));
				}
			}
			return user != null ? new UsernamePasswordAuthenticationToken(user, null, roller) : null;
		}else{
			System.out.println("Token is null");
			return null;
		}

	}
}
