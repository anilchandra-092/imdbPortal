package com.alacriti.imdbportal.bo.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.alacriti.imdbportal.dao.impl.SearchDAO;
import com.alacriti.imdbportal.exceptions.BOException;
import com.alacriti.imdbportal.exceptions.DAOException;
import com.alacriti.imdbportal.exceptions.ValidateException;
import com.alacriti.imdbportal.models.Movie;
import com.alacriti.imdbportal.models.SearchModel;

public class SearchBO extends BaseBO{

	SearchDAO searchDao=null;
	
	public SearchBO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SearchBO(Connection con) {
		super(con);
		// TODO Auto-generated constructor stub
	}
	
	public boolean isSearchObjectValuesValid(SearchModel searchObj) throws ValidateException{
		boolean result=false;
		String searchOption;
		try{
			searchOption=searchObj.getSearchOption();
			if(searchOption.equals("categeory")){
				String categeory=searchObj.getSearchKey().toLowerCase().trim();
				if(categeory.equals("comedy")||categeory.equals("romantic")||categeory.equals("scifi")||categeory.equals("action")){
					result=true;
				}else{
					result=false;
				}
			}
			else if(searchOption.equals("rating")){
				Float.parseFloat(searchObj.getSearchKey());
				result=true;
			}
			else if(searchOption.equals("movie")){
				result=true;
			}
			else{
				result=false;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new ValidateException("validation Failed in SearchBO");
		}
		return result;
	}
	
	public List<Movie> getAllMovies(int start,int end,SearchModel searchObj) throws BOException{
		List<Movie> list=null;
		List<Movie> result=null;
		String searchOption;
		try{
			searchOption=searchObj.getSearchOption();
			searchDao=new SearchDAO(getConnection());
			if(searchOption.equals("categeory")){
				String categeory=searchObj.getSearchKey().toLowerCase().trim();
				list=searchDao.getAllMoviesByCategeory(categeory);
			}
			else if(searchOption.equals("rating")){
				float rate=Float.parseFloat(searchObj.getSearchKey().trim());
				list=searchDao.getAllMoviesByRating(rate);
			}
			else if(searchOption.equals("movie")){
				list=searchDao.getAllMoviesByMovieName(searchObj.getSearchKey().trim());
			}
			
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
	
	public int getMoviesCount(SearchModel searchObj) throws BOException{
		int result=0;
		String searchOption;
		try{
			searchOption=searchObj.getSearchOption();
			searchDao=new SearchDAO(getConnection());
			if(searchOption.equals("categeory")){
				String categeory=searchObj.getSearchKey().toLowerCase().trim();
				result=searchDao.getAllMoviesByCategeory(categeory).size();
			}
			else if(searchOption.equals("rating")){
				float rate=Float.parseFloat(searchObj.getSearchKey().trim());
				result=searchDao.getAllMoviesByRating(rate).size();
			}
			else if(searchOption.equals("movie")){
				result=searchDao.getAllMoviesByMovieName(searchObj.getSearchKey().trim()).size();
			}
			
		}catch (DAOException e) {
			throw new BOException("DAOException Occured");
		} catch (Exception e) {
			throw new BOException();
		}
		return result;
	}
	
	
}
