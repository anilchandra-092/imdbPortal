package com.alacriti.imdbportal.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Movie {
	private int id;
	private String title;
	private String imagePath;
	private String shortDescription;
	private float avgRating;
	private String language;
	private String director;
	private int year;
	private String duration;
	private String detailDescription;
	private String starCast;
	private float weightage;
	private boolean comedy;
	private boolean romantic;
	private boolean scifi;
	private boolean action;

	
	
	public Movie(int id,String title,String imagePath ,String shortDescription, float avgRating,
			String language, String director, int year, String duration,
			String detailDescription, String starCast,float weightage) {
		
		this(id,title,imagePath,shortDescription,avgRating);
		this.language = language;
		this.director = director;
		this.year = year;
		this.duration = duration;
		this.detailDescription = detailDescription;
		this.starCast = starCast;
		this.weightage=weightage;
	}
	
	/*public Movie(String title,String imagePath ,String shortDescription, float avgRating,
			String language, String director, int year, String duration,
			String detailDescription, String starCast,boolean comedy,boolean romantic,boolean scifi,boolean action) {
		this(title,imagePath,shortDescription,avgRating,language,director,year,duration,detailDescription,starCast);
		this.comedy = comedy;
		this.romantic=romantic;
		this.scifi = scifi;
		this.action = action;
	}*/
	
	public Movie(String title,String imagePath ,String shortDescription, float avgRating,
			String language, String director, int year, String duration,
			String detailDescription, String starCast,float weightage) {
		
		this.title = title;
		this.imagePath=imagePath;
		this.shortDescription = shortDescription;
		this.avgRating = avgRating;
		this.language = language;
		this.director = director;
		this.year = year;
		this.duration = duration;
		this.detailDescription = detailDescription;
		this.starCast = starCast;
		this.weightage=weightage;
	}

	public Movie(int id, String title,String imagePath,String shortDescription, float avgRating) {
		super();
		this.id = id;
		this.title = title;
		this.imagePath=imagePath;
		this.shortDescription = shortDescription;
		this.avgRating = avgRating;
	}



	public Movie() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}



	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	
	public float getAvgRating() {
		return avgRating;
	}
	
	public void setAvgRating(float avgRating) {
		this.avgRating = avgRating;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDetailDescription() {
		return detailDescription;
	}

	public void setDetailDescription(String detailDescription) {
		this.detailDescription = detailDescription;
	}

	public String getStarCast() {
		return starCast;
	}

	public void setStarCast(String starCast) {
		this.starCast = starCast;
	}

	public boolean isComedy() {
		return comedy;
	}

	public void setComedy(boolean comedy) {
		this.comedy = comedy;
	}

	public boolean isRomantic() {
		return romantic;
	}

	public void setRomantic(boolean romantic) {
		this.romantic = romantic;
	}

	public boolean isScifi() {
		return scifi;
	}

	public void setScifi(boolean scifi) {
		this.scifi = scifi;
	}

	public boolean isAction() {
		return action;
	}

	public void setAction(boolean action) {
		this.action = action;
	}

	public float getWeightage() {
		return weightage;
	}

	public void setWeightage(float weightage) {
		this.weightage = weightage;
	}
	
	

	
	
}
