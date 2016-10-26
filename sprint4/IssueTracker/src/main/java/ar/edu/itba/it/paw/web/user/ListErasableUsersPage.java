package ar.edu.itba.it.paw.web.user;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.user.User;
import ar.edu.itba.it.paw.domain.user.UserRepo;
import ar.edu.itba.it.paw.web.base.BasePage;

public class ListErasableUsersPage extends BasePage{

	@SpringBean
	private UserRepo userRepo;
	
	public ListErasableUsersPage() {
		addErasableUsersList();
	}
	
	private void addErasableUsersList() {
		IModel<List<User>> usersModel = new LoadableDetachableModel<List<User>>(){
			@Override
			protected List<User> load(){
				return userRepo.getDeletableUsers();
			}
		};
		
		ListView<User> userView = new PropertyListView<User>("userList", usersModel){
			@Override
			protected void populateItem(ListItem<User> item){
				item.add(new UserProfileLinkPanel("profileLinkUser", item.getModelObject()));
				item.add(new Label("firstName"));
				item.add(new Label("lastName"));
				item.add(new Label("type"));
				
				item.add(new Link<User>("delete", item.getModel()) {
					@Override
					public void onClick() {
						userRepo.deleteUser(getModelObject());
						setResponsePage(new ListErasableUsersPage());
					}
				});
			}
		};
		
		add(userView);
	}
	
}
