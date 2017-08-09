package com.alacriti.imdbportal.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.alacriti.imdbportal.exceptions.DAOException;
import com.alacriti.imdbportal.exceptions.FailedToInsertDataException;
import com.alacriti.imdbportal.models.User;

public class UserDAO extends BaseDAO {
	
	public UserDAO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UserDAO(Connection con) {
		super(con);

	}
	
	public  boolean addUserToDb( User usr)throws DAOException{
		boolean result=false;
		PreparedStatement pst=null;
		try{
			int i=0;
			String sqlCmd="insert into anilkumarreddyg_imdb_user_profile_tbl(uname,password,email,role)values(?,?,?,?);";
			pst=getPreparedStatement(getConnection(), sqlCmd);
			pst.setString(++i,usr.getUname());
			pst.setString(++i,usr.getPassword());
			pst.setString(++i,usr.getEmail());
			pst.setString(++i,usr.getRole());
			pst.executeUpdate();
			result=true;
		}catch(SQLException e){
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in preparedStatement");
		}
		catch(Exception e){
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(pst);
		}
		return result;
	}
	
	public boolean isUserExist(String uname) throws DAOException{
		boolean result=false;
		Statement st=null;
		ResultSet rs=null;
		String query="";
		try{
			st=getConnection().createStatement();
			query="select uname from anilkumarreddyg_imdb_user_profile_tbl where uname=\""+uname+"\";";
			rs=st.executeQuery(query);
			if(rs.next()){
				result=true;
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in selectStatement");
		}
		catch(Exception e){
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
		return result;
	}
	
	public User checkUnameAndPassword(String uname,String password) throws DAOException{
		Statement st=null;
		ResultSet rs=null;
		String query="";
		User user=null;
		try{
			st=getConnection().createStatement();
			query="select * from anilkumarreddyg_imdb_user_profile_tbl where uname=\""+uname+"\" and password=\""+password+"\";";
			rs=st.executeQuery(query);
			if(rs.next()){
				user=new User(rs.getInt("id"),rs.getString("uname"),rs.getString("password"),rs.getString("email"),rs.getString("role"));
				
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in selectStatement");
		}
		catch(Exception e){
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
		return user;
	}
}