package ar.edu.itba.it.paw.domain;

public enum Range {
	ALL("Todas"), LASTMONTH("Ultimo mes"), LASTWEEK("Ultima semana");

	private String name;

	Range(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return this.name();
	}
}
