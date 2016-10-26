package ar.edu.itba.it.paw.web;

import java.util.HashMap;

import org.apache.wicket.IConverterLocator;
import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.convert.ConverterLocator;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.itba.it.paw.domain.issue.Issue;
import ar.edu.itba.it.paw.domain.issue.IssueType;
import ar.edu.itba.it.paw.domain.issue.Priority;
import ar.edu.itba.it.paw.domain.issue.RelationType;
import ar.edu.itba.it.paw.domain.issue.Resolution;
import ar.edu.itba.it.paw.domain.issue.State;
import ar.edu.itba.it.paw.domain.project.Project;
import ar.edu.itba.it.paw.domain.project.ProjectRepo;
import ar.edu.itba.it.paw.domain.project.VersionState;
import ar.edu.itba.it.paw.domain.user.Type;
import ar.edu.itba.it.paw.domain.user.User;
import ar.edu.itba.it.paw.web.common.HibernateWebRequestCycle;
import ar.edu.itba.it.paw.web.converter.EnumConverter;
import ar.edu.itba.it.paw.web.converter.IssueConverter;
import ar.edu.itba.it.paw.web.converter.ProjectConverter;
import ar.edu.itba.it.paw.web.converter.UserConverter;

@Component
public class IssueTrackerApp extends WebApplication {
	public static final ResourceReference LOGO = new ResourceReference(IssueTrackerApp.class, "resources/tasks_icon.png");
	
	// Priority images
	public static final HashMap<String, ResourceReference> priorityImages = new HashMap<String, ResourceReference>();

	//Issue type images
	public static final HashMap<String, ResourceReference> issueTypeImages = new HashMap<String, ResourceReference>();
	
	private final SessionFactory sessionFactory;
	
	@Autowired
	public IssueTrackerApp(SessionFactory sessionFactory, ProjectRepo projectRepo) {
		this.sessionFactory = sessionFactory;
		
		priorityImages.put("TRIVIAL", new ResourceReference(IssueTrackerApp.class, "resources/Trivial.png"));
		priorityImages.put("LOW", new ResourceReference(IssueTrackerApp.class, "resources/Low.png"));
		priorityImages.put("NORMAL", new ResourceReference(IssueTrackerApp.class, "resources/Normal.png"));
		priorityImages.put("HIGH", new ResourceReference(IssueTrackerApp.class, "resources/High.png"));
		priorityImages.put("CRITICAL", new ResourceReference(IssueTrackerApp.class, "resources/Critical.png"));
		
		issueTypeImages.put("ERROR", new ResourceReference(IssueTrackerApp.class, "resources/Error.png"));
		issueTypeImages.put("NEWCHAR", new ResourceReference(IssueTrackerApp.class, "resources/Newchar.png"));
		issueTypeImages.put("IMPROVEMENT", new ResourceReference(IssueTrackerApp.class, "resources/Improvement.png"));
		issueTypeImages.put("TECHNIQUE", new ResourceReference(IssueTrackerApp.class, "resources/Technique.png"));
		issueTypeImages.put("ISSUE", new ResourceReference(IssueTrackerApp.class, "resources/Issue.png"));
	}
	
	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

	@Override
	protected void init() {
		super.init();
		addComponentInstantiationListener(new SpringComponentInjector(this));
		getApplicationSettings().setPageExpiredErrorPage(FaultErrorPage.class);
	}

	@Override
	public Session newSession(Request request, Response response) {
		return new WicketSession(request);
	}

	@Override
	public RequestCycle newRequestCycle(Request request, Response response) {
		return new HibernateWebRequestCycle(this, (WebRequest) request, response, sessionFactory);
	}

	@Override
	protected IConverterLocator newConverterLocator() {
		ConverterLocator converterLocator = new ConverterLocator();
		converterLocator.set(Project.class, new ProjectConverter());
		converterLocator.set(User.class, new UserConverter());
		converterLocator.set(Issue.class, new IssueConverter());
		converterLocator.set(IssueType.class, new EnumConverter());
		converterLocator.set(Priority.class, new EnumConverter());
		converterLocator.set(RelationType.class, new EnumConverter());
		converterLocator.set(Resolution.class, new EnumConverter());
		converterLocator.set(State.class, new EnumConverter());
		converterLocator.set(Type.class, new EnumConverter());
		converterLocator.set(VersionState.class, new EnumConverter());
		return converterLocator;
	}
	
}
