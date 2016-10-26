package ar.edu.itba.it.paw.web.project;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.issue.IssueChanges;
import ar.edu.itba.it.paw.domain.project.ProjectRepo;
import ar.edu.itba.it.paw.web.WicketSession;

public class ProjectIssuesChangesPanel extends Panel{
	
	private static final int ISSUE_CHANGES_QUANT = 5;
	
	@SpringBean
	private ProjectRepo projectRepo;
	
	public ProjectIssuesChangesPanel(String id){
		super(id);
		
		IModel<List<IssueChanges>> changesModel = new LoadableDetachableModel<List<IssueChanges>>(){
			@Override
			protected List<IssueChanges> load() {
				return projectRepo.getLastsIssueChanges(WicketSession.get().getProject(), ISSUE_CHANGES_QUANT);
			}
		};
		
		ListView<IssueChanges> issueChanges = new PropertyListView<IssueChanges>("issueChanges", changesModel){
			@Override
			protected void populateItem(ListItem<IssueChanges> item){
				item.add(new Label("issue.title"));
				item.add(new Label("changer"));
				item.add(new Label("changeDate"));
				item.add(new Label("field"));
				item.add(new Label("oldValue"));
				item.add(new Label("newValue"));
			}
		};
		
		add(issueChanges);
		
	}

}
