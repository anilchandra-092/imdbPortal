package com.alacriti.imdbportal.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.json.simple.JSONObject;

import com.alacriti.imdbportal.delegate.MovieDelegate;
import com.alacriti.imdbportal.models.Movie;
import com.alacriti.imdbportal.models.MovieImageFileForm;

@Path("file")
@Singleton
public class FileResource {
	
	public static final Logger log= Logger.getLogger(FileResource.class);
	
	@POST
	@Path("/uploadMovieDetails")
	@Consumes("multipart/form-data")
	public Response uploadFile(@MultipartForm MovieImageFileForm form) {
		JSONObject obj=null;
		MovieDelegate movieDelegate=null;
		log.debug("=========>> uploadFile method in FileResource class ::");
			try{
				obj=new JSONObject();
				movieDelegate=new MovieDelegate();
				movieDelegate.addMovie(getMovieFromFormData(form));
				obj.put("Status","Success");
			}catch(Exception e){
				log.error("Exception in uploadFile : "+ e.getMessage(), e);
				obj.put("Status","Fail");
				e.printStackTrace();
			}
		
		return Response.status(Status.OK).entity(obj).build();
	}

	private Movie getMovieFromFormData(MovieImageFileForm form) throws Exception{
		Movie movie=null;
		log.debug("=========>> getMovieFromFormData method in FileResource class ::");
		try{
			DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
			String fileName=df.format(new Date())+"."+form.getFileType();
			String fileLocation = "/home/anilkumarreddyg/wildfly-10.1.0.Final/assets/images/imdbportal/"+fileName;
			System.out.println("FileLocation : "+fileLocation);		
			System.out.println("File in form :"+form.getFile0());
			writeFile(form.getFile0(), fileLocation);
			movie=new Movie();
			movie.setTitle(form.getTitle());
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
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Exception occured in writing file data into movie Object");
			log.error("Exception occured in writing file data"+ e.getMessage(), e);
			throw e;
		}
		
		return movie;
	}
	
	private boolean writeFile(byte[] content, String filename) {
		log.debug("=========>> writeFile method in FileResource class ::");
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
			log.error("Exception occured in writing file into a fileObject"+ e.getMessage(), e);
			e.printStackTrace();
			return false;
		}
	}
}