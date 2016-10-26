package ar.edu.itba.it.paw.web.issue;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.issue.Issue;
import ar.edu.itba.it.paw.domain.issue.Resolution;
import ar.edu.itba.it.paw.domain.issue.State;
import ar.edu.itba.it.paw.domain.project.Version;
import ar.edu.itba.it.paw.domain.user.User;
import ar.edu.itba.it.paw.web.WicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;

public class ViewIssuePage extends BasePage{

	private transient Resolution resolution;
	
	public ViewIssuePage(Issue issue){
		
		final WicketSession session = WicketSession.get();
		
		add(new FeedbackPanel("feedback"));
		final IModel<Issue> issueModel = new EntityModel(Issue.class, issue);
		setDefaultModel(issueModel);
		
		add(new Label("code", new PropertyModel<String>(issueModel, "code")));
		add(new Label("title", new PropertyModel<String>(issueModel, "title")));
		add(new Label("description", new PropertyModel<String>(issueModel, "description")));
		add(new Label("creationDate", new PropertyModel<Date>(issueModel, "creationDate")));
		add(new Label("estimatedTime", new PropertyModel<Float>(issueModel, "estimatedTime")));
		add(new Label("assignedTo", new PropertyModel<User>(issueModel, "assignee")));
		add(new Label("reportedBy", new PropertyModel<User>(issueModel, "reporter")));
		add(new PriorityInfoPanel("priority", issueModel.getObject().getPriority()));
		add(new IssueTypeInfoPanel("type", issueModel.getObject().getIssueType()));
		add(new Label("state", new PropertyModel<State>(issueModel, "state")));
		add(new Label("resolution", new PropertyModel<Resolution>(issueModel, "resolution")));
		
		IModel<List<Version>> resVersionModel = new LoadableDetachableModel<List<Version>>(){
			@Override
			protected List<Version> load() {
				return new ArrayList<Version>(((Issue) ViewIssuePage.this.getDefaultModelObject()).getResolutionVersions());
			}
		};
		
		ListView<Version> resVersionsList = new PropertyListView<Version>("resolutionVersions", resVersionModel){
			@Override
			protected void populateItem(ListItem<Version> item){
				item.add(new Label("name"));
				item.add(new Label("releaseDate"));
			}
		};
		
		add(resVersionsList);
		
		IModel<List<Version>> affVersionModel = new LoadableDetachableModel<List<Version>>(){
			@Override
			protected List<Version> load(){
				return new ArrayList<Version>(issueModel.getObject().getAffectedVersions());
			}
		};
		
		ListView<Version> affVersionsList = new PropertyListView<Version>("affectedVersions", affVersionModel){
			@Override
			protected void populateItem(ListItem<Version> item){
				item.add(new Label("name"));
				item.add(new Label("releaseDate"));
			}
		};
		
		add(affVersionsList);
		
		Form<ViewIssuePage> form = new Form<ViewIssuePage>("resolveIssueForm", new CompoundPropertyModel<ViewIssuePage>(this)){
			@Override
			protected void onSubmit(){
				User loggedUser = session.getUser();
				issueModel.getObject().resolve(resolution, loggedUser);
			}
			
			@Override
			public boolean isVisible() {
				return WicketSession.get().getUser() != null &&
					WicketSession.get().getProject().userBelongs(WicketSession.get().getUser()) &&
					issueModel.getObject().getState().equals(State.ONCOURSE);
			}
		};
		
		IModel<List<Resolution>> resolutionModel = new LoadableDetachableModel<List<Resolution>>(){
			@Override
			protected List<Resolution> load() {
				return Arrays.asList(Resolution.values());
			}
		};
		
		form.add(new DropDownChoice<Resolution>("resolution", resolutionModel));
		form.add(new Button("select", new ResourceModel("select")));
		
		add(form);
		
		add(new Link<Void>("changeStateOnCourse"){
			@Override
			public void onClick(){
				User loggedUser = session.getUser();
				(issueModel.getObject()).setState(State.ONCOURSE, loggedUser);
			}
			
			@Override
			public boolean isVisible(){
				User user = session.getUser();
				Issue issue = issueModel.getObject();
				return user != null && WicketSession.get().getProject().userBelongs(user) &&
					issue.getAssignee() != null && issue.getAssignee().equals(user) && issue.getState().equals(State.OPEN);
			}
		});
		
		add(new Link<Void>("changeStateOpen"){
			@Override
			public void onClick(){
				User loggedUser = session.getUser();
				(issueModel.getObject()).setState(State.OPEN, loggedUser);
			}
			
			@Override
			public boolean isVisible(){
				User user = session.getUser();
				Issue issue = issueModel.getObject();
				return user != null && WicketSession.get().getProject().userBelongs(user) && 
					issue.getAssignee() != null && issue.getAssignee().equals(user) && issue.getState().equals(State.ONCOURSE);
			}
		});
		
		add(new Link<Void>("assignIssue"){
			@Override
			public void onClick(){
				issueModel.getObject().setAssignee(session.getUser(), session.getUser());
			}
			
			@Override
			public boolean isVisible(){
				User loggedUser = session.getUser();
				Issue issue = issueModel.getObject(); 
				return loggedUser != null && WicketSession.get().getProject().userBelongs(loggedUser) && 
					(issue.getAssignee() == null || !issue.getAssignee().equals(loggedUser));
			}
		});
		
		add(new Link<Void>("closeIssue"){
			@Override
			public void onClick(){
				User loggedUser = session.getUser();
				(issueModel.getObject()).setState(State.CLOSED, loggedUser);
			}
			
			@Override
			public boolean isVisible(){
				Issue issue = issueModel.getObject();
				User leader = issue.getProject().getLeader();
				User loggedUser = session.getUser();
				return loggedUser != null && leader != null && leader.equals(loggedUser) && 
					!issue.getState().equals(State.CLOSED);
			}
		});
		
		add(new Link<Void>("editIssue"){
			@Override
			public void onClick(){
				setResponsePage(new EditIssuePage(issueModel.getObject()));
				setRedirect(true);
			}
			
			@Override
			public boolean isVisible() {
				return WicketSession.get().getUser() != null &&
					WicketSession.get().getProject().userBelongs(WicketSession.get().getUser());
			}
		});
		
		add(new FollowingIssuesPanel("issueFollowersPanel", issueModel));
		add(new IssueChangesPanel("issueChangesPanel", issueModel));
		add(new IssueRelationsPanel("issueRelationsPanel", issueModel));
		add(new IssueVotesPanel("issueVotesPanel", issueModel));
		add(new IssueJobsPanel("issueJobsPanel", issueModel));
		add(new IssueCommentsPanel("issueCommentsPanel", issueModel));

	}
}
