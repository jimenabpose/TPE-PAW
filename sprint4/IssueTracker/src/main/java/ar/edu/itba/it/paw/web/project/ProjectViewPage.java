package ar.edu.itba.it.paw.web.project;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import ar.edu.itba.it.paw.domain.project.Project;
import ar.edu.itba.it.paw.domain.project.Version;
import ar.edu.itba.it.paw.domain.project.VersionCalculations;
import ar.edu.itba.it.paw.domain.user.User;
import ar.edu.itba.it.paw.web.WicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.user.UserProfileLinkPanel;

public class ProjectViewPage extends BasePage {

	public ProjectViewPage(Project project) {
		addProjectInfo();
		addLinks();
		addProjectMembersList();
		addChart(project);
		addProjectIssuesChanges();
	}
	
	private void addProjectInfo() {
		add(new Label("code", new PropertyModel<String>(WicketSession.get().getProjectModel(), "code")));
		add(new Label("name", new PropertyModel<String>(WicketSession.get().getProjectModel(), "name")));
		add(new Label("description", new PropertyModel<String>(WicketSession.get().getProjectModel(), "description")));
		add(new UserProfileLinkPanel("profileLinkLeader", WicketSession.get().getProject().getLeader()));
		add(new Label("public", WicketSession.get().getProject().isPublic() ? new ResourceModel("isPublic") : new ResourceModel("isNotPublic")));
	}
	
	private void addLinks() {
		add(new Link<Void>("createProjectVersion") {
			@Override
			public void onClick() {
				setResponsePage(new CreateVersionPage());
			}
			
			@Override
			public boolean isVisible() {
				return WicketSession.get().getUser() != null &&
				WicketSession.get().getUser().equals(WicketSession.get().getProject().getLeader());
			}
		});
		
		add(new Link<Void>("listProjectVersions") {
			@Override
			public void onClick() {
				setResponsePage(new ListVersionsPage());
			}
		});
		
		add(new Link<Void>("consultProjectState") {
			@Override
			public void onClick() {
				setResponsePage(new ProjectStatePage(WicketSession.get().getProject()));
			}
			
			@Override
			public boolean isVisible() {
				return WicketSession.get().getUser() != null &&
				WicketSession.get().getUser().equals(WicketSession.get().getProject().getLeader());
			}
		});
		
		add(new Link<Void>("selectProjectParticipants") {
			@Override
			public void onClick() {
				setResponsePage(new ProjectUsersSelectPage());
			}
			
			@Override
			public boolean isVisible() {
				return WicketSession.get().getUser() != null &&
				WicketSession.get().getUser().equals(WicketSession.get().getProject().getLeader());
			}
		});
		
	}
	
	private void addProjectMembersList() {
		
		IModel<List<User>> userModel = new LoadableDetachableModel<List<User>>(){
			@Override
			protected List<User> load(){
				return new ArrayList<User>(WicketSession.get().getProject().getUsers());
			}
		};
		
		ListView<User> userView = new PropertyListView<User>("projectMembers", userModel){
			@Override
			protected void populateItem(ListItem<User> item){
				item.add(new UserProfileLinkPanel("profileLinkUser", item.getModelObject()));
				item.add(new Label("firstName"));
				item.add(new Label("lastName"));
			}
		};
		
		add(userView);
	}
	
	private void addChart(Project project) {

		String versionNames = "";
		String estimatedTimeString = "";
		String workedTimeString = "";
		String maxTimeString;
		float maxTime = 0;
		
		for(Version version : project.getVersions()) {
			VersionCalculations versionCalculations = new VersionCalculations(version.getIssues());
			versionNames += "|" + version.getName();
			estimatedTimeString += String.valueOf(versionCalculations.getTTE()) + ",";
			workedTimeString += String.valueOf(versionCalculations.getTTT()) + ",";
			
			if(maxTime < versionCalculations.getTTE()) {
				maxTime = versionCalculations.getTTE();
			}
			
			if(maxTime < versionCalculations.getTTT()) {
				maxTime = versionCalculations.getTTT();
			}
		}
		
		if(estimatedTimeString.length() >= 1) {
			estimatedTimeString = estimatedTimeString.substring(0, estimatedTimeString.length() - 1);
		}
		if(workedTimeString.length() >= 1) {
			workedTimeString = workedTimeString.substring(0, workedTimeString.length() - 1);
		}
		maxTimeString = String.valueOf(maxTime);
			   
		String url = "https://chart.googleapis.com/chart";
		url += "?chxl=1:" + versionNames;
		url += "&chxr=0,0," + maxTimeString;
		url += "&chds=0," + maxTimeString;
		url += "&chxt=y,x";
		url += "&chbh=a";
		url += "&chs=700x225";
		url += "&cht=bvg";
		url += "&chco=A2C180,3D7930";
		
		url += "&chd=t:" + estimatedTimeString + "|" + workedTimeString;
		url += "&chdl=" + getString("estimatedTime") + "|" +getString("workedTime");
		url += "&chtt=" + getString("chartTitle");
		
		add(new WebMarkupContainer("chartContainer") {
			@Override
			public boolean isVisible() {
				return WicketSession.get().getUser() != null &&
					WicketSession.get().getProject().getLeader().equals(WicketSession.get().getUser());
			};
		}.add(new SimpleAttributeModifier("src", url)));
	}
	
	private void addProjectIssuesChanges() {
		add(new ProjectIssuesChangesPanel("projectIssueChanges"));
	}
}
