package ar.edu.itba.it.paw.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import ar.edu.itba.it.paw.domain.exceptions.DatabaseException;

@Component
public class ConnectionManager {
	
	private String username;
	private String password;
	private String connectionString;
	private String driver;
	private Connection connection;

	public ConnectionManager(String connectionString, String username, String password, String driver){
		this.connectionString = connectionString;
		this.username = username;
		this.password = password;
		this.driver = driver;
	}
	
	public Connection getConnection(){
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(connectionString, username, password);
			connection.setAutoCommit(false);
			
		} catch (SQLException e) {
			throw new DatabaseException();
		}catch (ClassNotFoundException e){
			throw new DatabaseException();
		}
		return connection;
	}

	public void closeConnection()
	{
		try
		{
			connection.commit();
			connection.close();
		}
		catch(Exception e)
		{
			throw new DatabaseException();
		}
	}
	
}
