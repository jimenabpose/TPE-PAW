package ar.edu.itba.it.paw.web.issue;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.issue.Issue;
import ar.edu.itba.it.paw.domain.issue.IssueRepo;
import ar.edu.itba.it.paw.domain.project.Project;
import ar.edu.itba.it.paw.web.WicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;

public class ListMyFollowedIssuesPage extends BasePage{

	@SpringBean
	IssueRepo issueRepo;
	
	private transient Project project;
	
	public ListMyFollowedIssuesPage(){
		
		IModel<List<Issue>> issueModel = new LoadableDetachableModel<List<Issue>>(){
			@Override
			protected List<Issue> load(){
				return new ArrayList<Issue>(WicketSession.get().getUser().getFollowingIssuesForProject(WicketSession.get().getProject()));
			}
		};
		
		add(new ListIssuesPanel("issuesPanel", issueModel));
	}
}
