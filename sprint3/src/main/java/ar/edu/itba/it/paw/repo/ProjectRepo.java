package ar.edu.itba.it.paw.repo;

import java.util.List;

import ar.edu.itba.it.paw.domain.IssueChanges;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.Version;

public interface ProjectRepo {
	
public Project getProjectByCode(String code);
	
	public Project getProjectByName(String name);
	
	public Project getProjectById(int id);
	
	public List<Project> getAllProjects();
	
	public void saveProject(Project project);
	
	public List<Project> getPublicProjects();
	
	/*Devuelve los proyectos en los que participa un usuario*/
	public List<Project> getUserProjects(User user);
	
	public Version getVersionById(int id);
	
	public List<IssueChanges> getLastFiveIssueChanges(Project project);
}
