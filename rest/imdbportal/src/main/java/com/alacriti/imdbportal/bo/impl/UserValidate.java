package com.alacriti.imdbportal.bo.impl;

import java.sql.Connection;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.alacriti.imdbportal.dao.impl.UserDAO;
import com.alacriti.imdbportal.exceptions.BOException;
import com.alacriti.imdbportal.exceptions.DAOException;

public class UserValidate extends BaseBO{
	public static final Logger log= Logger.getLogger(UserValidate.class);
	
	public UserValidate() {
		super();
	}
	
	public UserValidate(Connection con) {
		super(con);
	}
	
	public  boolean isUserNameExist(String uname)throws BOException{
		log.debug("=========>> isUserNameExist method in UserValidate class ::");
		boolean result=false;
		UserDAO userDAO=null;
		try{
		userDAO=new UserDAO(getConnection());
		result=userDAO.isUserNameExist(uname);
		}catch (DAOException e) {
			log.error("DAOException in isUserNameExist of UserValidate " + e.getMessage(), e);
			throw new BOException("DAOException Occured");
		} catch (Exception e) {
			log.error("Exception in isUserNameExist of UserValidate " + e.getMessage(), e);
			throw new BOException();
		}
		return result;
	}
	
	public  boolean isUserEmailExist(String email)throws BOException{
		log.debug("=========>> isUserEmailExist method in UserValidate class ::");
		boolean result=false;
		UserDAO userDAO=null;
		try{
		userDAO=new UserDAO(getConnection());
		result=userDAO.isUserEmailExist(email);
		}catch (DAOException e) {
			log.error("DAOException in isUserEmailExist of UserValidate " + e.getMessage(), e);
			throw new BOException("DAOException Occured");
		} catch (Exception e) {
			log.error("Exception in isUserNameExist of UserValidate " + e.getMessage(), e);
			throw new BOException();
		}
		return result;
	}
	
	public  boolean isApprovedUser(String uname)throws BOException{
		log.debug("=========>> isApprovedUser method in UserValidate class ::");
		boolean result=false;
		UserDAO userDAO=null;
		try{
		userDAO=new UserDAO(getConnection());
		result=userDAO.isApprovedUser(uname);
		}catch (DAOException e) {
			log.error("DAOException in isApprovedUser of UserValidate " + e.getMessage(), e);
			throw new BOException("DAOException Occured");
		} catch (Exception e) {
			log.error("Exception in isApprovedUser of UserValidate " + e.getMessage(), e);
			throw new BOException();
		}
		return result;
	}
	
	
	public boolean isValidData(String uname,String password){
		log.debug("=========>> isValidData method in UserValidate class ::");
		boolean result=true;
		if(uname.length()<4 || uname.length()>50){
			result= false;
		}
		if(!Pattern.matches("^[a-zA-Z0-9_]{4,50}$",uname)){
			result= false;
		}
		if(!Pattern.matches("^(?=.*?[a-zA-Z])(?=.*?[0-9])[a-zA-Z0-9]{8,16}$", password)){
			result= false;
		}
		return result;
	}
	public boolean isValidData(String uname,String password,String email){
		log.debug("=========>> isValidData method in UserValidate class ::");
		boolean result=false;
		boolean isValidUnameAndPassword=isValidData(uname, password);
		boolean isValidEmail=Pattern.matches("^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$",email);
		System.out.println("u&p: "+isValidUnameAndPassword);
		log.debug("=========>> username & password validation return  :"+isValidUnameAndPassword);
		log.debug("=========>> email validation return : "+isValidEmail);
		if(isValidUnameAndPassword  && isValidEmail){
			log.info("=========>> user data validation fail: bcz of in valid data");
			result= true;
		}
		return result;
	}
}
