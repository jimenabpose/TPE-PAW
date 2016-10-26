package ar.edu.itba.it.paw.web.project;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.exceptions.NotDeletableUserException;
import ar.edu.itba.it.paw.domain.user.User;
import ar.edu.itba.it.paw.domain.user.UserRepo;
import ar.edu.itba.it.paw.web.WicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.user.UserProfileLinkPanel;

public class ProjectUsersSelectPage extends BasePage{

	@SpringBean
	private UserRepo userRepo;
	
	public ProjectUsersSelectPage() {
		add(new FeedbackPanel("feedback"));
		addSystemUsers();
	}
	
	private void addSystemUsers() {
		IModel<List<User>> usersModel = new LoadableDetachableModel<List<User>>(){
			@Override
			protected List<User> load(){
				List<User> allUsers = userRepo.getActiveUsers(); 
				allUsers.remove(WicketSession.get().getProject().getLeader());
				return allUsers;
			}
		};
		
		ListView<User> userView = new PropertyListView<User>("users", usersModel){
			@Override
			protected void populateItem(ListItem<User> item){
				item.add(new UserProfileLinkPanel("profileLinkUser", item.getModelObject()));
				item.add(new Label("firstName"));
				item.add(new Label("lastName"));
				
				item.add(new Link<User>("delete", item.getModel()) {
					@Override
					public void onClick() {
						try {
							WicketSession.get().getProject().delete(getModelObject());
							setResponsePage(new ProjectUsersSelectPage());
						} catch(NotDeletableUserException e) {
							error(getString("notDeletableUserException"));
							return;
						}
					}
					
					@Override
					public boolean isVisible() {
						return !userRepo.getActiveUsersNotFromProject(WicketSession.get().getProject()).contains(getModelObject());
					}
				});
				item.add(new Link<User>("add", item.getModel()) {
					@Override
					public void onClick() {
						WicketSession.get().getProject().add(getModelObject());
						setResponsePage(new ProjectUsersSelectPage());
					}
					
					@Override
					public boolean isVisible() {
						return userRepo.getActiveUsersNotFromProject(WicketSession.get().getProject()).contains(getModelObject());
					}
				});
			}
		};
		add(userView);
	}
	
}
