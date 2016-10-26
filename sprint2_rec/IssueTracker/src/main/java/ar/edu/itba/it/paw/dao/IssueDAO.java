package ar.edu.itba.it.paw.dao;

import java.util.List;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.IssueFilter;
import ar.edu.itba.it.paw.domain.Job;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.Visit;

public interface IssueDAO {

	public void saveIssue(Issue issue);

	public Issue getIssue(int id);
	
	public List<Issue> getProjectIssues(Project project);
	
	public List<Issue> getProjectIssuesWithFilter(Project project, IssueFilter filter);
	
	/*Me devuelve las tareas que tiene asignado un determinado usuario*/
	public List<Issue> getUserIssues(User user);
	
	public void saveJob(Job job);
	
	public List<Job> getIssueJobs(Issue issue);
	
	public void saveComment(Comment comment);
	
	public List<Comment> getIssueComments(Issue issue);
	
	
	
//	public List<Visit> getPopularIssues(Project project, int quantity);
	
//	public void addAccessToIssue(Issue issue);
	
	public void saveVisit(Visit visit);

	List<Visit> getProjectVisits(Project project);

//	Issue getAssociatedIssue(Visit visit);
}
