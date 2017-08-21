package com.alacriti.imdbportal.delegate;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import com.alacriti.imdbportal.bo.impl.MovieBO;
import com.alacriti.imdbportal.exceptions.BOException;
import com.alacriti.imdbportal.exceptions.ValidateException;
import com.alacriti.imdbportal.models.Movie;

public class MovieDelegate extends BaseDelegate{
	
	public static final Logger log= Logger.getLogger(MovieDelegate.class);
	
	public MovieDelegate() {
		super();
	}
	
	public List<Movie> getAllMovies(int start,int end) throws Exception{
		log.debug("=========>> getAllMovies method in MovieDelegate class ::");
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
			log.error("Validation Exception in getAllMovies : "+ e.getMessage(), e);
			rollBack = true;
			throw e;
		}catch(BOException e){
			log.error("BOException in getAllMovies : "+ e.getMessage(), e);
			rollBack=true;
			e.printStackTrace();
			throw e;
		}catch(Exception e){
			log.error("Exception in getAllMovies : "+ e.getMessage(), e);
			rollBack = true;
			e.printStackTrace();
			throw e;
		}finally{
			endDBTransaction(connection, rollBack);
		}
		
		return list;
	}
	
	public List<Movie> getAllMoviesToAdmin(int start,int end,String categeory) throws Exception{
		log.debug("=========>> getAllMoviesToAdmin method in MovieDelegate class ::");
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
			log.error("Validation Exception in getAllMoviesToAdmin : "+ e.getMessage(), e);
			rollBack=true;
			throw e;
		}catch(BOException e){
			log.error("BOException in getAllMoviesToAdmin : "+ e.getMessage(), e);
			rollBack=true;
			e.printStackTrace();
			throw e;
		}catch(Exception e){
			rollBack=true;
			log.error("Exception in getAllMoviesToAdmin : "+ e.getMessage(), e);
			e.printStackTrace();
			throw e;
		}finally{
			endDBTransaction(connection, rollBack);
		}
		
		return list;
	}
	
	public int getMoviesCount() throws Exception{
		log.debug("=========>> getMoviesCount method in MovieDelegate class ::");
		MovieBO mbo=null;
		int result=0;
		Connection connection=null;
		boolean rollBack = false;
		try{
			connection = startDBTransaction();
			setConnection(connection);
			mbo=new MovieBO(connection);
			result=mbo.getMoviesCount();
		}catch(BOException e){
			log.error("BOException in getMoviesCount : "+ e.getMessage(), e);
			rollBack=true;
			e.printStackTrace();
			throw e;
		}catch(Exception e){
			log.error("Exception in getMoviesCount : "+ e.getMessage(), e);
			rollBack=true;
			throw e;
		}finally{
			endDBTransaction(connection, rollBack);
		}
		return result;
	}
	
	public int getMoviesCountToAdmin(String categeory) throws Exception{
		log.debug("=========>> getMoviesCountToAdmin method in MovieDelegate class ::");
		MovieBO mbo=null;
		int result=0;
		Connection connection=null;
		boolean rollBack = false;
		try{
			connection = startDBTransaction();
			setConnection(connection);
			mbo=new MovieBO(connection);
			result=mbo.getMoviesCountToAdmin(categeory);
		}catch(BOException e){
			log.error("BOException in getMoviesCountToAdmin : "+ e.getMessage(), e);
			rollBack=true;
			e.printStackTrace();
			throw e;
		}catch(Exception e){
			log.error("Exception in getMoviesCountToAdmin : "+ e.getMessage(), e);
			rollBack=true;
			throw e;
		}finally{
			endDBTransaction(connection, rollBack);
		}
		return result;
	}
	
	
	public Movie getMovie(int id) throws Exception{
		log.debug("=========>> getMovie method in MovieDelegate class ::");
		MovieBO mbo=null;
		Movie movie=null;
		Connection connection=null;
		boolean rollBack = false;
		try {
			connection = startDBTransaction();
			setConnection(connection);
			mbo=new MovieBO(connection);
			movie=mbo.getMovie(id);
		} catch(BOException e){
			log.error("BOException in getMovie : "+ e.getMessage(), e);
			rollBack=true;
			e.printStackTrace();
			throw e;
		}catch (Exception e) {
			log.error("Exception in getMovie : "+ e.getMessage(), e);
			e.printStackTrace();
			throw e;
		}finally{
			endDBTransaction(connection, rollBack);
		}
		return movie;
	}
	
	public Movie getMovieDetailsFully(int movieId) throws Exception{
		log.debug("=========>> getMovieDetailsFully method in MovieDelegate class ::");
		MovieBO mbo=null;
		Movie movie=null;
		Connection connection=null;
		boolean rollBack = false;
		try {
			connection = startDBTransaction();
			setConnection(connection);
			mbo=new MovieBO(connection);
			movie=mbo.getMovieDetailsFully(movieId);
		}catch(BOException e){
			log.error("BOException in getMovieDetailsFully : "+ e.getMessage(), e);
			rollBack=true;
			e.printStackTrace();
			throw e;
		}catch (Exception e) {
			log.error("Exception in getMovieDetailsFully : "+ e.getMessage(), e);
			rollBack=true;
			e.printStackTrace();
			throw e;
		}finally{
			endDBTransaction(connection, rollBack);
		}
		return movie;
	}
	
	public int getUserRatingofMovie(int mid,int uid) throws Exception{
		log.debug("=========>> getUserRatingofMovie method in MovieDelegate class ::");
		MovieBO mbo=null;
		int result=0;
		Connection connection=null;
		boolean rollBack = false;
		try{
			connection = startDBTransaction();
			setConnection(connection);
			mbo=new MovieBO(connection);
			result=mbo.getUserRate(mid,uid);
		}catch(BOException e){
			log.error("BOException in getUserRatingofMovie : "+ e.getMessage(), e);
			rollBack=true;
			e.printStackTrace();
			throw e;
		}catch(Exception e){
			log.error("Exception in getUserRatingofMovie : "+ e.getMessage(), e);
			rollBack=true;
			e.printStackTrace();
			throw e;
		}finally{
			endDBTransaction(connection, rollBack);
		}
		return result;
	}
	
	public void setUserRatingtoMovie(int mid,int uid,int rate) throws Exception {
		log.debug("=========>> setUserRatingtoMovie method in MovieDelegate class ::");
		MovieBO mbo=null;
		Connection connection=null;
		boolean rollBack = false;
		try{
			connection = startDBTransaction();
			setConnection(connection);
			mbo=new MovieBO(connection);
			mbo.setUserRatetoMovie(mid,uid,rate);
			
		}catch(BOException e){
			log.error("BOException in setUserRatingtoMovie : "+ e.getMessage(), e);
			rollBack=true;
			e.printStackTrace();
			throw e;
		}catch(Exception e){
			log.error("Exception in setUserRatingtoMovie : "+ e.getMessage(), e);
			rollBack=true;
			e.printStackTrace();
			throw e;
		}finally{
			endDBTransaction(connection, rollBack);
		}
	}
	
	public boolean addMovie(Movie movie) throws Exception{
		log.debug("=========>> addMovie method in MovieDelegate class ::");
		MovieBO mbo=null;
		Connection connection=null;
		boolean rollBack = false,added=false;;
		try{
			connection = startDBTransaction();
			setConnection(connection);
			mbo=new MovieBO(connection);
			if(mbo.isNotNullCheckPass(movie)){
				mbo.addMovie(movie);
				added=true;
			}
			else{
				added=false;
			}
		}catch(BOException e){
			added=false;
			log.error("BOException in addMovie : "+ e.getMessage(), e);
			rollBack=true;
			e.printStackTrace();
			throw e;
		}catch(Exception e){
			added=false;
			log.error("Exception in addMovie : "+ e.getMessage(), e);
			rollBack=true;
			e.printStackTrace();
			throw e;
		}finally{
			endDBTransaction(connection, rollBack);
		}
		return added;
	}
	
	public void updateMovie(int movieId,Movie movie) throws Exception{
		log.debug("=========>> updateMovie method in MovieDelegate class ::");
		MovieBO mbo=null;
		Connection connection=null;
		boolean rollBack = false;
		try{
			connection = startDBTransaction();
			setConnection(connection);
			mbo=new MovieBO(connection);
			mbo.updateMovie(movieId,movie);
			
		}catch(BOException e){
			log.error("BOException in updateMovie : "+ e.getMessage(), e);
			rollBack=true;
			e.printStackTrace();
			throw e;
		}catch(Exception e){
			log.error("Exception in updateMovie : "+ e.getMessage(), e);
			rollBack=true;
			e.printStackTrace();
			throw e;
		}finally{
			endDBTransaction(connection, rollBack);
		}
	}
	
	
	public void deleteMovie(int mid) throws Exception{
		log.debug("=========>> deleteMovie method in MovieDelegate class ::");
		MovieBO mbo=null;
		Connection connection=null;
		boolean rollBack = false;
		try{
			connection = startDBTransaction();
			setConnection(connection);
			mbo=new MovieBO(connection);
			mbo.deleteMovie(mid);
			
		}catch(BOException e){
			log.error("BOException in deleteMovie : "+ e.getMessage(), e);
			rollBack=true;
			e.printStackTrace();
			throw e;
		}
		catch(Exception e){
			log.error("Exception in deleteMovie : "+ e.getMessage(), e);
			rollBack=true;
			e.printStackTrace();
			throw e;
		}finally{
			endDBTransaction(connection, rollBack);
		}
	}

}
