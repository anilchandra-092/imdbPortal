package com.alacriti.imdbportal.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.alacriti.imdbportal.constants.Constants;
import com.alacriti.imdbportal.exceptions.DAOException;
import com.alacriti.imdbportal.models.Movie;

public class SearchDAO extends BaseDAO{
	public SearchDAO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SearchDAO(Connection con) {
		super(con);
		// TODO Auto-generated constructor stub
	}
	
	public List<Movie> getAllMoviesByCategeory(String categeory) throws DAOException{
		List<Movie> list=null;
		MovieDAO mdao=null;
		try{
			mdao=new MovieDAO(getConnection());
			list=mdao.getAllMoviesToAdmin(categeory);
		}catch(DAOException e){
			e.printStackTrace();
			throw new DAOException("DAO Exception occured in MoviesDAO");
		}
		catch(Exception e){
			e.printStackTrace();
			throw new DAOException();
		}finally{
		}
		return list;
	}
	
	public List<Movie> getAllMoviesByRating(float rate)throws DAOException{
		List<Movie> list=null;
		Statement st=null;
		ResultSet rs=null;
		try{
			list=new ArrayList<Movie>();
			st=getConnection().createStatement();
			rs=st.executeQuery("select * from anilkumarreddyg_imdb_movie_tbl where avg_rating>="+rate+" order by weightage desc;");
			while(rs.next()){
				list.add(new Movie(rs.getInt("id"), rs.getString("title"),Constants.IMAGE_BASE_URL+rs.getString("image_path"),rs.getString("short_desc"), rs.getFloat("avg_rating")));
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
		return list;
	}
	
	public List<Movie> getAllMoviesByMovieName(String movieName)throws DAOException{
		List<Movie> list=null;
		Statement st=null;
		ResultSet rs=null;
		try{
			list=new ArrayList<Movie>();
			st=getConnection().createStatement();
			rs=st.executeQuery("select * from anilkumarreddyg_imdb_movie_tbl where title like \"%"+movieName+"%\" order by weightage desc;");
			while(rs.next()){
				list.add(new Movie(rs.getInt("id"), rs.getString("title"),Constants.IMAGE_BASE_URL+rs.getString("image_path"),rs.getString("short_desc"), rs.getFloat("avg_rating")));
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
		return list;
	}
}
