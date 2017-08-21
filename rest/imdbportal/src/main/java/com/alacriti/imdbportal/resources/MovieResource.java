package com.alacriti.imdbportal.resources;

import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.alacriti.imdbportal.delegate.MovieDelegate;
import com.alacriti.imdbportal.models.Movie;

@Path("movies")
@Singleton
public class MovieResource {
	
	public static final Logger log= Logger.getLogger(MovieResource.class);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllMovies(@QueryParam("start") int start,@QueryParam("end") int end){
		log.debug("=========>> getAllMovies method in MovieResource class ::");
		List<Movie> list=null;
		MovieDelegate movieDelegate=null;
		
		try{
			movieDelegate=new MovieDelegate();
			list=movieDelegate.getAllMovies(start,end);
		}catch(Exception e){
			log.error("Exception in getAllMovies of MovieResource : "+ e.getMessage(), e);
			e.printStackTrace();
		}
		return Response.status(200).entity(list).build();
	}
	
	@GET
	@Path("admin")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> getAllMovies(
			@QueryParam("start") int start,
			@QueryParam("end") int end,
			@QueryParam("categeory") String categeory
			){
		log.debug("=========>> getAllMovies method in MovieResource class ::");
		List<Movie> list=null;
		MovieDelegate movieDelegate=null;
		try{
			movieDelegate=new MovieDelegate();
			list=movieDelegate.getAllMoviesToAdmin(start,end,categeory);
		}catch(Exception e){
			log.error("Exception in getAllMovies of MovieResource : "+ e.getMessage(), e);
			e.printStackTrace();
		}
		return list;
	}
	
	@GET
	@Path("admin/{movieId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Movie getMovieDetailsFully(@PathParam("movieId") int movieId){
		log.debug("=========>> getMovieDetailsFully method in MovieResource class ::");
		Movie movie=null;
		MovieDelegate movieDelegate=null;
		try{
		movieDelegate=new MovieDelegate();
		movie=movieDelegate.getMovieDetailsFully(movieId);
		}catch(Exception e){
			log.error("Exception in getMovieDetailsFully of MovieResource : "+ e.getMessage(), e);
			e.printStackTrace();
		}
		return movie;
	}
	
	@GET
	@Path("/{movieid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Movie getMovie(@PathParam("movieid") int id){
		log.debug("=========>> getMovie method in MovieResource class ::");
		Movie movie=null;
		MovieDelegate movieDelegate=null;
		try{
		movieDelegate=new MovieDelegate();
		movie=movieDelegate.getMovieDetailsFully(id);
		}catch(Exception e){
			log.error("Exception in getMovie of MovieResource : "+ e.getMessage(), e);
			e.printStackTrace();
		}
		return movie;
	}
	
	
	@Path("count")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject countOfMovies() throws Exception{
		log.debug("=========>> countOfMovies method in MovieResource class ::");
		JSONObject obj = null;
		MovieDelegate movieDelegate=null;
		try{
		obj=new JSONObject();
		movieDelegate=new MovieDelegate();
		obj.put("count",new Integer(movieDelegate.getMoviesCount()));
		}catch(Exception e){
			log.error("Exception in countOfMovies of MovieResource : "+ e.getMessage(), e);			
			e.printStackTrace();
			
		}
		return obj;
	}
	
	@Path("count/admin")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject countOfMovies(@QueryParam("categeory") String categeory) throws Exception{
		log.debug("=========>> countOfMovies method in MovieResource class ::");
		JSONObject obj = null;
		MovieDelegate movieDelegate=null;
		try{
		obj=new JSONObject();
		movieDelegate=new MovieDelegate();
		obj.put("count",new Integer(movieDelegate.getMoviesCountToAdmin(categeory)));
		}catch(Exception e){
			log.error("Exception in countOfMovies of MovieResource : "+ e.getMessage(), e);
			e.printStackTrace();
			
		}
		return obj;
	}
	
	

	@GET
	@Path("/{movieid}/{userid}")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject getRating(@PathParam("movieid") int mid,@PathParam("userid") int uid){
		log.debug("=========>> getRating method in MovieResource class ::");
		JSONObject obj=null;
		MovieDelegate movieDelegate=null;
		int rate=0;
		try{
			obj=new JSONObject();
			movieDelegate=new MovieDelegate();
			rate=movieDelegate.getUserRatingofMovie(mid,uid);
			obj.put("rate", rate);
		}catch(Exception e){
			log.error("Exception in getRating of MovieResource : "+ e.getMessage(), e);
			e.printStackTrace();
		}
		return obj;
	}
	
	@POST
	@Path("setRate")
	public void setRating(
			@QueryParam("movieid") int mid,
			@QueryParam("userid") int uid,
			@QueryParam("rate") int rate
			){
		log.debug("=========>> setRating method in MovieResource class ::");
		MovieDelegate movieDelegate=null;
		try{
			movieDelegate=new MovieDelegate();
			movieDelegate.setUserRatingtoMovie(mid,uid,rate);
		}catch(Exception e){
			log.error("Exception in setRating of MovieResource : "+ e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	@DELETE
	@Path("/{movieId}")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject deleteMovie(@PathParam("movieId") int mid){
		log.debug("=========>> deleteMovie method in MovieResource class ::");
		JSONObject obj=null;
		MovieDelegate movieDelegate=null;
		try{
			obj=new JSONObject();
			movieDelegate=new MovieDelegate();
			movieDelegate.deleteMovie(mid);
			obj.put("Status","Success");
		}catch(Exception e){
			log.error("Exception in deleteMovie of MovieResource : "+ e.getMessage(), e);
			obj.put("Status","Fail");
			e.printStackTrace();
		}
		return obj;
	}
	
	@POST
	@Path("/addMovie")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject addMovie(Movie movie){
		log.debug("=========>> addMovie method in MovieResource class ::");
		JSONObject obj=null;
		MovieDelegate movieDelegate=null;
		boolean added=false;
		try{
			obj=new JSONObject();
			movieDelegate=new MovieDelegate();
			added=movieDelegate.addMovie(movie);
			if(added){
				obj.put("Status","Success");
			}
			else{
				obj.put("Status","Fail");
			}
		}catch(Exception e){
			log.error("Exception in addMovie of MovieResource : "+ e.getMessage(), e);
			obj.put("Status","Fail");
			e.printStackTrace();
		}
		return obj;
	}
	
	@PUT
	@Path("/updateMovie")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject updateMovie(@QueryParam("movieId") int movieId,Movie movie){
		log.debug("=========>> updateMovie method in MovieResource class ::");
		MovieDelegate movieDelegate=null;
		JSONObject obj=null;
		try{
			obj=new JSONObject();
			movieDelegate=new MovieDelegate();
			movieDelegate.updateMovie(movieId,movie);
			obj.put("Status","Success");
		}catch(Exception e){
			log.error("Exception in updateMovie of MovieResource : "+ e.getMessage(), e);
			obj.put("Status","Fail");
			e.printStackTrace();
		}
		return obj;
	}
	
}
