package ar.edu.itba.it.paw.web.user;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.StringResourceModel;

import ar.edu.itba.it.paw.web.WicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;


public class IndexPage extends BasePage{

	public IndexPage(){
		add(new Label("welcomeMessage", WicketSession.get().getUser() == null ? new StringResourceModel("publicWelcomeMessage", WicketSession.get().getUserModel()) : new StringResourceModel("welcomeMessage", WicketSession.get().getUserModel())));
	}
}
