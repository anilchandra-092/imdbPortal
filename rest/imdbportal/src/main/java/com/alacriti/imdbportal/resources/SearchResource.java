package com.alacriti.imdbportal.resources;

import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.alacriti.imdbportal.delegate.SearchDelegate;
import com.alacriti.imdbportal.models.Movie;
import com.alacriti.imdbportal.models.SearchModel;

@Path("search")
@Singleton
public class SearchResource {
	
	public static final Logger log= Logger.getLogger(SearchResource.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> getAllMovies(
			@QueryParam("start") int start,
			@QueryParam("end") int end,
			SearchModel searchObj
			){
		log.debug("=========>> getAllMovies method in SearchResource class ::");
		SearchDelegate searchDelegate=null;
		List<Movie> list=null;
		try{
			searchDelegate=new SearchDelegate();
			list=searchDelegate.getAllMovies(start,end,searchObj);
		}catch(Exception e){
			log.error("Exception in getAllMovies of SearchResource : "+ e.getMessage(), e);
			e.printStackTrace();
		}
		return list;
	}
	
	@Path("count")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject countOfMovies(SearchModel searchObj) throws Exception{
		log.debug("=========>> countOfMovies method in SearchResource class ::");
		JSONObject obj = null;
		SearchDelegate searchDelegate=null;
		try{
		obj=new JSONObject();
		searchDelegate=new SearchDelegate();
		obj.put("count",new Integer(searchDelegate.getMoviesCount(searchObj)));
		}catch(Exception e){
			log.error("Exception in countOfMovies of SearchResource : "+ e.getMessage(), e);
			e.printStackTrace();
			
		}
		return obj;
	}
}
