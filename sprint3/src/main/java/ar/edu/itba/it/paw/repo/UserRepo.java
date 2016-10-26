package ar.edu.itba.it.paw.repo;

import java.util.List;

import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.User;

public interface UserRepo {
	
	public void saveUser(User user);

	public User getUser(String username);
	
	public User getUser(int id);
	
	public List<User> getAllUsers();
	
	public List<User> getActiveUsers();
	
	public List<User> getDeletableUsers();

	public boolean authenticate(String username, String password);
	
	public void deleteUser(User user);
	
	public List<User> getActiveUsersNotFromProject(Project project);
	
}
