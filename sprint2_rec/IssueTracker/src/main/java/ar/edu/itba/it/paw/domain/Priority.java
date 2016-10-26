package ar.edu.itba.it.paw.domain;


public enum Priority {
	TRIVIAL("Trivial"), LOW("Baja"), NORMAL("Normal"), HIGH("Alta"), CRITICAL("Crítica");
	
	private String name;
	
	Priority(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription(){
		return this.name();
	}
}
