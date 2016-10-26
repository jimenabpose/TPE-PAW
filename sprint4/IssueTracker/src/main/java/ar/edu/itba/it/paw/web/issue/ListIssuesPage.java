package ar.edu.itba.it.paw.web.issue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.issue.Issue;
import ar.edu.itba.it.paw.domain.issue.IssueRepo;
import ar.edu.itba.it.paw.domain.issue.IssueSearchCriteria;
import ar.edu.itba.it.paw.domain.issue.IssueType;
import ar.edu.itba.it.paw.domain.issue.Resolution;
import ar.edu.itba.it.paw.domain.issue.State;
import ar.edu.itba.it.paw.domain.project.Project;
import ar.edu.itba.it.paw.domain.project.Version;
import ar.edu.itba.it.paw.domain.user.User;
import ar.edu.itba.it.paw.web.WicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.user.UserWorkedHoursPage;

public class ListIssuesPage extends BasePage{

	private transient String inpCode;
	private transient String inpTitle;
	private transient String inpDescription;
	private transient State inpState;
	private transient Resolution inpResolution;
	private transient IssueType inpIssueType;
	private transient Date inpDateBegin;
	private transient Date inpDateEnd;
	
	private IModel<User> inpReporter = new EntityModel<User>(User.class);
	private IModel<User> inpAssignee = new EntityModel<User>(User.class);
	private IModel<Version> inpResolutionVersion = new EntityModel<Version>(Version.class);
	private IModel<Version> inpAffectedVersion = new EntityModel<Version>(Version.class);
	private IModel<Project> project = new EntityModel<Project>(Project.class);
	
	@SpringBean
	IssueRepo issueRepo;
	
	public ListIssuesPage() {
		WicketSession session  = WicketSession.get();
		project.setObject(session.getProject());
		
		/*Formulario de búsqueda*/
		add(new FeedbackPanel("feedback"));
		Form<ListIssuesPage> form = new Form<ListIssuesPage>("issueFilter", 
				new CompoundPropertyModel<ListIssuesPage>(this)){
			@Override
			protected void onSubmit(){
			}
		};
		
		form.add(new TextField<String>("inpCode"));
		form.add(new TextField<String>("inpTitle"));
		form.add(new TextField<String>("inpDescription"));
		
		IModel<List<User>> usersModel = new LoadableDetachableModel<List<User>>(){
			@Override
			protected List<User> load(){
				return (new ArrayList<User>(WicketSession.get().getProject().getUsers()));
			}
		};
		
		form.add(new DropDownChoice<User>("inpReporter", inpReporter, usersModel).setNullValid(true));
		form.add(new DropDownChoice<User>("inpAssignee", inpAssignee, usersModel).setNullValid(true));
		
		IModel<List<State>> stateModel = new LoadableDetachableModel<List<State>>(){
			@Override
			protected List<State> load(){
				return Arrays.asList(State.values());
			}
		};

		form.add(new DropDownChoice<State>("inpState", stateModel).setNullValid(true));
		List<Resolution> resolutions = new ArrayList<Resolution>();
		resolutions = Arrays.asList(Resolution.values());
		
		IModel<List<Resolution>> resolutionModel = new LoadableDetachableModel<List<Resolution>>(){
			@Override
			protected List<Resolution> load(){
				return Arrays.asList(Resolution.values());
			}
		};
		
		form.add(new DropDownChoice<Resolution>("inpResolution", resolutionModel).setNullValid(true));
		
		IModel<List<IssueType>> typeModel = new LoadableDetachableModel<List<IssueType>>(){
			@Override
			protected List<IssueType> load(){
				return Arrays.asList(IssueType.values());
			}
		};
		
		form.add(new DropDownChoice<IssueType>("inpIssueType", typeModel).setNullValid(true));

		IModel<List<Version>> versionsModel = new LoadableDetachableModel<List<Version>>(){
			@Override
			protected List<Version> load() {
				return new ArrayList<Version>(WicketSession.get().getProject().getVersions());
			}
		};
		
		form.add(new DropDownChoice<Version>("inpResolutionVersion", inpResolutionVersion, versionsModel).setNullValid(true));
		form.add(new DropDownChoice<Version>("inpAffectedVersion", inpAffectedVersion, versionsModel).setNullValid(true));
		form.add(new DateTextField("inpDateBegin", null, new PatternDateConverter("dd/MM/yyyy", false)));
		form.add(new DateTextField("inpDateEnd", null, new PatternDateConverter("dd/MM/yyyy", false)));
		
		form.add(new Button("search", new ResourceModel("search")));
		
		add(form);
		
		/*Lista de issues*/
		IModel<List<Issue>> issueModel = new LoadableDetachableModel<List<Issue>>(){
			@Override
			protected List<Issue> load(){
				IssueSearchCriteria criteria = new IssueSearchCriteria();
				criteria.setCode(inpCode);
				criteria.setTitle(inpTitle);
				criteria.setDescription(inpDescription);
				criteria.setReporter(inpReporter.getObject());
				criteria.setAsignee(inpAssignee.getObject());
				criteria.setState(inpState);
				criteria.setDate_st(inpDateBegin);
				criteria.setDate_et(inpDateEnd);
				criteria.setResolution(inpResolution);
				criteria.setIssueType(inpIssueType);
				criteria.setResolutionVersion(inpResolutionVersion.getObject());
				criteria.setAffectedVersion(inpAffectedVersion.getObject());
				 
				List<Issue> issues = issueRepo.getIssuesWithFilter(project.getObject(), criteria);
				
				return issues;
			}
		};
		
		add(new ListIssuesPanel("issuesPanel", issueModel));
		
		/*Links*/
		add(new Link<Void>("listActive"){
			@Override
			public void onClick(){
				setResponsePage(new ListMyActiveIssuesPage());
			}
			
			@Override
			public boolean isVisible() {
				return WicketSession.get().getUser() != null &&
					WicketSession.get().getProject().userBelongs(WicketSession.get().getUser());
			}
		});
		
		add(new Link<Void>("sumJobReport"){
			@Override
			public void onClick(){
				setResponsePage(new UserWorkedHoursPage());
			}
			
			@Override
			public boolean isVisible() {
				return WicketSession.get().getUser() != null &&
					WicketSession.get().getProject().getLeader().equals(WicketSession.get().getUser());
			}
		});
		
		add(new Link<Void>("listFollowingIssues"){
			@Override
			public void onClick(){
				setResponsePage(new ListMyFollowedIssuesPage());
			}
			
			@Override
			public boolean isVisible() {
				return WicketSession.get().getUser() != null;
			}
		});
	}
	
}
