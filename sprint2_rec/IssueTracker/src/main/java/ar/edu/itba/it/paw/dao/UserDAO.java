package ar.edu.itba.it.paw.dao;

import java.util.List;

import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.User;

public interface UserDAO {

	public User getUser(String username);
	
	public User getUser(int id);
	
	public void saveUser(User user);
	
	public List<User> getAllUsers();

	public List<User> getUsersFromProject(Project project);

}
