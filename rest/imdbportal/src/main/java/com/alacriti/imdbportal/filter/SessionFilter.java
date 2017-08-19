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

import org.apache.log4j.Logger;
import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ServerResponse;

import com.alacriti.imdbportal.util.SessionUtility;

@Provider
public class SessionFilter implements ContainerRequestFilter{
	
	private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("Nobody can access this resource", 403, new Headers<Object>());;
	public static final Logger log= Logger.getLogger(SessionFilter.class);
	
	@Context HttpServletRequest servletRequest;
	public void filter(ContainerRequestContext requestContext) throws IOException {
		log.debug("==============>>filter method in SessionFilter class");
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
					if(!("/imdbportal/users/checkSessionForUser/admin".equals(servletRequest.getRequestURI()))){
						//abort the reaquest with 403 status
						requestContext.abortWith(ACCESS_FORBIDDEN);
					}
				}
			}
		}catch(Exception e){
			log.error("exception occured in filter method of SessionFilter class "+e.getMessage(),e);
			e.printStackTrace();
		}
		return;
		
	}

	private static List<String> listOfWordsRequiredSession() throws Exception{
		log.debug("==============>>listOfWordsRequiredSession method in SessionFilter class");
		List<String> urlList=null;
		try{
			urlList=new ArrayList<String>();
			urlList.add("admin");
			urlList.add("updateMovie");
			urlList.add("addMovie");
			urlList.add("newUsers");
			urlList.add("setRate");
			urlList.add("uploadMovieDetails");
			urlList.add("getSessionData");
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("exception occured in listOfWordsRequiredSession method of SessionFilter class "+e.getMessage(),e);
			System.out.println("exception occured in listOfWordsRequiredSession method of SessionFilter class");
			throw e;
		}
		return urlList;
	}
}
