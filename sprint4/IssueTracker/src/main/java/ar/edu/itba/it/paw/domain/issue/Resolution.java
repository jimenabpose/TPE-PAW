package ar.edu.itba.it.paw.domain.issue;

public enum Resolution {
	SOLVED("Resuelta"), WONTSOLVE("No resuelve"), IRREPRODUCIBLE("Irreproducible"), DUPLICATED("Duplicada"), INCOMPLETE("Incompleta");
	
	private String name;
	
	Resolution(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription(){
		return this.name();
	}
}
