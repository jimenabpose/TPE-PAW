package ar.edu.itba.it.paw.services;

import java.util.List;

import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.IssueAccesses;
import ar.edu.itba.it.paw.domain.IssueFilter;
import ar.edu.itba.it.paw.domain.Job;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.Range;
import ar.edu.itba.it.paw.domain.Resolution;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.Visit;

public interface IssueServices {
	
	public void saveIssue(Issue issue);
	
	/**
	 * Este metodo es usado cuando se requiere un objeto Issue y solamente
	 * contamos con su codigo.
	 * 
	 * @param code es el codigo de la tarea
	 * @return devuelve un objeto Issue que representa a la tarea
	 */
	//public Issue getIssue(String code);
	
	public Issue getIssue(int id);

	public List<Issue> getUserIssues(User user);
	
	/*Me devuelve las tareas con estado OPEN o ONCOURSE de un usuario*/
	public List<Issue> getUserActiveIssues(User user);
	
	public List<Issue> getIssues(Project project);
	
	public void saveComment(Comment comment);
	
	public List<Comment> getIssueComments(Issue issue);
	
	public void saveJob(Job job);
	
	public List<Job> getIssueJobs(Issue issue);
	
	public List<Issue> getIssuesWithFilter(Project project, IssueFilter filter);

	public void resolveIssue(Issue issue, Resolution resolution);
	
	public void saveVisit(Visit visit);
	
	public List<IssueAccesses> getPopularIssues(Project project, int quantity, Range range);

}
