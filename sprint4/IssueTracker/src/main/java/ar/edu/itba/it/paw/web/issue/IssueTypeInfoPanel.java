package ar.edu.itba.it.paw.web.issue;

import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;

import ar.edu.itba.it.paw.domain.issue.IssueType;
import ar.edu.itba.it.paw.web.IssueTrackerApp;

public class IssueTypeInfoPanel extends Panel{

	private transient int dedicatedTime;
	private transient String description;
	
	
	public IssueTypeInfoPanel(String id, IssueType issueType){
		super(id);
		add(new Image("issueType", issueType != null? IssueTrackerApp.issueTypeImages.get(issueType.toString()) : null));
	}
}
