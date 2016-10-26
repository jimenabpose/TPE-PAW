package ar.edu.itba.it.paw.domain.project;

public enum VersionState {
	OPEN("Abierta"), ONCOURSE("En curso"), RELEASED("Liberada");
	
	private String name;
	
	VersionState(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription(){
		return this.name();
	}
}
