package ar.edu.itba.it.paw.dao;

import ar.edu.itba.it.paw.domain.Issue;
import java.util.List;

import ar.edu.itba.it.paw.domain.User;

public interface UserDAO {

	public User getUser(String username);
	
	public User getUser(int id);
	
	public void saveUser(User user);
	
	public void asignIssue(String username, Issue issue);
	
	public List<User> getAllUsers();

}
