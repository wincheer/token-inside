package com.ywq.ti.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;

@Order(1)
@WebFilter(filterName = "CorsFilter", urlPatterns = "/*")
public class CORSFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		response.setHeader("Access-Control-Allow-Origin", "*");
	    response.setHeader("Access-Control-Allow-Methods", "POST, GET");
	    response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Connection, User-Agent, Cookie");
	    
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {

	}
}
