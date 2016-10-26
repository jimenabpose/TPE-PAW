package ar.edu.itba.it.paw.domain;

public enum State {
	OPEN("Abierto"), ONCOURSE("En curso"), FINISHED("Finalizada"), CLOSED("Cerrada");
	
	private String name;
	
	State(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription(){
		return this.name();
	}
}
