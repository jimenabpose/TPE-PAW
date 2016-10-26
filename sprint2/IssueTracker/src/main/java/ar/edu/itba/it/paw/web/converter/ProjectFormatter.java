package ar.edu.itba.it.paw.web.converter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.services.ProjectServices;


public class ProjectFormatter implements Formatter<Project> {

	ProjectServices service;

	@Autowired
	public ProjectFormatter(ProjectServices service) {
		this.service = service;
	}

	@Override
	public String print(Project arg0, Locale arg1) {
		if(arg0 == null) {
			return "";
		}
		return arg0.getId() + "";
	}

	@Override
	public Project parse(String arg0, Locale arg1) throws ParseException {
		if(arg0.equals("-1")) {
			return null;
		}
		return service.getProjectById(Integer.valueOf(arg0));
	}
}
