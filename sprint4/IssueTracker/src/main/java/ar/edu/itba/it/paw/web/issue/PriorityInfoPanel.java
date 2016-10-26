package ar.edu.itba.it.paw.web.issue;

import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;

import ar.edu.itba.it.paw.domain.issue.Priority;
import ar.edu.itba.it.paw.web.IssueTrackerApp;

public class PriorityInfoPanel extends Panel{

	private transient int dedicatedTime;
	private transient String description;
	
	
	public PriorityInfoPanel(String id, Priority priority){
		super(id);
		add(new Image("priority", IssueTrackerApp.priorityImages.get(priority.toString())));
	}
}
