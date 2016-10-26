package ar.edu.itba.it.paw.services;

import java.util.Date;
import java.util.List;

import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.User;

public interface ProjectServices {

	public void saveProject(Project project);

	public Project getProjectById(int id);
	
	public List<Project> getPublicProjects();
	
	public List<Project> getProjectsForUser(User user);
	
	/*Me devuelve los proyectos que puede visualizar el usuario.
	 * Es decir, los proyectos en los que participa más los proyectos públicos*/
//	public List<Project> getUserProjects(User user);
	
	public void addUserToProject(User user, Project project);
	
	public void deleteUserFromProject(User user, Project project);
	
	public Integer projectTimeWorked(Project project, User user, Date st, Date et);
}
