package ar.edu.itba.it.paw.web.project;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.project.Version;
import ar.edu.itba.it.paw.web.base.BasePage;

public class EditVersionPage extends BasePage {
	
	public EditVersionPage(Version version) {
		add(new FeedbackPanel("feedback"));
		final Form<Version> form = new Form<Version>("form", new CompoundPropertyModel<Version>(new EntityModel<Version>(Version.class, version)));
		form.add(new VersionInfoPanel("versionsInfo", false));
		form.add(new Button("accept", new ResourceModel("accept")) {
			@Override
			public void onSubmit() {
				setResponsePage(new ListVersionsPage());
			}
		});
		add(form);
	}

}