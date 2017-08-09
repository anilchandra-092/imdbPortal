package com.alacriti.imdbportal.models;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Rate {
	private int userId;
	private int movieId;
	private float rating;
	private Date created;
	
	public Rate(int userId, int movieId,float rating) {
		super();
		this.userId = userId;
		this.movieId=movieId;
		this.rating = rating;
		this.created = new Date();
	}
	
	public Rate() {
		// this constructor for json not to create error
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	
	
}
