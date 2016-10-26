package ar.edu.itba.it.paw.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.it.paw.dao.IssueDAO;
import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.Job;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.Resolution;
import ar.edu.itba.it.paw.domain.State;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.services.IssueServices;
import ar.edu.itba.it.paw.web.command.issueFilter;

@Service
public class IssueServicesImpl implements IssueServices {
	
	private IssueDAO issueDAO;
	
	@Autowired
	public IssueServicesImpl(IssueDAO issue_dao){
		this.issueDAO = issue_dao;
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
		List<Issue> issues = issueDAO.getProjectIssues(project);
		return issues;
	}
	
	@Override
	public List<Comment> getIssueComments(Issue issue) {
		return issueDAO.getIssueComments(issue);
	}

	@Override
	public void saveComment(Comment comment) {
		issueDAO.saveComment(comment);
	}
	
	@Override
	public List<Job> getIssueJobs(Issue issue) {
		return issueDAO.getIssueJobs(issue);
	}

	@Override
	public void saveJob(Job job) {
		issueDAO.saveJob(job);
	}
	
	@Override
	public List<Issue> getIssuesWithFilter(Project project, issueFilter filter){
		List<Issue> issues = issueDAO.getProjectIssuesWithFilter(project, filter);
		
		if(issues == null){
			return issues;
		}
		List<Issue> issues2 = new ArrayList<Issue>();
		
		if ( !filter.getCode().isEmpty()){
			for (int i=0; i< issues.size(); i++)
			  {
				  if (issues.get(i).getCode().contains(filter.getCode())){
					  issues2.add(issues.get(i));
				  }
			  }
			issues=issues2;
		}
		return issues;
	}
	
	public List<String> getStates(){
		List <String> states = new ArrayList<String>();
		states.add("ANY");
		states.add(State.OPEN.toString());
		states.add(State.ONCOURSE.toString());
		states.add(State.FINISHED.toString());
		states.add(State.CLOSED.toString());

		return states;
	}
	
	public List<String> getResolutions(){
		List <String> resolutions = new ArrayList<String>();
		resolutions.add("ANY");
		resolutions.add(Resolution.DUPLICATED.toString());
		resolutions.add(Resolution.INCOMPLETE.toString());
		resolutions.add(Resolution.IRREPRODUCIBLE.toString());
		resolutions.add(Resolution.SOLVED.toString());
		resolutions.add(Resolution.WONTSOLVE.toString());

		return resolutions;
	}
}
