package ar.edu.itba.it.paw.services.impl;

import java.util.List;

import ar.edu.itba.it.paw.dao.UserDAO;
import ar.edu.itba.it.paw.dao.impl.DatabaseUserDAO;
import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.exceptions.RegisteredUsernameException;
import ar.edu.itba.it.paw.services.UserServices;

public class UserServicesImpl implements UserServices{

	private static UserServices instance;
	private static UserDAO user_dao;
	
	public static synchronized UserServices getInstance(){
		if(instance == null){
			instance = new UserServicesImpl();
			user_dao = DatabaseUserDAO.getInstance();
		}
		return instance;
	}
	
	@Override
	public void saveUser(User user) throws RegisteredUsernameException {
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
		if((user = user_dao.getUser(username)) != null)
			if(user.getPassword().equals(password))
				return true;
		return false;
	}

	@Override
	public void asignIssue(String username, Issue issue) {
		user_dao.asignIssue(username,issue);
	}

	@Override
	public List<User> getAllUsers() {
		return user_dao.getAllUsers();
	}
}
