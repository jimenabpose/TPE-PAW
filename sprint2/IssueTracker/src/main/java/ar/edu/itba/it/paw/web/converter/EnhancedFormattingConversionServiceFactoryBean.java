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
import ar.edu.itba.it.paw.services.IssueServices;
import ar.edu.itba.it.paw.services.ProjectServices;
import ar.edu.itba.it.paw.services.UserServices;

public class EnhancedFormattingConversionServiceFactoryBean 
				extends FormattingConversionServiceFactoryBean {

	private Converter<?,?>[] converters ;
	private UserServices userService;
	private IssueServices issueService;
	private ProjectServices projectService;
	
	@Autowired
	public EnhancedFormattingConversionServiceFactoryBean(Converter<?,?>[] converters, 
			UserServices userService, IssueServices issueService, 
			ProjectServices projectService) {
		this.converters = converters;
		this.userService = userService;
		this.issueService = issueService;
		this.projectService = projectService;
	}
	
	@Override
	protected void installFormatters(FormatterRegistry registry){
		super.installFormatters(registry);
		for (Converter<?,?> c: converters) {
			registry.addConverter(c);
		}
		registry.addFormatterForFieldType(User.class, new UserFormatter(userService));
		registry.addFormatterForFieldType(Issue.class, new IssueFormatter(issueService));
		registry.addFormatterForFieldType(Project.class, new ProjectFormatter(projectService));
		registry.addFormatterForFieldType(Resolution.class, new ResolutionFormatter());
		registry.addFormatterForFieldType(Date.class, new DateFormatter());
	}
}

