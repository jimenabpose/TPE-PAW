package ar.edu.itba.it.paw.domain.user;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.it.paw.domain.AbstractHibernateRepo;

@Repository
public class HibernateUserSolicitationRepo extends AbstractHibernateRepo implements UserSolicitationRepo{

	
	@Autowired
	public HibernateUserSolicitationRepo(SessionFactory sessionFactory){
		super(sessionFactory);
	}
	
	@Override
	public void saveSolicitation(UserSolicitation userSolicitation) {
		this.save(userSolicitation);
	}

	@Override
	public List<UserSolicitation> getAllSolicitations() {
		return this.find("from UserSolicitation");
	}

	@Override
	public void deleteSolicitation(UserSolicitation userSolicitation) {
		delete(userSolicitation);
	}
	
	@Override
	public UserSolicitation getSolicitation(int id) {
		return this.get(UserSolicitation.class, id);
	}
	
	
}
