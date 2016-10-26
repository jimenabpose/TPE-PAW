package ar.edu.itba.it.paw.web.project;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.exceptions.ProjectCodeRepeatedException;
import ar.edu.itba.it.paw.domain.exceptions.ProjectNameRepeatedException;
import ar.edu.itba.it.paw.domain.project.Project;
import ar.edu.itba.it.paw.domain.project.ProjectRepo;
import ar.edu.itba.it.paw.domain.user.User;
import ar.edu.itba.it.paw.web.WicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.user.IndexPage;

public class CreateProjectPage extends BasePage {

	@SpringBean
	private ProjectRepo projectRepo;

	private transient String code;
	private transient String name;
	private transient String description;
	private transient User leader;
	private transient boolean isPublic;

	public CreateProjectPage() {
		add(new FeedbackPanel("feedback"));
		final Form<CreateProjectPage> form = new Form<CreateProjectPage>("form", new CompoundPropertyModel<CreateProjectPage>(this));
		form.add(new ProjectInfoPanel("projectsInfo"));
		form.add(new Button("accept", new ResourceModel("accept")) {
			@Override
			public void onSubmit() {
				Project project = new Project(name, code, description, leader, isPublic);
				try {
					projectRepo.saveProject(project);
					WicketSession.get().setProject(project);
					setResponsePage(new IndexPage());
				} catch (ProjectCodeRepeatedException e) {
					error(getString("projectCodeRepeatedException"));
					return;
				} catch (ProjectNameRepeatedException e) {
					error(getString("projectNameRepeatedException"));
					return;
				}
			}
		});
		add(form);
	}
}