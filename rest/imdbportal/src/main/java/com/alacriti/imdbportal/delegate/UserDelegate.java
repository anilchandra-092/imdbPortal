package com.alacriti.imdbportal.delegate;

import java.sql.Connection;
import java.util.List;

import org.json.simple.JSONObject;

import com.alacriti.imdbportal.bo.impl.UserBO;
import com.alacriti.imdbportal.bo.impl.UserValidate;
import com.alacriti.imdbportal.models.LoginModel;
import com.alacriti.imdbportal.models.User;

public class UserDelegate extends BaseDelegate{
	
	UserBO userbo=null;
	UserValidate userValidate=null;
	
	public UserDelegate() {
		super();
	}
	public  JSONObject addUser(User usr){
		JSONObject obj=null;
		Connection connection=null;
		boolean rollBack = false;
		
		try{
			connection = startDBTransaction();
			setConnection(connection);
			userValidate=new UserValidate(connection);
			if(userValidate.isValidData(usr.getUname(),usr.getPassword())){
				
				if(userValidate.isUserExist(usr.getUname())){
					obj=createJsonObject("Fail","user already exists");
				}
				else{
					userbo=new UserBO(connection);
					if(userbo.addUser(usr)){
						obj=createJsonObject("Success","user added successfully");
					}
					else{
						obj=createJsonObject("Fail","not able to addd user");
						rollBack=true;
					}
				}
			}
			else{
				rollBack=true;
				obj=createJsonObject("Fail","User data Validation Fail");
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
		JSONObject obj=null;
		Connection connection=null;
		boolean rollBack = false;
		try{
			connection = startDBTransaction();
			setConnection(connection);
			userValidate=new UserValidate(connection);
			if(userValidate.isValidData(login.getUname(),login.getPassword())){
				if(userValidate.isUserExist(login.getUname())){
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
					obj=createJsonObject("Fail","user not existed");
				}
			}
			else{
				obj=createJsonObject("Fail","User data Validation Fail,Invalid format");
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
	
	
}
