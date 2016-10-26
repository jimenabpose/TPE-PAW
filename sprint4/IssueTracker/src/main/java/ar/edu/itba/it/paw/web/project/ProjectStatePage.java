package ar.edu.itba.it.paw.web.project;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.project.Project;
import ar.edu.itba.it.paw.domain.project.ProjectState;
import ar.edu.itba.it.paw.web.base.BasePage;

public class ProjectStatePage extends BasePage {

	public ProjectStatePage(Project project) {
		addProjectStateInfo(project);
	}
	
	private void addProjectStateInfo(Project project) {
		final IModel<Project> projectModel = new EntityModel<Project>(Project.class, project);
		
		IModel<ProjectState> projectState = new LoadableDetachableModel<ProjectState>() {
			@Override
			protected ProjectState load() {
				ProjectState projectState = new ProjectState();
				projectState.build(projectModel.getObject().getIssues());
				return projectState;
			}
		};
		
		add(new Label("countOpen", new PropertyModel<String>(projectState, "countOpen")));
		add(new Label("countOnCourse", new PropertyModel<String>(projectState, "countOnCourse")));
		add(new Label("countFinished", new PropertyModel<String>(projectState, "countFinished")));
		add(new Label("countClosed", new PropertyModel<String>(projectState, "countClosed")));
		add(new Label("timeOpen", new PropertyModel<String>(projectState, "timeOpen")));
		add(new Label("timeOnCourse", new PropertyModel<String>(projectState, "timeOnCourse")));
		add(new Label("timeFinished", new PropertyModel<String>(projectState, "timeFinished")));
		add(new Label("timeClosed", new PropertyModel<String>(projectState, "timeClosed")));
	}
}
