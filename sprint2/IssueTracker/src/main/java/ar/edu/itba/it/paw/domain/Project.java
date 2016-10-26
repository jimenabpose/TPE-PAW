package ar.edu.itba.it.paw.domain;

public class Project extends PersistentAttributes {

	private String name;
	private String code;
	private String description;
	private User leader;
	private boolean isPublic;
	
	public Project(String name, String code, String description, User leader, boolean state){
		this.setName(name);
		this.setCode(code);
		this.setDescription(description);
		this.setLeader(leader);
		this.setPublic(state);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) throws IllegalArgumentException {
		if(name == null || name.equals("") || name.length() > 20){
			throw new IllegalArgumentException();
		}else{
			this.name = name;
		}
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) throws IllegalArgumentException {
		if(code == null || code.equals("") || code.length() > 10){
			throw new IllegalArgumentException();
		}else{
			this.code = code;
		}
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) throws IllegalArgumentException {
		if(description.length() > 250){
			throw new IllegalArgumentException();
		}else{
			this.description = description;
		}
	}
	
	public User getLeader() {
		return leader;
	}
	
	public void setLeader(User leader) throws IllegalArgumentException {
		if(leader == null){
			throw new IllegalArgumentException();
		}else{
			this.leader = leader;
		}
	}
	
	public boolean isPublic(){
		return isPublic;
	}
	
	public void setPublic(boolean state){
		this.isPublic = state;
	}
	
	public String getFullDescription(){
		String ans="";
		ans = this.getId() + " | Codigo: " + this.code + " | Nombre: " + this.name;
		return ans;
	}
	
	public String toString(){
		return code + " " + name + " " + this.getId();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		Project other = (Project) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}
	
}
