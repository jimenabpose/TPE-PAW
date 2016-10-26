package ar.edu.itba.it.paw.domain.project;

import java.util.List;

import ar.edu.itba.it.paw.domain.issue.IssueChanges;
import ar.edu.itba.it.paw.domain.user.User;

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
	
	public List<IssueChanges> getLastsIssueChanges(Project project, int quant);
	
	public List<Project> getProjectsForUser(User user);
}
