package com.alacriti.imdbportal.bo.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.alacriti.imdbportal.dao.impl.MovieDAO;
import com.alacriti.imdbportal.exceptions.BOException;
import com.alacriti.imdbportal.exceptions.DAOException;
import com.alacriti.imdbportal.models.Movie;

public class MovieBO extends BaseBO{
	public MovieBO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MovieBO(Connection con) {
		super(con);
		// TODO Auto-generated constructor stub
	}
	
	static MovieDAO mdao=null;
	public List<Movie> getAllMovies(int start,int end) throws BOException{
		List<Movie> list=null;
		List<Movie> result=null;
		try{
			mdao=new MovieDAO(getConnection());
			list =mdao.getAllMovies();
			result=new ArrayList<Movie>();
			if(start<list.size()){
				if(end > list.size()){
						result=list.subList(start, list.size());
				}
				else{
					result=list.subList(start, end);
				}
			}
		}catch (DAOException e) {
			throw new BOException("DAOException Occured");
		} catch (Exception e) {
			throw new BOException();
		}
		return result;
	}
	
	public List<Movie> getAllMoviesToAdmin(int start,int end,String categeory) throws BOException{
		List<Movie> list=null;
		List<Movie> result=null;
		try{
			mdao=new MovieDAO(getConnection());
			list =mdao.getAllMoviesToAdmin(categeory);
			result=new ArrayList<Movie>();
			
			if(start+end > list.size()){
					result=list.subList(start, list.size());
			}
			else{
				result=list.subList(start, end);
			}
		}catch (DAOException e) {
			throw new BOException("DAOException Occured");
		} catch (Exception e) {
			throw new BOException();
		}
		return result;
	}
	
	
	public boolean isGetMoviesParametersValid(int start,int end){
		boolean result=false;
		if(start<=end){
			result=true;
		}
		return result;
	}
	
	public int getMoviesCount() throws BOException{
		int result=0;
		try{
			mdao=new MovieDAO(getConnection());
			result=mdao.getMoviesCount();
		}catch (DAOException e) {
			throw new BOException("DAOException Occured");
		} catch (Exception e) {
			throw new BOException();
		}
		return result;
	}
	
	public int getMoviesCountToAdmin(String categeory) throws BOException{
		int result=0;
		try{
			mdao=new MovieDAO(getConnection());
			result=mdao.getMoviesCountToAdmin(categeory);
		}catch (DAOException e) {
			throw new BOException("DAOException Occured");
		} catch (Exception e) {
			throw new BOException();
		}
		return result;
	} 
	
	
	
	public Movie getMovie(int id) throws BOException{
		Movie movie=null;
		try{
			mdao=new MovieDAO(getConnection());
			movie=mdao.getMovieDeatils(id);
		}catch (DAOException e) {
			throw new BOException("DAOException Occured");
		} catch (Exception e) {
			throw new BOException();
		}
		return movie;
	}
	
	public Movie getMovieDetailsFully(int movieId) throws BOException{
		Movie movie=null;
		try{
			mdao=new MovieDAO(getConnection());
			movie=mdao.getMovieDeatilsFully(movieId);
		}catch (DAOException e) {
			throw new BOException("DAOException Occured");
		} catch (Exception e) {
			throw new BOException();
		}
		return movie;
	}
	
	public int getUserRate(int mid,int uid) throws BOException{
		int result=0;
		try{
			mdao=new MovieDAO(getConnection());
			result=mdao.getUserRate(mid,uid);
		}catch (DAOException e) {
			throw new BOException("DAOException Occured");
		} catch (Exception e) {
			throw new BOException();
		}
		return result;
	}
	public void setUserRatetoMovie(int mid,int uid,int rate) throws BOException{
		try{
			mdao=new MovieDAO(getConnection());
			mdao.setUserRatetoMovie(mid,uid,rate);
		}catch (DAOException e) {
			throw new BOException("DAOException Occured");
		} catch (Exception e) {
			throw new BOException();
		}
	}
	
	public void addMovie(Movie movie) throws BOException{
		try{
			mdao=new MovieDAO(getConnection());
			mdao.addMovieToDB(movie);
		}catch (DAOException e) {
			throw new BOException("DAOException Occured");
		} catch (Exception e) {
			throw new BOException();
		}
	}
	
	public void updateMovie(int movieId,Movie movie) throws BOException{
		try{
			mdao=new MovieDAO(getConnection());
			mdao.updateMovieInDB(movieId,movie);
		}catch (DAOException e) {
			throw new BOException("DAOException Occured");
		} catch (Exception e) {
			throw new BOException();
		}
	}
	
	public void deleteMovie(int mid) throws BOException{
		try{
			mdao=new MovieDAO(getConnection());
			mdao.deleteMovieFromDB(mid);
		}catch (DAOException e) {
			throw new BOException("DAOException Occured");
		} catch (Exception e) {
			throw new BOException();
		}
	}
	
}
