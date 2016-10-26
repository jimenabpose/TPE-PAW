package ar.edu.itba.it.paw.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.user.UserRepo;
import ar.edu.itba.it.paw.web.project.ProjectSelectPage;
import ar.edu.itba.it.paw.web.user.UserSolicitationPage;


public class HomePage extends WebPage {

	@SpringBean
	private UserRepo users;

	private transient String username;
	private transient String password;

	public HomePage() {
		addLoginComponents();
		addPublicUserLink();
		addRegisterSolicitationLink();
		addLogoImage();
	}

	private void addLoginComponents() {
		add(new FeedbackPanel("feedback"));
		Form<HomePage> form = new Form<HomePage>("loginForm",
				new CompoundPropertyModel<HomePage>(this)) {
			@Override
			protected void onSubmit() {
				WicketSession session = WicketSession.get();

				if (session.signIn(username, password, users)) {
					if (!continueToOriginalDestination()) {
						setResponsePage(new ProjectSelectPage());
					}
				} else {
					error(getString("invalidCredentials"));
					return;
				}
				setRedirect(true);
			}
		};

		form.add(new TextField<String>("username").setRequired(true));
		form.add(new PasswordTextField("password"));
		form.add(new Button("login", new ResourceModel("login")));
		add(form);
	}

	private void addPublicUserLink() {
		add(new Link<Void>("publicUserAccess") {
			@Override
			public void onClick() {
				setResponsePage(new ProjectSelectPage());
			}
		});
	}
	
	private void addRegisterSolicitationLink() {
		add(new Link<Void>("registerSolicitation") {
			@Override
			public void onClick() {
				setResponsePage(new UserSolicitationPage());
			}
		});
	}
	
	private void addLogoImage() {
		add(new Image("logoImage", IssueTrackerApp.LOGO));
	}
}
