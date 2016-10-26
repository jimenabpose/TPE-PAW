package ar.edu.itba.it.paw.dao;

import java.util.List;

import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.User;

public interface IssueDAO {

	public void saveIssue(Issue issue);

	public Issue getIssue(int id);
	
	public List<Issue> getProjectIssues(Project project);
	
	public List<Issue> getUserIssues(User user);
	
}
