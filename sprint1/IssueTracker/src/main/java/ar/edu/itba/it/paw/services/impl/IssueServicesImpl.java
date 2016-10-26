package ar.edu.itba.it.paw.services.impl;

import java.util.Iterator;
import java.util.List;

import ar.edu.itba.it.paw.dao.IssueDAO;
import ar.edu.itba.it.paw.dao.impl.DatabaseIssueDAO;
import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.State;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.services.IssueServices;

public class IssueServicesImpl implements IssueServices {

	private static IssueServices instance;
	private static IssueDAO issueDAO;
	
	public static synchronized IssueServices getInstance(){
		if(instance == null){
			instance = new IssueServicesImpl();
			issueDAO = DatabaseIssueDAO.getInstance();
		}
		return instance;
	}
	
	@Override
	public void saveIssue(Issue issue) {
			issueDAO.saveIssue(issue);
	}


	@Override
	public Issue getIssue(int id) {
		return issueDAO.getIssue(id);
	}

	public List<Issue> getUserActiveIssues(User user) {
		List<Issue> issues = issueDAO.getUserIssues(user);
		Iterator<Issue> iterator = issues.iterator();
		
		while(iterator.hasNext()){
			Issue issue = (Issue) iterator.next();
			if(!issue.getState().equals(State.OPEN) && !issue.getState().equals(State.ONCOURSE)){
				iterator.remove();
			}
		}
		return issues;
	}
	
	public List<Issue> getIssues(Project project){
		return issueDAO.getProjectIssues(project);
	}
}
