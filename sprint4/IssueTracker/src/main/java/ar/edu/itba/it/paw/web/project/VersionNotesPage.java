package ar.edu.itba.it.paw.web.project;

import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.issue.Issue;
import ar.edu.itba.it.paw.domain.issue.IssueType;
import ar.edu.itba.it.paw.domain.project.Version;
import ar.edu.itba.it.paw.web.base.BasePage;

public class VersionNotesPage extends BasePage {

	public VersionNotesPage(Version version) {
		addVersionNotesInfo(version);
	}
	
	private void addVersionNotesInfo(Version version) {
		
		final IModel<Version> versionModel = new EntityModel<Version>(Version.class, version);
		
		IModel<List<Issue>> errorIssues = new LoadableDetachableModel<List<Issue>>() {
			@Override
			protected List<Issue> load() {
				return versionModel.getObject().getIssuesByType().get(IssueType.ERROR.getName());
			}
		};
		add(new VersionNotesPanel("errorPanel", "errorType", errorIssues));
		
		IModel<List<Issue>> newCharIssues = new LoadableDetachableModel<List<Issue>>() {
			@Override
			protected List<Issue> load() {
				return versionModel.getObject().getIssuesByType().get(IssueType.NEWCHAR.getName());
			}
		};
		add(new VersionNotesPanel("newCharPanel", "newCharType", newCharIssues));
		
		IModel<List<Issue>> improvementIssues = new LoadableDetachableModel<List<Issue>>() {
			@Override
			protected List<Issue> load() {
				return versionModel.getObject().getIssuesByType().get(IssueType.IMPROVEMENT.getName());
			}
		};
		add(new VersionNotesPanel("improvementPanel", "improvementType", improvementIssues));
		
		IModel<List<Issue>> techniqueIssues = new LoadableDetachableModel<List<Issue>>() {
			@Override
			protected List<Issue> load() {
				return versionModel.getObject().getIssuesByType().get(IssueType.TECHNIQUE.getName());
			}
		};
		add(new VersionNotesPanel("techniquePanel", "techniqueType", techniqueIssues));
		
		IModel<List<Issue>> issueIssues = new LoadableDetachableModel<List<Issue>>() {
			@Override
			protected List<Issue> load() {
				return versionModel.getObject().getIssuesByType().get(IssueType.ISSUE.getName());
			}
		};
		add(new VersionNotesPanel("issuePanel", "issueType", issueIssues));
		
		IModel<List<Issue>> undefinedIssues = new LoadableDetachableModel<List<Issue>>() {
			@Override
			protected List<Issue> load() {
				return versionModel.getObject().getIssuesByType().get("no definido");
			}
		};
		add(new VersionNotesPanel("undefinedPanel", "undefinedType", undefinedIssues));
		
	}
}
