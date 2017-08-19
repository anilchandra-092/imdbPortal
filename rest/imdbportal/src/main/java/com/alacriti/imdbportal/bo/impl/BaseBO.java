package com.alacriti.imdbportal.bo.impl;


import java.sql.Connection;

import org.apache.log4j.Logger;



public class BaseBO {
	private Connection connection = null;
	public static final Logger log= Logger.getLogger(BaseBO.class);
	public BaseBO() {
	}

	public BaseBO(Connection connection) {
		log.debug("=========>> parametrized constructor in BaseBO class ::");
		this.connection = connection;
	}

	public Connection getConnection() {
		log.debug("=========>> getConnection method in BaseBO class ::");
		return connection;
	}

	public void setConnection(Connection connection) {
		log.debug("=========>> setConnection method in BaseBO class ::");
		this.connection = connection;
	}
}
