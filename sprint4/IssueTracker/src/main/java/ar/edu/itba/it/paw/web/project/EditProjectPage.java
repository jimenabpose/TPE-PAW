package ar.edu.itba.it.paw.web.project;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.project.Project;
import ar.edu.itba.it.paw.web.base.BasePage;

public class EditProjectPage extends BasePage {

	public EditProjectPage(Project project) {
		add(new FeedbackPanel("feedback"));
		setDefaultModel(new EntityModel<Project>(Project.class, project));
		Form<Project> form = new Form<Project>("form", new CompoundPropertyModel<Project>(new EntityModel<Project>(Project.class, project)));
		form.add(new ProjectInfoPanel("projectsInfo"));
		form.add(new Button("accept", new ResourceModel("accept")) {
			@Override
			public void onSubmit() {
				setResponsePage(new ProjectViewPage((Project) EditProjectPage.this.getDefaultModelObject()));
			}
		});
		add(form);
	}
}