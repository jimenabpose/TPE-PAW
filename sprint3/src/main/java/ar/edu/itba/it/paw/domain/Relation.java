package ar.edu.itba.it.paw.domain;

public enum Relation {
	DEPENDS("Depende de"), NECESSARY("Es necesaria para"), RELATED("Esta relacionada con"), DUPLICATED("Esta duplicada con");

	private String name;
	
	Relation(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription(){
		return this.name();
	}
}
