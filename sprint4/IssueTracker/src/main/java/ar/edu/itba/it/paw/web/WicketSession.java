package ar.edu.itba.it.paw.web;

import org.apache.wicket.Request;
import org.apache.wicket.Session;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebSession;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.project.Project;
import ar.edu.itba.it.paw.domain.user.User;
import ar.edu.itba.it.paw.domain.user.UserRepo;

public class WicketSession extends WebSession {

	private IModel<User> user = new EntityModel<User>(User.class);
	private IModel<Project> project = new EntityModel<Project>(Project.class);

	public static WicketSession get() {
		return (WicketSession) Session.get();
	}

	public WicketSession(Request request) {
		super(request);
	}

	public boolean signIn(String username, String password, UserRepo users) {
		User user = users.getUser(username);
		if (user != null && user.isActive() && user.getPassword().equals(password)) {
			this.user.setObject(user);
			return true;
		}
		return false;
	}

	public boolean isSignedIn() {
		return user.getObject() != null;
	}

	public void signOut() {
        Session.get().invalidate();
        clear();
	}
	
	public User getUser() {
		return user.getObject();
	}
	
	public Project getProject() {
		return project.getObject();
	}
	
	public void setProject(Project project) {
		this.project.setObject(project);
	}
	
	public void setUser(User user) {
		this.user.setObject(user);
	}

	public IModel<User> getUserModel() {
		return this.user;
	}
	
	public IModel<Project> getProjectModel() {
		return this.project;
	}
	
	@Override
	protected void detach() {
		super.detach();
		user.detach();
		project.detach();
	}
	
}
