package ar.edu.itba.it.paw.domain.issue;

public enum IssueType {
	ERROR("Error"), NEWCHAR("Nueva característica"), IMPROVEMENT("Mejora") ,
	TECHNIQUE("Técnica"), ISSUE("Tarea");
	
	private String name;
	
	IssueType(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public String getDescription(){
		return this.name();
	}
	
}
