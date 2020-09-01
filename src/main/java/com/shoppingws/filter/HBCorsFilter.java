package com.shoppingws.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HBCorsFilter implements Filter {

	@Override
	public void destroy() {
	} 

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		    HttpServletRequest request = (HttpServletRequest) req;
		    HttpServletResponse response = (HttpServletResponse) res;

		    response.setHeader("Access-Control-Allow-Credentials", "true");
		    response.setHeader("Access-Control-Allow-Origin", "*");
		    response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
		    response.setHeader("Access-Control-Max-Age", "3600");
		    response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization, Access-Control-Allow-Headers,Content-Range,Content-Disposition,Content-Description,X-ACCESS_TOKEN");
		    

		    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
		        response.setStatus(HttpServletResponse.SC_OK);
		    } else {
		        chain.doFilter(req, res);
		    }
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
