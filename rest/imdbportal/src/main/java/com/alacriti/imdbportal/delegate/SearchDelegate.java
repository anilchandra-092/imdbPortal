package com.alacriti.imdbportal.delegate;

import java.sql.Connection;
import java.util.List;

import com.alacriti.imdbportal.bo.impl.SearchBO;
import com.alacriti.imdbportal.exceptions.ValidateException;
import com.alacriti.imdbportal.models.Movie;
import com.alacriti.imdbportal.models.SearchModel;

public class SearchDelegate extends BaseDelegate{
	
	static SearchBO sbo=null;
	
	public SearchDelegate() {
		super();
	}
	
	
	public List<Movie> getAllMovies(int start,int end,SearchModel searchObj) throws Exception{
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
	public int getMoviesCount(SearchModel searchObj) throws Exception{
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
		}catch(Exception e){
			rollBack=true;
			throw e;
		}finally{
			endDBTransaction(connection, rollBack);
		}
		return result;
	}
}
