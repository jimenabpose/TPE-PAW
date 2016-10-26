package ar.edu.itba.it.paw.repo.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.IssueSearchCriteria;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.State;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.repo.AbstractHibernateRepo;
import ar.edu.itba.it.paw.repo.IssueRepo;

@Repository
public class HibernateIssueRepo extends AbstractHibernateRepo implements IssueRepo{

	@Autowired
	public HibernateIssueRepo(SessionFactory sessionFactory){
		super(sessionFactory);
	}

	@Override
	public Issue getIssue(int id) {
		return get(Issue.class, id);
	}

	public List<Issue> getAllIssues() {
		return this.find("from Issue");
	}
	
	@Override
	public List<Issue> getUserIssues(User user){
		List<Issue> issues = this.getAllIssues();
		Iterator<Issue> iterator = issues.iterator();
		while(iterator.hasNext()){
			Issue issue = (Issue) iterator.next();
			if(issue.getAssignee() == null || !issue.getAssignee().equals(user)){
				iterator.remove();
			}
		}
		
		return issues;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Issue> getIssuesWithFilter(Project project, IssueSearchCriteria c) {
		Criteria crit = getSession().createCriteria(Issue.class);
		crit.add(Restrictions.sqlRestriction("projectid = ?", project.getId(),Hibernate.INTEGER));
		crit.add(Restrictions.ilike("title", c.getTitle(), MatchMode.ANYWHERE));
		crit.add(Restrictions.ilike("description", c.getDescription(), MatchMode.ANYWHERE));
		if (c.getAsignee()!=null){
			crit.add(Restrictions.eq("assignee.id",c.getAsignee().getId()));
		}
		if (c.getReporter()!=null){
			crit.add(Restrictions.eq("reporter.id",c.getReporter().getId()));
		}
		if (c.getState()!=null && !c.getState().isEmpty()){
			crit.add(Restrictions.sqlRestriction("state = ?", c.getState(), Hibernate.STRING));
		}
		if (c.getIssueType()!=null && !c.getIssueType().isEmpty()){
			crit.add(Restrictions.sqlRestriction("issueType = ?", c.getIssueType(), Hibernate.STRING));
		}
		if (c.getResolution()!=null && !c.getResolution().isEmpty()){
			crit.add(Restrictions.sqlRestriction("resolution = ?", c.getResolution(), Hibernate.STRING));
		}
		if (c.getDate_st() != null){
			String sql_st = new java.sql.Date(c.getDate_st().getTime()).toString();
			crit.add(Restrictions.sqlRestriction("creationdate >= DATE '"+sql_st+"'"));
		}
		if (c.getDate_et() != null){
			String sql_st = new java.sql.Date(c.getDate_et().getTime()).toString();
			crit.add(Restrictions.sqlRestriction("creationdate <= DATE '"+sql_st+"'"));
		}	
		List<Issue> issues = crit.list();
		
		if(issues == null){
			return issues;
		}
		
		/*filtro por codigo de issue*/
		List<Issue> issues2 = new ArrayList<Issue>();
		
		if ( !c.getCode().isEmpty()){
			for (int i=0; i< issues.size(); i++)
			  {
				  if (issues.get(i).getCode().contains(c.getCode())){
					  issues2.add(issues.get(i));
				  }
			  }
			issues=issues2;
		}
		
		if(c.getResolutionVersion() != null){
			Iterator<Issue> iterator = issues.iterator();
			
			while(iterator.hasNext()){
				Issue issue = (Issue) iterator.next();
				if(!issue.getResolutionVersions().contains(c.getResolutionVersion())){
					iterator.remove();
				}
			}
		}
		
		if(c.getAffectedVersion() != null){
			Iterator<Issue> iterator = issues.iterator();
			
			while(iterator.hasNext()){
				Issue issue = (Issue) iterator.next();
				if(!issue.getAffectedVersions().contains(c.getAffectedVersion())){
					iterator.remove();
				}
			}
		}
		
		return issues;
	}

//	@Override
//	public void saveComment(Comment comment) {
//		save(comment);
//	}
//
//	@Override
//	public void saveJob(Job job) {
//		save(job);
//	}

	@Override
	public List<Issue> getUserActiveIssues(User user){
		List<Issue> issues = this.getUserIssues(user);
		Iterator<Issue> iterator = issues.iterator();
		
		while(iterator.hasNext()){
			Issue issue = (Issue) iterator.next();
			if(!issue.getState().equals(State.OPEN) && !issue.getState().equals(State.ONCOURSE)){
				iterator.remove();
			}
		}
		
		return issues;
	}

//	@Override
//	public Version getVersion(Issue issue){
//		return this.getOne("from Version as i where i.issue = ?", issue);
//	}
	
	@Override
	public List<Issue> getUserProjectActiveIssue(User user, Project project){
		List<Issue> issues = this.getUserActiveIssues(user);
		List<Issue> projectIssues = project.getIssues();
		Iterator<Issue> iterator = issues.iterator();
		
		while(iterator.hasNext()){
			Issue issue = (Issue) iterator.next();
			if(!projectIssues.contains(issue)){
				iterator.remove();
			}
		}
		
		return issues;
	}
}
