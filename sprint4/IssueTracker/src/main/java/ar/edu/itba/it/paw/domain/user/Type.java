package ar.edu.itba.it.paw.domain.user;

public enum Type {
	ADMIN("Administrador"), REGULAR("Regular");
	
	private String name;
	
	Type(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public String getDescription(){
		return this.name();
	}
	
}
