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
	public void setName(String name) {
		ValidationUtils.checkRequiredMaxText(name, 20);
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		ValidationUtils.checkRequiredMaxText(code, 10);
		this.code = code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description)  {
		ValidationUtils.checkTextMaxLength(description, 250);
		this.description = description;
	}
	
	public User getLeader() {
		return leader;
	}
	
	public void setLeader(User leader)  {
		ValidationUtils.checkNotNull(leader);
		this.leader = leader;
	}
	
	public boolean isPublic(){
		return isPublic;
	}
	
	public void setPublic(boolean state){
		this.isPublic = state;
	}

	public String getFullDescription(){
		String ans="";
		ans = this.code + " | " + this.name;
		return ans;
	}
	
	@Override
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
