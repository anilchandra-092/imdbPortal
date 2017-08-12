package com.alacriti.imdbportal.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
	private int id;
	private String uname;
	private String password;
	private String email;
	private String role;
	private String status;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(int id, String uname, String email, String role) {
		this(uname,email,role);
		this.id = id;
	}

	public User(String uname, String email, String role) {
		super();
		this.uname = uname;
		this.email = email;
		this.role = role;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
