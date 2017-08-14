package com.alacriti.imdbportal.resources;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import com.alacriti.imdbportal.delegate.UserDelegate;
import com.alacriti.imdbportal.models.User;
import com.alacriti.imdbportal.util.SessionUtility;

@Path("users")
public class UserResource {

	@POST
	@Path("/adduser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject addUser(User usr) {
		JSONObject obj = null;
		UserDelegate userDelegate=null;
		try {
			userDelegate=new UserDelegate();
			obj =userDelegate.addUser(usr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;

	}
	
	@GET
	@Path("/newUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getNewUsers() {
		List<User> list=null;
		UserDelegate userDelegate=null;
		try {
			userDelegate=new UserDelegate();
			list = userDelegate.getNewUsers();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public boolean updateUserStatus(
			@PathParam("id") int userId,
			@QueryParam("status") String status
			) {
		boolean result=false;
		UserDelegate userDelegate=null;
		try {
			userDelegate=new UserDelegate();
			result = userDelegate.updateUserStatus(userId,status);
		} catch (Exception e) {
			result=false;
			e.printStackTrace();
		}
		return result;

	}
	
	@GET
	@Path("/checkSession")
	@Produces(MediaType.TEXT_PLAIN)
	public boolean checkSessoin(@Context HttpServletRequest request) {
		boolean result = false;
		SessionUtility sessionUtility;
		try {
			sessionUtility = new SessionUtility();
			result = sessionUtility.checkForSession(request);
		} catch (Exception e) {
			System.out
					.println("exception in checkSession mehtod of User Resource");
			e.printStackTrace();
		}
		return result;
	}

	@GET
	@Path("/checkSessionForUser/{user}")
	@Produces(MediaType.TEXT_PLAIN)
	public boolean checkSessoinWithUserRole(@PathParam("user") String userRole,
			@Context HttpServletRequest request) {
		boolean result = false;
		SessionUtility sessionUtility=null;
		try {
			sessionUtility = new SessionUtility();
			result = sessionUtility.checkForSession(request, userRole);
		} catch (Exception e) {
			System.out.println("exception in checkSession mehtod of User Resource");
			e.printStackTrace();
		}
		return result;
	}

	@GET
	@Path("/logout")
	@Produces(MediaType.TEXT_PLAIN)
	public boolean userLogOut(@Context HttpServletRequest request) {
		boolean result = false;
		SessionUtility sessionUtility=null;
		try {
			sessionUtility = new SessionUtility();
			result = sessionUtility.destroySession(request);
		} catch (Exception e) {
			System.out.println("exception in userLogOut mehtod of User Resource");
			e.printStackTrace();
		}
		return result;
	}
	
	@GET
	@Path("/getSessionData")
	@Produces(MediaType.APPLICATION_JSON)
	public User getSessionData(@Context HttpServletRequest request) {
		User user=null;
		UserDelegate userDelegate=null;
		try {
			userDelegate=new UserDelegate();
			user = userDelegate.getSessionData(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;

	}
}
