package com.shoppingws.config;

import com.vor.onlinecheckin.filter.HBCorsFilter;
import com.vor.onlinecheckin.filter.JWTAuthenticationFilter;
import com.vor.onlinecheckin.filter.JWTLoginFilter;
import com.vor.onlinecheckin.security.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomAuthenticationProvider authProvider;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
			.disable()
			.authorizeRequests().antMatchers("/**")
			.permitAll().anyRequest().authenticated().and()

			.addFilterBefore(new HBCorsFilter(), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),	UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //Sadece 1 session oluşturulmasını sağlar
		
			
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}
	
	
	
	
}