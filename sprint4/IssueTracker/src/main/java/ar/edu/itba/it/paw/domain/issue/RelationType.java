package ar.edu.itba.it.paw.domain.issue;

public enum RelationType {
	DEPENDS("Depende de"), NECESSARY("Es necesaria para"), RELATED("Esta relacionada con"), DUPLICATED("Esta duplicada con");

	private String name;
	
	RelationType(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription(){
		return this.name();
	}
}
