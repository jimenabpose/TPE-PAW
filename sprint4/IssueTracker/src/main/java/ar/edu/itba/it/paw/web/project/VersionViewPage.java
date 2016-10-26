package ar.edu.itba.it.paw.web.project;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.issue.Issue;
import ar.edu.itba.it.paw.domain.issue.Priority;
import ar.edu.itba.it.paw.domain.issue.State;
import ar.edu.itba.it.paw.domain.project.Version;
import ar.edu.itba.it.paw.domain.project.VersionCalculations;
import ar.edu.itba.it.paw.domain.project.VersionState;
import ar.edu.itba.it.paw.web.WicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;

public class VersionViewPage extends BasePage {

	public VersionViewPage(Version version) {
		final IModel<Version> versionModel = new EntityModel<Version>(Version.class, version);
		
		add(new Label("name", new PropertyModel<String>(versionModel, "name")));
		add(new Label("state", new PropertyModel<VersionState>(versionModel, "state")));
		add(new Label("releaseDate", new PropertyModel<Date>(versionModel, "releaseDate")));
		
		final IModel<VersionCalculations> versionCalculations = new LoadableDetachableModel<VersionCalculations>() {
			@Override
			protected VersionCalculations load() {
				VersionCalculations vc = new VersionCalculations(versionModel.getObject().getIssues());
				return vc;
			}
		};
		
		Map<String, Integer> states = versionCalculations.getObject().getStates_count();
		
		add(new Label("openIssues", String.valueOf(states.get(State.OPEN.getName()))));
		add(new Label("finishedIssues", states.get(State.FINISHED.getName()).toString()));
		add(new Label("onCourseIssues", states.get(State.ONCOURSE.getName()).toString()));
		add(new Label("closedIssues", states.get(State.CLOSED.getName()).toString()));
		
		add(new Label("totalEstimatedTime", new PropertyModel<String>(versionCalculations, "TTE")));
		add(new Label("totalWorkedTime", new PropertyModel<String>(versionCalculations, "TTT")));
		add(new Label("timeToFinish", new PropertyModel<String>(versionCalculations, "TEFV")));
		
		add(new Link<Void>("versionNotes") {
			@Override
			public void onClick() {
				setResponsePage(new VersionNotesPage(versionModel.getObject()));
			}
		});

		int issuesQuantity = version.getIssues().size();
		
		HashMap<String, List<Issue>> issuesByPriority = version.getIssuesByPriority();
		
		String url = "https://chart.googleapis.com/chart?";
		url += "cht=p3";
		url += "&chs=400x100";
		url += "&chd=t:"+ (float)issuesByPriority.get(Priority.CRITICAL.toString()).size()/(float)issuesQuantity;
		url +="," + (float)issuesByPriority.get(Priority.HIGH.toString()).size()/(float)issuesQuantity;
		url +="," + (float)issuesByPriority.get(Priority.LOW.toString()).size()/(float)issuesQuantity;
		url +="," + (float)issuesByPriority.get(Priority.NORMAL.toString()).size()/(float)issuesQuantity;
		url +="," + (float)issuesByPriority.get(Priority.TRIVIAL.toString()).size()/(float)issuesQuantity;
		url += "&chl=" + getString(Priority.class.getSimpleName() + "." + Priority.CRITICAL);
		url += "|" + getString(Priority.class.getSimpleName() + "." + Priority.HIGH);
		url += "|" + getString(Priority.class.getSimpleName() + "." + Priority.LOW);
		url += "|" + getString(Priority.class.getSimpleName() + "." + Priority.NORMAL);
		url += "|" + getString(Priority.class.getSimpleName() + "." + Priority.TRIVIAL);
		
		add(new WebMarkupContainer("chartContainer"){
			@Override
			public boolean isVisible() {
				return WicketSession.get().getUser() != null &&
					WicketSession.get().getProject().userBelongs(WicketSession.get().getUser());
			};
		}.add(new SimpleAttributeModifier("src", url)));
		
	}
	
}
