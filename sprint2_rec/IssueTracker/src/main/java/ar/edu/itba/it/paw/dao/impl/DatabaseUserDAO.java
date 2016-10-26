package ar.edu.itba.it.paw.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.dao.UserDAO;
import ar.edu.itba.it.paw.domain.ConnectionManager;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.Type;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.exceptions.DatabaseException;

@Repository
public class DatabaseUserDAO implements UserDAO{

	private ConnectionManager manager;
	
	@Autowired
	public DatabaseUserDAO(ConnectionManager manager){
		this.manager = manager;
	}
	
	
	@Override
	public User getUser(String username) {
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		User user = null;

		try {
			
			stmt = conn.prepareStatement("select * from users where username = ?");
			stmt.setString(1, username);
			ResultSet cur = stmt.executeQuery();
			
			if (cur.next()) {
				user = getNewUser(cur);
			} else {
				manager.closeConnection();
				return null;
			}
			
		} catch (Exception e) {
			throw new DatabaseException();
		} finally {
			manager.closeConnection();
		}
		return user;
	}
	
	
	@Override
	public User getUser(int id) {
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		User user = null;
		
		try{
			stmt = conn.prepareStatement("select * from users where id = ?");
			stmt.setInt(1, id);
			ResultSet cur = stmt.executeQuery();
			
			if(cur.next()){
				user = getNewUser(cur);
			}
		} catch(Exception e){
			throw new DatabaseException();
		} finally {
			manager.closeConnection();
		}
		return user;
	}
	
	public List<User> getAllUsers() {
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		List<User> users = new ArrayList<User>();
		
		try{
			stmt = conn.prepareStatement("select * from users");
			ResultSet cur = stmt.executeQuery();
			
			while(cur.next()){
				User user = getNewUser(cur);
				users.add(user);
			}
			
		} catch(Exception e){
			throw new DatabaseException();
		} finally {
			manager.closeConnection();
		}
		return users;
	}

	
	@Override
	public void saveUser(User user) {
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		
		try{
			if(user.isNew()) {
				stmt = conn.prepareStatement("insert into users (firstname, lastname, pass, usertype, username, is_active) values (?,?,?,?,?,?)");
				stmt.setString(1, user.getFirstName());
				stmt.setString(2, user.getLastName()); 
				stmt.setString(3, user.getPassword());
				stmt.setString(4, user.getType().toString());
				stmt.setString(5, user.getUsername());
				stmt.setBoolean(6, user.isActive());
			} else {
				stmt = conn.prepareStatement("update users set firstname=?, lastname=?, pass=?, usertype=?, username=?, is_active=? where id=?");
				stmt.setString(1, user.getFirstName());
				stmt.setString(2, user.getLastName()); 
				stmt.setString(3, user.getPassword());
				stmt.setString(4, user.getType().toString()); 
				stmt.setString(5, user.getUsername());
				stmt.setBoolean(6, user.isActive());
				stmt.setInt(7, user.getId());
			}
			stmt.execute();
		} catch(Exception e){
			throw new DatabaseException();
		} finally {
			manager.closeConnection();
		}
	}
	
	@Override
	public List<User> getUsersFromProject(Project project) {
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		List<User> users = new ArrayList<User>();
		
		try {
			stmt = conn.prepareStatement("select users.id, username, firstname, lastname, pass, usertype, is_active " +
					"from rel_users_projects join users on rel_users_projects.user_fk = users.id where rel_users_projects.user_fk = users.id and rel_users_projects.project_fk = ?");
			stmt.setInt(1, project.getId());
			ResultSet cur = stmt.executeQuery();
			
			while(cur.next()){
				User user = getNewUser(cur);
				users.add(user);
			}
		} catch (Exception e) {
			throw new DatabaseException();
		} finally {
			manager.closeConnection();
		}
		return users;
	}

	
	private User getNewUser(ResultSet cur) {
		
		User user= null;
		
		try {
			user = new User(cur.getString("username"),
					cur.getString("firstname"), cur.getString("lastName"),
					cur.getString("pass"), Type.valueOf(cur.getString("usertype")), cur.getBoolean("is_active"));
			user.setId(cur.getInt("id"));
		} catch (SQLException e) {
			throw new DatabaseException();
		}
		
		return user;
	}
	
}
