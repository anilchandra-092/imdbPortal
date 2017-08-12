package com.alacriti.imdbportal.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtility {
	
	public SessionUtility() {}
	
	public boolean checkForSession(HttpServletRequest request) {
	
		HttpSession session= request.getSession(false);
		System.out.println("printing the status of session :");
		System.out.println(session);
		if (session == null)
			return false;
		else
			return true;
	}
	
	public boolean checkForSession(HttpServletRequest request,String userRole) {
		
			HttpSession session= request.getSession(false);
			System.out.println("printing the status of session :");
			System.out.println(session);
			boolean result=false;
			
			if (session != null && session.getAttribute("role").equals(userRole)){			
				result=true;
			}
			
			return result;
	}
	
	public boolean destroySession(HttpServletRequest request) {
		
		HttpSession session= request.getSession(false);
		System.out.println("printing the status of session before Destroy :");
		System.out.println(session);
		boolean result=false;
		
		if (session != null ){
			session.invalidate();
			System.out.println("printing the status of session after Destroy :");
			System.out.println(session);
			result=true;
		}
		
		return result;
}
	
	
	
	
}
