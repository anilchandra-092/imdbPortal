package com.alacriti.imdbportal.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.alacriti.imdbportal.delegate.UserDelegate;
import com.alacriti.imdbportal.models.User;

@Path("users")
public class UserResource {
	
	@POST
	@Path("/adduser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject addUser(User usr){
		JSONObject obj=null;
		try{
			obj= new UserDelegate().addUser(usr);
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;
		
	}
	
		
}
