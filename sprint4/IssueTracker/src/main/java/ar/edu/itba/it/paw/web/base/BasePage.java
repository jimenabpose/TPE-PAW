package ar.edu.itba.it.paw.web.base;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.it.paw.domain.user.User;
import ar.edu.itba.it.paw.web.HomePage;
import ar.edu.itba.it.paw.web.WicketSession;
import ar.edu.itba.it.paw.web.issue.CreateIssuePage;
import ar.edu.itba.it.paw.web.issue.ListIssuesPage;
import ar.edu.itba.it.paw.web.project.EditProjectPage;
import ar.edu.itba.it.paw.web.project.ProjectSelectPage;
import ar.edu.itba.it.paw.web.project.ProjectViewPage;
import ar.edu.itba.it.paw.web.user.ListErasableUsersPage;
import ar.edu.itba.it.paw.web.user.RegisterUserPage;
import ar.edu.itba.it.paw.web.user.UserProfileLinkPanel;

public class BasePage extends WebPage {

	public BasePage() {
		addData();
		addLinks();
	}
	
	private void addData() {
		add(new Label("separator", WicketSession.get().getUser() != null && WicketSession.get().getProject() != null ? " | " : ""));
		add(new UserProfileLinkPanel("profileLinkUser", WicketSession.get().getUser()));
		add(new Label("project", new PropertyModel<String>(WicketSession.get().getProjectModel(), "name")));
	}
	
	private void addLinks() {
		add(new Link<Void>("viewIssues") {
			@Override
			public void onClick() {
				setResponsePage(new ListIssuesPage());
			}
			
			@Override
			public boolean isVisible() {
				return WicketSession.get().getProject() != null &&
					WicketSession.get().getProject() != null;
			}
		});
		
		add(new Link<Void>("createIssue") {
			@Override
			public void onClick() {
				setResponsePage(new CreateIssuePage());
			}
			
			@Override
			public boolean isVisible() {
				return WicketSession.get().getUser() != null &&
					WicketSession.get().getProject() != null &&
					WicketSession.get().getProject().userBelongs(WicketSession.get().getUser());
			}
		});
		
		add(new Link<Void>("projectDetail") {
			@Override
			public void onClick() {
				setResponsePage(new ProjectViewPage(WicketSession.get().getProject()));
			}
			
			@Override
			public boolean isVisible() {
				return WicketSession.get().getProject() != null;
			}
		});
		
		add(new Link<Void>("editProject") {
			@Override
			public void onClick() {
				setResponsePage(new EditProjectPage(WicketSession.get().getProject()));
			}
			
			@Override
			public boolean isVisible() {
				return WicketSession.get().getUser() != null && 
					((User) WicketSession.get().getUser()).isAdmin() &&
					WicketSession.get().getProject() != null;
			}
		});
		
		add(new Link<Void>("registerUser") {
			@Override
			public void onClick() {
				setResponsePage(new RegisterUserPage());
			}
			
			@Override
			public boolean isVisible() {
				return WicketSession.get().getUser() != null &&
					((User) WicketSession.get().getUser()).isAdmin();
			}
		});
		
		add(new Link<Void>("eraseUser") {
			@Override
			public void onClick() {
				setResponsePage(new ListErasableUsersPage());
			}
			
			@Override
			public boolean isVisible() {
				return WicketSession.get().getUser() != null &&
					((User) WicketSession.get().getUser()).isAdmin();
			}
		});
		
		add(new Link<Void>("logout") {
			@Override
			public void onClick() {
				WicketSession.get().setUser(null);
				WicketSession.get().setProject(null);
				setResponsePage(new HomePage());
			}
			
			@Override
			public boolean isVisible() {
				return WicketSession.get().getUser() != null;
			}
		});
		
		add(new Link<Void>("selectProject") {
			@Override
			public void onClick() {
				setResponsePage(new ProjectSelectPage());
			}
		});
		
		add(new Link<Void>("login") {
			@Override
			public void onClick() {
				WicketSession.get().setUser(null);
				WicketSession.get().setProject(null);
				setResponsePage(new HomePage());
			}
			
			@Override
			public boolean isVisible() {
				return WicketSession.get().getUser() == null;
			}
		});
	}
	
}
