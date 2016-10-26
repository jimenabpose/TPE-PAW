package ar.edu.itba.it.paw.services;

import java.util.List;

import ar.edu.itba.it.paw.domain.Project;

public interface ProjectServices {

	public void saveProject(Project project);

	public Project getProjectById(int id);
	
	public List<Project> getProjects();
	
}
