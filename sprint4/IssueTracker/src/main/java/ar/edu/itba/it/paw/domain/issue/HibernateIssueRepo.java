package ar.edu.itba.it.paw.domain.issue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.domain.AbstractHibernateRepo;
import ar.edu.itba.it.paw.domain.project.Project;
import ar.edu.itba.it.paw.domain.user.User;

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
		crit.add(Restrictions.eq("project", project));
		if(c.getTitle() != null && !c.getTitle().isEmpty()) {
			crit.add(Restrictions.ilike("title", c.getTitle(), MatchMode.ANYWHERE));
		}
		if(c.getDescription() != null && !c.getDescription().isEmpty()) {
			crit.add(Restrictions.ilike("description", c.getDescription(), MatchMode.ANYWHERE));
		}
		if (c.getAsignee()!=null){
			crit.add(Restrictions.eq("assignee",c.getAsignee()));
		}
		if (c.getReporter()!=null){
			crit.add(Restrictions.eq("reporter",c.getReporter()));
		}
		if (c.getState()!=null){
			crit.add(Restrictions.eq("state", c.getState()));
		}
		if (c.getIssueType()!=null){
			crit.add(Restrictions.eq("issueType", c.getIssueType()));
		}
		if (c.getResolution()!=null){
			crit.add(Restrictions.eq("resolution", c.getResolution()));
		}
		if (c.getDate_st() != null){
			crit.add(Restrictions.ge("creationdate", c.getDate_st()));
		}
		if (c.getDate_et() != null){
			crit.add(Restrictions.le("creationdate", c.getDate_et()));
		}	
		
		List<Issue> issues = crit.list();
		
		if(issues == null){
			return issues;
		}
		
		/*filtro por codigo de issue*/
		List<Issue> issues2 = new ArrayList<Issue>();
		
		if ( c.getCode() != null && !c.getCode().isEmpty()){
			for (int i=0; i< issues.size(); i++)
			  {
				  if (issues.get(i).getCode().toUpperCase().contains(c.getCode().toUpperCase())){
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
