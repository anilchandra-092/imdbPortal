package com.alacriti.imdbportal.bo.impl;

import java.sql.Connection;
import java.util.regex.Pattern;

import com.alacriti.imdbportal.dao.impl.UserDAO;
import com.alacriti.imdbportal.exceptions.BOException;
import com.alacriti.imdbportal.exceptions.DAOException;

public class UserValidate extends BaseBO{
	
	public UserValidate() {
		super();
	}
	
	public UserValidate(Connection con) {
		super(con);
	}
	
	public  boolean isUserNameExist(String uname)throws BOException{
		boolean result=false;
		UserDAO userDAO=null;
		try{
		userDAO=new UserDAO(getConnection());
		result=userDAO.isUserNameExist(uname);
		}catch (DAOException e) {
			throw new BOException("DAOException Occured");
		} catch (Exception e) {
			throw new BOException();
		}
		return result;
	}
	
	public  boolean isUserEmailExist(String email)throws BOException{
		boolean result=false;
		UserDAO userDAO=null;
		try{
		userDAO=new UserDAO(getConnection());
		result=userDAO.isUserEmailExist(email);
		}catch (DAOException e) {
			throw new BOException("DAOException Occured");
		} catch (Exception e) {
			throw new BOException();
		}
		return result;
	}
	
	public  boolean isApprovedUser(String uname)throws BOException{
		boolean result=false;
		UserDAO userDAO=null;
		try{
		userDAO=new UserDAO(getConnection());
		result=userDAO.isApprovedUser(uname);
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
