package com.alacriti.imdbportal.bo.impl;

import java.sql.Connection;

import com.alacriti.imdbportal.dao.impl.UserDAO;
import com.alacriti.imdbportal.exceptions.BOException;
import com.alacriti.imdbportal.exceptions.DAOException;
import com.alacriti.imdbportal.exceptions.FailedToInsertDataException;
import com.alacriti.imdbportal.models.User;

public class UserBO extends BaseBO {
	public UserBO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserBO(Connection con) {
		super(con);
		// TODO Auto-generated constructor stub
	}
	
	UserDAO userdao=null;
	public  boolean addUser(User usr) throws BOException{
		try{
			userdao=new UserDAO(getConnection());
			userdao.addUserToDb(usr);
		}catch (DAOException e) {
			throw new BOException("DAOException Occured");
		} catch (Exception e) {
			throw new BOException();
		}
		return true;
	}
	public User checkPassword(String uname,String password) throws BOException{
		User usr=null;
		try{
			userdao=new UserDAO(getConnection());
			usr=userdao.checkUnameAndPassword(uname,password);
			
		}catch (DAOException e) {
			throw new BOException("DAOException Occured");
		} catch (Exception e) {
			throw new BOException();
		}
		return usr;
	}
}
