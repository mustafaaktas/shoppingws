package com.shoppingws.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.google.gson.Gson;
import com.vor.onlinecheckin.exceptions.ErrorDetail;
import com.vor.onlinecheckin.model.base.AccountCredentials;
import com.vor.onlinecheckin.security.TokenAuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	public JWTLoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));

		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException, IOException, ServletException {
		AccountCredentials creds = null;
		try {
			String marshalledXml = org.apache.commons.io.IOUtils.toString(req.getInputStream());
			creds = new ObjectMapper().readValue(marshalledXml, AccountCredentials.class);
			marshalledXml = null;
		} catch (UnrecognizedPropertyException e) {
			throw new BadCredentialsException("Undefinded Parameter");
		}
		UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(creds.getUsername(),	creds.getPassword(), Collections.<GrantedAuthority>emptyList());

		upat.setDetails(creds.getAppName());
		return getAuthenticationManager().authenticate(upat);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
		UsernamePasswordAuthenticationToken userAuth = (UsernamePasswordAuthenticationToken) auth;
		List<String> roller = new ArrayList<>();
		Iterator<GrantedAuthority> it = userAuth.getAuthorities().iterator();
		while (it.hasNext()) {
			roller.add(it.next().getAuthority().toString());
		}
		TokenAuthenticationService.addAuthentication(res, auth.getName(), roller);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		new Gson().toJson(new ErrorDetail(-9, "invalid Username or password!"), response.getWriter());

	}

}