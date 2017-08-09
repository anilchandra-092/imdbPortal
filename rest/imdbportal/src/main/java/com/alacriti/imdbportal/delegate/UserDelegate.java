package com.alacriti.imdbportal.delegate;

import java.sql.Connection;

import org.json.simple.JSONObject;

import com.alacriti.imdbportal.bo.impl.UserBO;
import com.alacriti.imdbportal.bo.impl.UserValidate;
import com.alacriti.imdbportal.datasource.MySqlDataSource;
import com.alacriti.imdbportal.exceptions.FailedToInsertDataException;
import com.alacriti.imdbportal.exceptions.ValidateException;
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
	
	public static JSONObject createJsonObject(String status,String msg){
		JSONObject obj=new JSONObject();
		obj.put("status",status);
		obj.put("message", msg);
		return obj;
		
	}
}
