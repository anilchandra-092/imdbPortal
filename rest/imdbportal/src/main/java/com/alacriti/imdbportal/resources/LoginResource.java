package com.alacriti.imdbportal.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.alacriti.imdbportal.delegate.UserDelegate;
import com.alacriti.imdbportal.models.LoginModel;

@Path("login")
public class LoginResource {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject checkUser(LoginModel login){
		JSONObject obj=null;
		try{
			obj=new UserDelegate().checkUser(login);
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;
		
	}
}
