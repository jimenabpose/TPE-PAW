package ar.edu.itba.it.paw.web.issue;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import ar.edu.itba.it.paw.domain.issue.Issue;
import ar.edu.itba.it.paw.domain.user.User;
import ar.edu.itba.it.paw.web.WicketSession;

public class FollowingIssuesPanel extends Panel{

	public FollowingIssuesPanel(String id, final IModel<Issue> issueModel){
		super(id);
		
		final WicketSession session = WicketSession.get();
		
		IModel<List<User>> followersModel = new LoadableDetachableModel<List<User>>(){
			@Override
			protected List<User> load(){
				return new ArrayList<User>(issueModel.getObject().getFollowers());
			}
		};
		
		ListView<User> followersList = new PropertyListView<User>("followersList", followersModel){
			@Override
			protected void populateItem(ListItem<User> item){
				item.add(new Label("username"));
			}
		};
		
		add(followersList);
		
		add(new Link<Void>("followIssue"){
			@Override
			public void onClick(){
				issueModel.getObject().addFollower(session.getUser());
			}
			
			@Override
			public boolean isVisible() {
				return session.getUser() != null && 
					!issueModel.getObject().getFollowers().contains(session.getUser());
			}
		});
			
		add(new Link<Void>("stopFollowingIssue"){
			@Override
			public void onClick(){
				issueModel.getObject().removeFollower(session.getUser());
			}
			
			@Override
			public boolean isVisible() {
				return  session.getUser() != null &&
					issueModel.getObject().getFollowers().contains(session.getUser());
			}
		});
	}
}
