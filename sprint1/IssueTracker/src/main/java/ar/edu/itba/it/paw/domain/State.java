package ar.edu.itba.it.paw.domain;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum State {
	OPEN("Abierta"), ONCOURSE("En curso"), FINISHED("Finalizada"), CLOSED("Cerrada");
	
	private static final Map<String,State> lookup 
    = new HashMap<String,State>();
	
    static {
        for(State s : EnumSet.allOf(State.class))
             lookup.put(s.toString(), s);
   }
	
	private String spanish;
	
	State(String spanish){
		this.spanish = spanish;
	}
	
	public String toString(){
		return this.spanish;
	}
	
	public static State getEnum(String name){
		return lookup.get(name);
	}

}
