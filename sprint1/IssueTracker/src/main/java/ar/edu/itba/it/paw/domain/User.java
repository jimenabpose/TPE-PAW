package ar.edu.itba.it.paw.domain;

public class User extends PersistentAttributes{
	
	private String username;
	private String firstName;
	private String lastName;
	private String password;
	private Type type;

	public User(String username, String firstName, String lastName, String password, Type type) throws IllegalArgumentException{
		this.setUsername(username);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setPassword(password);
		this.setType(type);
	}
	
	public String getUsername(){
		return username;
	}
	
	private void setUsername(String username){
		if(username == null || username.equals("") || username.length() > 50){
			throw new IllegalArgumentException();
		}else{
			this.username = username;
		}
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	private void setFirstName(String firstName){
		if(firstName == null || firstName.equals("") || firstName.length() > 50){
			throw new IllegalArgumentException();
		}else{
			this.firstName = firstName;
		}
	}
	
	public String getLastName(){
		return lastName;
	}
	
	private void setLastName(String lastName) {
		if(lastName == null || lastName.equals("") || lastName.length() > 50){
			throw new IllegalArgumentException();
		}else{
			this.lastName = lastName;
		}
	}
	
	public String getPassword(){
		return password;
	}

	private void setPassword(String password){
		if(password == null || password.equals("") || password.length() > 10){
			throw new IllegalArgumentException();
		} else{
			this.password = password;
		}
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
