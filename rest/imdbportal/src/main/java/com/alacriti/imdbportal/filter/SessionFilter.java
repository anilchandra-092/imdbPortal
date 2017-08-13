package com.alacriti.imdbportal.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ServerResponse;

import com.alacriti.imdbportal.util.SessionUtility;

@Provider
public class SessionFilter implements ContainerRequestFilter{
	 private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("Nobody can access this resource", 403, new Headers<Object>());;
	@Context HttpServletRequest servletRequest;
	public void filter(ContainerRequestContext requestContext) throws IOException {
		System.out.println(servletRequest.getRequestURI());
		SessionUtility sessionUtility=null;
		List<String> urlList=null;
		boolean isSessionValidationRequired=false;
		boolean isValidSession=false;
		try{
			urlList=listOfWordsRequiredSession();
			Iterator<String> itr=urlList.iterator();
			isSessionValidationRequired=false;
			while(itr.hasNext()){
				if(servletRequest.getRequestURI().contains(itr.next())){
					isSessionValidationRequired=true;
					break;
				}
			}
			if(isSessionValidationRequired){
				sessionUtility = new SessionUtility();
				isValidSession = sessionUtility.checkForSession(servletRequest);
				if(!isValidSession){
					//abort the reaquest with 403 status
					requestContext.abortWith(ACCESS_FORBIDDEN);
				}
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return;
		
	}

	private static List<String> listOfWordsRequiredSession() throws Exception{
		List<String> urlList=null;
		try{
			urlList=new ArrayList<String>();
			urlList.add("admin");
			urlList.add("updateMovie");
			urlList.add("addMovie");
			urlList.add("newUsers");
			urlList.add("setRate");
			urlList.add("uploadMovieDetails");
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("exception occured in Context Request Filter");
			throw e;
		}
		return urlList;
	}
}
