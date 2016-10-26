package ar.edu.itba.it.paw.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User extends PersistentAttributes{

	@Column(nullable=false, unique=true)
	private String username;
	private String firstName;
	private String lastName;
	@Column(name="pass")
	private String password;
	@Column(name="usertype")
	@Enumerated(EnumType.STRING)
	private Type type;
	private boolean active;
	
	@SuppressWarnings("unused")
	private User() {
		
	}
	
	public User(String username, String firstName, String lastName, String password, Type type, boolean active) {
		this.setUsername(username);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setPassword(password);
		this.setType(type);
		this.setActive(active);
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
	
	public Type getType(){
		return type;
	}
	
	private void setType(Type type){
		this.type = type;
	}
	
	public boolean isAdmin(){
		return type.equals(Type.ADMIN);
	}
	
	public String getCompleteName() {
		return this.firstName + " " + this.lastName;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
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
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
}
