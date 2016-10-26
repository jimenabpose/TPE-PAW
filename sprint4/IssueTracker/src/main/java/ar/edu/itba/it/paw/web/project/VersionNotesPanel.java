package ar.edu.itba.it.paw.web.project;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;

import ar.edu.itba.it.paw.domain.issue.Issue;

public class VersionNotesPanel extends Panel {

	public VersionNotesPanel(String id, String typeName, IModel<List<Issue>> issueModel) {
		super(id);
		
		add(new Label("typeName", new StringResourceModel(typeName, null)));

		ListView<Issue> issueView = new PropertyListView<Issue>("issueList", issueModel){
			@Override
			protected void populateItem(ListItem<Issue> item){
				item.add(new Label("code"));
				item.add(new Label("title"));
			}
		};
		
		add(issueView);
	}
}
