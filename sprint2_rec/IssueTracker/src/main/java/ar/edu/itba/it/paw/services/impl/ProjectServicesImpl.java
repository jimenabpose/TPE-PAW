package ar.edu.itba.it.paw.services.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.it.paw.dao.IssueDAO;
import ar.edu.itba.it.paw.dao.ProjectDAO;
import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.Job;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.exceptions.NotDeletableUserException;
import ar.edu.itba.it.paw.domain.exceptions.ProjectCodeRepeatedException;
import ar.edu.itba.it.paw.domain.exceptions.ProjectNameRepeatedException;
import ar.edu.itba.it.paw.services.ProjectServices;

@Service
public class ProjectServicesImpl implements ProjectServices {

	private ProjectDAO projectDAO;
	private IssueDAO issueDAO;
	
	@Autowired
	public ProjectServicesImpl(ProjectDAO projectDAO, IssueDAO issueDAO){
		this.projectDAO = projectDAO;
		this.issueDAO = issueDAO;
	}
	
	@Override
	public void saveProject(Project project) {
		Project p;
		
		p = projectDAO.getProjectByCode(project.getCode());
		if(p != null && p.getId() != project.getId()) {
			throw new ProjectCodeRepeatedException();
		}
		
		p = projectDAO.getProjectByName(project.getName());
		if(p != null && p.getId() != project.getId()) {
			throw new ProjectNameRepeatedException();
		}
		
		projectDAO.saveProject(project);
		
	}

	@Override
	public Project getProjectById(int id) {
		return projectDAO.getProjectById(id);
	}
	
	private List<Project> getProjects() {
		return projectDAO.getProjects();
	}
	
	@Override
	public void addUserToProject(User user, Project project){
		projectDAO.addUserToProject(user, project);
	}

	@Override
	public void deleteUserFromProject(User user, Project project){
		if(project.getLeader().equals(user))
			throw new NotDeletableUserException();
		projectDAO.deleteUserFromProject(user, project);
	}

	private List<Project> getUserProjects(User user){
		List<Project> projects = projectDAO.getUserProjects(user);
		List<Project> publicProjects = this.getPublicProjects();
		Iterator<Project> iterator = publicProjects.iterator();
		
		while(iterator.hasNext()){
			Project project = (Project) iterator.next();
			if(!projects.contains(project)){
				projects.add(project);
			}
		}

		return projects;
	}
	
	@Override
	public List<Project> getPublicProjects(){
		List<Project> projects = projectDAO.getProjects();
		
		Iterator<Project> iterator = projects.iterator();
		
		while(iterator.hasNext()){
			Project project = (Project) iterator.next();
			if(!project.isPublic()){
				iterator.remove();
			}
		}
		return projects;
	}

	@Override
	public List<Project> getProjectsForUser(User user) {
		List<Project> projects;
		if(user == null){
			projects = getPublicProjects();
		}
		else if(user.isAdmin()){
			projects = getProjects();
		}
		else{
			projects = getUserProjects(user);
		}
		return projects;
	}
	
	
	/*devuelve el tiempo trabajado por el usuario en horas para el proyecto*/
	public Integer projectTimeWorked(Project project, User user, Date st, Date et){
		Integer sum = 0;
		
		if (user == null){
			return sum;
		}
		
		for (Issue i : issueDAO.getProjectIssues(project)) {
			for (Job j : issueDAO.getIssueJobs(i)) {
				if (j.getUser().equals(user)
						&& (st == null || j.getDate().after(st) || j
								.getDate().equals(st))
						&& (et == null || j.getDate().before(et) || j
								.getDate().equals(et))) {

					sum += j.getElapsedTime();
				}
			}
		}
		return sum;
	}
}
