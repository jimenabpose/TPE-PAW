package ar.edu.itba.it.paw.services;

import java.util.List;

import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.User;

public interface UserServices {

	public void saveUser(User user);

	public User getUser(String username);
	
	public User getUser(int id);
	
	public List<User> getAllUsers();
	
	public List<User> getActiveUsers();
	
	public List<User> getDeletableUsers();

	public boolean authenticate(String username, String password);
	
	public void asignIssue(User user, Issue issue);
	
	public void deleteUser(User user);
	
	public List<User> getUsersFromProject(Project project);
	
	public List<User> getUsersNotFromProject(Project project);
}
