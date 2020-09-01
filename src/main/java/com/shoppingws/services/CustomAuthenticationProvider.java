package com.shoppingws.services;

import com.shoppingws.model.user.User;
import com.shoppingws.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {


    @Autowired
    private UserServices userServices;

    private boolean doLogin(String userName, String password){
       return userServices.login(new User(userName,password));
    }

    public UsernamePasswordAuthenticationToken login(String userName,String password){
        if((doLogin(userName,password))){

            return new UsernamePasswordAuthenticationToken(userName,password, Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        }
        return null;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
       Authentication auth=null;
       String userName= Helper.checkNulls(authentication.getName(),"");
       String password= Helper.checkNulls(authentication.getCredentials().toString(),"");
       if(userName.length()>3 || password.isEmpty() ){
           return auth;
       }
       auth=login(userName,password);
       return auth;

    }


    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
