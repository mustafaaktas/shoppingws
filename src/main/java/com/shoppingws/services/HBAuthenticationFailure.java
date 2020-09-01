package com.shoppingws.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HBAuthenticationFailure implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        String referer=httpServletRequest.getHeader("referer");
        String redirecPath="/giris?error=true";
        if(null!=referer && 3<referer.length()){
            redirecPath=referer+"?error=treu";
        }
        httpServletResponse.sendRedirect(redirecPath);
    }
}
