package ar.edu.itba.it.paw.web.issue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import ar.edu.itba.it.paw.domain.issue.IssueType;
import ar.edu.itba.it.paw.domain.issue.Priority;
import ar.edu.itba.it.paw.domain.project.Project;
import ar.edu.itba.it.paw.domain.project.Version;
import ar.edu.itba.it.paw.domain.user.User;
import ar.edu.itba.it.paw.web.WicketSession;

public class IssueInfoPanel extends Panel{

	public IssueInfoPanel(String id){
		super(id);
		
		add(new TextField<String>("title").setRequired(true));
		add(new TextField<String>("description"));
		add(new TextField<Float>("estimatedTime").setRequired(false).add(new IValidator<Float>() {
			@Override
			public void validate(IValidatable<Float> validatable) {
				try{
					if(validatable.getValue() != null && validatable.getValue() < 0) {
						validatable.error(new ValidationError().addMessageKey("invalidNumber"));
					}
				}catch(NumberFormatException e){
					validatable.error(new ValidationError().addMessageKey("invalidInput"));
				}
			}
		}));
		
		IModel<List<User>> usersModel = new LoadableDetachableModel<List<User>>() {
			WicketSession session = WicketSession.get();
			@Override
			protected List<User> load() {
				Project project = session.getProject();
				return new ArrayList<User>(project.getUsers()); 
			}
		};
		
		add(new DropDownChoice<User>("assignee", usersModel).setNullValid(true));
		
		IModel<List<Priority>> priorityModel = new LoadableDetachableModel<List<Priority>>(){
			@Override
			protected List<Priority> load() {
				return Arrays.asList(Priority.values());
			}
		};
		
		add(new DropDownChoice<Priority>("priority", priorityModel).setRequired(true));
		
		IModel<List<IssueType>> issueTypeModel = new LoadableDetachableModel<List<IssueType>>(){
			@Override
			protected List<IssueType> load() {
				return Arrays.asList(IssueType.values());
			}
		};
		
		add(new DropDownChoice<IssueType>("issueType", issueTypeModel).setNullValid(true));
		
		IModel<List<Version>> versionsModel = new LoadableDetachableModel<List<Version>>(){
			WicketSession session = WicketSession.get();
			@Override
			protected List<Version> load() {
				Project project = session.getProject();
				return new ArrayList<Version>(project.getVersions());
			}
		};
	
		add(new ListMultipleChoice<Version>("resolutionVersions", versionsModel));
		add(new ListMultipleChoice<Version>("affectedVersions", versionsModel));
	}
}
