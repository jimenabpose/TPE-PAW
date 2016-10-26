package ar.edu.itba.it.paw.services.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.it.paw.dao.ProjectDAO;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.exceptions.ProjectCodeRepeatedException;
import ar.edu.itba.it.paw.domain.exceptions.ProjectNameRepeatedException;
import ar.edu.itba.it.paw.services.ProjectServices;

@Service
public class ProjectServicesImpl implements ProjectServices {

	private ProjectDAO projectDAO;
	
	@Autowired
	public ProjectServicesImpl(ProjectDAO projectDAO){
		this.projectDAO = projectDAO;
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
	
	@Override
	public List<Project> getProjects() {
		return projectDAO.getProjects();
	}
	
	@Override
	public void addUserToProject(User user, Project project){
		projectDAO.addUserToProject(user, project);
	}

	@Override
	public void deleteUserFromProject(User user, Project project){
		projectDAO.deleteUserFromProject(user, project);
	}

	@Override
	public List<Project> getUserProjects(User user){
		List<Project> projects = projectDAO.getUserProjects(user);
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
}
