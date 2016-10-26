package ar.edu.itba.it.paw.web.issue;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;

import ar.edu.itba.it.paw.domain.issue.Comment;
import ar.edu.itba.it.paw.domain.issue.Issue;
import ar.edu.itba.it.paw.domain.user.User;
import ar.edu.itba.it.paw.web.WicketSession;

public class IssueCommentsPanel extends Panel{

	private transient String text;
	
	public IssueCommentsPanel(String id, final IModel<Issue> issueModel){
		super(id);
		setDefaultModel(issueModel);
		
		final WicketSession session = WicketSession.get();
		
		IModel<List<Comment>> commentModel = new LoadableDetachableModel<List<Comment>>(){
			@Override
			protected List<Comment> load(){
				return ((Issue) IssueCommentsPanel.this.getDefaultModelObject()).getComments();
			}
		};
		
		ListView<Comment> commentsList = new PropertyListView<Comment>("commentsList", commentModel){
			@Override
			protected void populateItem(ListItem<Comment> item){
				item.add(new Label("user"));
				item.add(new Label("text"));
				item.add(new Label("creationDate"));
			}
		};
		
		add(commentsList);

		Form<IssueCommentsPanel> form = new Form<IssueCommentsPanel>("addComment", new CompoundPropertyModel<IssueCommentsPanel>(this)){
			@Override
			protected void onSubmit(){
				try {
					User user = session.getUser();
					Comment comment = new Comment(text, user, (Issue)IssueCommentsPanel.this.getDefaultModelObject());
				} catch (IllegalArgumentException e) {
					error(getString("emptyComment"));
					return;
				}
			}
			
			@Override
			public boolean isVisible() {
				return WicketSession.get().getUser() != null &&
					WicketSession.get().getProject().userBelongs(WicketSession.get().getUser());
			}
		};

		form.add(new TextArea<String>("text").setRequired(true));
		form.add(new Button("publish", new ResourceModel("publish")));
		
		add(form);
	}
}
