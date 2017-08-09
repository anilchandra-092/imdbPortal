package com.alacriti.imdbportal.resources;

import java.util.List;

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

import org.json.simple.JSONObject;

import com.alacriti.imdbportal.delegate.MovieDelegate;
import com.alacriti.imdbportal.models.Movie;

@Path("movies")
public class MovieResource {
	
	MovieDelegate movieDelegate=null;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllMovies(
			@QueryParam("start") int start,
			@QueryParam("end") int end
			){
		List<Movie> list=null;
		try{
			movieDelegate=new MovieDelegate();
			list=movieDelegate.getAllMovies(start,end);
		}catch(Exception e){
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
		List<Movie> list=null;
		try{
			movieDelegate=new MovieDelegate();
			list=movieDelegate.getAllMoviesToAdmin(start,end,categeory);
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	@GET
	@Path("admin/{movieId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Movie getMovieDetailsFully(@PathParam("movieId") int movieId){
		Movie movie=null;
		try{
		movieDelegate=new MovieDelegate();
		movie=movieDelegate.getMovieDetailsFully(movieId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return movie;
	}
	
	
	
	
	@Path("count")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject countOfMovies() throws Exception{
		JSONObject obj = null;
		try{
		obj=new JSONObject();
		movieDelegate=new MovieDelegate();
		obj.put("count",new Integer(movieDelegate.getMoviesCount()));
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return obj;
	}
	
	@Path("count/admin")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject countOfMovies(@QueryParam("categeory") String categeory) throws Exception{
		JSONObject obj = null;
		try{
		obj=new JSONObject();
		movieDelegate=new MovieDelegate();
		obj.put("count",new Integer(movieDelegate.getMoviesCountToAdmin(categeory)));
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return obj;
	}
	
	
	@GET
	@Path("/{movieid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Movie getMovie(@PathParam("movieid") int id){
		Movie movie=null;
		try{
		movieDelegate=new MovieDelegate();
		movie=movieDelegate.getMovie(id);
		}catch(Exception e){
			e.printStackTrace();
		}
		return movie;
	}
	@GET
	@Path("/{movieid}/{userid}")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject getRating(
			@PathParam("movieid") int mid,
			@PathParam("userid") int uid
			){
		JSONObject obj=null;
		int rate=0;
		try{
			obj=new JSONObject();
			movieDelegate=new MovieDelegate();
			rate=movieDelegate.getUserRatingofMovie(mid,uid);
			obj.put("rate", rate);
		}catch(Exception e){
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
		try{
			movieDelegate=new MovieDelegate();
			movieDelegate.setUserRatingtoMovie(mid,uid,rate);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@DELETE
	@Path("/{movieId}")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject deleteMovie(@PathParam("movieId") int mid){
		JSONObject obj=null;
		try{
			obj=new JSONObject();
			movieDelegate=new MovieDelegate();
			movieDelegate.deleteMovie(mid);
			obj.put("Status","Success");
		}catch(Exception e){
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
		JSONObject obj=null;
		try{
			obj=new JSONObject();
			movieDelegate=new MovieDelegate();
			movieDelegate.addMovie(movie);
			obj.put("Status","Success");
		}catch(Exception e){
			obj.put("Status","Fail");
			e.printStackTrace();
		}
		return obj;
	}
	
	@PUT
	@Path("/updateMovie")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject updateMovie(
			@QueryParam("movieId") int movieId,
			Movie movie
			){
		JSONObject obj=null;
		try{
			obj=new JSONObject();
			movieDelegate=new MovieDelegate();
			movieDelegate.updateMovie(movieId,movie);
			obj.put("Status","Success");
		}catch(Exception e){
			obj.put("Status","Fail");
			e.printStackTrace();
		}
		return obj;
	}
	
}
