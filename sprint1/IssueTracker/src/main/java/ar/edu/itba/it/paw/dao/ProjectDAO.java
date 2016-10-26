package ar.edu.itba.it.paw.dao;

import java.util.List;

import ar.edu.itba.it.paw.domain.Project;

public interface ProjectDAO {
	
	public Project getProjectByCode(String code);
	
	public Project getProjectByName(String name);
	
	public Project getProjectById(int id);
	
	public List<Project> getProjects();
	
	public void saveProject(Project project);
}
