package ar.edu.itba.it.paw.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.dao.ProjectDAO;
import ar.edu.itba.it.paw.dao.UserDAO;
import ar.edu.itba.it.paw.domain.ConnectionManager;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.exceptions.DatabaseException;

@Repository
public class DatabaseProjectDAO implements ProjectDAO {

	private ConnectionManager manager;
	private UserDAO user_dao;
	
	@Autowired
	public DatabaseProjectDAO(ConnectionManager manager, UserDAO user_dao){
		this.manager = manager;
		this.user_dao = user_dao;
	}
	
	@Override
	public void saveProject(Project project) {
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		
		try{
			if(project.getId() == -1){
				stmt = conn.prepareStatement("insert into projects (code, description, leaderId, name, is_public) values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, project.getCode());
				stmt.setString(2, project.getDescription()); 
				stmt.setInt(3, project.getLeader().getId());
				stmt.setString(4, project.getName());
				stmt.setBoolean(5, project.isPublic());
			} else {
				stmt = conn.prepareStatement("update projects set code=?, description=?, leaderId=?, name=?, is_public=? where id=?");
				stmt.setString(1, project.getCode());
				stmt.setString(2, project.getDescription()); 
				stmt.setInt(3, project.getLeader().getId());
				stmt.setString(4, project.getName());
				stmt.setBoolean(5, project.isPublic());
				stmt.setInt(6, project.getId());
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
		} finally {
			manager.closeConnection();
		}
		try {
			this.addUserToProject(project.getLeader(), project);
		} catch(Exception e) {
			
		}
	}

	@Override
	public Project getProjectByCode(String code) {
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		Project project = null;

		try {
			
			stmt = conn.prepareStatement("select * from projects where code=?");
			stmt.setString(1, code);
			ResultSet cur = stmt.executeQuery();
			
			if (cur.next()) {
				project = getNewProject(cur);
			}
			
		} catch (Exception e) {
			throw new DatabaseException();
		} finally {
			manager.closeConnection();
		}
		return project;
	}

	@Override
	public Project getProjectById(int id) {
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		Project project = null;

		try {
			
			stmt = conn.prepareStatement("select * from projects where id=?");
			stmt.setInt(1, id);
			ResultSet cur = stmt.executeQuery();
			
			if (cur.next()) {
				project = getNewProject(cur);
			}
		} catch (Exception e) {
			throw new DatabaseException();
		} finally {
			manager.closeConnection();
		}
		return project;
	}

	public List<Project> getProjects() {
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		Project project;
		List<Project> projects = new ArrayList<Project>();
		try {

			stmt = conn.prepareStatement("select * from projects");
			ResultSet cur = stmt.executeQuery();

			while (cur.next()) {
				project = getNewProject(cur);
				projects.add(project);
			}
		} catch (Exception e) {
			throw new DatabaseException();
		} finally {
			manager.closeConnection();
		}
		return projects;
	}

	@Override
	public Project getProjectByName(String name) {
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		Project project = null;

		try {
			
			stmt = conn.prepareStatement("select * from projects where name=?");
			stmt.setString(1, name);
			ResultSet cur = stmt.executeQuery();
			
			if (cur.next()) {
				project = getNewProject(cur);
			} 
		} catch (Exception e) {
			throw new DatabaseException();
		} finally {
			manager.closeConnection();
		}
		return project;
	}
	
	@Override
	public void addUserToProject(User user, Project project){
		Connection conn = manager.getConnection();
		PreparedStatement stmt;

		try {
			stmt = conn.prepareStatement("insert into rel_users_projects(user_fk, project_fk) values(?,?)");
			stmt.setInt(1, user.getId());
			stmt.setInt(2, project.getId());
			stmt.execute();
		} catch (Exception e) {
			throw new DatabaseException();
		} finally {
			manager.closeConnection();
		}
	}

	@Override
	public void deleteUserFromProject(User user, Project project){
		Connection conn = manager.getConnection();
		PreparedStatement stmt;

		try {
			stmt = conn.prepareStatement("delete from rel_users_projects where user_fk = ?");
			stmt.setInt(1, user.getId());
			stmt.execute();
		} catch (Exception e) {
			throw new DatabaseException();
		} finally {
			manager.closeConnection();
		}
	}
	
	@Override
	public List<Project> getUserProjects(User user) {
		Connection conn = manager.getConnection();
		PreparedStatement stmt;
		List<Project> projects = new ArrayList<Project>();

		try {
			stmt = conn.prepareStatement("select projects.id, code, description, leaderid, name, is_public from projects, " +
					"rel_users_projects where rel_users_projects.project_fk = projects.id and rel_users_projects.user_fk = ?");
			stmt.setInt(1, user.getId());
			ResultSet cur = stmt.executeQuery();
			
			while(cur.next()){
				Project project = getNewProject(cur);
				projects.add(project);
			}
		} catch (Exception e) {
			throw new DatabaseException();
		} finally {
			manager.closeConnection();
		}
		return projects;
	}
	
	private Project getNewProject(ResultSet cur) {
		Project project = null;
		
		try {
			User leader = user_dao.getUser(cur.getInt("leaderid"));
			
			project = new Project(cur.getString("name"), 
					cur.getString("code"), 
					cur.getString("description"), 
					leader, 
					cur.getBoolean("is_public"));
			project.setId(cur.getInt("id"));
			
		} catch (SQLException e) {
			throw new DatabaseException();
		}
		
		return project;
	}
}
