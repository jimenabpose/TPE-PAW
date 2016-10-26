package ar.edu.itba.it.paw.domain;


public abstract class PersistentAttributes {

	private int id = -1;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
}
