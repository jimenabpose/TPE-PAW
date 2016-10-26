package ar.edu.itba.it.paw.domain.user;

import java.util.List;


public interface UserSolicitationRepo {
	
	public void saveSolicitation(UserSolicitation userSolicitation);
	
	public List<UserSolicitation> getAllSolicitations();
	
	public void deleteSolicitation(UserSolicitation userSolicitation);
	
	public UserSolicitation getSolicitation(int id);
}
