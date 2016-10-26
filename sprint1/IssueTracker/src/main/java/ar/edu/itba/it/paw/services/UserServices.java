package ar.edu.itba.it.paw.services;

import java.util.List;

import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.exceptions.RegisteredUsernameException;

public interface UserServices {

	public void saveUser(User user) throws RegisteredUsernameException;

	public User getUser(String username);
	
	public User getUser(int id);
	
	public List<User> getAllUsers();

	public boolean authenticate(String username, String password);
	
	public void asignIssue(String username, Issue issue);
}
