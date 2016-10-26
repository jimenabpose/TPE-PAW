package ar.edu.itba.it.paw.web.project;

import java.util.Date;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;

import ar.edu.itba.it.paw.domain.exceptions.VersionNameRepeatedException;
import ar.edu.itba.it.paw.domain.project.Version;
import ar.edu.itba.it.paw.domain.project.VersionState;
import ar.edu.itba.it.paw.web.WicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;

public class CreateVersionPage extends BasePage {

	private transient String name;
	private transient Date releaseDate;
	private transient String description;
	private transient VersionState state;

	public CreateVersionPage() {
		add(new FeedbackPanel("feedback"));
		final Form<Version> form = new Form<Version>("form", new CompoundPropertyModel<Version>(this));
		form.add(new VersionInfoPanel("versionsInfo", true));
		form.add(new Button("accept", new ResourceModel("accept")) {
			@Override
			public void onSubmit() {
				try {
					Version version = new Version(name, description, releaseDate, WicketSession.get().getProject());
					setResponsePage(new ProjectViewPage(WicketSession.get().getProject()));
				} catch (VersionNameRepeatedException e) {
					error(getString("versionNameRepeatedException"));
					return;
				}
			}
		});
		add(form);
	}
}