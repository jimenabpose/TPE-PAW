package ar.edu.itba.it.paw.web.issue;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import ar.edu.itba.it.paw.domain.issue.Issue;
import ar.edu.itba.it.paw.domain.issue.RelationType;
import ar.edu.itba.it.paw.web.WicketSession;

public class IssueRelationTypePanel extends Panel{
	
	public IssueRelationTypePanel(String id, final IModel<Issue> issueModel, final IModel<List<Issue>> issueListModel, final RelationType relationType){
		super(id);
		setDefaultModel(issueModel);
		
		ListView<Issue> issueListView = new PropertyListView<Issue>("issueRelationList", issueListModel){
			@Override
			protected void populateItem(ListItem<Issue> item){
				item.add(new Link<Issue>("viewIssue",  item.getModel()){
					@Override
					public void onClick(){
						setResponsePage(new ViewIssuePage(getModelObject()));
					}
				}.add(new Label("title")));
				
				item.add(new Link<Issue>("deleteRelation",  item.getModel()){
					@Override
					public void onClick(){
						issueModel.getObject().removeRelation(getModelObject(), relationType);
						setResponsePage(new ViewIssuePage((Issue) IssueRelationTypePanel.this.getDefaultModelObject()));
					}
					
					@Override
					public boolean isVisible() {
						return WicketSession.get().getUser() != null &&
							WicketSession.get().getProject().userBelongs(WicketSession.get().getUser());
					}
				});
			}
		};
		
		add(issueListView);
	}
}
