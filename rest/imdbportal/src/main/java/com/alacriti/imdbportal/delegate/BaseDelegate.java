package com.alacriti.imdbportal.delegate;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.alacriti.imdbportal.datasource.MySqlDataSource;


public class BaseDelegate {

	private Connection connection;
	public static final Logger log= Logger.getLogger(BaseDelegate.class);
	public BaseDelegate() {
	}

	public void setConnection(Connection _connection) {
		log.debug("=========>> setConnection method in BaseDelegate class ::");
		this.connection = _connection;
	}

	public Connection getConnection() {
		log.debug("=========>> getConnection method in BaseDelegate class ::");
		return connection;
	}

	protected void endDBTransaction(Connection connection) {
		log.debug("=========>> endDBTransaction method in BaseDelegate class ::");
		try {
			connection.commit();

		} catch (SQLException e) {
			System.out.println("SQLException in endDBTransaction " + e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				System.out.println("SQLException in endDBTransaction" + e1.getMessage());
			}
		} catch (Exception e) {
			log.error("Exception in endDBTransaction "+e.getMessage(),e);
			System.out.println("Exception in endDBTransaction" + e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				log.error("SQLException in endDBTransaction "+e.getMessage(),e);
				System.out.println("SQLException in endDBTransaction" + e.getMessage());
			}
		}

	}

	protected void endDBTransaction(Connection connection, boolean isRollback) {
		log.debug("=========>> endDBTransaction method in BaseDelegate class ::");
		if (isRollback) {
			try {
				connection.rollback();
				System.out.println("Rolled Back on some exception....!!!");
			} catch (SQLException e) {
				System.out.println("SQLException in endDBTransaction " + e.getMessage());
			}

			finally {
				try {
					if (connection != null)
						connection.close();
				} catch (SQLException e) {
					log.error("SQLException in endDBTransaction "+e.getMessage(),e);
					System.out.println("SQLException in endDBTransaction " + e.getMessage());
				}
			}
		} else {
			endDBTransaction(connection);
		}

	}

	protected Connection startDBTransaction() {
		log.debug("=========>> startDBTransaction method in BaseDelegate class ::");
		Connection conn = null;
		try {
			if (conn == null || conn.isClosed())
				conn = MySqlDataSource.getInstance().getConnection();

			conn.setAutoCommit(false);
		} catch (SQLException e) {
			log.error("SQLException in startDBTransaction "+e.getMessage(),e);
			System.out.println("SQLException in startDBTransaction " + e.getMessage());
		}
		return conn;

	}
}