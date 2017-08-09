package com.alacriti.imdbportal.bo.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

import com.alacriti.imdbportal.dao.impl.UserDAO;
import com.alacriti.imdbportal.datasource.MySqlDataSource;
import com.alacriti.imdbportal.exceptions.BOException;
import com.alacriti.imdbportal.exceptions.DAOException;
import com.alacriti.imdbportal.exceptions.ValidateException;
import com.alacriti.imdbportal.models.User;

public class UserValidate extends BaseBO{
	public UserValidate() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserValidate(Connection con) {
		super(con);
		// TODO Auto-generated constructor stub
	}
	public  boolean isUserExist(String uname)throws BOException{
		boolean result=false;
		UserDAO userDAO=null;
		try{
		userDAO=new UserDAO(getConnection());
		result=userDAO.isUserExist(uname);
		}catch (DAOException e) {
			throw new BOException("DAOException Occured");
		} catch (Exception e) {
			throw new BOException();
		}
		return result;
	}
	
	public boolean isValidData(String uname,String password){
		boolean result=true;
		if(uname.length()<4){
			return false;
		}
		if(!Pattern.matches("^(?=.*?[a-zA-Z])(?=.*?[0-9])[a-zA-Z0-9]{8,16}$", password)){
			return false;
		}
		return result;
	}
}
