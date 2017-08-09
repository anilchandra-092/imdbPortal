package com.alacriti.imdbportal.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Genre {
	private int id;
	private String name;
	public Genre(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Genre() {
		//  this constructor for json not to create error
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
