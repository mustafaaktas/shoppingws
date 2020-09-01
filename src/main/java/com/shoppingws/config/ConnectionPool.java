package com.shoppingws.config;

import com.shoppingws.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


@Repository
public class ConnectionPool {

	@Autowired
	@Qualifier("dataSource")
	DataSource dataSource;



	private Connection loadWebSphereDataSource(String dataSourceName) throws NamingException, SQLException {
		DataSource ds;

		Connection connection;
		/*** WebSphere Config */
		InitialContext context;
		context = new InitialContext();
		ds = (DataSource) context.lookup(dataSourceName);
		connection = ds.getConnection();

		return connection;
	}
	
	private Connection initConnection() {

		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			Helper.errorLogger(getClass(), e);
			return null;
		}

	}
	
	
	public void closeConnection(Connection connection) {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (RuntimeException re) {
			Helper.errorLogger(getClass(), re);
		} catch (Exception e) {
			Helper.errorLogger(getClass(), e);
		}
	}

	public Connection getConnection() {
		return initConnection();
	}

	public void closeStatement(Statement st) {
		try {
			if (st != null) {
				st.close();
			}
		} catch (RuntimeException | SQLException e) {
			Helper.errorLogger(getClass(), e);
		}
	}
	
	public void closeResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (RuntimeException | SQLException e) {
			Helper.errorLogger(getClass(), e);
		}
	}

	public void rollBack(Connection con) {
		try {
			con.rollback();
		} catch (RuntimeException | SQLException e) {
			Helper.errorLogger(getClass(), e);
		}
	}
}
