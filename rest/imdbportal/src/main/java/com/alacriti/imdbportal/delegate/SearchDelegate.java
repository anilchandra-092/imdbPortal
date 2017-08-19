package com.alacriti.imdbportal.delegate;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import com.alacriti.imdbportal.bo.impl.SearchBO;
import com.alacriti.imdbportal.exceptions.BOException;
import com.alacriti.imdbportal.exceptions.ValidateException;
import com.alacriti.imdbportal.models.Movie;
import com.alacriti.imdbportal.models.SearchModel;

public class SearchDelegate extends BaseDelegate{
	public static final Logger log= Logger.getLogger(SearchDelegate.class);
	public SearchDelegate() {
		super();
	}
	
	
	public List<Movie> getAllMovies(int start,int end,SearchModel searchObj) throws Exception{
		log.debug("=========>> getAllMovies method in SearchDelegate class ::");
		SearchBO sbo=null;
		List<Movie> list=null;
		Connection connection=null;
		boolean rollBack = false;
		try{
			connection = startDBTransaction();
			setConnection(connection);
			sbo=new SearchBO(connection);
			if(sbo.isSearchObjectValuesValid(searchObj)){
				list=sbo.getAllMovies(start,end,searchObj);
			}
			else{
				throw new ValidateException("Validation Failed bcz of wrong parameters");
			}
		}catch(ValidateException e){
			log.error("ValidateException in getAllMovies : "+ e.getMessage(), e);
			rollBack=true;
			throw e;
		}catch(BOException e){
			log.error("BOException in getAllMovies : "+ e.getMessage(), e);
			rollBack=true;
			e.printStackTrace();
			throw e;
		}catch(Exception e){
			log.error("Exception in getAllMovies : "+ e.getMessage(), e);
			rollBack=true;
			e.printStackTrace();
			throw e;
		}finally{
			endDBTransaction(connection, rollBack);
		}
		
		return list;
	}
	
	public int getMoviesCount(SearchModel searchObj) throws Exception{
		log.debug("=========>> getMoviesCount method in SearchDelegate class ::");
		SearchBO sbo=null;
		int result=0;
		Connection connection=null;
		boolean rollBack = false;
		try{
			connection = startDBTransaction();
			setConnection(connection);
			sbo=new SearchBO(connection);
			if(sbo.isSearchObjectValuesValid(searchObj)){
				result=sbo.getMoviesCount(searchObj);
			}
			else{
				throw new ValidateException("Validation Failed bcz of wrong parameters");
			}
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
}
