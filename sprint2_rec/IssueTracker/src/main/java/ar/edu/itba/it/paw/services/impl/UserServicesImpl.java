package ar.edu.itba.it.paw.services.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.it.paw.dao.IssueDAO;
import ar.edu.itba.it.paw.dao.UserDAO;
import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.Type;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.exceptions.NotDeletableUserException;
import ar.edu.itba.it.paw.domain.exceptions.RegisteredUsernameException;
import ar.edu.itba.it.paw.services.IssueServices;
import ar.edu.itba.it.paw.services.UserServices;

@Service
public class UserServicesImpl implements UserServices{
	
	private UserDAO user_dao;
	private IssueServices issue_services;
	private IssueDAO issueDAO;
	
	@Autowired
	public UserServicesImpl(UserDAO user_dao, IssueServices issue_services, IssueDAO issueDAO){
		this.user_dao = user_dao;
		this.issue_services = issue_services;
		this.issueDAO = issueDAO;
	}
	
	@Override
	public void saveUser(User user)  {
		if(user_dao.getUser(user.getUsername()) == null){
			user_dao.saveUser(user);
		}
		else{
			throw new RegisteredUsernameException();
		}
	}

	@Override
	public User getUser(String username) {
		return user_dao.getUser(username);
	}

	@Override
	public User getUser(int id) {
		return user_dao.getUser(id);
	}

	@Override
	public boolean authenticate(String username, String password) {
		User user;
		if((user = user_dao.getUser(username)) != null){
			if(!user.isActive())
				return false;
			if(user.getPassword().equals(password))
				return true;
		}
		return false;
	}

	@Override
	public void asignIssue(User user, Issue issue) {
		issue.setAssignee(user);
		issueDAO.saveIssue(issue);
	}

	@Override
	public List<User> getAllUsers() {
		return user_dao.getAllUsers();
	}
	
	@Override
	public List<User> getActiveUsers(){
		List<User> users = this.getAllUsers();
		Iterator<User> iterator = users.iterator();
		
		while(iterator.hasNext()){
			User user = (User) iterator.next();
			if(!user.isActive()){
				iterator.remove();
			}
		}
		return users;
	}
	
	@Override
	public List<User> getDeletableUsers(){
		List<User> users = this.getAllUsers();
		Iterator<User> iterator = users.iterator();
		
		while(iterator.hasNext()){
			User user = (User) iterator.next();
			List<Issue> list = issue_services.getUserActiveIssues(user);
			if(!user.isActive() || user.getType().equals(Type.ADMIN) || !list.isEmpty()){
				iterator.remove();
			}
		}
		return users;
	}
	
	@Override
	public void deleteUser(User user){
		List<Issue> list = issue_services.getUserActiveIssues(user);
		if(user.isAdmin() || !list.isEmpty())
			throw new NotDeletableUserException();
		user.setActive(false);
		user_dao.saveUser(user);
	}

	@Override
	public List<User> getUsersFromProject(Project project) {
		return user_dao.getUsersFromProject(project);
	}
	
	@Override
	public List<User> getUsersNotFromProject(Project project){
		List<User> allUsers = this.getActiveUsers();
		List<User> projectUsers = this.getUsersFromProject(project);
		
		/*Elimino los que ya están en el proyecto*/
		Iterator<User> iterator = allUsers.iterator();
		while(iterator.hasNext()){
			User user = (User) iterator.next();
			if(projectUsers.contains(user)){
				iterator.remove();
			}
		}

		return allUsers;
	}
}
