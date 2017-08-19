package com.alacriti.imdbportal.dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.alacriti.imdbportal.bo.impl.BaseBO;



public class BaseDAO {

	private Connection connection;
	public static final Logger log= Logger.getLogger(BaseDAO.class);
	public BaseDAO() {

	}

	public BaseDAO(Connection _connection) {
		log.debug("=========>> parametrized constructor in BaseDAO class ::");
		this.connection = _connection;

	}

	public Connection getConnection() {
		log.debug("=========>> getConnection method in BaseDAO class ::");
		return connection;
	}

	public void setConnection(Connection connection) {
		log.debug("=========>> setConnection method in BaseDAO class ::");
		this.connection = connection;
	}

	public void close(ResultSet rs) {
		log.debug("=========>> close(ResultSet) method in BaseDAO class ::");
		
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Exception in close " + e.getMessage(), e);
			}
		}
	}

	public void close(Statement stmt) {
		log.debug("=========>> close(Statement) method in BaseDAO class ::");
		
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Exception in close " + e.getMessage(), e);
			}
		}
	}

	public void close(PreparedStatement stmt, ResultSet rs) {
		log.debug("=========>> close(PreparedStatement,ResultSet) method in BaseDAO class ::");
		close(stmt);
		close(rs);

	}

	protected PreparedStatement getPreparedStatement(Connection connection, String sqlCmd) throws SQLException {
		log.debug("=========>> getPreparedStatement method in BaseDAO class ::");
		log.info("getPreparedStatement :: "+sqlCmd);
		try {
			return connection.prepareStatement(sqlCmd);
		} catch (SQLException e) {
			log.error("SQLException in getPreparedStatement " + e.getMessage(), e);
			throw e;
		}
	}

	protected PreparedStatement getPreparedStatementReturnPK(Connection connection, String sqlCmd) throws SQLException {
		log.debug("=========>> getPreparedStatementReturnPK method in BaseDAO class ::");
		try {
			return connection.prepareStatement(sqlCmd, Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {
			log.error("Exception in getPreparedStatementReturnPK " + e.getMessage(), e);
			throw e;
		}
	}

	protected ResultSet executeQuery(PreparedStatement ps) throws SQLException {
		log.debug("=========>> executeQuery method in BaseDAO class ::");
		try {

			return ps.executeQuery();
		} catch (SQLException e) {
			log.error("SQLException in executeQuery " + e.getMessage(), e);
			throw e;
		}
	}

	protected int executeUpdate(PreparedStatement ps) throws SQLException {
		
		log.debug("=========>> executeUpdate method in BaseDAO class ::");

		try {

			return ps.executeUpdate();
		} catch (SQLException e) {
			log.error("SQLException in executeUpdate " + e.getMessage(), e);
			throw e;
		} finally {
			close(ps);
		}
	}

		
}
