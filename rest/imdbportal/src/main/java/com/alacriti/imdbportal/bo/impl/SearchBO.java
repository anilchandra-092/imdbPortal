package com.alacriti.imdbportal.bo.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.alacriti.imdbportal.constants.DBColumnConstants;
import com.alacriti.imdbportal.dao.impl.SearchDAO;
import com.alacriti.imdbportal.exceptions.BOException;
import com.alacriti.imdbportal.exceptions.DAOException;
import com.alacriti.imdbportal.exceptions.ValidateException;
import com.alacriti.imdbportal.models.Movie;
import com.alacriti.imdbportal.models.SearchModel;

public class SearchBO extends BaseBO{
	public static final Logger log= Logger.getLogger(SearchBO.class);

	public SearchBO() {
		super();
	}
	
	public SearchBO(Connection con) {
		super(con);
	}
	
	public boolean isSearchObjectValuesValid(SearchModel searchObj) throws ValidateException{
		log.debug("=========>> isSearchObjectValuesValid method in SearchBO class ::");
		boolean result=false;
		String searchOption;
		try{
			searchOption=searchObj.getSearchOption();
			if(searchOption.equals("categeory")){
				String categeory=searchObj.getSearchKey().toLowerCase().trim();
				if(
					categeory.equals(DBColumnConstants.GENRES_TBL_NAME_COMEDY)||
					categeory.equals(DBColumnConstants.GENRES_TBL_NAME_ROMANTIC)||
					categeory.equals(DBColumnConstants.GENRES_TBL_NAME_SCIFI)||
					categeory.equals(DBColumnConstants.GENRES_TBL_NAME_ACTION)
				){
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
			//e.printStackTrace();
			log.error("Exception in isSearchObjectValuesValid of searchBo " + e.getMessage(), e);
			throw new ValidateException("validation Failed in SearchBO");
		}
		return result;
	}
	
	public List<Movie> getAllMovies(int start,int end,SearchModel searchObj) throws BOException{
		log.debug("=========>> getAllMovies method in SearchBO class ::");
		SearchDAO searchDao=null;
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
			if(start<list.size()){
				if(end > list.size()){
						result=list.subList(start, list.size());
				}
				else{
					result=list.subList(start, end);
				}
			}
			
		}catch (DAOException e) {
			log.error("DAOException in getAllMovies of searchBo " + e.getMessage(), e);
			throw new BOException("DAOException Occured");
		} catch (Exception e) {
			log.error("Exception in getAllMovies of searchBo " + e.getMessage(), e);
			throw new BOException();
		}
		return result;
	}
	
	public int getMoviesCount(SearchModel searchObj) throws BOException{
		log.debug("=========>> getMoviesCount method in SearchBO class ::");
		SearchDAO searchDao=null;
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
			log.error("DAOException in getMoviesCount of searchBo " + e.getMessage(), e);
			throw new BOException("DAOException Occured");
		} catch (Exception e) {
			log.error("Exception in getMoviesCount of searchBo " + e.getMessage(), e);
			throw new BOException();
		}
		return result;
	}
	
	
}
