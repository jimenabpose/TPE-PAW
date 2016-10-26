package ar.edu.itba.it.paw.web.issue;

import java.awt.font.NumericShaper;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.MinimumValidator;

import ar.edu.itba.it.paw.domain.issue.Issue;
import ar.edu.itba.it.paw.domain.issue.Job;
import ar.edu.itba.it.paw.domain.user.User;
import ar.edu.itba.it.paw.web.WicketSession;

public class IssueJobsPanel extends Panel{

	private transient Integer dedicatedTime;
	private transient String description;
	
	
	public IssueJobsPanel(String id, final IModel<Issue> issueModel){
		super(id);
		
		final WicketSession session = WicketSession.get();
		
		add(new Label("noJobsRegistered", new ResourceModel("noJobsRegistered")){
			@Override
			public boolean isVisible(){
				return issueModel.getObject().getJobs().isEmpty();
			}
		});
		
		IModel<List<Job>> jobModel = new LoadableDetachableModel<List<Job>>(){
			@Override
			protected List<Job> load(){
				return (issueModel.getObject()).getJobs();
			}
		};
		
		ListView<Job> jobList = new PropertyListView<Job>("jobList", jobModel){
			@Override
			protected void populateItem(ListItem<Job> item){
				item.add(new Label("user.username"));
				item.add(new Label("date"));
				item.add(new Label("elapsedTime"));
				item.add(new Label("description"));
			}
			
			@Override
			public boolean isVisible(){
				return !issueModel.getObject().getJobs().isEmpty();
			}
		};
		
		add(jobList);

		Form<IssueJobsPanel> form = new Form<IssueJobsPanel>("jobForm", new CompoundPropertyModel<IssueJobsPanel>(this)){
			@Override
			protected void onSubmit(){
				User loggedUser = session.getUser();
				try {
					Job job= new Job(dedicatedTime, description, loggedUser, new Date(), (Issue)issueModel.getObject());
				} catch (IllegalArgumentException e) {
					error(getString("illegalArgument"));
					return;
				}
			}
			
			@Override
			public boolean isVisible() {
				return WicketSession.get().getUser() != null &&
					WicketSession.get().getProject().userBelongs(WicketSession.get().getUser());
			}
		};

		form.add(new TextField<Integer>("dedicatedTime").setRequired(true).add(new MinimumValidator<Integer>(0)));
		form.add(new TextArea<String>("description").setRequired(true));
		form.add(new Button("publish", new ResourceModel("publish")));
		
		add(form);
	}
}
