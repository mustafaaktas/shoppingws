package com.shoppingws.filter;

import com.google.gson.Gson;
import com.vor.onlinecheckin.exceptions.ErrorDetail;
import com.vor.onlinecheckin.security.TokenAuthenticationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class JWTAuthenticationFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		Authentication authentication = null;
		try {
			authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest) request);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}catch(Exception e) {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			new Gson().toJson(new ErrorDetail(-9," Faulty Token Info!"),response.getWriter());
			SecurityContextHolder.clearContext();
			return;
		}
		filterChain.doFilter(request, response);
	}
}
