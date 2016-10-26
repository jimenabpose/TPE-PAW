package ar.edu.itba.it.paw.repo;

import java.util.List;

import ar.edu.itba.it.paw.domain.UserSolicitation;

public interface UserSolicitationRepo {
	
	public void saveSolicitation(UserSolicitation userSolicitation);
	
	public List<UserSolicitation> getAllSolicitations();
	
	public void deleteSolicitation(UserSolicitation userSolicitation);
	
	public UserSolicitation getSolicitation(int id);
}
