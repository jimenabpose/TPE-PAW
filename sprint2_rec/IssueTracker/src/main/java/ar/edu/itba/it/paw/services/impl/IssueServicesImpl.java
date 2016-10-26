package ar.edu.itba.it.paw.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.it.paw.dao.IssueDAO;
import ar.edu.itba.it.paw.domain.Comment;
import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.IssueAccesses;
import ar.edu.itba.it.paw.domain.IssueFilter;
import ar.edu.itba.it.paw.domain.Job;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.Range;
import ar.edu.itba.it.paw.domain.Resolution;
import ar.edu.itba.it.paw.domain.State;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.Visit;
import ar.edu.itba.it.paw.domain.exceptions.IssueNameRepeatedException;
import ar.edu.itba.it.paw.services.IssueServices;

@Service
public class IssueServicesImpl implements IssueServices {
	
	private IssueDAO issueDAO;
	
	@Autowired
	public IssueServicesImpl(IssueDAO issue_dao){
		this.issueDAO = issue_dao;
	}
	
	@Override
	public void saveIssue(Issue issue) {
		List<Issue> issues = issueDAO.getProjectIssues(issue.getProject());
		
		for(Issue i : issues) {
			if(i.getId() != issue.getId() && i.getTitle().equals(issue.getTitle())) {
				throw new IssueNameRepeatedException();
			}
		}
		
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
	
	public List<Issue> getUserIssues(User user) {
		return issueDAO.getUserIssues(user);
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
	public List<Issue> getIssuesWithFilter(Project project, IssueFilter filter){
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
		
	@Override
	public void resolveIssue(Issue issue, Resolution resolution){
		issue.setResolution(resolution);
		issue.setState(State.FINISHED);
		issueDAO.saveIssue(issue);
	}
	
	@Override
	public void saveVisit(Visit visit) {
		issueDAO.saveVisit(visit);
	}

	private List<Visit> filterVisits(List<Visit> visits, Range range){
		List<Visit> ans = new ArrayList<Visit>();
		if(range.equals(Range.ALL)){
			return visits;
		}
		else if(range.equals(Range.LASTMONTH)){
			LocalDate currentDate = new LocalDate();
			int month = currentDate.getMonthOfYear();
			int year = currentDate.getYear();
			for(int i=0; i<visits.size(); i++){
				if(visits.get(i).getDate().getMonthOfYear() == month &&
						visits.get(i).getDate().getYear() == year){
					ans.add(visits.get(i));
				}
			}
		}
		else if(range.equals(Range.LASTWEEK)){
			LocalDate currentDate = new LocalDate();
			int week = currentDate.getWeekOfWeekyear();
			int year = currentDate.getYear();
			for(int i=0; i<visits.size(); i++){
				if(visits.get(i).getDate().getWeekOfWeekyear() == week &&
						visits.get(i).getDate().getYear() == year){
					ans.add(visits.get(i));
				}
			}
		}
		return ans;
	}
	
	private List<IssueAccesses> getMostVisited(List<IssueAccesses> issueacc, int quantity){
		Collections.sort(issueacc);
		List<IssueAccesses> ans = new ArrayList<IssueAccesses>();
		
		for(int i=0; i<quantity && i<issueacc.size(); i++){
			ans.add(issueacc.get(i));
		}
		
		return ans;
	}
	
	@Override
	public List<IssueAccesses> getPopularIssues(Project project, int quantity, Range range) {
		List<Visit> visits = issueDAO.getProjectVisits(project);
		List<Visit> filtered = this.filterVisits(visits, range);
		
		List<IssueAccesses> ans = new ArrayList<IssueAccesses>();
		
		for(int i=0; i<filtered.size(); i++){
			int found = 0;
			Issue issue = filtered.get(i).getIssue();
			for(int j=0; j<ans.size(); j++){
				if(ans.get(j).getIssue().equals(issue)){
					ans.get(j).incQuantity();
					found = 1;
				}
			}
			if(found == 0){
				ans.add(new IssueAccesses(issue, 1));
			}
		}
		
		return this.getMostVisited(ans, quantity);
	}
}
