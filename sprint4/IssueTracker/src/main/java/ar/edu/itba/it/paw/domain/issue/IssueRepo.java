package ar.edu.itba.it.paw.domain.issue;

import java.util.List;

import ar.edu.itba.it.paw.domain.project.Project;
import ar.edu.itba.it.paw.domain.user.User;

public interface IssueRepo {
	
	public Issue getIssue(int id);

	public List<Issue> getIssuesWithFilter(Project project, IssueSearchCriteria criteria);
	
	public List<Issue> getAllIssues();
	
//	public Version getVersion(Issue issue);
	
	/*Devuelve las issues que tiene asignadas un usuario*/
	public List<Issue> getUserIssues(User user);
	
	/*Devuelve las issues activas que tiene asignadas un usuario*/
	public List<Issue> getUserActiveIssues(User user);
	
	/*Devuelve las las issues activas relativas a un proyecto que tiene asignadas un usuario*/
	public List<Issue> getUserProjectActiveIssue(User user, Project project);
}
