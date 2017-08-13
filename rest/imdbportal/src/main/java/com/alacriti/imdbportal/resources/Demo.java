package com.alacriti.imdbportal.resources;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;

public class Demo implements ContainerRequestFilter{

	@Context HttpServletRequest servletRequest;
	public void filter(ContainerRequestContext arg0) throws IOException {
		
		servletRequest.getRequestURI();
		
		
	}

	
}
