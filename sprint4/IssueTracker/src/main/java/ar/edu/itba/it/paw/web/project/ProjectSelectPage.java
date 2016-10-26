package ar.edu.itba.it.paw.web.project;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.project.Project;
import ar.edu.itba.it.paw.domain.project.ProjectRepo;
import ar.edu.itba.it.paw.domain.user.User;
import ar.edu.itba.it.paw.web.HomePage;
import ar.edu.itba.it.paw.web.IssueTrackerApp;
import ar.edu.itba.it.paw.web.WicketSession;
import ar.edu.itba.it.paw.web.user.IndexPage;
import ar.edu.itba.it.paw.web.user.ListUsersForAdministrationPage;

public class ProjectSelectPage extends WebPage {

	@SpringBean
	private ProjectRepo projectRepo;
	private transient Project project;
	
	public ProjectSelectPage() {
		addSelectionForm();
		addCreateProjectLink();
		addAdministrateUsersLink();
		addLogoutLink();
		addLogoImage();
	}
	
	private void addSelectionForm() {
		add(new FeedbackPanel("feedback"));
		
		Form<Void> form = new Form<Void>("form") {
			@Override
			protected void onSubmit() {
				WicketSession session = WicketSession.get();
				session.setProject(project);
				setResponsePage(new IndexPage());
				setRedirect(true);
			}
		};
		
		
		IModel<List<Project>> projectsModel = new LoadableDetachableModel<List<Project>>() {
			WicketSession session = WicketSession.get();
			@Override
			protected List<Project> load() {
				User user = session.getUser();
				return projectRepo.getProjectsForUser(WicketSession.get().getUser());
			}
		};
		
		IModel<Project> project = new PropertyModel<Project>(this, "project");
		
		form.add(new DropDownChoice<Project>("project", project, projectsModel).setRequired(true));
		
		form.add(new Button("accept", new ResourceModel("accept")));
		add(form);
	}
	
	private void addCreateProjectLink() {
		add(new Link<Void>("createProject") {
			@Override
			public void onClick() {
				setResponsePage(new CreateProjectPage());
			}
			@Override
			public boolean isVisible() {
				return WicketSession.get().getUser() != null &&
					WicketSession.get().getUser().isAdmin();
			}
		});
		
	}
	
	private void addLogoutLink() {
		add(new Link<Void>("logout") {
			@Override
			public void onClick() {
				WicketSession.get().setUser(null);
				setResponsePage(new HomePage());
			}
		});
	}
	
	private void addAdministrateUsersLink() {
		add(new Link<Void>("administrateUsers") {
			@Override
			public void onClick() {
				setResponsePage(new ListUsersForAdministrationPage());
			}
			@Override
			public boolean isVisible() {
				return WicketSession.get().getUser() != null &&
					WicketSession.get().getUser().isAdmin();
			}
		});
	}
	
	private void addLogoImage() {
		add(new Image("logoImage", IssueTrackerApp.LOGO));
	}
}