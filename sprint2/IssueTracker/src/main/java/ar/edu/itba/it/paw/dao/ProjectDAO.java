package ar.edu.itba.it.paw.dao;

import java.util.List;

import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.User;

public interface ProjectDAO {
	
	public Project getProjectByCode(String code);
	
	public Project getProjectByName(String name);
	
	public Project getProjectById(int id);
	
	public List<Project> getProjects();
	
	public void saveProject(Project project);
	
	public void addUserToProject(User user, Project project);
	
	public void deleteUserFromProject(User user, Project project);
	
	public List<Project> getUserProjects(User user);
}
