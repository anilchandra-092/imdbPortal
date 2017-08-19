package com.alacriti.imdbportal.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.alacriti.imdbportal.constants.DBColumnConstants;
import com.alacriti.imdbportal.exceptions.DAOException;
import com.alacriti.imdbportal.models.Movie;

public class SearchDAO extends BaseDAO{
	
	public static final Logger log= Logger.getLogger(SearchDAO.class);
	
	public SearchDAO() {
		super();
	}
	
	public SearchDAO(Connection con) {
		super(con);
	}
	
	public List<Movie> getAllMoviesByCategeory(String categeory) throws DAOException{
		log.debug("=========>> getAllMoviesByCategeory method in SearchDAO class ::");
		List<Movie> list=null;
		MovieDAO mdao=null;
		try{
			mdao=new MovieDAO(getConnection());
			list=mdao.getAllMoviesToAdmin(categeory);
		}catch(DAOException e){
			log.error("DAO Exception Occured in getAllMoviesByCategeory: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("DAO Exception occured in MoviesDAO");
		}
		catch(Exception e){
			log.error("Exception Occured in getAllMoviesByCategeory: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
		}
		return list;
	}
	
	public List<Movie> getAllMoviesByRating(float rate)throws DAOException{
		log.debug("=========>> getAllMoviesByRating method in SearchDAO class ::");
		List<Movie> list=null;
		Statement st=null;
		ResultSet rs=null;
		MovieDAO mdao=null;
		try{
			list=new ArrayList<Movie>();
			mdao=new MovieDAO();
			st=getConnection().createStatement();
			rs=st.executeQuery(getAllMoviesByRatingSqlCmd(rate));
			while(rs.next()){
				list.add(mdao.getMovieWithMinimumDetails(rs));
			} 
		}catch(SQLException e){
			log.error("SQL Exception Occured in getAllMoviesByRating: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in selectStatement");
		}
		catch(Exception e){
			log.error("Exception Occured in getAllMoviesByRating: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
		return list;
	}
	
	private String getAllMoviesByRatingSqlCmd(float rate){
		return "select * from anilkumarreddyg_imdb_movie_tbl "
				+" where "+ DBColumnConstants.MOVIE_TBL_AVG_RATING +">="+rate
				+" order by "+ DBColumnConstants.MOVIE_TBL_WEIGHTAGE +" desc;";
	}
	
	public List<Movie> getAllMoviesByMovieName(String movieName)throws DAOException{
		log.debug("=========>> getAllMoviesByMovieName method in SearchDAO class ::");
		List<Movie> list=null;
		Statement st=null;
		ResultSet rs=null;
		MovieDAO mdao=null;
		try{
			list=new ArrayList<Movie>();
			mdao=new MovieDAO();
			st=getConnection().createStatement();
			rs=st.executeQuery(getAllMoviesByMovieNameSqlCmd(movieName));
			while(rs.next()){
				list.add(mdao.getMovieWithMinimumDetails(rs));
			} 
		}catch(SQLException e){
			log.error("SQL Exception Occured in getAllMoviesByMovieName: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException("SQL Exception Occured in selectStatement");
		}
		catch(Exception e){
			log.error("Exception Occured in getAllMoviesByMovieName: " + e.getMessage(), e);
			e.printStackTrace();
			throw new DAOException();
		}finally{
			close(rs);
			close(st);
		}
		return list;
	}

	private String getAllMoviesByMovieNameSqlCmd(String movieName){
		return "select * from anilkumarreddyg_imdb_movie_tbl "
				+" where "+DBColumnConstants.MOVIE_TBL_TITLE+" like \"%"+movieName+"%\" "
				+" order by "+DBColumnConstants.MOVIE_TBL_WEIGHTAGE+" desc;";
	}
}
