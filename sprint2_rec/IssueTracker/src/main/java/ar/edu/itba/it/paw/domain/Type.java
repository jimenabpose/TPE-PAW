package ar.edu.itba.it.paw.domain;

public enum Type {
	ADMIN("Administrador"), REGULAR("Regular");
	
	private String name;
	
	Type(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
