package ar.edu.itba.it.paw.web.converter;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.repo.ProjectRepo;


public class ProjectFormatter implements Formatter<Project> {

	private ProjectRepo projectRepo;

	@Autowired
	public ProjectFormatter(ProjectRepo projectRepo) {
		this.projectRepo = projectRepo;
	}

	@Override
	public String print(Project arg0, Locale arg1) {
		if(arg0 == null) {
			return "";
		}
		return arg0.getId() + "";
	}

	@Override
	public Project parse(String arg0, Locale arg1) {
		return projectRepo.getProjectById(Integer.valueOf(arg0));
	}
}
