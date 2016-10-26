package ar.edu.itba.it.paw.web.converter;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.repo.UserRepo;


public class UserFormatter implements Formatter<User> {

	private UserRepo userRepo;

	@Autowired
	public UserFormatter(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public String print(User arg0, Locale arg1) {
		if(arg0 == null) {
			return "";
		}
		return arg0.getId() + "";
	}

	@Override
	public User parse(String arg0, Locale arg1) {
		Integer id = Integer.valueOf(arg0);
		User user;
		user = userRepo.getUser(id);
		return user;
	}
}
