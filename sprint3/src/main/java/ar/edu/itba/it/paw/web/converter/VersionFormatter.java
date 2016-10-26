package ar.edu.itba.it.paw.web.converter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import ar.edu.itba.it.paw.domain.Version;
import ar.edu.itba.it.paw.repo.ProjectRepo;

public class VersionFormatter implements Formatter<Version> {

	private ProjectRepo projectRepo;
	
	@Autowired
	public VersionFormatter(ProjectRepo projectRepo){
		this.projectRepo = projectRepo;
	}
	
	@Override
	public String print(Version arg0, Locale arg1) {
//		if(arg0 == null) {
//			return "";
//		}
		return arg0.getId() + "";
	}

	@Override
	public Version parse(String arg0, Locale arg1) throws ParseException {
		Integer id = Integer.valueOf(arg0);
		Version version;
		version = projectRepo.getVersionById(id);
		return version;
	}

}
