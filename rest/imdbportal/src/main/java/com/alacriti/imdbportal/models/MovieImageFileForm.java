package com.alacriti.imdbportal.models;

import javax.ws.rs.FormParam;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class MovieImageFileForm {
	
	private byte[] file0;
	private String fileType;
	private String title;
	private String shortDescription;
	private String language;
	private String director;
	private int year;
	private String duration;
	private String detailDescription;
	private String starCast;
	private boolean comedy;
	private boolean romantic;
	private boolean scifi;
	private boolean action;
		
	public byte[] getFile0() {
		return file0;
	}
	
	@FormParam("imageFile")
	@PartType("application/octet-stream")
	public void setFile0(byte[] file0) {
		this.file0 = file0;
	}
	public String getTitle() {
		return title;
	}
	
	public String getFileType() {
		return fileType;
	}
	@FormParam("fileType")
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@FormParam("title")
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getShortDescription() {
		return shortDescription;
	}
	@FormParam("shortDescription")
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getLanguage() {
		return language;
	}
	@FormParam("language")
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getDirector() {
		return director;
	}
	@FormParam("director")
	public void setDirector(String director) {
		this.director = director;
	}
	public int getYear() {
		return year;
	}
	@FormParam("year")
	public void setYear(int year) {
		this.year = year;
	}
	public String getDuration() {
		return duration;
	}
	@FormParam("duration")
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getDetailDescription() {
		return detailDescription;
	}
	@FormParam("detailDescription")
	public void setDetailDescription(String detailDescription) {
		this.detailDescription = detailDescription;
	}
	public String getStarCast() {
		return starCast;
	}
	@FormParam("starCast")
	public void setStarCast(String starCast) {
		this.starCast = starCast;
	}
	public boolean isComedy() {
		return comedy;
	}
	@FormParam("comedy")
	public void setComedy(boolean comedy) {
		this.comedy = comedy;
	}
	public boolean isRomantic() {
		return romantic;
	}
	@FormParam("romantic")
	public void setRomantic(boolean romantic) {
		this.romantic = romantic;
	}
	public boolean isScifi() {
		return scifi;
	}
	@FormParam("scifi")
	public void setScifi(boolean scifi) {
		this.scifi = scifi;
	}
	
	public boolean isAction() {
		return action;
	}
	@FormParam("action")
	public void setAction(boolean action) {
		this.action = action;
	}
	
}
