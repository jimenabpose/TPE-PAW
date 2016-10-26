package ar.edu.itba.it.paw.web.user;

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

import ar.edu.itba.it.paw.domain.exceptions.RegisteredUsernameException;
import ar.edu.itba.it.paw.domain.user.UserRepo;
import ar.edu.itba.it.paw.domain.user.UserSolicitation;
import ar.edu.itba.it.paw.domain.user.UserSolicitationRepo;
import ar.edu.itba.it.paw.web.base.BasePage;

public class ListUsersForAdministrationPage extends BasePage{

	@SpringBean
	private UserRepo userRepo;
	
	@SpringBean
	private UserSolicitationRepo userSolicitationRepo;
	
	public ListUsersForAdministrationPage() {
		addErasableUsersList();
	}
	
	private void addErasableUsersList() {
		add(new FeedbackPanel("feedback"));
		
		IModel<List<UserSolicitation>> userSolicitationModel = new LoadableDetachableModel<List<UserSolicitation>>(){
			@Override
			protected List<UserSolicitation> load(){
				return userSolicitationRepo.getAllSolicitations();
			}
		};
		
		ListView<UserSolicitation> userView = new PropertyListView<UserSolicitation>("userList", userSolicitationModel){
			@Override
			protected void populateItem(ListItem<UserSolicitation> item){
				item.add(new Label("username"));
				item.add(new Label("firstName"));
				item.add(new Label("lastName"));
				
				item.add(new Link<UserSolicitation>("accept", item.getModel()) {
					@Override
					public void onClick() {
						try {
							userRepo.saveUser(getModelObject().getUser());
							userSolicitationRepo.deleteSolicitation(getModelObject());
						} catch (RegisteredUsernameException e) {
							error(getString("registeredUsernameException"));
							return;
						}
						
						setResponsePage(new ListUsersForAdministrationPage());
					}
				});
				
				item.add(new Link<UserSolicitation>("deny", item.getModel()) {
					@Override
					public void onClick() {
						userSolicitationRepo.deleteSolicitation(getModelObject());
						setResponsePage(new ListUsersForAdministrationPage());
					}
				});
			}
		};
		
		add(userView);
	}
	
}
