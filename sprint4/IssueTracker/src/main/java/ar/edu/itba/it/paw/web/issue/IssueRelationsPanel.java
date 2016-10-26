package ar.edu.itba.it.paw.web.issue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;

import ar.edu.itba.it.paw.domain.exceptions.IssueRelationException;
import ar.edu.itba.it.paw.domain.issue.Issue;
import ar.edu.itba.it.paw.domain.issue.RelationType;
import ar.edu.itba.it.paw.domain.user.User;
import ar.edu.itba.it.paw.web.WicketSession;

public class IssueRelationsPanel extends Panel{

	private transient User loggedUser;
	private transient RelationType relation;
	private transient Issue relatedIssue;
	
	public IssueRelationsPanel(String id, final IModel<Issue> issueModel){
		super(id);
		
		final WicketSession session = WicketSession.get();
		loggedUser = session.getUser();
		
		Form<IssueRelationsPanel> formRelation = new Form<IssueRelationsPanel>("relateIssueForm", new CompoundPropertyModel<IssueRelationsPanel>(this)){
			@Override
			protected void onSubmit(){
				try {
					issueModel.getObject().relate(relatedIssue, relation);
				} catch (IssueRelationException e) {
					error(getString("issueRelationException"));
					return;
				}
			}
			
			@Override
			public boolean isVisible() {
				return WicketSession.get().getUser() != null &&
					WicketSession.get().getProject().userBelongs(WicketSession.get().getUser());
			}
		};
		
		IModel<List<RelationType>> relationModel = new LoadableDetachableModel<List<RelationType>>(){
			@Override
			protected List<RelationType> load() {
				return Arrays.asList(RelationType.values());
			}
		};
		
		formRelation.add(new DropDownChoice<RelationType>("relation", relationModel).setRequired(true));
		
		IModel<List<Issue>> relatedIssueModel = new LoadableDetachableModel<List<Issue>>(){
			@Override
			protected List<Issue> load() {
				return session.getProject().getIssues();
			}
		};
		
		formRelation.add(new DropDownChoice<Issue>("relatedIssue", relatedIssueModel).setRequired(true));
		
		formRelation.add(new Button("relate", new ResourceModel("relate")));
		
		add(formRelation);
		
		IModel<List<Issue>> dependsOnIssueModel = new LoadableDetachableModel<List<Issue>>(){
			@Override
			protected List<Issue> load(){
				return new ArrayList<Issue>(issueModel.getObject().getIssuesByRelationType(RelationType.DEPENDS));
			}
		};
		
		add(new IssueRelationTypePanel("issueDependencyListPanel", issueModel, dependsOnIssueModel, RelationType.DEPENDS));

		
		IModel<List<Issue>> necessaryForIssueModel = new LoadableDetachableModel<List<Issue>>(){
			@Override
			protected List<Issue> load(){
				return new ArrayList<Issue>(issueModel.getObject().getIssuesByRelationType(RelationType.NECESSARY));
			}
		};
		
		add(new IssueRelationTypePanel("necessaryForIssuePanel", issueModel, necessaryForIssueModel, RelationType.NECESSARY));
		
		IModel<List<Issue>> relatedWithIssueModel = new LoadableDetachableModel<List<Issue>>(){
			@Override
			protected List<Issue> load(){
				return new ArrayList<Issue>(issueModel.getObject().getIssuesByRelationType(RelationType.RELATED));
			}
		};
		
		add(new IssueRelationTypePanel("relatedWithIssuePanel", issueModel, relatedWithIssueModel, RelationType.RELATED));
		
		IModel<List<Issue>> duplicatedWithIssueModel = new LoadableDetachableModel<List<Issue>>(){
			@Override
			protected List<Issue> load(){
				return new ArrayList<Issue>(issueModel.getObject().getIssuesByRelationType(RelationType.DUPLICATED));
			}
		};
		
		add(new IssueRelationTypePanel("duplicatedWithIssuePanel", issueModel, duplicatedWithIssueModel, RelationType.DUPLICATED));
		
	}
}
