package ar.edu.itba.it.paw.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.it.paw.dao.ProjectDAO;
import ar.edu.itba.it.paw.domain.ConnectionManager;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.exceptions.DatabaseException;

public class DatabaseProjectDAO implements ProjectDAO {

	private static ProjectDAO instance;
	
	public static synchronized ProjectDAO getInstance(){
		if(instance == null)
			instance = new DatabaseProjectDAO();
		return instance;
	}
	
	@Override
	public void saveProject(Project project) {
		ConnectionManager manager = ConnectionManager.getInstance();
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		
		try{
			if(project.getId() == -1){
				stmt = conn.prepareStatement("insert into projects (code, description, leaderId, name) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, project.getCode());
				stmt.setString(2, project.getDescription()); 
				stmt.setInt(3, project.getLeader().getId());
				stmt.setString(4, project.getName()); 
			} else {
				stmt = conn.prepareStatement("update projects set code=?, description=?, leaderId=?, name=? where id=?");
				stmt.setString(1, project.getCode());
				stmt.setString(2, project.getDescription()); 
				stmt.setInt(3, project.getLeader().getId());
				stmt.setString(4, project.getName());
				stmt.setInt(5, project.getId());
			}
			
			stmt.execute();
			
			if(project.getId() == -1) {
				ResultSet rs = stmt.getGeneratedKeys();
				
				String key = "-1";
				if(rs.next()) {
					do {
						key = rs.getString(1);
					} while (rs.next());
				} 
				
				project.setId(Integer.valueOf(key));
			}
			
		} catch(Exception e){
			throw new DatabaseException();
		}
		manager.closeConnection();
	}

	@Override
	public Project getProjectByCode(String code) {
		ConnectionManager manager = ConnectionManager.getInstance();
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		Project project = null;

		try {
			
			stmt = conn.prepareStatement("select * from projects where code=?");
			stmt.setString(1, code);
			ResultSet cur = stmt.executeQuery();
			
			if (cur.next()) {
				int leaderId = Integer.valueOf(cur.getString("leaderId"));
				User leader = DatabaseUserDAO.getInstance().getUser(leaderId);
				project = new Project(cur.getString("name"), cur.getString("code"), 
						cur.getString("description"), leader);
				project.setId(cur.getInt("id"));
			} else {
				manager.closeConnection();
				return null;
			}
			
		} catch (Exception e) {
			throw new DatabaseException();
		}
		
		manager.closeConnection();
		return project;
	}

	@Override
	public Project getProjectById(int id) {
		ConnectionManager manager = ConnectionManager.getInstance();
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		Project project = null;

		try {
			
			stmt = conn.prepareStatement("select * from projects where id=?");
			stmt.setInt(1, id);
			ResultSet cur = stmt.executeQuery();
			
			if (cur.next()) {
				int leaderId = Integer.valueOf(cur.getString("leaderId"));
				User leader = DatabaseUserDAO.getInstance().getUser(leaderId);
				project = new Project(cur.getString("name"), cur.getString("code"), 
						cur.getString("description"), leader);
				project.setId(id);
			} else {
				manager.closeConnection();
				return null;
			}
			
		} catch (Exception e) {
			throw new DatabaseException();
		}
		
		manager.closeConnection();
		return project;
	}

	public List<Project> getProjects() {
		ConnectionManager manager = ConnectionManager.getInstance();
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		User user;
		Project project;
		List<Project> projects = new ArrayList<Project>();
		try {

			stmt = conn.prepareStatement("select * from projects");
			ResultSet cur = stmt.executeQuery();

			while (cur.next()) {
				user = DatabaseUserDAO.getInstance().getUser(cur.getInt("leaderid"));
				project = new Project(cur.getString("name"), cur.getString("code"), cur.getString("description"), user);
				project.setId(cur.getInt("id"));
				projects.add(project);
			}
		} catch (Exception e) {
			throw new DatabaseException();
		}

		return projects;
	}

	@Override
	public Project getProjectByName(String name) {
		ConnectionManager manager = ConnectionManager.getInstance();
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		Project project = null;

		try {
			
			stmt = conn.prepareStatement("select * from projects where name=?");
			stmt.setString(1, name);
			ResultSet cur = stmt.executeQuery();
			
			if (cur.next()) {
				int leaderId = Integer.valueOf(cur.getString("leaderId"));
				User leader = DatabaseUserDAO.getInstance().getUser(leaderId);
				project = new Project(cur.getString("name"), cur.getString("code"), 
						cur.getString("description"), leader);
				project.setId(cur.getInt("id"));
			} else {
				manager.closeConnection();
				return null;
			}
			
		} catch (Exception e) {
			throw new DatabaseException();
		}
		
		manager.closeConnection();
		return project;
	}

}
