package ar.edu.itba.it.paw.web.converter;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import ar.edu.itba.it.paw.domain.UserSolicitation;
import ar.edu.itba.it.paw.repo.UserSolicitationRepo;


public class UserSolicitationFormatter implements Formatter<UserSolicitation> {

	private UserSolicitationRepo userSolicitationRepo;

	@Autowired
	public UserSolicitationFormatter(UserSolicitationRepo userSolicitationRepo) {
		this.userSolicitationRepo = userSolicitationRepo;
	}

	@Override
	public String print(UserSolicitation arg0, Locale arg1) {
		if(arg0 == null) {
			return "";
		}
		return arg0.getId() + "";
	}

	@Override
	public UserSolicitation parse(String arg0, Locale arg1) {
		Integer id = Integer.valueOf(arg0);
		UserSolicitation solicitation = userSolicitationRepo.getSolicitation(id);
		return solicitation;
	}
}
