package ar.edu.itba.it.paw.web.command;

import ar.edu.itba.it.paw.domain.Type;
import ar.edu.itba.it.paw.domain.User;

public class RegisterUserForm {

	private String username;
	private String firstName;
	private String lastName;
	private String password;
	private String rep_password;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public void setRep_password(String rep_password) {
		this.rep_password = rep_password;
	}
	public String getRep_password() {
		return rep_password;
	}
	
	public User getUser() {
		return new User(this.getUsername(), this.getFirstName(), this.getLastName(), 
				this.getPassword(), Type.REGULAR, true);
	}
}
