package ar.edu.itba.it.paw.web.issue;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import ar.edu.itba.it.paw.domain.issue.Issue;
import ar.edu.itba.it.paw.domain.issue.IssueChanges;
import ar.edu.itba.it.paw.domain.issue.Resolution;

public class IssueChangesPanel extends Panel{
	
	private transient Resolution resolution;
	
	public IssueChangesPanel(String id, final IModel<Issue> issueModel){
		super(id);
		
		IModel<List<IssueChanges>> changesModel = new LoadableDetachableModel<List<IssueChanges>>(){
			@Override
			protected List<IssueChanges> load() {
				return (issueModel.getObject()).getChanges();
			}
		};
		
		ListView<IssueChanges> issueChanges = new PropertyListView<IssueChanges>("issueChanges", changesModel){
			@Override
			protected void populateItem(ListItem<IssueChanges> item){
				item.add(new Label("changer"));
				item.add(new Label("changeDate"));
				item.add(new Label("field"));
				item.add(new Label("oldValue"));
				item.add(new Label("newValue"));
			}
			
			@Override
			public boolean isVisible(){
				return !issueModel.getObject().getChanges().isEmpty();
			}
		};
		
		add(issueChanges);

	}

}
