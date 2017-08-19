package com.alacriti.imdbportal.bo.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import com.alacriti.imdbportal.dao.impl.UserDAO;
import com.alacriti.imdbportal.exceptions.BOException;
import com.alacriti.imdbportal.exceptions.DAOException;
import com.alacriti.imdbportal.models.User;
import com.alacriti.imdbportal.util.MailUtil;

public class UserBO extends BaseBO {
	
	public static final Logger log= Logger.getLogger(UserBO.class);
	
	public UserBO() {
		super();
	}
	public UserBO(Connection con) {
		super(con);
	}
	
	public  boolean addUser(User usr) throws BOException{
		log.debug("=========>> addUser method in UserBO class ::");
		UserDAO userdao=null;
		try{
			userdao=new UserDAO(getConnection());
			userdao.addUserToDb(usr);
		}catch (DAOException e) {
			log.error("DAOException in addUser of UserBo " + e.getMessage(), e);
			throw new BOException("DAOException Occured");
		} catch (Exception e) {
			log.error("Exception in addUser of UserBo " + e.getMessage(), e);
			throw new BOException();
		}
		return true;
	}
	
	public User checkPassword(String uname,String password) throws BOException{
		log.debug("=========>> checkPassword method in UserBO class ::");
		UserDAO userdao=null;
		User usr=null;
		try{
			userdao=new UserDAO(getConnection());
			usr=userdao.checkUnameAndPassword(uname,password);
			
		}catch (DAOException e) {
			log.error("DAOException in checkPassword of UserBo " + e.getMessage(), e);
			throw new BOException("DAOException Occured");
		} catch (Exception e) {
			log.error("Exception in checkPassword of UserBo " + e.getMessage(), e);
			throw new BOException();
		}
		return usr;
	}
	
	public List<User> getNewUsers() throws BOException{
		log.debug("=========>> getNewUsers method in UserBO class ::");
		UserDAO userdao=null;
		List<User> list=null;
		try{
			userdao=new UserDAO(getConnection());
			list=userdao.getNewUsers();
		}catch (DAOException e) {
			log.error("DAOException in getNewUsers of UserBo " + e.getMessage(), e);
			throw new BOException("DAOException Occured");
		} catch (Exception e) {
			log.error("Exception in getNewUsers of UserBo " + e.getMessage(), e);
			throw new BOException();
		}
		return list;
	}
	
	public  boolean updateUserStatus(int userId,String status) throws BOException{
		log.debug("=========>> updateUserStatus method in UserBO class ::");
		UserDAO userdao=null;
		boolean result=false;
		MailUtil mailUtil=new MailUtil();
		String mailId=null;
		try{
			userdao=new UserDAO(getConnection());
			result=userdao.updateUserStatus(userId,status);
			mailId=userdao.getUserMailId(userId);
			mailUtil.send(mailId,status); // for sending mail to users
		}catch (DAOException e) {
			log.error("DAOException in updateUsersStatus of UserBo " + e.getMessage(), e);
			throw new BOException("DAOException Occured");
		} catch (Exception e) {
			log.error("Exception in updateUsersStatus of UserBo " + e.getMessage(), e);
			throw new BOException();
		}
		return result;
	}
	
}
