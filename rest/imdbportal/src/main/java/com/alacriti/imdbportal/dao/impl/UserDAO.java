package com.alacriti.imdbportal.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.alacriti.imdbportal.constants.DBColumnConstants;
import com.alacriti.imdbportal.exceptions.DAOException;
import com.alacriti.imdbportal.models.User;

public class UserDAO extends BaseDAO {
	
	public static final Logger log= Logger.getLogger(UserDAO.class);
	
	public UserDAO() {
		super();
	}
	
	public UserDAO(Connection con) {
		super(con);

	}
	
	public  boolean addUserToDb(User usr)throws DAOException{
		log.debug("=========>> addUserToDb method in UserDAO class ::");
		boolean result=false;
		PreparedStatement pst=null;
		try{
			int i=0;
			pst=getPreparedStatement(getConnection(), addUserToDbSqlCmd());
			pst.setString(++i,usr.getUname());
			pst.setString(++i,usr.getPassword());
			pst.setString(++i,usr.getEmail());
			pst.setString(++i,usr.getRole());
			pst.executeUpdate();
			result=true;
		}catch(SQLException e){
			log.error("SQL Exception Occured in addUserToDb: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in preparedStatement");
		}
		catch(Exception e){
			log.error("Exception Occured in addUserToDb: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(pst);
		}
		return result;
	}
	
	private String addUserToDbSqlCmd(){
		return "insert into anilkumarreddyg_imdb_user_profile_tbl("
				+DBColumnConstants.USER_PROFILE_TBL_UNAME+","
				+DBColumnConstants.USER_PROFILE_TBL_PASSWORD+","
				+DBColumnConstants.USER_PROFILE_TBL_EMAIL+","
				+DBColumnConstants.USER_PROFILE_TBL_ROLE
				+")values(?,?,?,?);";
	}
	
	public boolean isUserNameExist(String uname) throws DAOException{
		log.debug("=========>> isUserNameExist method in UserDAO class ::");
		boolean result=false;
		Statement st=null;
		ResultSet rs=null;
		try{
			st=getConnection().createStatement();
			rs=st.executeQuery(isUserNameExistSqlCmd(uname));
			if(rs.next()){
				result=true;
			}
		}catch(SQLException e){
			log.error("SQL Exception Occured in isUserNameExist: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in selectStatement");
		}
		catch(Exception e){
			log.error("Exception Occured in isUserNameExist: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
		return result;
	}
	
	private String isUserNameExistSqlCmd(String uname){
		return "select "+DBColumnConstants.USER_PROFILE_TBL_UNAME
				+" from anilkumarreddyg_imdb_user_profile_tbl "
				+" where "+DBColumnConstants.USER_PROFILE_TBL_UNAME+"=\""+uname+"\";";
	}
	
	public boolean isUserEmailExist(String email) throws DAOException{
		log.debug("=========>> isUserEmailExist method in UserDAO class ::");
		boolean result=false;
		Statement st=null;
		ResultSet rs=null;
		try{
			st=getConnection().createStatement();
			rs=st.executeQuery(isUserEmailExistSqlCmd(email));
			if(rs.next()){
				result=true;
			}
		}catch(SQLException e){
			log.error("SQL Exception Occured in isUserEmailExist: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in selectStatement");
		}
		catch(Exception e){
			log.error("Exception Occured in isUserEmailExist: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
		return result;
	}
	
	private String isUserEmailExistSqlCmd(String email){
		return "select "+DBColumnConstants.USER_PROFILE_TBL_UNAME
				+" from anilkumarreddyg_imdb_user_profile_tbl "
				+" where "+DBColumnConstants.USER_PROFILE_TBL_EMAIL+"=\""+email+"\";";
	}
	
	public boolean isApprovedUser(String uname) throws DAOException{
		log.debug("=========>> isApprovedUser method in UserDAO class ::");
		boolean result=false;
		Statement st=null;
		ResultSet rs=null;
		try{
			st=getConnection().createStatement();
			rs=st.executeQuery(isApprovedUserSqlCmd(uname));
			if(rs.next()){
				result=true;
			}
		}catch(SQLException e){
			log.error("SQL Exception Occured in isApprovedUser: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in selectStatement");
		}
		catch(Exception e){
			log.error("Exception Occured in isApprovedUser: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
		return result;
	}
	
	private String isApprovedUserSqlCmd(String uname){
		return "select "+DBColumnConstants.USER_PROFILE_TBL_UNAME
				+" from anilkumarreddyg_imdb_user_profile_tbl "
				+" where "+DBColumnConstants.USER_PROFILE_TBL_UNAME+"=\""+uname+"\" "
				+" and "+DBColumnConstants.USER_PROFILE_TBL_STATUS+"=2;";
	}
	
	public User checkUnameAndPassword(String uname,String password) throws DAOException{
		log.debug("=========>> checkUnameAndPassword method in UserDAO class ::");
		Statement st=null;
		ResultSet rs=null;
		User user=null;
		try{
			st=getConnection().createStatement();
			rs=st.executeQuery(checkUnameAndPasswordSqlCmd(uname,password));
			if(rs.next()){
				user=getUser(rs);
			}
		}catch(SQLException e){
			log.error("SQL Exception Occured in checkUnameAndPassword: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in selectStatement");
		}
		catch(Exception e){
			log.error("Exception Occured in checkUnameAndPassword: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
		return user;
	}
	
	private User getUser(ResultSet rs) throws DAOException{
		log.debug("=========>> getUser method in UserDAO class ::");
		User user=null;
		try{
			user=new User(
					rs.getInt(DBColumnConstants.USER_PROFILE_TBL_ID),
					rs.getString(DBColumnConstants.USER_PROFILE_TBL_UNAME),
					rs.getString(DBColumnConstants.USER_PROFILE_TBL_EMAIL),
					rs.getString(DBColumnConstants.USER_PROFILE_TBL_ROLE));
			
		}catch(SQLException e){
			log.error("SQL Exception Occured in Processing ResultSet: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in Processing ResultSet");
		}
		catch(Exception e){
			log.error("Exception Occured in Processing ResultSet: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}
		return user;
	}
	
	private String checkUnameAndPasswordSqlCmd(String uname,String password){
		return "select * from anilkumarreddyg_imdb_user_profile_tbl "
				+" where "+DBColumnConstants.USER_PROFILE_TBL_UNAME+"=\""+uname+"\" "
				+" and "+DBColumnConstants.USER_PROFILE_TBL_PASSWORD+"=\""+password+"\";";
	}
	
	public List<User> getNewUsers() throws DAOException{
		log.debug("=========>> getNewUsers method in UserDAO class ::");
		List<User> list=null;
		Statement st=null;
		ResultSet rs=null;
		try{
			list=new ArrayList<User>();
			st=getConnection().createStatement();
			rs=st.executeQuery(getNewUsersSqlCmd());
			while(rs.next()){
				list.add(getUser(rs));
			} 
		}catch(SQLException e){
			log.error("SQL Exception Occured in getNewUsers: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in selectStatement");
		}
		catch(Exception e){
			log.error("Exception Occured in getNewUsers: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
		return list;
	}
	
	private String getNewUsersSqlCmd(){
		return "select * from anilkumarreddyg_imdb_user_profile_tbl "
				+" where "+DBColumnConstants.USER_PROFILE_TBL_STATUS+"=1"; 
	}
	
	public boolean updateUserStatus(int userId,String status) throws DAOException{
		log.debug("=========>> updateUserStatus method in UserDAO class ::");
		boolean result=false;
		Statement st=null;
		int statusId;
		try{
			st=getConnection().createStatement();
			if("approve".equalsIgnoreCase(status))
			{
				statusId=2;
			}
			else{
				statusId=0;
			}
			st.executeUpdate(updateUserStatusSqlCmd(userId,statusId));
			result=true;
		}catch(SQLException e){
			log.error("SQL Exception Occured in updateUserStatus: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in updateStatement");
		}
		catch(Exception e){
			log.error("Exception Occured in updateUserStatus: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
		
			close(st);
		}
		return result;
	}
	
	private String updateUserStatusSqlCmd(int userId,int statusId){
		return "update anilkumarreddyg_imdb_user_profile_tbl set "
				+DBColumnConstants.USER_PROFILE_TBL_STATUS+"="+statusId
				+" where "+DBColumnConstants.USER_PROFILE_TBL_ID+"="+userId;
	}
	
	public String getUserMailId(int userId) throws DAOException{
		log.debug("=========>> getUserMailId method in UserDAO class ::");
		String email="";
		Statement st=null;
		ResultSet rs=null;;
		try{
			st=getConnection().createStatement();
			rs=st.executeQuery(getUserMailIdSqlCmd(userId));
			if(rs.next()){
				email=rs.getString(DBColumnConstants.USER_PROFILE_TBL_EMAIL);
			}
		}catch(SQLException e){
			log.error("SQL Exception Occured in getUserMailId: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in selectStatement");
		}
		catch(Exception e){
			log.error("Exception Occured in getUserMailId: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
		return email;
	}

	private String getUserMailIdSqlCmd(int userId){
		return "select "+DBColumnConstants.USER_PROFILE_TBL_EMAIL
				+" from anilkumarreddyg_imdb_user_profile_tbl "
				+"where "+DBColumnConstants.USER_PROFILE_TBL_ID+"="+userId;
	}
}
