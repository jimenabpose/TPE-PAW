package ar.edu.itba.it.paw.web.converter;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.services.UserServices;


public class UserFormatter implements Formatter<User> {

	private UserServices service;

	@Autowired
	public UserFormatter(UserServices service) {
		this.service = service;
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
		User user = service.getUser(id);
		return user;
	}
}
