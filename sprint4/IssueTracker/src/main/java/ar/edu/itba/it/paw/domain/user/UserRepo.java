package ar.edu.itba.it.paw.domain.user;

import java.util.List;

import ar.edu.itba.it.paw.domain.project.Project;

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
