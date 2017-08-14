package com.alacriti.imdbportal.bo.impl;

import java.sql.Connection;
import java.util.List;

import com.alacriti.imdbportal.dao.impl.UserDAO;
import com.alacriti.imdbportal.exceptions.BOException;
import com.alacriti.imdbportal.exceptions.DAOException;
import com.alacriti.imdbportal.models.User;
import com.alacriti.imdbportal.util.MailUtil;

public class UserBO extends BaseBO {
	public UserBO() {
		super();
	}
	public UserBO(Connection con) {
		super(con);
	}
	
	public  boolean addUser(User usr) throws BOException{
		UserDAO userdao=null;
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
		UserDAO userdao=null;
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
	
	public List<User> getNewUsers() throws BOException{
		UserDAO userdao=null;
		List<User> list=null;
		try{
			userdao=new UserDAO(getConnection());
			list=userdao.getNewUsers();
		}catch (DAOException e) {
			throw new BOException("DAOException Occured");
		} catch (Exception e) {
			throw new BOException();
		}
		return list;
	}
	
	public  boolean updateUserStatus(int userId,String status) throws BOException{
		UserDAO userdao=null;
		boolean result=false;
		//MailUtil mailUtil=new MailUtil();
		//String mailId=null;
		try{
			userdao=new UserDAO(getConnection());
			result=userdao.updateUserStatus(userId,status);
			//mailId=userdao.getUserMailId(userId);
			//mailUtil.sendMailTo(mailId,status); // for sending mail to users
		}catch (DAOException e) {
			throw new BOException("DAOException Occured");
		} catch (Exception e) {
			throw new BOException();
		}
		return result;
	}
	
}
