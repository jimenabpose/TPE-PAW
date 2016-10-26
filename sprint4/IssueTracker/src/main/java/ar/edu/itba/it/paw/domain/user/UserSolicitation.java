package ar.edu.itba.it.paw.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;

import ar.edu.itba.it.paw.domain.PersistentAttributes;
import ar.edu.itba.it.paw.domain.ValidationUtils;

@Entity
public class UserSolicitation extends PersistentAttributes{

	@Column(nullable=false)
	private String username;
	@Column(nullable=false)
	private String firstName;
	@Column(nullable=false)
	private String lastName;
	@Column(name="pass", nullable=false)
	private String password;
	
	@SuppressWarnings("unused")
	private UserSolicitation() {
		
	}
	
	public UserSolicitation(String username, String firstName, String lastName, String password) {
		this.setUsername(username);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setPassword(password);
	}
	
	public String getUsername(){
		return username;
	}
	
	private void setUsername(String username){
		ValidationUtils.checkRequiredMaxText(username, 50);
		this.username = username;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	private void setFirstName(String firstName){
		ValidationUtils.checkRequiredMaxText(firstName, 50);
		this.firstName = firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	private void setLastName(String lastName) {
		ValidationUtils.checkRequiredMaxText(lastName, 50);
		this.lastName = lastName;
	}
	
	public String getPassword(){
		return password;
	}

	private void setPassword(String password){
		ValidationUtils.checkRequiredMaxText(password, 10);
		this.password = password;
	}
	
	public User getUser() {
		return new User(this.username, this.firstName, this.lastName,
				this.password, Type.REGULAR, true);
	}
	
	@Override
	public String toString(){
		return username + " " + this.getId();
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserSolicitation other = (UserSolicitation) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
}
