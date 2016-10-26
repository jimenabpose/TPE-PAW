package ar.edu.itba.it.paw.web.issue;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.issue.Issue;
import ar.edu.itba.it.paw.web.base.BasePage;

public class EditIssuePage extends BasePage{
	
	public EditIssuePage(Issue issue){
		add(new FeedbackPanel("feedback"));
		setDefaultModel(new EntityModel<Issue>(Issue.class, issue));
		Form<Issue> form = new Form<Issue>("form", new CompoundPropertyModel<Issue>(new EntityModel<Issue>(Issue.class, issue)));
		form.add(new IssueInfoPanel("issuesInfo"));
		
		form.add(new Button("submit", new ResourceModel("register")){
			@Override
			public void onSubmit() {
				setResponsePage(new ViewIssuePage((Issue) EditIssuePage.this.getDefaultModelObject()));
				setRedirect(true);
			}
		});
		add(form);
	}
}
