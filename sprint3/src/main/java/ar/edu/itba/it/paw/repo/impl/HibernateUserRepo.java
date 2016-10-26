package ar.edu.itba.it.paw.repo.impl;

import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.Type;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.exceptions.NotDeletableUserException;
import ar.edu.itba.it.paw.domain.exceptions.RegisteredUsernameException;
import ar.edu.itba.it.paw.repo.AbstractHibernateRepo;
import ar.edu.itba.it.paw.repo.IssueRepo;
import ar.edu.itba.it.paw.repo.UserRepo;

@Repository
public class HibernateUserRepo extends AbstractHibernateRepo implements UserRepo{

	private IssueRepo issueRepo;
	
	@Autowired
	public HibernateUserRepo(SessionFactory sessionFactory, IssueRepo issueRepo){
		super(sessionFactory);
		this.issueRepo = issueRepo;
	}
	
	@Override
	public void saveUser(User user) {
		if(this.getUser(user.getUsername()) == null){
			this.save(user);
		}
		else{
			throw new RegisteredUsernameException();
		}
	}

	@Override
	public User getUser(String username) {
		return this.getOne("from User as u where u.username = ?", username);
	}

	@Override
	public User getUser(int id) {
		return this.get(User.class, id);
	}

	@Override
	public List<User> getAllUsers() {
		return this.find("from User");
	}

	@Override
	public List<User> getActiveUsers() {
		return this.find("from User as u where u.active = true");
	}

	@Override
	public List<User> getDeletableUsers() {
		
		List<User> users = this.getAllUsers();
		Iterator<User> iterator = users.iterator();
		
		while(iterator.hasNext()){
			User user = (User) iterator.next();
			List<Issue> list = issueRepo.getUserActiveIssues(user);
			if(!user.isActive() || user.getType().equals(Type.ADMIN) || !list.isEmpty()){
				iterator.remove();
			}
		}
		return users;
	}

	@Override
	public boolean authenticate(String username, String password) {
		User user;
		if((user = this.getUser(username)) != null){
			if(!user.isActive())
				return false;
			if(user.getPassword().equals(password))
				return true;
		}
		return false;
	}
	
	@Override
	public void deleteUser(User user) {
		List<Issue> list = issueRepo.getUserActiveIssues(user);
		if(user.isAdmin() || !list.isEmpty())
			throw new NotDeletableUserException();
		user.setActive(false);
		this.save(user);
	}

	@Override
	public List<User> getActiveUsersNotFromProject(Project project) {
		List<User> users = this.getActiveUsers();
		users.removeAll(project.getUsers());
		return users;
	}
	
}
