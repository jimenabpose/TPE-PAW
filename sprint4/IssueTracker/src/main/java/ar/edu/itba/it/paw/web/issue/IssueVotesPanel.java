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
import org.apache.wicket.model.ResourceModel;

import ar.edu.itba.it.paw.domain.exceptions.UserCantVoteException;
import ar.edu.itba.it.paw.domain.issue.Issue;
import ar.edu.itba.it.paw.domain.user.User;
import ar.edu.itba.it.paw.web.WicketSession;

public class IssueVotesPanel extends Panel{

	public IssueVotesPanel(String id, final IModel<Issue> issueModel){
		super(id);
		
		final WicketSession session = WicketSession.get();
		
		add(new Label("noVotes", new ResourceModel("noVotes")){
			@Override
			public boolean isVisible(){
				return issueModel.getObject().getVoters().isEmpty();
			}
		});
		
		IModel<List<User>> votersModel = new LoadableDetachableModel<List<User>>(){
			@Override
			protected List<User> load(){
				return new ArrayList<User>(issueModel.getObject().getVoters());
			}
		};
		
		ListView<User> votersList = new PropertyListView<User>("votersList", votersModel){
			@Override
			protected void populateItem(ListItem<User> item){
				item.add(new Label("username"));
			}
			
			@Override
			public boolean isVisible(){
				return !issueModel.getObject().getVoters().isEmpty();
			}
		};
		
		add(votersList);
		
		add(new Link<Void>("addVoter"){
			@Override
			public void onClick(){
				try {
					(issueModel.getObject()).addVoter(session.getUser());
				} catch (UserCantVoteException e) {
					error(getString("userCantVoteException"));
					return;
				}
			}
			
			@Override
			public boolean isVisible(){
				User loggedUser = session.getUser();
				return loggedUser != null && !issueModel.getObject().getVoters().contains(loggedUser);
			}
		});
			
		add(new Link<Void>("removeVoter"){
			@Override
			public void onClick(){
				(issueModel.getObject()).removeVoter(session.getUser());
			}
			
			@Override
			public boolean isVisible(){
				User loggedUser = session.getUser();
				return loggedUser != null && issueModel.getObject().getVoters().contains(loggedUser);
			}
		});
	}
}
