package ar.edu.itba.it.paw.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.it.paw.dao.UserDAO;
import ar.edu.itba.it.paw.domain.ConnectionManager;
import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.Type;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.exceptions.DatabaseException;

public class DatabaseUserDAO implements UserDAO{

	private static UserDAO instance;
	
	public static synchronized UserDAO getInstance(){
		if(instance == null)
			instance = new DatabaseUserDAO();
		return instance;
	}
	
	
	@Override
	public User getUser(String username) {
		ConnectionManager manager = ConnectionManager.getInstance();
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		User user = null;

		try {
			
			stmt = conn.prepareStatement("select * from users where username = ?");
			stmt.setString(1, username);
			ResultSet cur = stmt.executeQuery();
			
			if (cur.next()) {
				user = new User(cur.getString("username"),
						cur.getString("firstname"), cur.getString("lastName"),
						cur.getString("pass"), Type.valueOf(cur.getString("usertype")));
				user.setId(cur.getInt("id"));
			} else {
				manager.closeConnection();
				return null;
			}
			
		} catch (Exception e) {
			throw new DatabaseException();
		}
		
		manager.closeConnection();
		return user;
	}

	
	@Override
	public User getUser(int id) {
		ConnectionManager manager = ConnectionManager.getInstance();
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		User user = null;
		
		try{
			stmt = conn.prepareStatement("select * from users where id = ?");
			stmt.setInt(1, id);
			ResultSet cur = stmt.executeQuery();
			
			if(cur.next()){
				user = new User(cur.getString("username"),
						cur.getString("firstname"), cur.getString("lastName"),
						cur.getString("pass"), Type.valueOf(cur.getString("usertype")));
				user.setId(cur.getInt("id"));
			}
			else{
				manager.closeConnection();
				return null;
			}
		} catch(Exception e){
			throw new DatabaseException();
		}
		manager.closeConnection();
		return user;
	}
	
	public List<User> getAllUsers() {
		ConnectionManager manager = ConnectionManager.getInstance();
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		List<User> users = new ArrayList<User>();
		
		try{
			stmt = conn.prepareStatement("select * from users");
			ResultSet cur = stmt.executeQuery();
			
			while(cur.next()){
				User user = null;
				user = new User(cur.getString("username"),
						cur.getString("firstname"), cur.getString("lastName"),
						cur.getString("pass"), Type.valueOf(cur.getString("usertype")));
				user.setId(cur.getInt("id"));
				users.add(user);
			}
			
			manager.closeConnection();
		} catch(Exception e){
			throw new DatabaseException();
		}
		manager.closeConnection();
		return users;
	}

	@Override
	public void saveUser(User user) {
		ConnectionManager manager = ConnectionManager.getInstance();
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		
		try{
			if(user.getId() == -1) {
				stmt = conn.prepareStatement("insert into users (firstname, lastname, pass, usertype, username) values (?,?,?,?,?)");
				stmt.setString(1, user.getFirstName());
				stmt.setString(2, user.getLastName()); 
				stmt.setString(3, user.getPassword());
				stmt.setString(4, user.getType().toString());
				stmt.setString(5, user.getUsername());
			} else {
				stmt = conn.prepareStatement("update users set firstname=?, lastname=?, pass=?, usertype=?, username=?) values (?,?,?,?,?)");
				stmt.setString(1, user.getFirstName());
				stmt.setString(2, user.getLastName()); 
				stmt.setString(3, user.getPassword());
				stmt.setString(4, user.getType().toString()); 
				stmt.setString(5, user.getUsername());
			}
			stmt.execute();
		} catch(Exception e){
			throw new DatabaseException();
		}
		manager.closeConnection();
	}


	@Override
	public void asignIssue(String username, Issue issue) {
		ConnectionManager manager = ConnectionManager.getInstance();
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		
		try {
			stmt = conn.prepareStatement("update issues set assigneeid = (select id from users where username =?) where id = ?");
			stmt.setString(1, username);
			stmt.setInt(2, issue.getId());
			stmt.execute();
		} catch (Exception e) {
			throw new DatabaseException();
		}
		manager.closeConnection();
	}

}
