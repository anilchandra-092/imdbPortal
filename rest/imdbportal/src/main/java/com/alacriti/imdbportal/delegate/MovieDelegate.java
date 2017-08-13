package com.alacriti.imdbportal.delegate;

import java.sql.Connection;
import java.util.List;

import com.alacriti.imdbportal.bo.impl.MovieBO;
import com.alacriti.imdbportal.exceptions.ValidateException;
import com.alacriti.imdbportal.models.Movie;

public class MovieDelegate extends BaseDelegate{
	
	public MovieDelegate() {
		super();
	}
	
	public List<Movie> getAllMovies(int start,int end) throws Exception{
		MovieBO mbo=null;
		List<Movie> list=null;
		Connection connection=null;
		boolean rollBack = false;
		try{
			connection = startDBTransaction();
			setConnection(connection);
			mbo=new MovieBO(connection);
			if(mbo.isGetMoviesParametersValid(start, end)){
				list=mbo.getAllMovies(start,end);
			}
			else{
				throw new ValidateException("Validation Failed bcz of wrong parameters");
			}
		}catch(ValidateException e){
			rollBack = true;
			throw e;
		}
		catch(Exception e){
			rollBack = true;
			e.printStackTrace();
			throw e;
		}finally{
			endDBTransaction(connection, rollBack);
		}
		
		return list;
	}
	
	public List<Movie> getAllMoviesToAdmin(int start,int end,String categeory) throws Exception{
		MovieBO mbo=null;
		List<Movie> list=null;
		Connection connection=null;
		boolean rollBack = false;
		try{
			connection = startDBTransaction();
			setConnection(connection);
			mbo=new MovieBO(connection);
			if(mbo.isGetMoviesParametersValid(start, end)){
				list=mbo.getAllMoviesToAdmin(start,end,categeory);
			}
			else{
				throw new ValidateException("Validation Failed bcz of wrong parameters");
			}
		}catch(ValidateException e){
			rollBack=true;
			throw e;
		}
		catch(Exception e){
			rollBack=true;
			e.printStackTrace();
			throw e;
		}finally{
			endDBTransaction(connection, rollBack);
		}
		
		return list;
	}
	
	public int getMoviesCount() throws Exception{
		MovieBO mbo=null;
		int result=0;
		Connection connection=null;
		boolean rollBack = false;
		try{
			connection = startDBTransaction();
			setConnection(connection);
			mbo=new MovieBO(connection);
			result=mbo.getMoviesCount();
		}catch(Exception e){
			rollBack=true;
			throw e;
		}finally{
			endDBTransaction(connection, rollBack);
		}
		return result;
	}
	
	public int getMoviesCountToAdmin(String categeory) throws Exception{
		MovieBO mbo=null;
		int result=0;
		Connection connection=null;
		boolean rollBack = false;
		try{
			connection = startDBTransaction();
			setConnection(connection);
			mbo=new MovieBO(connection);
			result=mbo.getMoviesCountToAdmin(categeory);
		}catch(Exception e){
			rollBack=true;
			throw e;
		}finally{
			endDBTransaction(connection, rollBack);
		}
		return result;
	}
	
	
	public Movie getMovie(int id) throws Exception{
		MovieBO mbo=null;
		Movie movie=null;
		Connection connection=null;
		boolean rollBack = false;
		try {
			connection = startDBTransaction();
			setConnection(connection);
			mbo=new MovieBO(connection);
			movie=mbo.getMovie(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			endDBTransaction(connection, rollBack);
		}
		return movie;
	}
	
	public Movie getMovieDetailsFully(int movieId) throws Exception{
		MovieBO mbo=null;
		Movie movie=null;
		Connection connection=null;
		boolean rollBack = false;
		try {
			connection = startDBTransaction();
			setConnection(connection);
			mbo=new MovieBO(connection);
			movie=mbo.getMovieDetailsFully(movieId);
		} catch (Exception e) {
			rollBack=true;
			e.printStackTrace();
			throw e;
		}finally{
			endDBTransaction(connection, rollBack);
		}
		return movie;
	}
	
	public int getUserRatingofMovie(int mid,int uid) throws Exception{
		MovieBO mbo=null;
		int result=0;
		Connection connection=null;
		boolean rollBack = false;
		try{
			connection = startDBTransaction();
			setConnection(connection);
			mbo=new MovieBO(connection);
			result=mbo.getUserRate(mid,uid);
		}catch(Exception e){
			rollBack=true;
			e.printStackTrace();
			throw e;
		}finally{
			endDBTransaction(connection, rollBack);
		}
		return result;
	}
	
	public void setUserRatingtoMovie(int mid,int uid,int rate) throws Exception {
		MovieBO mbo=null;
		Connection connection=null;
		boolean rollBack = false;
		try{
			connection = startDBTransaction();
			setConnection(connection);
			mbo=new MovieBO(connection);
			mbo.setUserRatetoMovie(mid,uid,rate);
			
		}catch(Exception e){
			rollBack=true;
			e.printStackTrace();
			throw e;
		}finally{
			endDBTransaction(connection, rollBack);
		}
	}
	
	public void addMovie(Movie movie) throws Exception{
		MovieBO mbo=null;
		Connection connection=null;
		boolean rollBack = false;
		try{
			connection = startDBTransaction();
			setConnection(connection);
			mbo=new MovieBO(connection);
			mbo.addMovie(movie);
			
		}catch(Exception e){
			rollBack=true;
			e.printStackTrace();
			throw e;
		}finally{
			endDBTransaction(connection, rollBack);
		}
	}
	
	public void updateMovie(int movieId,Movie movie) throws Exception{
		MovieBO mbo=null;
		Connection connection=null;
		boolean rollBack = false;
		try{
			connection = startDBTransaction();
			setConnection(connection);
			mbo=new MovieBO(connection);
			mbo.updateMovie(movieId,movie);
			
		}catch(Exception e){
			rollBack=true;
			e.printStackTrace();
			throw e;
		}finally{
			endDBTransaction(connection, rollBack);
		}
	}
	
	
	public void deleteMovie(int mid) throws Exception{
		MovieBO mbo=null;
		Connection connection=null;
		boolean rollBack = false;
		try{
			connection = startDBTransaction();
			setConnection(connection);
			mbo=new MovieBO(connection);
			mbo.deleteMovie(mid);
			
		}catch(Exception e){
			rollBack=true;
			e.printStackTrace();
			throw e;
		}finally{
			endDBTransaction(connection, rollBack);
		}
	}

}
