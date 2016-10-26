package ar.edu.itba.it.paw.web.converter;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.Resolution;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserSolicitation;
import ar.edu.itba.it.paw.domain.Version;
import ar.edu.itba.it.paw.repo.IssueRepo;
import ar.edu.itba.it.paw.repo.ProjectRepo;
import ar.edu.itba.it.paw.repo.UserRepo;
import ar.edu.itba.it.paw.repo.UserSolicitationRepo;

public class EnhancedFormattingConversionServiceFactoryBean 
				extends FormattingConversionServiceFactoryBean {

	private Converter<?,?>[] converters ;
	private UserRepo userRepo;
	private IssueRepo issueRepo;
	private ProjectRepo projectRepo;
	private UserSolicitationRepo userSolicitationRepo;
	
	@Autowired
	public EnhancedFormattingConversionServiceFactoryBean(Converter<?,?>[] converters, 
			UserRepo userRepo, IssueRepo issueRepo, ProjectRepo projectRepo, 
			UserSolicitationRepo userSolicitationRepo) {
		this.converters = converters;
		this.userRepo = userRepo;
		this.issueRepo = issueRepo;
		this.projectRepo = projectRepo;
		this.userSolicitationRepo = userSolicitationRepo;
	}
	
	@Override
	protected void installFormatters(FormatterRegistry registry){
		super.installFormatters(registry);
		for (Converter<?,?> c: converters) {
			registry.addConverter(c);
		}
		registry.addFormatterForFieldType(Version.class, new VersionFormatter(projectRepo));
		registry.addFormatterForFieldType(User.class, new UserFormatter(userRepo));
		registry.addFormatterForFieldType(UserSolicitation.class, new UserSolicitationFormatter(userSolicitationRepo));
		registry.addFormatterForFieldType(Issue.class, new IssueFormatter(issueRepo));
		registry.addFormatterForFieldType(Project.class, new ProjectFormatter(projectRepo));
		registry.addFormatterForFieldType(Resolution.class, new ResolutionFormatter());
		registry.addFormatterForFieldType(Date.class, new DateFormatter());
	}
}

