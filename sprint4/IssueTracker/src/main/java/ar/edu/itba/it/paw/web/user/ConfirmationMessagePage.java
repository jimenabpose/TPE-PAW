package ar.edu.itba.it.paw.web.user;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.ResourceModel;

import ar.edu.itba.it.paw.web.WicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;

public class ConfirmationMessagePage extends BasePage{

	public ConfirmationMessagePage(){
		add(new Label("message", new ResourceModel("confirmation")));
		
		add(new Link<Void>("register"){
			@Override
			public void onClick(){
				setResponsePage(new RegisterUserPage());
				setRedirect(true);
			}
			
			@Override
			public boolean isVisible() {
				return WicketSession.get().getUser().isAdmin();
			}
		});

		add(new Link<Void>("index"){
			@Override
			public void onClick(){
				setResponsePage(new IndexPage());
				setRedirect(true);
			}
		});
	}
}
