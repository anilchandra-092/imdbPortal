package com.alacriti.imdbportal.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class SessionUtility {
	public static final Logger log= Logger.getLogger(SessionUtility.class);
	public SessionUtility() {}
	
	public boolean checkForSession(HttpServletRequest request) {
		log.debug("===========>> checkForSession method in SessionUtility class");
		HttpSession session= request.getSession(false);
		System.out.println("printing the status of session :");
		System.out.println(session);
		log.debug("printing the status of session :: "+session);
		if (session == null)
			return false;
		else
			return true;
	}
	
	public boolean checkForSession(HttpServletRequest request,String userRole) {
		log.debug("===========>> checkForSession method in SessionUtility class");
			HttpSession session= request.getSession(false);
			System.out.println("printing the status of session :");
			System.out.println(session);
			log.debug("printing the status of session :: "+session);
			boolean result=false;
			
			if (session != null && session.getAttribute("role").equals(userRole)){			
				result=true;
			}
			
			return result;
	}
	
	public boolean destroySession(HttpServletRequest request) {
		log.debug("===========>> destroySession method in SessionUtility class");
		HttpSession session= request.getSession(false);
		System.out.println("printing the status of session before Destroy :");
		System.out.println(session);
		log.debug("printing the status of session before Destroy :: "+session);
		boolean result=false;
		
		if (session != null ){
			session.invalidate();
			System.out.println("printing the status of session after Destroy :");
			System.out.println(session);
			log.debug("printing the status of session after Destroy :: "+session);
			result=true;
		}
		
		return result;
}
	
	
	
	
}
