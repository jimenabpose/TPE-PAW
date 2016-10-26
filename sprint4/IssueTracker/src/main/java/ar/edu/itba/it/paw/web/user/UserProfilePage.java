package ar.edu.itba.it.paw.web.user;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.user.User;
import ar.edu.itba.it.paw.domain.user.UserRepo;
import ar.edu.itba.it.paw.web.WicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;

public class UserProfilePage extends BasePage{

	@SpringBean
	UserRepo userRepo;
	
	public UserProfilePage(final IModel<User> userModel){
		final WicketSession session = WicketSession.get();
		
		add(new Label("profUsername", new PropertyModel<String>(userModel, "username")));
		add(new Label("profFirstName", new PropertyModel<String>(userModel, "firstName")));
		add(new Label("profLastName", new PropertyModel<String>(userModel, "lastName")));
		
		add(new Link<Void>("editUser"){
			@Override
			public void onClick(){
				setResponsePage(new EditUserPage(userModel));
				setRedirect(true);
			}
			
			@Override
			public boolean isVisible(){
				User loggedUser = session.getUser();
				User selectedUser = userModel.getObject();
				return loggedUser != null && loggedUser.equals(userModel.getObject());
//				return true;
			}
		});
	}
}
