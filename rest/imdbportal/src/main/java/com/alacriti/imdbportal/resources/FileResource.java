package com.alacriti.imdbportal.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.json.simple.JSONObject;

import com.alacriti.imdbportal.delegate.MovieDelegate;
import com.alacriti.imdbportal.models.Movie;
import com.alacriti.imdbportal.models.MovieImageFileForm;

@Path("file")
public class FileResource {
	@POST
	@Path("/uploadMovieDetails")
	@Consumes("multipart/form-data")
	public Response uploadFile(@MultipartForm MovieImageFileForm form) {
		
		DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
		String fileName=df.format(new Date())+"."+form.getFileType();
		String fileLocation = "/home/anilkumarreddyg/wildfly-10.1.0.Final/assets/images/imdbportal/"+fileName;
		System.out.println("FileLocation : "+fileLocation);		
		System.out.println("File in form :"+form.getFile0());
		writeFile(form.getFile0(), fileLocation);
		
		Movie movie=new Movie();
		JSONObject obj=null;
		MovieDelegate movieDelegate=null;
		try{
			System.out.println("Form title ===>"+form.getTitle());
			movie.setTitle(form.getTitle());
			System.out.println("movie title ===>"+movie.getTitle());
			movie.setImagePath(fileName);
			movie.setShortDescription(form.getShortDescription());
			movie.setLanguage(form.getLanguage());
			movie.setDirector(form.getDirector());
			movie.setYear(form.getYear());
			movie.setDuration(form.getDuration());
			movie.setDetailDescription(form.getDetailDescription());
			movie.setStarCast(form.getStarCast());
			movie.setComedy(form.isComedy());
			movie.setRomantic(form.isRomantic());
			movie.setAction(form.isAction());
			movie.setScifi(form.isScifi());
			
			
			try{
				obj=new JSONObject();
				movieDelegate=new MovieDelegate();
				movieDelegate.addMovie(movie);
				obj.put("Status","Success");
			}catch(Exception e){
				obj.put("Status","Fail");
				e.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Exception occured in writing file==>");
		}
		
		return Response.status(Status.OK).entity(obj).build();
	}


	private boolean writeFile(byte[] content, String filename) {
		try {

			File file = new File(filename);

			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream fop = new FileOutputStream(file);

			fop.write(content);
			fop.flush();
			fop.close();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}