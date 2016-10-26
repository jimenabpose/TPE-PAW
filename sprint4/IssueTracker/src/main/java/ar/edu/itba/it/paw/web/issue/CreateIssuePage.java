package ar.edu.itba.it.paw.web.issue;

import java.util.HashSet;
import java.util.List;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;

import ar.edu.itba.it.paw.domain.exceptions.IssueNameRepeatedException;
import ar.edu.itba.it.paw.domain.issue.Issue;
import ar.edu.itba.it.paw.domain.issue.IssueType;
import ar.edu.itba.it.paw.domain.issue.Priority;
import ar.edu.itba.it.paw.domain.project.Project;
import ar.edu.itba.it.paw.domain.project.Version;
import ar.edu.itba.it.paw.domain.user.User;
import ar.edu.itba.it.paw.web.WicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;

public class CreateIssuePage extends BasePage{

	private transient String title;
	private transient String description;
	private transient Float estimatedTime;
	private transient Priority priority;
	private transient User assignee;
	private transient IssueType issueType;
	private transient List<Version> resolutionVersions;
	private transient List<Version> affectedVersions;
	
	public CreateIssuePage(){
		final WicketSession session = WicketSession.get();
		
		add(new FeedbackPanel("feedback"));
		Form<CreateIssuePage> form = new Form<CreateIssuePage>("form", new CompoundPropertyModel<CreateIssuePage>(this));
		form.add(new IssueInfoPanel("issuesInfo"));
		
		form.add(new Button("submit", new ResourceModel("create")){
			@Override
			public void onSubmit() {
				try {
					User reporter = session.getUser();
					Project project = session.getProject();
					Issue issue = new Issue(title, description, estimatedTime, priority, reporter, assignee, project,
							issueType, new HashSet<Version>(resolutionVersions), new HashSet<Version>(affectedVersions));
					setResponsePage(new ListIssuesPage());
					setRedirect(true);
				} catch (IssueNameRepeatedException e) {
					error(getString("issueNameRepeatedException"));
					return;
				}
			}
		});
		add(form);
	}
}
