package com.alacriti.imdbportal.delegate;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.alacriti.imdbportal.bo.impl.UserBO;
import com.alacriti.imdbportal.bo.impl.UserValidate;
import com.alacriti.imdbportal.models.LoginModel;
import com.alacriti.imdbportal.models.User;

public class UserDelegate extends BaseDelegate{
	
	public UserDelegate() {
		super();
	}
	public  JSONObject addUser(User usr){
		UserBO userbo=null;
		JSONObject obj=null;
		Connection connection=null;
		boolean rollBack = false;
		UserValidate userValidate=null;
		try{
			connection = startDBTransaction();
			setConnection(connection);
			userValidate=new UserValidate(connection);
			if(userValidate.isValidData(usr.getUname(),usr.getPassword())){
				if(userValidate.isUserNameExist(usr.getUname())){
					obj=createJsonObject("Fail","user name allready exists");
				}
				else{
					if(!(userValidate.isUserEmailExist(usr.getEmail()))){
					userbo=new UserBO(connection);
					if(userbo.addUser(usr)){
						obj=createJsonObject("Success","user added successfully");
					}
					else{
						obj=createJsonObject("Fail","not able to addd user");
						rollBack=true;
					}
					
					}else{
						obj=createJsonObject("Fail","user email allready existed");
					}
				}
			}
			else{
				rollBack=true;
				obj=createJsonObject("Fail","Please Insert the Valid Data");
			}
		}catch(Exception e){
			rollBack=true;
			obj=createJsonObject("Fail","not able to addd user");
		}finally{
			endDBTransaction(connection, rollBack);
		}
		return obj;
	}
	
	public JSONObject checkUser(LoginModel login){
		UserBO userbo=null;
		JSONObject obj=null;
		Connection connection=null;
		boolean rollBack = false;
		UserValidate userValidate=null;
		try{
			connection = startDBTransaction();
			setConnection(connection);
			userValidate=new UserValidate(connection);
			if(userValidate.isValidData(login.getUname(),login.getPassword())){
				if(userValidate.isUserNameExist(login.getUname())){
					if(userValidate.isApprovedUser(login.getUname())){
						userbo=new UserBO(connection);
						User usr=userbo.checkPassword(login.getUname(),login.getPassword());
						if(usr!=null){
							obj=createJsonObject("Success","Valid User");
								obj.put("role", usr.getRole());
								obj.put("id", usr.getId());
						}
						else{	
							
							obj=createJsonObject("Fail","Invalid Password");
						}
					}
					else{
						obj=createJsonObject("Fail","Admin not yet approved your Registration");
					}
				}
				else{
					obj=createJsonObject("Fail","Invalid UserName");
				}
			}
			else{
				obj=createJsonObject("Fail","Invalid UserName or Password");
			}
		}catch(Exception e){
			rollBack=true;
			obj=createJsonObject("Fail","Exception occured");
		}finally{
			endDBTransaction(connection, rollBack);
		}
		return obj;
	}
	
	public JSONObject createJsonObject(String status,String msg){
		JSONObject obj=new JSONObject();
		obj.put("status",status);
		obj.put("message", msg);
		return obj;
	}
	
	public List<User> getNewUsers(){
		UserBO userbo=null;
		List<User> list=null;
		Connection connection=null;
		boolean rollBack = false;
		try{
			connection = startDBTransaction();
			setConnection(connection);
			userbo=new UserBO(connection);
			list=userbo.getNewUsers();
		}catch(Exception e){
			e.printStackTrace();
			rollBack=true;
		}finally{
			endDBTransaction(connection, rollBack);
		}
		return list;
	}
	
	public boolean updateUserStatus(int userId,String status){
		UserBO userbo=null;
		boolean result=false;
		Connection connection=null;
		boolean rollBack = false;
		try{
			connection = startDBTransaction();
			setConnection(connection);
			userbo=new UserBO(connection);
			result=userbo.updateUserStatus(userId,status);
		}catch(Exception e){
			e.printStackTrace();
			rollBack=true;
		}finally{
			endDBTransaction(connection, rollBack);
		}
		return result;
	}
	
	public User getSessionData(HttpServletRequest request){
		User user=null;
		HttpSession session=null;
		try{
			session= request.getSession(false);
			user=new User();
			if(session!=null){
				int id=(Integer)session.getAttribute("id");
				user.setId(id);
				user.setRole(session.getAttribute("role").toString());
			}
			
		}catch(Exception e){
			System.out.println("exception Occured in getting session attributes in user delegate layer");
			e.printStackTrace();
		}
		return user;
	}
	
}
