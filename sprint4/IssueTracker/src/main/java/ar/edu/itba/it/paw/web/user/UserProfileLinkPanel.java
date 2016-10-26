package ar.edu.itba.it.paw.web.user;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.project.Project;
import ar.edu.itba.it.paw.domain.user.User;
import ar.edu.itba.it.paw.web.WicketSession;

public class UserProfileLinkPanel extends Panel{

	public UserProfileLinkPanel(String id, User user){
		super(id);
		final WicketSession session = WicketSession.get();
		
		final IModel<User> userModel = new EntityModel<User>(User.class, user);
		setDefaultModel(userModel);

		Label userLabel = new Label("user", new PropertyModel<String>(userModel, "username")){
			@Override
			public boolean isVisible(){
				Project project = session.getProject();
				return UserProfileLinkPanel.this.getDefaultModelObject()!= null &&
					project != null &&
					!UserProfileLinkPanel.this.getDefaultModelObject().equals(project.getLeader());
			}
		};
		
		Label leaderLabel = new Label("leader", new PropertyModel<String>(userModel, "username")){
			@Override
			public boolean isVisible(){
				Project project = session.getProject();
				return UserProfileLinkPanel.this.getDefaultModelObject()!= null &&
					project != null &&
					UserProfileLinkPanel.this.getDefaultModelObject().equals(project.getLeader());
			}
		};
		
		add(new Link<Void>("editUser"){
			@Override
			public void onClick(){
				setResponsePage(new UserProfilePage(userModel));
				setRedirect(true);
			}
		}.add(userLabel).add(leaderLabel));
	}
}
