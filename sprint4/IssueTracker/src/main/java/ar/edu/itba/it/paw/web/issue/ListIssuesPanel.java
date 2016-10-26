package ar.edu.itba.it.paw.web.issue;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.issue.Issue;
import ar.edu.itba.it.paw.web.user.UserProfileLinkPanel;

public class ListIssuesPanel extends Panel{
	
	public ListIssuesPanel(String id, IModel<List<Issue>> issueModel){
		super(id);
		
		PageableListView<Issue> listView = new PageableListView<Issue>("issueList", issueModel, 5){
			@Override
			protected void populateItem(final ListItem<Issue> item){
				item.setModel(new EntityModel<Issue>(Issue.class, item.getModelObject()));
				item.add(new Label("code", new PropertyModel<String>(item.getModel(), "code")));
				item.add(new Link<Void>("titleLink"){
					@Override
					public void onClick(){
						setResponsePage(new ViewIssuePage(item.getModelObject()));
					}
				}.add(new Label("title", new PropertyModel<String>(item.getModel(), "title"))));
				item.add(new Label("creationDate", new PropertyModel<String>(item.getModel(), "creationDate")));
				item.add(new UserProfileLinkPanel("profileLinkAssignee", item.getModelObject().getAssignee()));
				item.add(new UserProfileLinkPanel("profileLinkReporter", item.getModelObject().getReporter()));
				item.add(new Label("state", new PropertyModel<String>(item.getModel(), "state")));
				item.add(new Label("resolution", new PropertyModel<String>(item.getModel(), "resolution")));
				item.add(new PriorityInfoPanel("priority", item.getModelObject().getPriority()));
				item.add(new IssueTypeInfoPanel("issueType", item.getModelObject().getIssueType()));
			}
		};
		
		add(listView);
		add(new PagingNavigator("navigator", listView));
	}

}
