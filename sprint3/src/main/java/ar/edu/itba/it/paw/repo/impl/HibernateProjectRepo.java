package ar.edu.itba.it.paw.repo.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.domain.IssueChanges;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.Version;
import ar.edu.itba.it.paw.domain.exceptions.ProjectCodeRepeatedException;
import ar.edu.itba.it.paw.domain.exceptions.ProjectNameRepeatedException;
import ar.edu.itba.it.paw.repo.AbstractHibernateRepo;
import ar.edu.itba.it.paw.repo.ProjectRepo;

@Repository
public class HibernateProjectRepo extends AbstractHibernateRepo implements ProjectRepo {

	@Autowired
	public HibernateProjectRepo(SessionFactory sessionFactory){
		super(sessionFactory);
	}
	
	@Override
	public List<Project> getAllProjects() {
		return find("from Project");
	}
	
	@Override
	public List<Project> getUserProjects(User user){
		List<Project> projects = this.getAllProjects();
		
		Iterator<Project> iterator = projects.iterator();
		
		while(iterator.hasNext()){
			Project project = (Project) iterator.next();
			if(!project.getUsers().contains(user)){
				iterator.remove();
			}
		}
		
		return projects;
	}

	@Override
	public Project getProjectByCode(String code) {
		return this.getOne("from Project p where p.code = ?", code);
	}

	@Override
	public Project getProjectById(int id) {
		return get(Project.class, id);
	}

	@Override
	public Project getProjectByName(String name) {
		return getOne("from Project p where p.name = ?", name);
	}

	@Override
	public void saveProject(Project project) {
		
		Project p;
		
		p = this.getProjectByCode(project.getCode());
		if(p != null && p.getId() != project.getId()) {
			throw new ProjectCodeRepeatedException();
		}
		
		p = this.getProjectByName(project.getName());
		if(p != null && p.getId() != project.getId()) {
			throw new ProjectNameRepeatedException();
		}
		
		save(project);
	}
	
	@Override
	public List<Project> getPublicProjects(){
		List<Project> projects = this.getAllProjects();
		
		Iterator<Project> iterator = projects.iterator();
		
		while(iterator.hasNext()){
			Project project = (Project) iterator.next();
			if(!project.isPublic()){
				iterator.remove();
			}
		}
		return projects;
	}

	@Override
	public Version getVersionById(int id) {
		return get(Version.class, id);
	}
	
	@Override
	public List<IssueChanges> getLastFiveIssueChanges(Project project) {
		String query = "from IssueChanges where issue.project.id = "
			+ project.getId() + " order by changeDate desc";

		List<IssueChanges> list = null;
		list = find(query);
		
		List<IssueChanges> lastFive = new ArrayList<IssueChanges>();
		
		for(int i = 0 ; i < 5 && i < list.size(); i++) {
			lastFive.add(list.get(i));
		}
		return lastFive;
	}

}
