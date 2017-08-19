package com.alacriti.imdbportal.resources;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.alacriti.imdbportal.delegate.UserDelegate;
import com.alacriti.imdbportal.models.LoginModel;

@Path("login")
@Singleton
public class LoginResource {
	
	public static final Logger log= Logger.getLogger(LoginResource.class);
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject checkUser(LoginModel login,@Context HttpServletRequest request){
		log.debug("=========>> checkUser method in LoginResource class ::");
		JSONObject obj=null;
		UserDelegate delegate = null;
		try{
			delegate = new UserDelegate();
			obj=delegate.checkUser(login);
			if(obj.get("status").equals("Success")){
				System.out.println("creating session for login");
				HttpSession session = request.getSession();
				session.setAttribute("role", obj.get("role"));
				session.setAttribute("id",obj.get("id"));
				System.out.println("session created for login");
			}
		}catch(Exception e){
			log.error("Exception in checkUser of login resource : "+ e.getMessage(), e);
			e.printStackTrace();
		}
		return obj;
		
	}
}
