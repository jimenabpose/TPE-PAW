package ar.edu.itba.it.paw.services.impl;

import java.util.List;

import ar.edu.itba.it.paw.dao.ProjectDAO;
import ar.edu.itba.it.paw.dao.impl.DatabaseProjectDAO;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.exceptions.ProjectCodeRepeatedException;
import ar.edu.itba.it.paw.domain.exceptions.ProjectNameRepeatedException;
import ar.edu.itba.it.paw.services.ProjectServices;

public class ProjectServicesImpl implements ProjectServices {

	private static ProjectServices instance;
	private static ProjectDAO projectDAO;
	
	public static synchronized ProjectServices getInstance(){
		if(instance == null){
			instance = new ProjectServicesImpl();
			projectDAO = DatabaseProjectDAO.getInstance();
		}
		return instance;
	}
	
	@Override
	public void saveProject(Project project) throws ProjectCodeRepeatedException, ProjectNameRepeatedException {
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
	
	public List<Project> getProjects() {
		return projectDAO.getProjects();
	}

}
